from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Any, Dict, List

import xml.etree.ElementTree as ET


def _namespace_to_package(ns_uri: str) -> str:
    # Basic heuristic: strip scheme, replace '/' and '-' with '.', remove trailing slashes
    if ns_uri.startswith("http://"):
        ns = ns_uri[len("http://"):]
    elif ns_uri.startswith("https://"):
        ns = ns_uri[len("https://"):]
    else:
        ns = ns_uri
    ns = ns.strip("/")
    ns = ns.replace("/", ".").replace("-", "_")
    # Ensure starts with a letter where possible
    return ns


def _parse_xsd_with_fields(xsd_path: Path) -> Dict[str, Any]:
    ns = {"xsd": "http://www.w3.org/2001/XMLSchema"}
    try:
        tree = ET.parse(xsd_path)
    except ET.ParseError:
        return {"file": str(xsd_path), "error": "parse_error"}
    root = tree.getroot()
    target_ns = root.attrib.get("targetNamespace")

    classes: List[Dict[str, Any]] = []
    def extract_fields(container) -> List[Dict[str, Any]]:
        out: List[Dict[str, Any]] = []
        # sequence elements
        for seq in container.findall("xsd:sequence", ns):
            for el in seq.findall("xsd:element", ns):
                out.append({
                    "name": el.attrib.get("name"),
                    "type": el.attrib.get("type"),
                    "minOccurs": el.attrib.get("minOccurs"),
                    "maxOccurs": el.attrib.get("maxOccurs"),
                })
        # all elements
        for all_el in container.findall("xsd:all", ns):
            for el in all_el.findall("xsd:element", ns):
                out.append({
                    "name": el.attrib.get("name"),
                    "type": el.attrib.get("type"),
                    "minOccurs": el.attrib.get("minOccurs"),
                    "maxOccurs": el.attrib.get("maxOccurs"),
                })
        # choice elements
        for choice in container.findall("xsd:choice", ns):
            for el in choice.findall("xsd:element", ns):
                out.append({
                    "name": el.attrib.get("name"),
                    "type": el.attrib.get("type"),
                    "minOccurs": el.attrib.get("minOccurs"),
                    "maxOccurs": el.attrib.get("maxOccurs"),
                    "choice": True,
                })
        return out

    # complexType fields under sequences/all/choice and complexContent extensions
    for ct in root.findall("xsd:complexType", ns):
        name = ct.attrib.get("name")
        fields: List[Dict[str, Any]] = []
        attributes: List[Dict[str, Any]] = []
        base_type = None

        # Direct containers
        fields.extend(extract_fields(ct))
        # Attributes on the complexType
        for attr in ct.findall("xsd:attribute", ns):
            attributes.append({
                "name": attr.attrib.get("name"),
                "type": attr.attrib.get("type"),
                "use": attr.attrib.get("use"),
            })

        # complexContent with extension/restriction
        for cc in ct.findall("xsd:complexContent", ns):
            ext = cc.find("xsd:extension", ns)
            res = cc.find("xsd:restriction", ns)
            node = ext or res
            if node is not None:
                base_type = node.attrib.get("base")
                fields.extend(extract_fields(node))
                for attr in node.findall("xsd:attribute", ns):
                    attributes.append({
                        "name": attr.attrib.get("name"),
                        "type": attr.attrib.get("type"),
                        "use": attr.attrib.get("use"),
                    })

        classes.append({
            "name": name,
            "namespace": target_ns,
            "base_type": base_type,
            "fields": fields,
            "attributes": attributes,
            "source": str(xsd_path),
        })

    # Top-level elements
    elements: List[Dict[str, Any]] = []
    for el in root.findall("xsd:element", ns):
        simple_type_info = None
        st = el.find("xsd:simpleType", ns)
        if st is not None:
            # capture enumeration values and basic restrictions
            enums = [e.attrib.get("value") for e in st.findall("xsd:restriction/xsd:enumeration", ns)]
            pattern = None
            pat = st.find("xsd:restriction/xsd:pattern", ns)
            if pat is not None:
                pattern = pat.attrib.get("value")
            simple_type_info = {
                "enumerations": [v for v in enums if v is not None],
                "pattern": pattern,
            }
        elements.append({
            "name": el.attrib.get("name"),
            "type": el.attrib.get("type"),
            "namespace": target_ns,
            "source": str(xsd_path),
            "simpleType": simple_type_info,
        })

    return {
        "file": str(xsd_path),
        "targetNamespace": target_ns,
        "classes": classes,
        "elements": elements,
    }


def build_dto_plan(manifest: Dict[str, Any], package_root: str | None = None) -> Dict[str, Any]:
    artifacts: List[Dict[str, Any]] = manifest.get("artifacts", [])
    xsd_files = [Path(a.get("abs_path", a.get("path", ""))) for a in artifacts if a.get("type") == "xsd_schema"]

    parsed: List[Dict[str, Any]] = [_parse_xsd_with_fields(p) for p in xsd_files]
    packages: Dict[str, str] = {}
    classes_out: List[Dict[str, Any]] = []
    elements_out: List[Dict[str, Any]] = []

    for entry in parsed:
        ns_uri = entry.get("targetNamespace")
        if ns_uri and ns_uri not in packages:
            derived = _namespace_to_package(ns_uri)
            packages[ns_uri] = f"{package_root}.{derived}" if package_root else derived
        # Emit classes and attach package
        for cls in entry.get("classes", []):
            pkg = packages.get(cls.get("namespace")) if cls.get("namespace") else None
            classes_out.append({
                "name": cls.get("name"),
                "namespace": cls.get("namespace"),
                "package": pkg,
                "fields": cls.get("fields", []),
                "base_type": cls.get("base_type"),
                "attributes": cls.get("attributes", []),
                "source": cls.get("source"),
                "constraints": [],
            })
        # Emit elements
        for el in entry.get("elements", []):
            pkg = packages.get(el.get("namespace")) if el.get("namespace") else None
            elements_out.append({
                "name": el.get("name"),
                "type": el.get("type"),
                "namespace": el.get("namespace"),
                "package": pkg,
                "source": el.get("source"),
                "simpleType": el.get("simpleType"),
            })

    plan = {
        "packages": packages,
        "classes": classes_out,
        "elements": elements_out,
        "summary": {
            "class_count": len(classes_out),
            "element_count": len(elements_out),
            "namespace_count": len(packages),
        },
    }
    return plan


def main():
    parser = argparse.ArgumentParser(description="Generate DTO plan from XSDs")
    parser.add_argument("--manifest", required=True, help="Path to artifact manifest JSON")
    parser.add_argument("--output", required=True, help="Path to write DTO plan JSON")
    parser.add_argument("--package-root", default=None, help="Optional package root prefix (e.g., com.chubb.ei)")
    args = parser.parse_args()

    manifest = json.loads(Path(args.manifest).read_text())
    plan = build_dto_plan(manifest, package_root=args.package_root)
    Path(args.output).write_text(json.dumps(plan, indent=2))
    print(f"DTO plan written to {args.output}")


if __name__ == "__main__":
    main()