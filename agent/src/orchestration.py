from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Any, Dict, List

import xml.etree.ElementTree as ET


def _load_manifest(manifest_path: Path) -> Dict[str, Any]:
    return json.loads(manifest_path.read_text())


def _parse_bpel(bpel_path: Path) -> Dict[str, Any]:
    ns = {
        "bpel": "http://docs.oasis-open.org/wsbpel/2.0/process/executable",
        "bpws": "http://schemas.xmlsoap.org/ws/2003/03/business-process/",
    }
    try:
        tree = ET.parse(bpel_path)
    except ET.ParseError:
        return {"file": str(bpel_path), "error": "parse_error", "steps": []}
    root = tree.getroot()

    steps: List[Dict[str, Any]] = []

    # Namespace-agnostic traversal to catch variations
    def ln(tag: str) -> str:
        return tag.split('}')[-1]

    for el in root.iter():
        tag = ln(el.tag)
        if tag == "receive":
            steps.append({
                "kind": "receive",
                "partnerLink": el.attrib.get("partnerLink"),
                "operation": el.attrib.get("operation"),
                "variable": el.attrib.get("variable"),
            })
        elif tag == "reply":
            steps.append({
                "kind": "reply",
                "partnerLink": el.attrib.get("partnerLink"),
                "operation": el.attrib.get("operation"),
                "variable": el.attrib.get("variable"),
            })
        elif tag == "assign":
            steps.append({
                "kind": "assign",
                "name": el.attrib.get("name"),
            })
        elif tag == "invoke":
            steps.append({
                "kind": "invoke",
                "partnerLink": el.attrib.get("partnerLink"),
                "portType": el.attrib.get("portType"),
                "operation": el.attrib.get("operation"),
                "inputVariable": el.attrib.get("inputVariable"),
                "outputVariable": el.attrib.get("outputVariable"),
            })
        elif tag in ("switch", "if"):
            steps.append({"kind": "choice"})
        elif tag == "flow":
            steps.append({"kind": "parallel"})
        elif tag == "pick":
            steps.append({"kind": "pick"})
        elif tag == "scope":
            catches = []
            for sub in el.iter():
                if ln(sub.tag) == "catch":
                    catches.append({"faultName": sub.attrib.get("faultName")})
            if catches:
                steps.append({"kind": "faultHandlers", "catches": catches})
        elif tag == "extensionActivity":
            steps.append({"kind": "log", "raw": ET.tostring(el, encoding="unicode")})

    return {"file": str(bpel_path), "steps": steps}


def _parse_mediation(med_path: Path) -> Dict[str, Any]:
    # IBM mediation files vary; capture common hints
    try:
        tree = ET.parse(med_path)
    except ET.ParseError:
        return {"file": str(med_path), "error": "parse_error", "steps": []}
    root = tree.getroot()

    steps: List[Dict[str, Any]] = []

    # Heuristic: namespace-agnostic detection of transforms, callouts, and logs
    def ln(tag: str) -> str:
        return tag.split('}')[-1]

    for el in root.iter():
        tag = ln(el.tag)
        attrs = el.attrib
        # Transform mappers
        if "mapRef" in attrs or "map" in attrs or ("ref" in attrs and (".map" in attrs["ref"] or "maps/" in attrs["ref"])):
            steps.append({"kind": "transform", "attrs": {k: attrs[k] for k in attrs}})
        # IBM WID primitives
        if tag == "primitive":
            kind = attrs.get("kind", "")
            if any(k in kind for k in ["Callout", "MessageLogger", "Mapper", "DataMapper"]):
                kmap = {
                    "Callout": "invoke",
                    "MessageLogger": "log",
                    "Mapper": "transform",
                    "DataMapper": "transform",
                }
                mapped_kind = next((kmap[k] for k in kmap if k in kind), "invoke")
                steps.append({"kind": mapped_kind, "primitiveKind": kind})
        # Generic tag names
        if tag in ("invoke", "callout"):
            steps.append({"kind": "invoke", "raw": ET.tostring(el, encoding="unicode")})
        elif tag == "log" or attrs.get("type") == "log":
            steps.append({"kind": "log", "raw": ET.tostring(el, encoding="unicode")})
        elif tag == "filter":
            steps.append({"kind": "filter"})
        elif tag == "enrich":
            steps.append({"kind": "enrich"})
        elif tag == "aggregate":
            steps.append({"kind": "aggregate"})

    return {"file": str(med_path), "steps": steps}


def build_orchestration_plan(manifest: Dict[str, Any]) -> Dict[str, Any]:
    artifacts: List[Dict[str, Any]] = manifest.get("artifacts", [])
    bpel_files = [Path(a.get("abs_path", a.get("path", ""))) for a in artifacts if a.get("type") == "bpel"]
    mediation_files = [Path(a.get("abs_path", a.get("path", ""))) for a in artifacts if a.get("type") == "ibm_mediation"]

    bpel_plans = [_parse_bpel(p) for p in bpel_files]
    med_plans = [_parse_mediation(p) for p in mediation_files]

    # Normalize into a single ordered plan per file type; at this stage, we keep per-file plans
    plan = {
        "bpel": bpel_plans,
        "mediation": med_plans,
        "summary": {
            "bpel_count": len(bpel_plans),
            "mediation_count": len(med_plans),
            "invoke_count": sum(1 for p in bpel_plans for s in p.get("steps", []) if s.get("kind") == "invoke")
                            + sum(1 for p in med_plans for s in p.get("steps", []) if s.get("kind") == "invoke"),
            "transform_count": sum(1 for p in med_plans for s in p.get("steps", []) if s.get("kind") == "transform")
                               + sum(1 for p in bpel_plans for s in p.get("steps", []) if s.get("kind") == "assign"),
        },
    }
    return plan


def main():
    parser = argparse.ArgumentParser(description="Build orchestration plan from BPEL and mediation XML")
    parser.add_argument("--manifest", required=True, help="Path to artifact manifest JSON")
    parser.add_argument("--output", required=True, help="Path to write orchestration plan JSON")
    parser.add_argument("--yaml-output", default=None, help="Optional path to write orchestration plan YAML (Emilio-aligned)")
    args = parser.parse_args()

    manifest = _load_manifest(Path(args.manifest))
    plan = build_orchestration_plan(manifest)
    Path(args.output).write_text(json.dumps(plan, indent=2))
    print(f"Orchestration plan written to {args.output}")

    # Optional YAML mirror for Emilio's step (Camel route represented in YAML; plan mirrored here for convenience)
    if args.yaml_output:
        def _yaml_scalar(value: Any) -> str:
            if value is None:
                return "null"
            if isinstance(value, bool):
                return "true" if value else "false"
            if isinstance(value, (int, float)):
                return str(value)
            # escape colon and special chars minimally
            s = str(value)
            if any(ch in s for ch in [":", "#", "-", "{", "}", "[", "]", "\n"]):
                return '"' + s.replace('"', '\\"') + '"'
            return s

        def _to_yaml(obj: Any, indent: int = 0) -> str:
            sp = " " * indent
            if isinstance(obj, dict):
                lines: List[str] = []
                for k, v in obj.items():
                    if isinstance(v, (dict, list)):
                        lines.append(f"{sp}{k}:")
                        lines.append(_to_yaml(v, indent + 2))
                    else:
                        lines.append(f"{sp}{k}: {_yaml_scalar(v)}")
                return "\n".join(lines)
            elif isinstance(obj, list):
                lines: List[str] = []
                for item in obj:
                    if isinstance(item, (dict, list)):
                        lines.append(f"{sp}-")
                        lines.append(_to_yaml(item, indent + 2))
                    else:
                        lines.append(f"{sp}- {_yaml_scalar(item)}")
                return "\n".join(lines)
            else:
                return f"{sp}{_yaml_scalar(obj)}"

        yaml_text = _to_yaml(plan) + "\n"
        Path(args.yaml_output).write_text(yaml_text)
        print(f"Orchestration plan YAML written to {args.yaml_output}")


if __name__ == "__main__":
    main()
