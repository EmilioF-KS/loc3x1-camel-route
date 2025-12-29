import os
from pathlib import Path
from lxml import etree

from agent.src.codegen_inputs import assemble_contracts


def test_selected_contracts_have_resolvable_imports(tmp_path: Path):
    dest = tmp_path / "selected"
    copied = assemble_contracts("agent/manifest.json", str(dest), wsdl_filter=["LocationRetrievalLOC3X1M.wsdl"])
    assert copied
    xsds = [Path(p) for p in copied if p.endswith(".xsd")]
    for xsd in xsds:
        doc = etree.parse(str(xsd))
        for imp in doc.xpath("//xs:import", namespaces={"xs": "http://www.w3.org/2001/XMLSchema"}):
            loc = imp.get("schemaLocation")
            if not loc:
                continue
            probe = (xsd.parent / loc).resolve()
            assert probe.exists(), f"Missing import {loc} for {xsd}"


def test_wsdl_embedded_types_imports_resolve(tmp_path: Path):
    dest = tmp_path / "selected"
    copied = assemble_contracts("agent/manifest.json", str(dest), wsdl_filter=["LocationRetrievalLOC3X1M.wsdl"])
    wsdls = [Path(p) for p in copied if p.endswith(".wsdl")]
    assert wsdls, "No WSDL copied"
    for wsdl in wsdls:
        doc = etree.parse(str(wsdl))
        for imp in doc.xpath("//wsdl:types/xs:schema/xs:import", namespaces={"wsdl": "http://schemas.xmlsoap.org/wsdl/", "xs": "http://www.w3.org/2001/XMLSchema"}):
            loc = imp.get("schemaLocation")
            if not loc:
                continue
            probe = (wsdl.parent / loc).resolve()
            assert probe.exists(), f"Missing types import {loc} for {wsdl}"

