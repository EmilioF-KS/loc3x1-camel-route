import json
import os
from pathlib import Path

from agent.src.yaml_schema_validator import validate_camel_integration_yaml_file
from agent.src.deterministic_translators import translate_bpel_to_yaml, translate_mediation_to_yaml


def test_bpel_yaml_validates_against_schema(tmp_path: Path):
    plan = {"bpel": [{"steps": [{"kind": "receive"}, {"kind": "invoke", "operation": "OpA"}, {"kind": "faultHandlers"}]}]}
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(json.dumps(plan), encoding="utf-8")
    out_dir = tmp_path / "routes"
    result = translate_bpel_to_yaml(str(plan_file), str(out_dir), service_name="svc")
    validate_camel_integration_yaml_file(result["path"])  # type: ignore[index]


def test_mediation_yaml_validates_against_schema(tmp_path: Path):
    plan = {"mediation": [{"steps": [{"kind": "transform"}, {"kind": "invoke"}, {"kind": "log"}]}]}
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(json.dumps(plan), encoding="utf-8")
    out_dir = tmp_path / "routes"
    result = translate_mediation_to_yaml(str(plan_file), str(out_dir), service_name="svc")
    validate_camel_integration_yaml_file(result["path"])  # type: ignore[index]

