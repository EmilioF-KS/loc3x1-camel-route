import argparse
import json
import os
from pathlib import Path
from typing import Any, Dict, List


def load_plan(path: str) -> Dict[str, Any]:
    p = Path(path)
    txt = p.read_text(encoding='utf-8')
    # try JSON first; if fails, treat as simple YAML (key: value) using a naive parser
    try:
        return json.loads(txt)
    except json.JSONDecodeError:
        plan: Dict[str, Any] = {}
        for line in txt.splitlines():
            line = line.strip()
            if not line or line.startswith('#'):
                continue
            if ':' in line:
                k, v = line.split(':', 1)
                plan[k.strip()] = v.strip().strip('"')
        return plan


def synthesize_routes(
    orchestration_plan_path: str,
    output_dir: str,
    service_name: str = 'loc-service',
    controller_path: str | None = None,
    request_xslt: str | None = None,
    reply_xslt: str | None = None,
    provider_uri: str | None = None,
    allowed_ops: List[str] | None = None,
) -> str:
    plan = load_plan(orchestration_plan_path)
    os.makedirs(output_dir, exist_ok=True)

    ops: List[str] = []
    if isinstance(plan, dict) and 'bpel' in plan:
        for b in plan.get('bpel', []):
            for s in b.get('steps', []):
                op = s.get('operation') if isinstance(s, dict) else None
                if op and op != 'null':
                    ops.append(op)
    # de-duplicate while preserving order
    seen = set()
    ops = [x for x in ops if not (x in seen or seen.add(x))]

    base_header = (
        "apiVersion: camel.apache.org/v1\n"
        "kind: Integration\n"
        "metadata:\n"
        f"  name: {service_name}\n"
        "spec:\n"
        "  flows:\n"
    )

    routes_yaml = []
    # Build provider URI with timeout options if provided; otherwise keep placeholder with options
    def build_provider_uri(uri: str | None) -> str:
        if uri:
            sep = '&' if '?' in uri else '?'
            return f"{uri}{sep}connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"
        else:
            return "{{{{provider_uri}}}}?connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"
    if allowed_ops:
        ops = [op for op in ops if op in allowed_ops]
    if ops:
        for op in ops:
            routes_yaml.append(
                (
                    "    - from:\n"
                    f"        uri: platform-http:/loc/{op}\n"
                    "        steps:\n"
                    "          - setHeader:\n"
                    "              name: Content-Type\n"
                    "              constant: application/xml\n"
                    "          - setHeader:\n"
                    "              name: X-Correlation-ID\n"
                    "              simple: \"${exchangeId}\"\n"
                    "          - log:\n"
                    "              message: \"[${header.X-Correlation-ID}] Request received\"\n"
                    "              loggingLevel: INFO\n"
                    "          - to:\n"
                    f"              uri: xslt:{request_xslt if request_xslt else '{{{{request_xslt}}}}'}\n"
                    "          - to:\n"
                    f"              uri: http://localhost:8081/svc/{op}?bridgeEndpoint=true&throwExceptionOnFailure=false&connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}\n"
                    "          - to:\n"
                    f"              uri: xslt:{reply_xslt if reply_xslt else '{{{{reply_xslt}}}}'}\n"
                )
            )
    else:
        routes_yaml.append(
            (
                "    - from:\n"
                f"        uri: platform-http:/loc/{service_name}\n"
                "        steps:\n"
                "          - setHeader:\n"
                "              name: Content-Type\n"
                "              constant: application/xml\n"
                "          - setHeader:\n"
                "              name: X-Correlation-ID\n"
                "              simple: \"${exchangeId}\"\n"
                "          - log:\n"
                "              message: \"[${header.X-Correlation-ID}] Request received\"\n"
                "              loggingLevel: INFO\n"
                "          - to:\n"
                f"              uri: xslt:{request_xslt if request_xslt else '{{{{request_xslt}}}}'}\n"
                "          - to:\n"
                f"              uri: http://localhost:8081/svc/{service_name}?bridgeEndpoint=true&throwExceptionOnFailure=false&connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}\n"
                "          - to:\n"
                f"              uri: xslt:{reply_xslt if reply_xslt else '{{{{reply_xslt}}}}'}\n"
            )
        )

    exception_yaml = (
        "    - onException:\n"
        "        exception: java.lang.Exception\n"
        "        handled:\n"
        "          constant: true\n"
        "        redeliveryPolicy:\n"
        "          maximumRedeliveries: 3\n"
        "          redeliveryDelay: 1000\n"
        "        steps:\n"
        "          - setHeader:\n"
        "              name: Content-Type\n"
        "              constant: application/xml\n"
        "          - setHeader:\n"
        "              name: CamelHttpResponseCode\n"
        "              constant: 500\n"
        "          - setBody:\n"
        "              constant: <Error>Internal Server Error</Error>\n"
        "          - log:\n"
        "              message: \"[${header.X-Correlation-ID}] Error handled: ${exception.message}\"\n"
        "              loggingLevel: ERROR\n"
    )

    yaml_text = base_header + exception_yaml + "".join(routes_yaml)

    out_path = os.path.join(output_dir, f"{service_name}.yaml")
    with open(out_path, 'w', encoding='utf-8') as f:
        f.write(yaml_text)
    return out_path


def synthesize_routes_split(
    orchestration_plan_path: str,
    output_dir: str,
    service_name: str = 'loc-service',
    controller_path: str | None = None,
    request_xslt: str | None = None,
    reply_xslt: str | None = None,
    provider_uri: str | None = None,
) -> List[str]:
    plan = load_plan(orchestration_plan_path)
    os.makedirs(output_dir, exist_ok=True)

    ops: List[str] = []
    if isinstance(plan, dict) and 'bpel' in plan:
        for b in plan.get('bpel', []):
            for s in b.get('steps', []):
                op = s.get('operation') if isinstance(s, dict) else None
                if op and op != 'null':
                    ops.append(op)
    seen = set()
    ops = [x for x in ops if not (x in seen or seen.add(x))]

    generated: List[str] = []
    if not ops:
        # Fallback: generate a single controller file
        path = synthesize_routes(orchestration_plan_path, output_dir, service_name, controller_path, request_xslt, reply_xslt, provider_uri)
        return [path]

    for op in ops:
        base_header = (
            "apiVersion: camel.apache.org/v1\n"
            "kind: Integration\n"
            "metadata:\n"
            f"  name: {service_name}-{op}\n"
            "spec:\n"
            "  flows:\n"
        )
        def build_provider_uri(uri: str | None) -> str:
            if uri:
                sep = '&' if '?' in uri else '?'
                return f"{uri}{sep}connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"
            else:
                return "{{{{provider_uri}}}}?connectTimeout={{{{provider.timeoutMs}}}}&socketTimeout={{{{provider.timeoutMs}}}}"

        route_yaml = (
            "    - route:\n"
            f"        id: {service_name}-{op}-controller\n"
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
            "          - xslt:\n"
            f"              resourceUri: {request_xslt if request_xslt else '{{{{request_xslt}}}}'}\n"
            "          - to:\n"
            f"              uri: {build_provider_uri(provider_uri)}\n"
            "          - xslt:\n"
            f"              resourceUri: {reply_xslt if reply_xslt else '{{{{reply_xslt}}}}'}\n"
        )
        exception_yaml = (
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
        yaml_text = base_header + route_yaml + exception_yaml
        out_path = os.path.join(output_dir, f"{service_name}-{op}.yaml")
        with open(out_path, 'w', encoding='utf-8') as f:
            f.write(yaml_text)
        generated.append(out_path)

    return generated


def validate_route_yaml(yaml_path: str, mode: str = 'unresolved', resources_root: str | None = None) -> None:
    text = Path(yaml_path).read_text(encoding='utf-8')
    placeholders = ["{{controller_path}}", "{{request_xslt}}", "{{provider_uri}}", "{{reply_xslt}}"]
    if mode == 'unresolved':
        missing = [ph for ph in placeholders if ph not in text]
        if missing:
            raise ValueError(f"Missing required placeholders: {', '.join(missing)}")
    elif mode == 'resolved':
        present = [ph for ph in placeholders if ph in text]
        if present:
            raise ValueError(f"Unresolved placeholders present: {', '.join(present)}")
        # Ensure resourceUri entries use classpath and optionally exist on disk
        lines = text.splitlines()
        xslt_uris: List[str] = []
        for ln in lines:
            ln = ln.strip()
            if ln.startswith('resourceUri:'):
                _, val = ln.split(':', 1)
                val = val.strip()
                xslt_uris.append(val)
        for uri in xslt_uris:
            if not uri.startswith('classpath:'):
                raise ValueError(f"XSLT resourceUri must use 'classpath:' prefix, got: {uri}")
            if resources_root:
                rel = uri[len('classpath:'):]
                probe = Path(resources_root) / rel
                if not probe.exists():
                    raise ValueError(f"XSLT resource not found at resources root: {probe}")
    else:
        raise ValueError("mode must be 'unresolved' or 'resolved'")


def main():
    parser = argparse.ArgumentParser(description='Synthesize Camel YAML route skeletons from orchestration plan')
    parser.add_argument('--plan', default='agent/orchestration_plan.json', help='Path to orchestration plan (JSON or YAML)')
    parser.add_argument('--output-dir', default='agent/output/routes', help='Output directory for route YAML files')
    parser.add_argument('--service-name', default='loc-service', help='Service name used in route ids and filenames')
    parser.add_argument('--validate-file', help='Validate an existing route YAML for placeholders')
    parser.add_argument('--validate-mode', default='unresolved', help="Validation mode: 'unresolved' requires placeholders present; 'resolved' requires none and checks classpath URIs")
    parser.add_argument('--validate-resources-root', help='Optional root to check existence of classpath resources (e.g., src/main/resources)')
    parser.add_argument('--split-per-operation', action='store_true', help='Emit one YAML per operation discovered in the plan')
    parser.add_argument('--controller-path', help='Fill controller path placeholder (e.g., loc/getLocationList)')
    parser.add_argument('--request-xslt', help='Fill request XSLT resourceUri (e.g., classpath:GetLocationListRequest.xsl)')
    parser.add_argument('--reply-xslt', help='Fill reply XSLT resourceUri (e.g., classpath:LocationListReply.xsl)')
    parser.add_argument('--provider-uri', help='Fill provider URI (e.g., http://provider/locations)')
    args = parser.parse_args()

    if args.validate_file:
        validate_route_yaml(args.validate_file, args.validate_mode, resources_root=args.validate_resources_root)
        print('Validation successful')
        return

    if args.split_per_operation:
        paths = synthesize_routes_split(
            args.plan,
            args.output_dir,
            args.service_name,
            controller_path=args.controller_path,
            request_xslt=args.request_xslt,
            reply_xslt=args.reply_xslt,
            provider_uri=args.provider_uri,
        )
        for p in paths:
            print(f"Generated route: {p}")
    else:
        out_path = synthesize_routes(
            args.plan,
            args.output_dir,
            args.service_name,
            controller_path=args.controller_path,
            request_xslt=args.request_xslt,
            reply_xslt=args.reply_xslt,
            provider_uri=args.provider_uri,
        )
        print(f"Generated route: {out_path}")


if __name__ == '__main__':
    main()
