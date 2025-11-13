import json
import os
from pathlib import Path
from typing import Any, Dict, List


JAVA_TMPL = (
    "package {pkg}.controller;\n\n"
    "import org.springframework.http.MediaType;\n"
    "import org.springframework.web.bind.annotation.PostMapping;\n"
    "import org.springframework.web.bind.annotation.RequestMapping;\n"
    "import org.springframework.web.bind.annotation.RestController;\n\n"
    "@RestController\n"
    "@RequestMapping(path = \"/{path}\")\n"
    "public class {cls}Controller {{\n\n"
    "    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)\n"
    "    public String invoke() {{\n"
    "        return \"<ok/>\";\n"
    "    }}\n"
    "}}\n"
)


def _load_plan(path: str) -> Dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def _ops_from_plan(plan: Dict[str, Any]) -> List[str]:
    ops: List[str] = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation") if isinstance(s, dict) else None
            if op and op != "null":
                ops.append(op)
    seen = set()
    return [x for x in ops if not (x in seen or seen.add(x))]


def generate_controllers(plan_path: str, java_root: str, java_package: str) -> List[str]:
    plan = _load_plan(plan_path)
    ops = _ops_from_plan(plan)
    out_paths: List[str] = []
    for op in ops:
        cls = op
        path = f"loc/{op}"
        src = JAVA_TMPL.format(pkg=java_package, path=path, cls=cls)
        dst_dir = Path(java_root) / java_package.replace(".", "/") / "controller"
        os.makedirs(dst_dir, exist_ok=True)
        dst = dst_dir / f"{cls}Controller.java"
        dst.write_text(src, encoding="utf-8")
        out_paths.append(str(dst))
    return out_paths


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Generate Spring controllers per operation from orchestration plan")
    parser.add_argument("--plan", default="agent/orchestration_plan.json")
    parser.add_argument("--java-root", default="generated/result/src/main/java")
    parser.add_argument("--java-package", default="com.example")
    args = parser.parse_args()
    paths = generate_controllers(args.plan, args.java_root, args.java_package)
    print("\n".join(paths))


if __name__ == "__main__":
    main()
