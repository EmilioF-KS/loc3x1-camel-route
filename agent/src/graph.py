from __future__ import annotations

from pathlib import Path
from typing import Dict, List, Tuple, DefaultDict, Optional
from collections import defaultdict
import json
import re


def build_import_graph(manifest: Dict) -> Dict:
    """Build a simple import graph from the manifest.

    Nodes: unique artifact paths with types.
    Edges: (from -> to) for each import reference present.
    """
    artifacts = manifest.get("artifacts", [])
    nodes: Dict[str, Dict] = {}
    edges: List[Dict] = []

    # Index artifacts by path (relative within scanned root)
    for a in artifacts:
        node: Dict = {"id": a["path"], "type": a.get("type", "unknown")}
        # Attach namespace info for WSDL/XSD if available
        if a.get("type") in {"xsd_schema", "wsdl"} and a.get("abs_path"):
            ns = _extract_namespaces(Path(a["abs_path"]))
            if ns:
                node["namespaces"] = ns
        nodes[a["path"]] = node

    # Build indices to help resolve import references
    by_path = {a["path"] for a in artifacts}
    by_basename: DefaultDict[str, List[str]] = defaultdict(list)
    for a in artifacts:
        by_basename[Path(a["path"]).name].append(a["path"])

    # Build edges using the imports as relative references
    for a in artifacts:
        src = a["path"]
        src_dir = Path(src).parent
        # Imports
        for ref in a.get("imports", []):
            # Attempt to resolve relative reference against src directory
            candidate = (src_dir / ref).as_posix()
            target = None
            if candidate in by_path:
                target = candidate
            elif ref in by_path:
                target = ref
            else:
                # Try basename matching if unique
                base = Path(ref).name
                candidates = by_basename.get(base, [])
                if len(candidates) == 1:
                    target = candidates[0]
            edges.append({"from": src, "to": target or ref, "resolved": target is not None, "kind": "import"})

        # WSDL relationships
        if a.get("type") == "wsdl" and a.get("abs_path"):
            rels = _extract_wsdl_relations(Path(a["abs_path"]))
            for pt in rels.get("portTypes", []):
                edges.append({"from": src, "to": pt, "resolved": False, "kind": "wsdl_portType"})
            for msg in rels.get("messages", []):
                edges.append({"from": src, "to": msg, "resolved": False, "kind": "wsdl_message"})

    graph = {"nodes": list(nodes.values()), "edges": edges}
    # Cycle detection among resolved import edges only
    cycles = _detect_cycles(graph, only_kinds={"import"})
    graph["report"] = {"cycle_count": len(cycles), "cycles": cycles}
    return graph


def _extract_namespaces(file_path: Path) -> Optional[Dict]:
    """Extract targetNamespace and prefix→URI mappings via regex from XML.

    ElementTree does not expose xmlns declarations; use regex over text.
    """
    try:
        text = file_path.read_text(encoding="utf-8", errors="ignore")
    except Exception:
        return None

    # Find targetNamespace
    m = re.search(r"targetNamespace\s*=\s*\"([^\"]+)\"", text, flags=re.IGNORECASE | re.DOTALL)
    target_ns = m.group(1) if m else None

    # Find xmlns declarations with prefixes
    prefixes: Dict[str, str] = {}
    for pref, uri in re.findall(r"xmlns:([A-Za-z0-9_\-]+)\s*=\s*\"([^\"]+)\"", text):
        prefixes[pref] = uri
    # Default namespace
    mdef = re.search(r"xmlns\s*=\s*\"([^\"]+)\"", text)
    default_ns = mdef.group(1) if mdef else None

    result: Dict[str, Optional[Dict]] = {
        "targetNamespace": target_ns,
        "prefixes": prefixes,
    }
    if default_ns:
        result["defaultNamespace"] = default_ns
    return result


def _extract_wsdl_relations(file_path: Path) -> Dict[str, List[str]]:
    """Extract WSDL portTypes and messages by regex.
    This is a lightweight pass sufficient for building edges and tests.
    """
    try:
        text = file_path.read_text(encoding="utf-8", errors="ignore")
    except Exception:
        return {"portTypes": [], "messages": []}
    port_types = re.findall(r"<\s*[^:>]*:?portType\s+name=\"([^\"]+)\"", text)
    messages = re.findall(r"<\s*[^:>]*:?message\s+name=\"([^\"]+)\"", text)
    return {"portTypes": port_types, "messages": messages}


def _detect_cycles(graph: Dict, only_kinds: Optional[set] = None) -> List[List[str]]:
    """Detect cycles using DFS over resolved edges.
    Returns list of cycles as paths (start node repeats at end omitted)."""
    edges = graph.get("edges", [])
    nodes = {n["id"] for n in graph.get("nodes", [])}
    adj: Dict[str, List[str]] = defaultdict(list)
    for e in edges:
        if only_kinds and e.get("kind") not in only_kinds:
            continue
        if not e.get("resolved"):
            continue
        src, dst = e["from"], e["to"]
        if src in nodes and dst in nodes:
            adj[src].append(dst)

    visited: Dict[str, int] = {}
    stack: List[str] = []
    cycles: List[List[str]] = []

    def dfs(u: str):
        visited[u] = 1  # visiting
        stack.append(u)
        for v in adj.get(u, []):
            if visited.get(v, 0) == 0:
                dfs(v)
            elif visited.get(v) == 1:
                # Found back edge -> cycle
                if v in stack:
                    idx = stack.index(v)
                    cycle = stack[idx:].copy()
                    cycles.append(cycle)
        stack.pop()
        visited[u] = 2  # done

    for n in nodes:
        if visited.get(n, 0) == 0:
            dfs(n)
    # Deduplicate cycles by normalized tuple
    norm = set()
    dedup: List[List[str]] = []
    for c in cycles:
        # rotate to smallest lexicographic for id
        min_i = min(range(len(c)), key=lambda i: c[i])
        rotated = tuple(c[min_i:] + c[:min_i])
        if rotated not in norm:
            norm.add(rotated)
            dedup.append(c)
    return dedup


def main(manifest_path: str, output_path: str = "agent/graph.json") -> Path:
    mp = Path(manifest_path)
    data = json.loads(mp.read_text())
    graph = build_import_graph(data)
    out = Path(output_path)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(graph, indent=2))
    return out


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Build import graph from manifest")
    parser.add_argument("--manifest", required=True)
    parser.add_argument("--output", default="agent/graph.json")
    args = parser.parse_args()
    result = main(args.manifest, args.output)
    print(f"Graph written to {result}")