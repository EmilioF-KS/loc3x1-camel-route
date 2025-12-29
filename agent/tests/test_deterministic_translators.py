import json
import os
from pathlib import Path

from agent.src.deterministic_translators import translate_bpel_to_yaml, translate_mediation_to_yaml
from agent.src.route_synthesis import validate_route_yaml


def test_bpel_translation_generates_yaml_with_placeholders(tmp_path: Path):
    plan = {
        "bpel": [
            {
                "steps": [
                    {"kind": "receive", "operation": "OpA"},
                    {"kind": "assign"},
                    {"kind": "invoke", "operation": "OpA"},
                    {"kind": "faultHandlers"},
                ]
            }
        ]
    }
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(json.dumps(plan), encoding="utf-8")

    out_dir = tmp_path / "routes"
    result = translate_bpel_to_yaml(str(plan_file), str(out_dir), service_name="svc")
    assert os.path.exists(result["path"])  # type: ignore[index]
    text = Path(result["path"]).read_text(encoding="utf-8")  # type: ignore[index]

    assert "apiVersion: camel.apache.org/v1" in text
    assert "kind: Integration" in text
    assert "spec:" in text and "flows:" in text
    assert "- route:" in text and "from:" in text and "steps:" in text
    assert "- on-exception:" in text and "handled: true" in text

    validate_route_yaml(str(result["path"]))  # type: ignore[index]


def test_mediation_translation_generates_yaml_with_placeholders(tmp_path: Path):
    plan = {
        "mediation": [
            {
                "steps": [
                    {"kind": "transform"},
                    {"kind": "invoke"},
                    {"kind": "log"},
                ]
            }
        ]
    }
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(json.dumps(plan), encoding="utf-8")

    out_dir = tmp_path / "routes"
    result = translate_mediation_to_yaml(str(plan_file), str(out_dir), service_name="svc")
    assert os.path.exists(result["path"])  # type: ignore[index]
    text = Path(result["path"]).read_text(encoding="utf-8")  # type: ignore[index]

    assert "apiVersion: camel.apache.org/v1" in text
    assert "kind: Integration" in text
    assert "spec:" in text and "flows:" in text
    assert "- route:" in text and "from:" in text and "steps:" in text
    assert "- on-exception:" in text and "handled: true" in text

    validate_route_yaml(str(result["path"]))  # type: ignore[index]

