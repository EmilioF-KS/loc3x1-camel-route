import json
import re
from pathlib import Path
from typing import Dict, List, Tuple, Any, Set
import xml.etree.ElementTree as ET


def _load_inventory_json(inv_json_path: Path) -> Dict[str, Any]:
    data = json.loads(inv_json_path.read_text(encoding="utf-8"))
    return data


def _load_inventory_md(inv_md_path: Path) -> str:
    return inv_md_path.read_text(encoding="utf-8")


def _parse_md_counts(md_text: str) -> Dict[str, int]:
    # Accept optional Markdown bold markers and varied spacing
    def find_count(label: str) -> int:
        m = re.search(rf"\b{label}\s*:\*\*?\s*(\d+)", md_text, re.IGNORECASE)
        if not m:
            m = re.search(rf"\b{label}\s*:\s*(\d+)", md_text, re.IGNORECASE)
        return int(m.group(1)) if m else -1

    return {
        "wsdl": find_count("WSDL Files"),
        "xsd": find_count("XSD Files"),
        "bpel": find_count("BPEL Files"),
        "total": find_count("Total Files"),
    }


def _parse_md_namespaces(md_text: str) -> List[str]:
    # Namespaces are listed as markdown bullets with backticks
    ns: List[str] = []
    for line in md_text.splitlines():
        line = line.strip()
        if line.startswith("-") and "`" in line:
            m = re.search(r"`([^`]+)`", line)
            if m:
                ns.append(m.group(1).strip())
    return ns


def _resolve_relative(base_file: Path, rel: str) -> Path:
    # Handle ../ and sibling paths relative to the base file directory
    return (base_file.parent / rel).resolve()


def _validate_file_existence(wsdl_files: List[Dict[str, Any]], xsd_files: List[Dict[str, Any]]) -> Tuple[int, List[str]]:
    missing: List[str] = []
    ok_count = 0
    for e in wsdl_files + xsd_files:
        p = Path(e.get("absolute_path", e.get("file_path", "")))
        if p.exists():
            ok_count += 1
        else:
            missing.append(str(p))
    return ok_count, missing


def _validate_wsdl_imports(wsdl_files: List[Dict[str, Any]]) -> Tuple[int, List[str]]:
    ok = 0
    unresolved: List[str] = []
    for w in wsdl_files:
        base = Path(w.get("absolute_path", w.get("file_path", "")))
        for imp in w.get("imports", []):
            location = imp.get("schemaLocation") or imp.get("location")
            if not location:
                unresolved.append(f"{base}: missing import location")
                continue
            resolved = _resolve_relative(base, location)
            if resolved.exists():
                ok += 1
            else:
                unresolved.append(f"{base}: import not found -> {resolved}")
    return ok, unresolved


def _validate_xsd_imports(xsd_files: List[Dict[str, Any]]) -> Tuple[int, List[str]]:
    ok = 0
    unresolved: List[str] = []
    for x in xsd_files:
        base = Path(x.get("absolute_path", x.get("file_path", "")))
        for imp in x.get("imports", []):
            location = imp.get("schemaLocation") or imp.get("location")
            if not location:
                unresolved.append(f"{base}: missing import location")
                continue
            resolved = _resolve_relative(base, location)
            if resolved.exists():
                ok += 1
            else:
                unresolved.append(f"{base}: import not found -> {resolved}")
    return ok, unresolved


def _collect_json_namespaces(inv: Dict[str, Any]) -> Dict[str, Any]:
    ns_targets: List[str] = []
    ns_imports: List[str] = []
    for w in inv.get("wsdl_files", []):
        tns = w.get("target_namespace")
        if tns:
            ns_targets.append(tns)
        for imp in w.get("imports", []):
            n = imp.get("namespace")
            if n:
                ns_imports.append(n)
    for x in inv.get("xsd_files", []):
        tns = x.get("target_namespace")
        if tns:
            ns_targets.append(tns)
        for imp in x.get("imports", []):
            n = imp.get("namespace")
            if n:
                ns_imports.append(n)
    return {
        "targets": sorted(set(ns_targets)),
        "imports": sorted(set(ns_imports)),
        "all": sorted(set(ns_targets) | set(ns_imports)),
    }

# -------------------- XML-based Robust Checks --------------------

def _xml_local(tag: str) -> str:
    return tag.split('}')[-1] if '}' in tag else tag


def _xsd_target_namespace(path: Path) -> str | None:
    try:
        root = ET.parse(path).getroot()
        if _xml_local(root.tag).lower() == 'schema':
            return root.attrib.get('targetNamespace')
    except Exception:
        return None
    return None


def _validate_xsd_target_namespaces(xsd_files: List[Dict[str, Any]]) -> List[str]:
    mismatches: List[str] = []
    for x in xsd_files:
        abs_path = Path(x.get("absolute_path", x.get("file_path", "")))
        inv_tns = x.get("target_namespace")
        parsed_tns = _xsd_target_namespace(abs_path)
        if inv_tns and parsed_tns and inv_tns != parsed_tns:
            mismatches.append(f"{abs_path}: inventory '{inv_tns}' != parsed '{parsed_tns}'")
    return mismatches


def _build_xsd_import_graph(xsd_files: List[Dict[str, Any]]) -> Dict[Path, List[Path]]:
    graph: Dict[Path, List[Path]] = {}
    for x in xsd_files:
        base = Path(x.get("absolute_path", x.get("file_path", "")))
        deps: List[Path] = []
        for imp in x.get("imports", []):
            location = imp.get("schemaLocation") or imp.get("location")
            if not location:
                continue
            deps.append(_resolve_relative(base, location))
        graph[base] = deps
    return graph


def _detect_cycles_in_graph(graph: Dict[Path, List[Path]]) -> List[List[str]]:
    visited: Set[Path] = set()
    stack: Set[Path] = set()
    cycles: List[List[str]] = []
    path_stack: List[Path] = []

    def dfs(node: Path):
        visited.add(node)
        stack.add(node)
        path_stack.append(node)
        for nei in graph.get(node, []):
            if nei not in visited:
                dfs(nei)
            elif nei in stack:
                # cycle found; slice path from nei to end
                try:
                    idx = path_stack.index(nei)
                    cycle = path_stack[idx:] + [nei]
                    cycles.append([str(p) for p in cycle])
                except ValueError:
                    pass
        stack.remove(node)
        path_stack.pop()

    for node in graph.keys():
        if node not in visited:
            dfs(node)
    return cycles


def _parse_wsdl_operations(path: Path) -> Set[str]:
    ops: Set[str] = set()
    try:
        root = ET.parse(path).getroot()
        # find all portType/operation names
        for pt in root.iter():
            if _xml_local(pt.tag).lower() == 'porttype':
                for op in list(pt):
                    if _xml_local(op.tag).lower() == 'operation':
                        name = op.attrib.get('name')
                        if name:
                            ops.add(name)
    except Exception:
        return ops
    return ops


def _parse_wsdl_binding_names(path: Path) -> Set[str]:
    names: Set[str] = set()
    try:
        root = ET.parse(path).getroot()
        for b in root.iter():
            if _xml_local(b.tag).lower() == 'binding':
                name = b.attrib.get('name')
                if name:
                    names.add(name)
    except Exception:
        return names
    return names


def _validate_wsdl_details(wsdl_files: List[Dict[str, Any]]) -> Tuple[List[str], List[str]]:
    op_mismatches: List[str] = []
    binding_mismatches: List[str] = []
    for w in wsdl_files:
        abs_path = Path(w.get("absolute_path", w.get("file_path", "")))
        inv_ops = set(w.get("operations", []) or [])
        parsed_ops = _parse_wsdl_operations(abs_path)
        # Only check if inventory declares operations
        if inv_ops and parsed_ops and inv_ops != parsed_ops:
            op_mismatches.append(f"{abs_path}: inventory ops {sorted(inv_ops)} != parsed ops {sorted(parsed_ops)}")
        # Binding names cross-check
        inv_binding_names = {b.get('name') for b in (w.get('bindings', []) or []) if b.get('name')}
        parsed_bindings = _parse_wsdl_binding_names(abs_path)
        if inv_binding_names and parsed_bindings and not inv_binding_names.issubset(parsed_bindings):
            missing = sorted(list(inv_binding_names - parsed_bindings))
            binding_mismatches.append(f"{abs_path}: missing bindings {missing}")
    return op_mismatches, binding_mismatches


def validate_contracts(project_root: Path | None = None) -> Dict[str, Any]:
    root = Path(project_root) if project_root else Path(__file__).resolve().parent.parent
    inv_json_path = root / "contracts" / "inventory.json"
    inv_md_path = root / "contracts" / "inventory.md"

    inv = _load_inventory_json(inv_json_path)
    md_text = _load_inventory_md(inv_md_path)

    # Counts
    md_counts = _parse_md_counts(md_text)
    json_counts = {
        "wsdl": len(inv.get("wsdl_files", [])),
        "xsd": len(inv.get("xsd_files", [])),
        "bpel": inv.get("metadata", {}).get("bpel_count", -1),  # optional, -1 if not tracked in JSON
        "total": len(inv.get("wsdl_files", [])) + len(inv.get("xsd_files", [])) + (inv.get("metadata", {}).get("bpel_count", 0) or 0),
    }
    # Robust matching: require WSDL/XSD counts to match; tolerate TOTAL/BPEL differences when JSON doesn't track BPEL
    wsdl_match = (md_counts["wsdl"] == -1 or md_counts["wsdl"] == json_counts["wsdl"]) 
    xsd_match = (md_counts["xsd"] == -1 or md_counts["xsd"] == json_counts["xsd"])   
    if json_counts["bpel"] == -1:
        total_match = True  # JSON doesn't track BPEL; MD total may include BPEL
        bpel_match = True   # Don't enforce when JSON lacks bpel count
    else:
        total_match = (md_counts["total"] == -1 or md_counts["total"] == json_counts["total"]) 
        bpel_match = (md_counts["bpel"] == -1 or md_counts["bpel"] == json_counts["bpel"])      
    counts_match = wsdl_match and xsd_match and total_match and bpel_match

    # File existence
    ok_files, missing_files = _validate_file_existence(inv.get("wsdl_files", []), inv.get("xsd_files", []))

    # Imports resolution
    ok_wsdl_imports, unresolved_wsdl = _validate_wsdl_imports(inv.get("wsdl_files", []))
    ok_xsd_imports, unresolved_xsd = _validate_xsd_imports(inv.get("xsd_files", []))

    # Namespaces
    md_namespaces = _parse_md_namespaces(md_text)
    json_ns = _collect_json_namespaces(inv)
    json_targets_not_in_md = sorted([n for n in json_ns["targets"] if n not in md_namespaces])
    md_extras = sorted([n for n in md_namespaces if n not in json_ns["all"]])

    # XML-based checks
    xsd_ns_mismatches = _validate_xsd_target_namespaces(inv.get("xsd_files", []))
    xsd_graph = _build_xsd_import_graph(inv.get("xsd_files", []))
    xsd_cycles = _detect_cycles_in_graph(xsd_graph)
    wsdl_op_mismatches, wsdl_binding_mismatches = _validate_wsdl_details(inv.get("wsdl_files", []))

    issues: List[str] = []
    if not counts_match:
        issues.append("Counts mismatch between inventory.md and inventory.json")
    if missing_files:
        issues.append(f"Missing files: {len(missing_files)}")
    if unresolved_wsdl or unresolved_xsd:
        issues.append(f"Unresolved imports: wsdl={len(unresolved_wsdl)}, xsd={len(unresolved_xsd)}")
    if json_targets_not_in_md:
        issues.append(f"JSON target namespaces not listed in MD: {len(json_targets_not_in_md)}")
    if xsd_ns_mismatches:
        issues.append(f"XSD targetNamespace mismatches: {len(xsd_ns_mismatches)}")
    if xsd_cycles:
        issues.append(f"XSD import cycles detected: {len(xsd_cycles)}")
    if wsdl_op_mismatches:
        issues.append(f"WSDL operation mismatches: {len(wsdl_op_mismatches)}")
    if wsdl_binding_mismatches:
        issues.append(f"WSDL binding mismatches: {len(wsdl_binding_mismatches)}")

    summary = {
        "ok_files": ok_files,
        "missing_files": len(missing_files),
        "ok_wsdl_imports": ok_wsdl_imports,
        "ok_xsd_imports": ok_xsd_imports,
        "unresolved_wsdl": len(unresolved_wsdl),
        "unresolved_xsd": len(unresolved_xsd),
        "counts_match": counts_match,
        "json_targets_not_in_md": len(json_targets_not_in_md),
        "md_extras": len(md_extras),
        "xsd_ns_mismatches": len(xsd_ns_mismatches),
        "xsd_cycles": len(xsd_cycles),
        "wsdl_op_mismatches": len(wsdl_op_mismatches),
        "wsdl_binding_mismatches": len(wsdl_binding_mismatches),
    }

    return {
        "paths": {
            "inventory_json": str(inv_json_path),
            "inventory_md": str(inv_md_path),
        },
        "counts": {
            "json": json_counts,
            "md": md_counts,
            "match": counts_match,
        },
        "file_existence": {
            "ok_count": ok_files,
            "missing": missing_files,
        },
        "imports": {
            "ok_wsdl": ok_wsdl_imports,
            "ok_xsd": ok_xsd_imports,
            "unresolved_wsdl": unresolved_wsdl,
            "unresolved_xsd": unresolved_xsd,
        },
        "namespaces": {
            "json_targets": json_ns["targets"],
            "json_imports": json_ns["imports"],
            "md_list": md_namespaces,
            "json_target_not_in_md": json_targets_not_in_md,
            "md_extras": md_extras,
        },
        "xml": {
            "xsd_namespace_mismatches": xsd_ns_mismatches,
            "xsd_import_cycles": xsd_cycles,
            "wsdl_operation_mismatches": wsdl_op_mismatches,
            "wsdl_binding_mismatches": wsdl_binding_mismatches,
        },
        "summary": summary,
        "issues": issues,
    }


def write_report(result: Dict[str, Any], output_path: Path) -> Path:
    lines: List[str] = []
    lines.append("# Contracts Validator Report\n")
    lines.append("\n## Paths\n")
    lines.append(f"- `inventory.json`: {result['paths']['inventory_json']}\n")
    lines.append(f"- `inventory.md`: {result['paths']['inventory_md']}\n")

    lines.append("\n## Counts\n")
    lines.append(f"- JSON WSDL: {result['counts']['json']['wsdl']}\n")
    lines.append(f"- JSON XSD: {result['counts']['json']['xsd']}\n")
    lines.append(f"- JSON BPEL: {result['counts']['json']['bpel']}\n")
    lines.append(f"- JSON Total: {result['counts']['json']['total']}\n")
    lines.append(f"- MD WSDL: {result['counts']['md']['wsdl']}\n")
    lines.append(f"- MD XSD: {result['counts']['md']['xsd']}\n")
    lines.append(f"- MD BPEL: {result['counts']['md']['bpel']}\n")
    lines.append(f"- MD Total: {result['counts']['md']['total']}\n")
    lines.append(f"- Counts Match: {result['counts']['match']}\n")

    lines.append("\n## File Existence\n")
    lines.append(f"- OK Files: {result['file_existence']['ok_count']}\n")
    lines.append(f"- Missing Files: {len(result['file_existence']['missing'])}\n")
    for m in result['file_existence']['missing']:
        lines.append(f"  - {m}\n")

    lines.append("\n## Imports\n")
    lines.append(f"- OK WSDL Imports: {result['imports']['ok_wsdl']}\n")
    lines.append(f"- OK XSD Imports: {result['imports']['ok_xsd']}\n")
    lines.append(f"- Unresolved WSDL Imports: {len(result['imports']['unresolved_wsdl'])}\n")
    for u in result['imports']['unresolved_wsdl']:
        lines.append(f"  - {u}\n")
    lines.append(f"- Unresolved XSD Imports: {len(result['imports']['unresolved_xsd'])}\n")
    for u in result['imports']['unresolved_xsd']:
        lines.append(f"  - {u}\n")

    lines.append("\n## Namespaces\n")
    lines.append(f"- JSON Target Namespaces: {len(result['namespaces']['json_targets'])}\n")
    lines.append(f"- JSON Import Namespaces: {len(result['namespaces']['json_imports'])}\n")
    lines.append(f"- MD Namespaces: {len(result['namespaces']['md_list'])}\n")
    lines.append(f"- JSON Targets Missing in MD: {len(result['namespaces']['json_target_not_in_md'])}\n")
    lines.append(f"- MD Extras Not in JSON: {len(result['namespaces']['md_extras'])}\n")

    lines.append("\n## XML Validation\n")
    lines.append(f"- XSD targetNamespace mismatches: {len(result['xml']['xsd_namespace_mismatches'])}\n")
    for x in result['xml']['xsd_namespace_mismatches']:
        lines.append(f"  - {x}\n")
    lines.append(f"- XSD import cycles: {len(result['xml']['xsd_import_cycles'])}\n")
    for cyc in result['xml']['xsd_import_cycles']:
        lines.append(f"  - {' -> '.join(cyc)}\n")
    lines.append(f"- WSDL operation mismatches: {len(result['xml']['wsdl_operation_mismatches'])}\n")
    for w in result['xml']['wsdl_operation_mismatches']:
        lines.append(f"  - {w}\n")
    lines.append(f"- WSDL binding mismatches: {len(result['xml']['wsdl_binding_mismatches'])}\n")
    for w in result['xml']['wsdl_binding_mismatches']:
        lines.append(f"  - {w}\n")

    lines.append("\n## Summary\n")
    for k, v in result.get("summary", {}).items():
        lines.append(f"- {k.replace('_', ' ').title()}: {v}\n")

    lines.append("\n## Issues\n")
    if result.get("issues"):
        for issue in result["issues"]:
            lines.append(f"- {issue}\n")
    else:
        lines.append("- None\n")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text("".join(lines), encoding="utf-8")
    return output_path


if __name__ == "__main__":
    root = Path(__file__).resolve().parent.parent
    res = validate_contracts(root)
    out = root / "doc" / ".preflight" / "contracts-checks.md"
    write_report(res, out)
    print(f"Wrote contracts validator report to {out}")