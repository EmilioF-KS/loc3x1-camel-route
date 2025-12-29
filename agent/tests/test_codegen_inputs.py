import os
from pathlib import Path

from agent.src.codegen_inputs import assemble_contracts


def test_assemble_selected_contracts(tmp_path: Path):
    dest = tmp_path / "selected"
    copied = assemble_contracts("agent/manifest.json", str(dest), wsdl_filter=["LocationRetrievalLOC3X1M.wsdl"])
    assert os.path.isdir(str(dest))
    assert copied
    names = [Path(p).name for p in copied]
    assert "LocationRetrievalLOC3X1M.wsdl" in names
    assert any(n.endswith(".xsd") for n in names)
    # Ensure directory structure preserved (Schemas and PrivateSchemas)
    assert (dest / "Schemas").exists()
    assert (dest / "PrivateSchemas").exists()
