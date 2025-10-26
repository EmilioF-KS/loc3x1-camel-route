#!/usr/bin/env python3
"""
Schema Governance Agent (LOC-003)

Reads contracts/inventory.json and produces:
- contracts/canonical-map.yaml: canonical schema selections and variant groups
- doc/.preflight/schema-governance.md: CI checklist and governance summary

Heuristics:
- Group XSD namespaces by a normalised base (strip trailing xN suffixes)
- Normalise trailing letter variants (e.g., loc3x1m → loc3x1)
- Prefer canonical namespace without suffix when present; otherwise choose lexicographically

This is deterministic and does not rely on LLMs. It is venv-friendly.
"""
from pathlib import Path
import json
import re
from typing import Dict, Any, List, Tuple
from datetime import datetime
import yaml

PROJECT_ROOT = Path(__file__).resolve().parents[1]
INVENTORY_JSON = PROJECT_ROOT / "contracts" / "inventory.json"
CANONICAL_MAP = PROJECT_ROOT / "contracts" / "canonical-map.yaml"
from scripts.utils.paths import PREVIEW_DIR, ensure_results_dirs
GOVERNANCE_MD = PREVIEW_DIR / "schema-governance.md"


def _normalise_namespace(ns: str) -> str:
    # Operate on last path segment for variant handling
    parts = ns.rsplit('/', 1)
    if len(parts) == 1:
        return ns
    base, last = parts
    # Normalise trailing letter after numeric x-version (e.g. loc3x1m -> loc3x1)
    last_norm = re.sub(r"([a-z0-9]+x[0-9]+)[a-z]$", r"\\1", last)
    # Strip xN suffixes for core variant namespaces (e.g. addressx3 -> address)
    last_norm = re.sub(r"x[0-9]+$", "", last_norm)
    return f"{base}/{last_norm}"


def load_inventory(root: Path) -> Dict[str, Any]:
    if not INVENTORY_JSON.exists():
        raise FileNotFoundError(f"Inventory JSON not found at {INVENTORY_JSON}. Run contracts stage first.")
    data = json.loads(INVENTORY_JSON.read_text(encoding="utf-8"))
    return data


def build_canonical_groups(inv: Dict[str, Any]) -> Tuple[List[Dict[str, Any]], Dict[str, Any]]:
    xsd_entries = inv.get("xsd_files", [])
    by_ns: Dict[str, List[Dict[str, Any]]] = {}
    for x in xsd_entries:
        ns = x.get("target_namespace") or ""
        if not ns:
            # Skip if no namespace
            continue
        by_ns.setdefault(ns, []).append(x)

    # Group by normalised namespace
    groups: Dict[str, Dict[str, Any]] = {}
    for ns, entries in by_ns.items():
        base = _normalise_namespace(ns)
        g = groups.setdefault(base, {"base_namespace": base, "variants": set(), "entries": []})
        g["variants"].add(ns)
        g["entries"].extend(entries)

    canonical_groups: List[Dict[str, Any]] = []
    for base, g in groups.items():
        variants = sorted(list(g["variants"]))
        # Prefer exact base if present; else lexicographically first
        if base in variants:
            canonical_ns = base
        else:
            canonical_ns = variants[0]
        # Pick a representative file for canonical
        canonical_file = None
        for e in g["entries"]:
            if e.get("target_namespace") == canonical_ns:
                canonical_file = e.get("file_path") or e.get("absolute_path")
                break
        canonical_groups.append({
            "id": _ns_id(base),
            "base_namespace": base,
            "canonical_namespace": canonical_ns,
            "variants": variants,
            "canonical_file": canonical_file,
        })

    summary = {
        "generated_at": datetime.now().isoformat(),
        "xsd_total": len(xsd_entries),
        "groups": len(canonical_groups),
        "variant_groups": sum(1 for g in canonical_groups if len(g["variants"]) > 1),
    }
    return canonical_groups, summary


def _ns_id(ns: str) -> str:
    # Create a simple identifier like ei.core.address
    try:
        parts = ns.split('//', 1)[-1].split('/')
        parts = [p for p in parts if p]
        return '.'.join(parts)
    except Exception:
        return ns


def write_canonical_map(groups: List[Dict[str, Any]], summary: Dict[str, Any], out: Path) -> Path:
    payload = {
        "summary": summary,
        "canonical_groups": groups,
        "rules": [
            "Prefer canonical namespaces without xN suffix when available",
            "Normalise trailing letter variants after numeric version (e.g., loc3x1m → loc3x1)",
            "WSDL/XSD imports should reference canonical namespaces where a group exists",
        ],
        "ci_policies": [
            "Fail if inventory namespaces missing from inventory.md",
            "Fail if XSD import cycles involve PrivateSchemas",
            "Warn on unresolved XSD imports; allow known gaps (Location.xsd variants)",
        ],
    }
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(yaml.safe_dump(payload, sort_keys=False), encoding="utf-8")
    return out


def write_governance_md(groups: List[Dict[str, Any]], summary: Dict[str, Any], out: Path) -> Path:
    lines: List[str] = []
    lines.append("# Schema Governance Checklist\n\n")
    lines.append(f"Generated: {summary['generated_at']}\n")
    lines.append(f"XSD Total: {summary['xsd_total']}\n")
    lines.append(f"Groups: {summary['groups']}\n")
    lines.append(f"Variant Groups: {summary['variant_groups']}\n\n")

    lines.append("## Rules\n")
    lines.append("- Prefer canonical namespaces without xN suffix when available\n")
    lines.append("- Normalise trailing letter variants after numeric version (e.g., loc3x1m → loc3x1)\n")
    lines.append("- Ensure imports reference canonical namespaces where groups exist\n\n")

    lines.append("## Canonical Groups\n")
    for g in groups[:100]:  # limit output for brevity
        lines.append(f"- id: `{g['id']}`\n")
        lines.append(f"  base: `{g['base_namespace']}`\n")
        lines.append(f"  canonical: `{g['canonical_namespace']}`\n")
        lines.append(f"  file: `{g.get('canonical_file')}`\n")
        if len(g['variants']) > 1:
            lines.append(f"  variants: {', '.join(g['variants'])}\n")
    lines.append("\nNote: Full list is available in `contracts/canonical-map.yaml`.\n")

    ensure_results_dirs()
    out.write_text(''.join(lines), encoding='utf-8')
    return out


def generate(root: Path | None = None) -> Dict[str, Any]:
    inv = load_inventory(PROJECT_ROOT)
    groups, summary = build_canonical_groups(inv)
    map_out = write_canonical_map(groups, summary, CANONICAL_MAP)
    md_out = write_governance_md(groups, summary, GOVERNANCE_MD)
    return {
        "canonical_map": str(map_out),
        "governance_md": str(md_out),
        "summary": summary,
        "ok": Path(map_out).exists() and Path(md_out).exists(),
    }


if __name__ == "__main__":
    res = generate(PROJECT_ROOT)
    print(json.dumps(res, indent=2))