import json
import os
import shutil
from pathlib import Path
from typing import Dict, Any, List, Set, Tuple


def _load_manifest(path: str) -> Dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def _rel_to_deps_root(abs_path: str) -> Tuple[str, str]:
    p = Path(abs_path)
    parts = p.parts
    try:
        idx = parts.index("Dependencies")
        rel = Path(*parts[idx + 1:])
        root = str(p.parents[len(parts) - (idx + 1)])
        return str(rel), root
    except ValueError:
        return p.name, str(p.parent)


def assemble_contracts(manifest_path: str, dest_root: str, wsdl_filter: List[str] | None = None) -> List[str]:
    manifest = _load_manifest(manifest_path)
    artifacts = manifest.get("artifacts", [])
    wsdl_files = [a for a in artifacts if a.get("type") == "wsdl"]
    if wsdl_filter:
        wsdl_files = [a for a in wsdl_files if any(f in a.get("abs_path", a.get("path", "")) for f in wsdl_filter)]

    selected: Set[str] = set()
    for w in wsdl_files:
        selected.add(w.get("abs_path", w.get("path", "")))
        for imp in w.get("imports", []) or []:
            base = os.path.dirname(w.get("abs_path", w.get("path", "")))
            candidate = os.path.normpath(os.path.join(base, imp))
            selected.add(candidate)

    # Add referenced XSD imports recursively where present in manifest
    path_to_art: Dict[str, Dict[str, Any]] = {a.get("abs_path", a.get("path", "")): a for a in artifacts}
    changed = True
    while changed:
        changed = False
        for p in list(selected):
            art = path_to_art.get(p)
            if not art:
                continue
            for imp in art.get("imports", []) or []:
                base = os.path.dirname(art.get("abs_path", art.get("path", "")))
                candidate = os.path.normpath(os.path.join(base, imp))
                if candidate not in selected:
                    selected.add(candidate)
                    changed = True

    dest = Path(dest_root)
    if dest.exists():
        shutil.rmtree(dest)
    os.makedirs(dest, exist_ok=True)

    copied: List[str] = []
    for src in sorted(selected):
        if not os.path.isfile(src):
            continue
        rel, _ = _rel_to_deps_root(src)
        dst = dest / rel
        os.makedirs(dst.parent, exist_ok=True)
        shutil.copy(src, dst)
        copied.append(str(dst))

    return copied


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Assemble WSDL/XSD contracts for Maven codegen")
    parser.add_argument("--manifest", default="agent/manifest.json")
    parser.add_argument("--dest", default="generated/result/src/main/resources/contracts/selected")
    parser.add_argument("--wsdl", action="append")
    args = parser.parse_args()
    copied = assemble_contracts(args.manifest, args.dest, wsdl_filter=args.wsdl)
    print(json.dumps({"copied": copied, "count": len(copied)}))


if __name__ == "__main__":
    main()
