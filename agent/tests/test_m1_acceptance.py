import json
from pathlib import Path

from agent.src.deterministic_translators import translate_bpel_to_yaml, translate_mediation_to_yaml
from agent.src.yaml_schema_validator import validate_camel_integration_yaml_file


def test_m1_acceptance_on_workspace_plan():
    plan_path = Path("agent/orchestration_plan.json")
    assert plan_path.exists(), "orchestration_plan.json must exist"

    # BPEL translation and validation
    bpel_result = translate_bpel_to_yaml(str(plan_path), "agent/output/routes", service_name="m1-accept-bpel")
    validate_camel_integration_yaml_file(bpel_result["path"])  # type: ignore[index]
    assert bpel_result["coverage"] >= 95  # type: ignore[index]

    # Mediation translation and validation
    med_result = translate_mediation_to_yaml(str(plan_path), "agent/output/routes", service_name="m1-accept-med")
    validate_camel_integration_yaml_file(med_result["path"])  # type: ignore[index]
    assert med_result["coverage"] >= 95  # type: ignore[index]

