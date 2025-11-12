from __future__ import annotations

import json
import re
from pathlib import Path
from typing import Dict, List, Optional
import hashlib
from datetime import datetime

import xml.etree.ElementTree as ET


ARTIFACT_TYPES = {
    ".xsd": "xsd_schema",
    ".wsdl": "wsdl",
    ".bpel": "bpel",
    ".xsl": "xslt",
    ".xslt": "xslt",
    ".xml": "xml",
    ".txt": "text_doc",
    ".pdf": "pdf_doc",
    ".tex": "tex_doc",
}


def classify_file(path: Path) -> str:
    ext = path.suffix.lower()
    base = path.name.lower()
    t = ARTIFACT_TYPES.get(ext, "unknown")

    # Disambiguate XMLs by heuristics on filename/location
    if t == "xml":
        if "mediation" in base or base.endswith("_mediation.xml"):
            return "ibm_mediation"
        if "map" in base:
            return "ibm_map"
        # Samples and outputs by directory hints
        parts = {p.lower() for p in path.parts}
        if "output" in parts:
            return "sample_output"
        if "sample" in parts or "input" in parts:
            return "sample_input"
        return "xml"

    return t


def _extract_imports_xml(path: Path) -> List[str]:
    """Extract import/include references from XSD/WSDL/BPEL-like XML files."""
    refs: List[str] = []
    try:
        tree = ET.parse(path)
        root = tree.getroot()
    except Exception:
        return refs

    # Namespaces common in XSD/WSDL/BPEL
    ns = {
        "xsd": "http://www.w3.org/2001/XMLSchema",
        "wsdl": "http://schemas.xmlsoap.org/wsdl/",
        "bpel": "http://docs.oasis-open.org/wsbpel/2.0/process/executable",
    }

    # XSD imports/includes
    for el in root.findall(".//{http://www.w3.org/2001/XMLSchema}import"):
        loc = el.attrib.get("schemaLocation")
        if loc:
            refs.append(loc)
    for el in root.findall(".//{http://www.w3.org/2001/XMLSchema}include"):
        loc = el.attrib.get("schemaLocation")
        if loc:
            refs.append(loc)

    # WSDL imports
    for el in root.findall(".//{http://schemas.xmlsoap.org/wsdl/}import"):
        loc = el.attrib.get("location") or el.attrib.get("schemaLocation")
        if loc:
            refs.append(loc)

    # BPEL imports (e.g., <import location="..." namespace="..." importType="..."/>)
    for el in root.findall(".//{http://docs.oasis-open.org/wsbpel/2.0/process/executable}import"):
        loc = el.attrib.get("location")
        if loc:
            refs.append(loc)

    # IBM map references (heuristic)
    for el in root.findall(".//*[@mapRef]"):
        loc = el.attrib.get("mapRef")
        if loc:
            refs.append(loc)

    # Normalize and filter obvious noise
    refs = [r.strip() for r in refs if r and not r.strip().startswith(("http://", "https://"))]
    return refs


def discover_artifacts(root: Path) -> List[Dict]:
    artifacts: List[Dict] = []
    for p in root.rglob("*"):
        if not p.is_file():
            continue
        a_type = classify_file(p)
        # File metadata for change detection
        stat = p.stat()
        size_bytes = stat.st_size
        mtime_iso = datetime.fromtimestamp(stat.st_mtime).isoformat()
        mtime_epoch = int(stat.st_mtime)
        file_mode = int(stat.st_mode)
        # Hash in binary mode
        sha256 = hashlib.sha256()
        try:
            with p.open("rb") as fh:
                for chunk in iter(lambda: fh.read(1024 * 1024), b""):
                    sha256.update(chunk)
            digest = sha256.hexdigest()
        except Exception:
            digest = None

        record: Dict[str, Optional[str] | List[str] | int] = {
            "path": str(p.relative_to(root)),
            "abs_path": str(p.resolve()),
            "type": a_type,
            "size_bytes": size_bytes,
            "sha256": digest,
            "modified_time": mtime_iso,
            "mtime_epoch": mtime_epoch,
            "file_mode": file_mode,
        }
        # Extract imports for XML-like types
        if a_type in {"xsd_schema", "wsdl", "bpel", "ibm_mediation", "ibm_map", "xml"}:
            imports = _extract_imports_xml(p)
            if imports:
                record["imports"] = imports
        artifacts.append(record)
    return artifacts


def write_manifest(output_path: Path, artifacts: List[Dict], scan_root: Optional[Path] = None) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with output_path.open("w", encoding="utf-8") as f:
        try:
            # Lazy import to avoid cycles
            from agent.src import VERSION as TOOL_VERSION
        except Exception:
            TOOL_VERSION = "unknown"

        info = {
            "root_path": str(scan_root.resolve()) if scan_root else None,
            "artifact_count": len(artifacts),
            "scan_time": datetime.utcnow().isoformat() + "Z",
            "tool_version": TOOL_VERSION,
        }
        json.dump({"scan_info": info, "artifacts": artifacts}, f, indent=2)


def main(input_dir: str, output_file: Optional[str] = None) -> Path:
    root = Path(input_dir).resolve()
    if not root.exists() or not root.is_dir():
        raise FileNotFoundError(f"Input directory not found: {root}")
    artifacts = discover_artifacts(root)
    out = Path(output_file) if output_file else Path("agent/manifest.json")
    write_manifest(out, artifacts, root)
    return out


if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description="Discover artifacts and generate manifest JSON")
    parser.add_argument("--input", required=True, help="Path to input folder (e.g., CHUBB/Dependencies)")
    parser.add_argument("--output", default="agent/manifest.json", help="Output manifest file path")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite output file if exists")
    args = parser.parse_args()

    out_path = Path(args.output)
    if out_path.exists() and not args.overwrite:
        raise FileExistsError(f"Output file exists: {out_path}. Use --overwrite to replace.")

    result = main(args.input, args.output)
    print(f"Manifest written to {result}")