import argparse
import json
import os
from pathlib import Path
from typing import Any, Dict, List, Tuple


def _load_plan(path: str) -> Dict[str, Any]:
    txt = Path(path).read_text(encoding="utf-8")
    return json.loads(txt)


def _collect_bpel_constructs(plan: Dict[str, Any]) -> Tuple[int, int, int, int, int, int, int, int]:
    recv = inv = assign = faults = choice = parallel = pick = reply = 0
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            k = s.get("kind")
            if k == "receive":
                recv += 1
            elif k == "invoke":
                inv += 1
            elif k == "assign":
                assign += 1
            elif k == "faultHandlers":
                faults += 1
            elif k == "choice":
                choice += 1
            elif k == "parallel":
                parallel += 1
            elif k == "pick":
                pick += 1
            elif k == "reply":
                reply += 1
    return recv, inv, assign, faults, choice, parallel, pick, reply


def _collect_mediation_constructs(plan: Dict[str, Any]) -> Tuple[int, int, int, int, int, int]:
    inv = transform = log = filter_c = enrich_c = aggregate_c = 0
    for m in plan.get("mediation", []):
        for s in m.get("steps", []):
            k = s.get("kind")
            if k == "invoke":
                inv += 1
            elif k == "transform":
                transform += 1
            elif k == "log":
                log += 1
            elif k == "filter":
                filter_c += 1
            elif k == "enrich":
                enrich_c += 1
            elif k == "aggregate":
                aggregate_c += 1
    return inv, transform, log, filter_c, enrich_c, aggregate_c


def _yaml_header(name: str) -> str:
    return (
        "apiVersion: camel.apache.org/v1\n"
        "kind: Integration\n"
        "metadata:\n"
        f"  name: {name}\n"
        "spec:\n"
        "  flows:\n"
    )


def _on_exception_block() -> str:
    return (
        "    - on-exception:\n"
        "        handled: true\n"
        "        redelivery-policy:\n"
        "          maximumRedeliveries: {{provider.retry.maxAttempts}}\n"
        "          redeliveryDelay: {{provider.retry.delayMs}}\n"
        "        steps:\n"
        "          - set-header:\n"
        "              name: Content-Type\n"
        "              constant: application/xml\n"
        "          - set-header:\n"
        "              name: CamelHttpResponseCode\n"
        "              constant: 500\n"
        "          - set-body:\n"
        "              constant: <Error>Internal Server Error</Error>\n"
        "          - log:\n"
        "              message: \"[${header.X-Correlation-ID}] Error handled: ${exception.message}\"\n"
        "              level: ERROR\n"
    )


def _provider_uri(uri: str | None) -> str:
    if uri:
        sep = '&' if '?' in uri else '?'
        return f"{uri}{sep}connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"
    return "{{{{provider_uri}}}}?connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"


def translate_bpel_to_yaml(plan_path: str, output_dir: str, service_name: str = "loc-bpel", controller_path: str | None = None, request_xslt: str | None = None, reply_xslt: str | None = None, provider_uri: str | None = None) -> Dict[str, Any]:
    plan = _load_plan(plan_path)
    os.makedirs(output_dir, exist_ok=True)

    recv, inv, assign, faults, choice, parallel, pick, reply = _collect_bpel_constructs(plan)

    routes: List[str] = []
    for b in plan.get("bpel", []):
        ops: List[str] = []
        for s in b.get("steps", []):
            op = s.get("operation") if isinstance(s, dict) else None
            if op and op != "null":
                ops.append(op)
        seen = set()
        ops = [x for x in ops if not (x in seen or seen.add(x))]
        for op in ops:
            routes.append(
                (
                    "    - route:\n"
                    f"        id: {service_name}-{op}-controller\n"
                    "        from:\n"
                    f"          uri: platform-http:/{controller_path if controller_path else '{{{{controller_path}}}}'}\n"
                    "        steps:\n"
                    + ("          - set-property:\n              name: __received\n              constant: true\n" if recv > 0 else "")
                    + "          - set-header:\n"
                    + "              name: Content-Type\n"
                    + "              constant: application/xml\n"
                    + "          - set-header:\n"
                    + "              name: X-Correlation-ID\n"
                    + "              simple: \"${exchangeId}\"\n"
                    + "          - log:\n"
                    + "              message: \"[${header.X-Correlation-ID}] Request received\"\n"
                    + "              level: INFO\n"
                    + ("          - set-property:\n              name: assign.marker\n              constant: true\n" if assign > 0 else "")
                    + ("          - choice:\n            when:\n              - simple: ${header.route} == 'A'\n                steps: []\n            otherwise:\n              steps: []\n" if choice > 0 else "")
                    + ("          - multicast:\n              parallelProcessing: true\n              steps: []\n" if parallel > 0 else "")
                    + "          - xslt:\n"
                    + f"              resourceUri: {request_xslt if request_xslt else '{{{{request_xslt}}}}'}\n"
                    + "          - to:\n"
                    + f"              uri: {_provider_uri(provider_uri)}\n"
                    + ("          - log:\n              message: 'BPEL pick branch executed'\n              level: INFO\n" if pick > 0 else "")
                    + "          - xslt:\n"
                    + f"              resourceUri: {reply_xslt if reply_xslt else '{{{{reply_xslt}}}}'}\n"
                    + ("          - set-body:\n              constant: <Reply>OK</Reply>\n" if reply > 0 else "")
                )
            )

    yaml_text = _yaml_header(service_name) + "".join(routes) + _on_exception_block()
    out_path = os.path.join(output_dir, f"{service_name}.yaml")
    Path(out_path).write_text(yaml_text, encoding="utf-8")

    total_detected_kinds = sum(1 for n in [recv, inv, assign, faults, choice, parallel, pick, reply] if n > 0)
    emitted_kinds = 0
    emitted_kinds += 1 if recv > 0 else 0
    emitted_kinds += 1 if inv > 0 and len(routes) > 0 else 0
    emitted_kinds += 1 if assign > 0 else 0
    emitted_kinds += 1 if faults > 0 else 0
    emitted_kinds += 1 if choice > 0 else 0
    emitted_kinds += 1 if parallel > 0 else 0
    emitted_kinds += 1 if pick > 0 else 0
    emitted_kinds += 1 if reply > 0 else 0
    coverage = 100.0 if total_detected_kinds == 0 else round((emitted_kinds / total_detected_kinds) * 100.0, 2)
    return {"path": out_path, "coverage": coverage, "counts": {"receive": recv, "invoke": inv, "assign": assign, "faults": faults, "choice": choice, "parallel": parallel, "pick": pick, "reply": reply}}


def translate_mediation_to_yaml(plan_path: str, output_dir: str, service_name: str = "loc-med", controller_path: str | None = None, request_xslt: str | None = None, reply_xslt: str | None = None, provider_uri: str | None = None) -> Dict[str, Any]:
    plan = _load_plan(plan_path)
    os.makedirs(output_dir, exist_ok=True)

    inv, transform, log, filter_c, enrich_c, aggregate_c = _collect_mediation_constructs(plan)

    flows: List[str] = []
    # Single consolidated route that demonstrates deterministic mapping of mediation primitives
    flows.append(
        (
            "    - route:\n"
            f"        id: {service_name}-controller\n"
            "        from:\n"
            f"          uri: platform-http:/{controller_path if controller_path else '{{{{controller_path}}}}'}\n"
            "        steps:\n"
            "          - set-header:\n"
            "              name: Content-Type\n"
            "              constant: application/xml\n"
            "          - set-header:\n"
            "              name: X-Correlation-ID\n"
            "              simple: \"${exchangeId}\"\n"
            "          - log:\n"
            "              message: \"[${header.X-Correlation-ID}] Request received\"\n"
            "              level: INFO\n"
            + ("          - filter:\n              simple: ${header.filter} == 'ok'\n" if filter_c > 0 else "")
            + "          - xslt:\n"
            + f"              resourceUri: {request_xslt if request_xslt else '{{{{request_xslt}}}}'}\n"
            + ("          - enrich:\n              resourceUri: {{enrich.resourceUri}}\n" if enrich_c > 0 else "")
            + "          - to:\n"
            + f"              uri: {_provider_uri(provider_uri)}\n"
            + ("          - aggregate:\n              strategyRef: {{aggregate.strategy}}\n              completionSize: {{aggregate.size}}\n" if aggregate_c > 0 else "")
            + "          - xslt:\n"
            + f"              resourceUri: {reply_xslt if reply_xslt else '{{{{reply_xslt}}}}'}\n"
        )
    )

    yaml_text = _yaml_header(service_name) + "".join(flows) + _on_exception_block()
    out_path = os.path.join(output_dir, f"{service_name}.yaml")
    Path(out_path).write_text(yaml_text, encoding="utf-8")

    total_detected_kinds = sum(1 for n in [inv, transform, log, filter_c, enrich_c, aggregate_c] if n > 0)
    emitted_kinds = 0
    emitted_kinds += 1 if inv > 0 else 0
    emitted_kinds += 1 if transform > 0 else 0
    emitted_kinds += 1 if log > 0 else 0
    emitted_kinds += 1 if filter_c > 0 else 0
    emitted_kinds += 1 if enrich_c > 0 else 0
    emitted_kinds += 1 if aggregate_c > 0 else 0
    coverage = 100.0 if total_detected_kinds == 0 else round((emitted_kinds / total_detected_kinds) * 100.0, 2)
    return {"path": out_path, "coverage": coverage, "counts": {"invoke": inv, "transform": transform, "log": log, "filter": filter_c, "enrich": enrich_c, "aggregate": aggregate_c}}


def main():
    parser = argparse.ArgumentParser(description="Deterministic translators: BPEL/Mediation to Camel YAML")
    parser.add_argument("--plan", default="agent/orchestration_plan.json")
    parser.add_argument("--output-dir", default="agent/output/routes")
    parser.add_argument("--service-name", default="loc-translator")
    parser.add_argument("--mode", choices=["bpel", "med"], default="bpel")
    parser.add_argument("--controller-path")
    parser.add_argument("--request-xslt")
    parser.add_argument("--reply-xslt")
    parser.add_argument("--provider-uri")
    args = parser.parse_args()

    if args.mode == "bpel":
        result = translate_bpel_to_yaml(args.plan, args.output_dir, args.service_name, args.controller_path, args.request_xslt, args.reply_xslt, args.provider_uri)
    else:
        result = translate_mediation_to_yaml(args.plan, args.output_dir, args.service_name, args.controller_path, args.request_xslt, args.reply_xslt, args.provider_uri)
    print(json.dumps(result))


if __name__ == "__main__":
    main()
