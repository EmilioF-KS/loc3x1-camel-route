import json
from agent.src.graph import build_import_graph


def test_build_import_graph_basic():
    manifest = {
        "artifacts": [
            {"path": "Schemas/A.xsd", "type": "xsd_schema", "imports": ["B.xsd"]},
            {"path": "Schemas/B.xsd", "type": "xsd_schema"},
        ]
    }
    graph = build_import_graph(manifest)
    nodes = {n["id"]: n for n in graph["nodes"]}
    assert "Schemas/A.xsd" in nodes and nodes["Schemas/A.xsd"]["type"] == "xsd_schema"
    assert "Schemas/B.xsd" in nodes
    # One edge from A -> Schemas/B.xsd (resolved by basename)
    assert any(e["from"] == "Schemas/A.xsd" and e["to"] == "Schemas/B.xsd" and e["resolved"] is True for e in graph["edges"])


def test_build_import_graph_resolution():
    manifest = {
        "artifacts": [
            {"path": "Subsystem/A.xsd", "type": "xsd_schema", "imports": ["../Schemas/B.xsd", "B.xsd"]},
            {"path": "Schemas/B.xsd", "type": "xsd_schema"},
        ]
    }
    graph = build_import_graph(manifest)
    # Expect edges with resolved targets
    assert any(e["from"] == "Subsystem/A.xsd" and e["to"] == "Schemas/B.xsd" and e["resolved"] is True for e in graph["edges"])


def test_namespace_extraction(tmp_path):
    # Create synthetic XSD and WSDL files
    xsd = tmp_path / "A.xsd"
    xsd.write_text(
        """
        <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                    xmlns:tns="http://example.com/types"
                    targetNamespace="http://example.com/types">
        </xsd:schema>
        """
    )
    wsdl = tmp_path / "B.wsdl"
    wsdl.write_text(
        """
        <wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                          xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                          xmlns:tns="http://example.com/service"
                          targetNamespace="http://example.com/service">
        </wsdl:definitions>
        """
    )

    manifest = {
        "artifacts": [
            {"path": "Schemas/A.xsd", "abs_path": str(xsd), "type": "xsd_schema"},
            {"path": "Services/B.wsdl", "abs_path": str(wsdl), "type": "wsdl"},
        ]
    }
    graph = build_import_graph(manifest)
    nodes = {n["id"]: n for n in graph["nodes"]}
    assert nodes["Schemas/A.xsd"]["namespaces"]["targetNamespace"] == "http://example.com/types"
    assert nodes["Services/B.wsdl"]["namespaces"]["targetNamespace"] == "http://example.com/service"
    assert nodes["Services/B.wsdl"]["namespaces"]["prefixes"]["soap"] == "http://schemas.xmlsoap.org/wsdl/soap/"


def test_wsdl_relationship_edges(tmp_path):
    wsdl = tmp_path / "S.wsdl"
    wsdl.write_text(
        """
        <wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
          <wsdl:portType name="ServicePortType"/>
          <wsdl:message name="GetStateRequest"/>
          <wsdl:message name="GetStateResponse"/>
        </wsdl:definitions>
        """
    )
    manifest = {
        "artifacts": [
            {"path": "Services/S.wsdl", "abs_path": str(wsdl), "type": "wsdl"},
        ]
    }
    graph = build_import_graph(manifest)
    edges = graph["edges"]
    assert any(e["kind"] == "wsdl_portType" and e["to"] == "ServicePortType" for e in edges)
    assert any(e["kind"] == "wsdl_message" and e["to"] == "GetStateRequest" for e in edges)
    assert any(e["kind"] == "wsdl_message" and e["to"] == "GetStateResponse" for e in edges)


def test_cycle_detection():
    manifest = {
        "artifacts": [
            {"path": "Schemas/A.xsd", "type": "xsd_schema", "imports": ["B.xsd"]},
            {"path": "Schemas/B.xsd", "type": "xsd_schema", "imports": ["A.xsd"]},
        ]
    }
    graph = build_import_graph(manifest)
    report = graph.get("report", {})
    assert report.get("cycle_count", 0) >= 1