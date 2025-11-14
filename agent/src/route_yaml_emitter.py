import json
import os
from pathlib import Path
from typing import Any, Dict, List


def _load_plan(path: str) -> Dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def _ops(plan: Dict[str, Any]) -> List[str]:
    ops: List[str] = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation")
            if op and op != "null":
                ops.append(op)
    seen = set()
    return [x for x in ops if not (x in seen or seen.add(x))]


def emit_per_operation_routes(plan_path: str, routes_dir: str, service_name: str = "loc-service") -> List[str]:
    plan = _load_plan(plan_path)
    ops = _ops(plan)
    out_paths: List[str] = []
    os.makedirs(routes_dir, exist_ok=True)
    for op in ops:
        name = f"route-{op}"
        lines = [
            "apiVersion: camel.apache.org/v1",
            "kind: Integration",
            "metadata:",
            f"  name: {name}",
            "spec:",
            "  flows:",
            "    - from:",
            "        uri: platform-http:/{{controller_path}}",
            "        steps:",
            "          - setHeader:",
            "              name: Content-Type",
            "              constant: application/xml",
            "          - setHeader:",
            "              name: X-Correlation-ID",
            "              simple: \"${exchangeId}\"",
            "          - log:",
            "              message: \"[${header.X-Correlation-ID}] Request received\"",
            "              loggingLevel: INFO",
            "          - to:",
            "              uri: \"xslt:{{request_xslt}}\"",
            "          - removeHeaders:",
            "              pattern: \"HTTP_*\"",
            "          - removeHeaders:",
            "              pattern: \"CamelHttp*\"",
            "          - removeHeader:",
            "              name: CamelHttpUri",
            "          - removeHeader:",
            "              name: CamelHttpUrl",
            "          - removeHeader:",
            "              name: CamelHttpPath",
            "          - to:",
            "              uri: {{provider.uri}}",
            "          - to:",
            "              uri: \"xslt:{{reply_xslt}}\"",
        ]
        text = "\n".join(lines) + "\n"
        out = Path(routes_dir) / f"route-{op}.yaml"
        out.write_text(text, encoding="utf-8")
        out_paths.append(str(out))
    return out_paths


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Emit per-operation Camel YAML routes")
    parser.add_argument("--plan", default="agent/orchestration_plan.json")
    parser.add_argument("--routes", default="generated/result/src/main/resources/routes")
    parser.add_argument("--service-name", default="loc-service")
    args = parser.parse_args()
    paths = emit_per_operation_routes(args.plan, args.routes, service_name=args.service_name)
    print("\n".join(paths))


if __name__ == "__main__":
    main()
