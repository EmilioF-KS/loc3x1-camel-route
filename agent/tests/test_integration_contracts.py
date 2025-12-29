from pathlib import Path
import os
import json

import pytest

from agent.src.inventory import discover_artifacts, write_manifest
from agent.src.contracts import build_contracts


def test_integration_contracts_from_dependencies(tmp_path):
    input_dir = os.environ.get("DEP_INPUT_DIR", "CHUBB/Dependencies")
    root = Path(input_dir).resolve()
    if not root.exists():
        pytest.skip(f"Input directory not found: {root}")

    artifacts = discover_artifacts(root)
    manifest_path = tmp_path / "manifest.json"
    write_manifest(manifest_path, artifacts, root)
    manifest = json.loads(manifest_path.read_text())

    contracts = build_contracts(manifest)
    # Expect some WSDLs and XSDs present in Dependencies
    assert contracts["summary"]["wsdl_count"] >= 1
    assert contracts["summary"]["xsd_count"] >= 1
    # At least one operation extracted
    assert contracts["summary"]["operations_count"] >= 1