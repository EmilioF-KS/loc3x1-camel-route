import os
import json
from pathlib import Path

from agent.src.scaffold import scaffold_result
from agent.src.binding_inference import resolve_generated_routes
from agent.src.route_synthesis import validate_route_yaml


def test_binding_inference_covers_all_operations(tmp_path: Path):
    out = "generated/result"
    scaffold_result(out, clean=True, orchestration_plan_path="agent/orchestration_plan.json", service_name="loc-service")
    routes_dir = os.path.join(out, "src/main/resources/routes")
    resources_root = os.path.join(out, "src/main/resources")
    plan = json.loads(Path("agent/orchestration_plan.json").read_text(encoding="utf-8"))
    ops = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation")
            if op and op != "null":
                ops.append(op)
    seen = set()
    ops = [x for x in ops if not (x in seen or seen.add(x))]
    resolved = resolve_generated_routes(routes_dir, resources_root)
    assert resolved
    yaml_file = Path(routes_dir) / "loc-service.yaml"
    text = yaml_file.read_text(encoding="utf-8")
    count = sum(1 for ln in text.splitlines() if ln.strip().startswith("id: "))
    assert count >= len(ops)
    validate_route_yaml(str(yaml_file), mode="resolved", resources_root=resources_root)

