from pathlib import Path
import json

from agent.src.inventory import classify_file, discover_artifacts, write_manifest


def test_classify_file_basic_cases(tmp_path):
    files = {
        "schema.xsd": "xsd_schema",
        "contract.wsdl": "wsdl",
        "flow.bpel": "bpel",
        "transform.xsl": "xslt",
        "notes.txt": "text_doc",
        "doc.pdf": "pdf_doc",
        "layout.tex": "tex_doc",
        "payload.xml": "xml",
    }
    for name, expected in files.items():
        p = tmp_path / name
        p.write_text("<root/>")
        assert classify_file(p) == expected


def test_discover_and_manifest(tmp_path):
    # Create a mini tree with imports
    (tmp_path / "Schemas").mkdir()
    a = tmp_path / "Schemas" / "A.xsd"
    b = tmp_path / "Schemas" / "B.xsd"
    a.write_text(
        """
        <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
          <xsd:import schemaLocation="B.xsd"/>
        </xsd:schema>
        """
    )
    b.write_text("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema'/>")

    artifacts = discover_artifacts(tmp_path)
    assert any(x["type"] == "xsd_schema" for x in artifacts)

    out = tmp_path / "manifest.json"
    write_manifest(out, artifacts)
    data = json.loads(out.read_text())
    assert "artifacts" in data and len(data["artifacts"]) >= 2
    assert "scan_info" in data
    assert isinstance(data["scan_info"].get("artifact_count"), int)
    assert isinstance(data["scan_info"].get("scan_time"), str)
    # Verify import captured
    a_rec = next(x for x in data["artifacts"] if x["path"].endswith("A.xsd"))
    assert "imports" in a_rec and "B.xsd" in a_rec["imports"]
    # Verify metadata present
    for rec in data["artifacts"]:
        assert "size_bytes" in rec and isinstance(rec["size_bytes"], int)
        assert "modified_time" in rec and isinstance(rec["modified_time"], str)
        assert "mtime_epoch" in rec and isinstance(rec["mtime_epoch"], int)
        assert "file_mode" in rec and isinstance(rec["file_mode"], int)
        # Hash may be None on unreadable files, but should exist for our synthetic ones
        assert rec.get("sha256") is None or isinstance(rec["sha256"], str)