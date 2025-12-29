import os
import json
from pathlib import Path

from agent.src.scaffold import scaffold_result


def test_scaffold_endpoints_and_routes_cover_all_ops(tmp_path: Path):
    out = "generated/result"
    scaffold_result(out, clean=True, orchestration_plan_path="agent/orchestration_plan.json", service_name="loc-service")
    plan = json.loads(Path("agent/orchestration_plan.json").read_text(encoding="utf-8"))
    ops = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation")
            if op and op != "null":
                ops.append(op)
    seen = set()
    ops = [x for x in ops if not (x in seen or seen.add(x))]

    # Controllers exist per op
    java_root = Path(out) / "src/main/java/com/example/controller"
    assert java_root.exists()
    for op in ops:
        ctrl = java_root / f"{op}Controller.java"
        assert ctrl.exists(), f"Missing controller for {op}"
        text = ctrl.read_text(encoding="utf-8")
        assert f"@RequestMapping(path = \"/loc/{op}\")" in text

    # Resolve placeholders and assert platform-http path per op
    from agent.src.binding_inference import resolve_generated_routes
    routes_dir = Path(out) / "src/main/resources/routes"
    resources_root = Path(out) / "src/main/resources"
    resolve_generated_routes(str(routes_dir), str(resources_root))
    routes_yaml = routes_dir / "loc-service.yaml"
    text = routes_yaml.read_text(encoding="utf-8")
    for op in ops:
        assert f"platform-http:/loc/{op}" in text

    # OpenAPI contains paths per op
    openapi = Path(out) / "src/main/resources/openapi.yaml"
    assert openapi.exists()
    otext = openapi.read_text(encoding="utf-8")
    for op in ops:
        assert f"/loc/{op}:" in otext
