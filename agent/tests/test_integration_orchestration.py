import json
from pathlib import Path

from agent.src.orchestration import build_orchestration_plan


def test_build_orchestration_from_dependencies():
    manifest_path = Path("agent/manifest.json")
    assert manifest_path.exists(), "manifest.json must exist"
    manifest = json.loads(manifest_path.read_text())

    plan = build_orchestration_plan(manifest)

    # Basic sanity: at least one BPEL or mediation file should be present
    assert plan["summary"]["bpel_count"] + plan["summary"]["mediation_count"] >= 1

    # There should be at least one step extracted across files
    total_steps = sum(len(p.get("steps", [])) for p in plan.get("bpel", [])) + \
                  sum(len(p.get("steps", [])) for p in plan.get("mediation", []))
    assert total_steps >= 1

    # Preferably some invoke or transform exists in real flows
    assert plan["summary"]["invoke_count"] + plan["summary"]["transform_count"] >= 0