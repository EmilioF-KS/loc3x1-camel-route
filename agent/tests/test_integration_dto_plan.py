from pathlib import Path
import os
import json

import pytest

from agent.src.inventory import discover_artifacts, write_manifest
from agent.src.dto_plan import build_dto_plan


def test_integration_dto_plan_dependencies(tmp_path):
    input_dir = os.environ.get("DEP_INPUT_DIR", "CHUBB/Dependencies")
    root = Path(input_dir).resolve()
    if not root.exists():
        pytest.skip(f"Input directory not found: {root}")

    artifacts = discover_artifacts(root)
    manifest_path = tmp_path / "manifest.json"
    write_manifest(manifest_path, artifacts, root)
    manifest = json.loads(manifest_path.read_text())

    plan = build_dto_plan(manifest)
    # Sanity: expect at least some classes and elements from workspace XSDs
    assert plan["summary"]["class_count"] >= 1
    assert plan["summary"]["element_count"] >= 1
    assert plan["summary"]["namespace_count"] >= 1