from pathlib import Path
import os
import json

from agent.src.inventory import discover_artifacts, write_manifest


def test_integration_discovers_known_files():
    input_dir = os.environ.get("DEP_INPUT_DIR", "CHUBB/Dependencies")
    root = Path(input_dir).resolve()
    if not root.exists():
        import pytest
        pytest.skip(f"Input directory not found: {root}")

    artifacts = discover_artifacts(root)
    # Should find at least a reasonable number of artifacts
    assert len(artifacts) >= 20

    # Verify presence of a few known files/types
    paths = [a["path"] for a in artifacts]
    types = {a["path"]: a["type"] for a in artifacts}
    # Metadata sanity: all artifacts have size and modified_time
    for a in artifacts:
        assert "size_bytes" in a and isinstance(a["size_bytes"], int)
        assert "modified_time" in a and isinstance(a["modified_time"], str)
        assert "mtime_epoch" in a and isinstance(a["mtime_epoch"], int)
        assert "file_mode" in a and isinstance(a["file_mode"], int)

    # BPEL process file
    assert any("LocationRetrievalLOC3X1Process.bpel" in p for p in paths)
    bpel_path = next(p for p in paths if "LocationRetrievalLOC3X1Process.bpel" in p)
    assert types[bpel_path] == "bpel"

    # XSD schemas
    assert any(p.endswith("Schemas/Address.xsd") for p in paths)
    addr_path = next(p for p in paths if p.endswith("Schemas/Address.xsd"))
    assert types[addr_path] == "xsd_schema"

    # Private schema
    assert any(p.endswith("PrivateSchemas/SimpleFault.xsd") for p in paths)

    # Write manifest and verify scan_info present
    out = Path("agent/manifest.json")
    write_manifest(out, artifacts, root)
    data = json.loads(out.read_text())
    assert "scan_info" in data
    assert data["scan_info"].get("root_path") == str(root)
    assert isinstance(data["scan_info"].get("artifact_count"), int)