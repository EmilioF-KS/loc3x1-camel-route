import os
from pathlib import Path

from agent.src.scaffold import scaffold_result
from agent.src.binding_inference import resolve_generated_routes
from agent.src.route_synthesis import validate_route_yaml


def test_binding_inference_resolves_placeholders(tmp_path: Path):
    out = "generated/result"
    scaffold_result(out, clean=True, orchestration_plan_path="agent/orchestration_plan.json", service_name="loc-service")
    routes_dir = os.path.join(out, "src/main/resources/routes")
    resources_root = os.path.join(out, "src/main/resources")
    resolved = resolve_generated_routes(routes_dir, resources_root)
    assert resolved
    for p in resolved:
        validate_route_yaml(p, mode="resolved", resources_root=resources_root)

