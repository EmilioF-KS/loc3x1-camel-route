import json
import os
from pathlib import Path
from typing import Any, Dict, List


def _load_plan(path: str) -> Dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def _ops(plan: Dict[str, Any]) -> List[str]:
    res: List[str] = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation")
            if op and op != "null":
                res.append(op)
    seen = set()
    return [x for x in res if not (x in seen or seen.add(x))]


def generate_openapi(plan_path: str, out_path: str, title: str = "LOC Service API") -> str:
    plan = _load_plan(plan_path)
    ops = _ops(plan)
    lines: List[str] = []
    lines.append("openapi: 3.0.3")
    lines.append("info:")
    lines.append(f"  title: {title}")
    lines.append("  version: 0.1.0")
    lines.append("paths:")
    for op in ops:
        lines.append(f"  /loc/{op}:")
        lines.append("    post:")
        lines.append(f"      operationId: {op}")
        lines.append("      requestBody:")
        lines.append("        required: true")
        lines.append("        content:")
        lines.append("          application/xml:")
        lines.append("            schema:")
        lines.append("              type: string")
        lines.append("      responses:")
        lines.append("        '200':")
        lines.append("          description: OK")
        lines.append("          content:")
        lines.append("            application/xml:")
        lines.append("              schema:")
        lines.append("                type: string")
    text = "\n".join(lines) + "\n"
    p = Path(out_path)
    os.makedirs(p.parent, exist_ok=True)
    p.write_text(text, encoding="utf-8")
    return str(p)


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Generate OpenAPI stub from orchestration plan")
    parser.add_argument("--plan", default="agent/orchestration_plan.json")
    parser.add_argument("--output", default="generated/result/src/main/resources/openapi.yaml")
    parser.add_argument("--title", default="LOC Service API")
    args = parser.parse_args()
    path = generate_openapi(args.plan, args.output, title=args.title)
    print(path)


if __name__ == "__main__":
    main()

