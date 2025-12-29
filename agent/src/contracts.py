from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Dict, List, Any

import xml.etree.ElementTree as ET


def _load_manifest(manifest_path: Path) -> Dict[str, Any]:
    data = json.loads(Path(manifest_path).read_text())
    return data


def _parse_wsdl(wsdl_path: Path) -> Dict[str, Any]:
    ns = {
        "wsdl": "http://schemas.xmlsoap.org/wsdl/",
        "xsd": "http://www.w3.org/2001/XMLSchema",
    }
    try:
        tree = ET.parse(wsdl_path)
    except ET.ParseError:
        return {"file": str(wsdl_path), "error": "parse_error"}
    root = tree.getroot()
    target_ns = root.attrib.get("targetNamespace")

    port_types = []
    for pt in root.findall("wsdl:portType", ns):
        pt_name = pt.attrib.get("name")
        operations = []
        for op in pt.findall("wsdl:operation", ns):
            op_name = op.attrib.get("name")
            input_el = op.find("wsdl:input", ns)
            output_el = op.find("wsdl:output", ns)
            fault_els = op.findall("wsdl:fault", ns)
            operations.append({
                "name": op_name,
                "input_message": input_el.attrib.get("message") if input_el is not None else None,
                "output_message": output_el.attrib.get("message") if output_el is not None else None,
                "faults": [f.attrib.get("name") for f in fault_els if f is not None],
            })
        port_types.append({"name": pt_name, "operations": operations})

    messages = []
    for msg in root.findall("wsdl:message", ns):
        msg_name = msg.attrib.get("name")
        parts = []
        for part in msg.findall("wsdl:part", ns):
            parts.append({
                "name": part.attrib.get("name"),
                "element": part.attrib.get("element"),
                "type": part.attrib.get("type"),
            })
        messages.append({"name": msg_name, "parts": parts})

    # Capture embedded XSD types in wsdl:types if present
    embedded_types = []
    for types_el in root.findall("wsdl:types", ns):
        # Raw serialization of the types section for now
        embedded_types.append(ET.tostring(types_el, encoding="unicode"))

    return {
        "file": str(wsdl_path),
        "targetNamespace": target_ns,
        "portTypes": port_types,
        "messages": messages,
        "embeddedTypesRaw": embedded_types,
    }


def _parse_xsd(xsd_path: Path) -> Dict[str, Any]:
    ns = {"xsd": "http://www.w3.org/2001/XMLSchema"}
    try:
        tree = ET.parse(xsd_path)
    except ET.ParseError:
        return {"file": str(xsd_path), "error": "parse_error"}
    root = tree.getroot()
    target_ns = root.attrib.get("targetNamespace")

    complex_types = []
    for ct in root.findall("xsd:complexType", ns):
        name = ct.attrib.get("name")
        complex_types.append({"name": name})

    elements = []
    for el in root.findall("xsd:element", ns):
        name = el.attrib.get("name")
        type_name = el.attrib.get("type")
        elements.append({"name": name, "type": type_name})

    return {
        "file": str(xsd_path),
        "targetNamespace": target_ns,
        "complexTypes": complex_types,
        "elements": elements,
    }


def build_contracts(manifest: Dict[str, Any]) -> Dict[str, Any]:
    artifacts: List[Dict[str, Any]] = manifest.get("artifacts", [])
    # Prefer absolute paths from manifest for robust parsing
    wsdl_files = [Path(a.get("abs_path", a.get("path", ""))) for a in artifacts if a.get("type") == "wsdl"]
    xsd_files = [Path(a.get("abs_path", a.get("path", ""))) for a in artifacts if a.get("type") == "xsd_schema"]

    wsdl_contracts = [_parse_wsdl(p) for p in wsdl_files]
    xsd_types = [_parse_xsd(p) for p in xsd_files]

    # Basic association by targetNamespace where possible
    types_by_ns: Dict[str, Dict[str, Any]] = {}
    for xt in xsd_types:
        ns_uri = xt.get("targetNamespace")
        if ns_uri:
            types_by_ns.setdefault(ns_uri, {"complexTypes": [], "elements": []})
            types_by_ns[ns_uri]["complexTypes"].extend(xt.get("complexTypes", []))
            types_by_ns[ns_uri]["elements"].extend(xt.get("elements", []))

    for wc in wsdl_contracts:
        ns_uri = wc.get("targetNamespace")
        if ns_uri and ns_uri in types_by_ns:
            wc["types"] = types_by_ns[ns_uri]
        else:
            wc["types"] = {"complexTypes": [], "elements": []}

    return {
        "contracts": wsdl_contracts,
        "types": xsd_types,
        "summary": {
            "wsdl_count": len(wsdl_contracts),
            "xsd_count": len(xsd_types),
            "operations_count": sum(len(pt.get("operations", [])) for c in wsdl_contracts for pt in c.get("portTypes", [])),
        },
    }


def main():
    parser = argparse.ArgumentParser(description="Extract contracts from WSDL/XSD files")
    parser.add_argument("--manifest", required=True, help="Path to artifact manifest JSON")
    parser.add_argument("--output", required=True, help="Path to write contracts JSON")
    args = parser.parse_args()

    manifest = _load_manifest(Path(args.manifest))
    contracts = build_contracts(manifest)
    Path(args.output).write_text(json.dumps(contracts, indent=2))
    print(f"Contracts written to {args.output}")


if __name__ == "__main__":
    main()