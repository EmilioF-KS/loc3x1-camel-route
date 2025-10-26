#!/usr/bin/env python3
import argparse
import os
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parent

def say(msg: str) -> None:
    print(f"[clean] {msg}")

def remove_path(p: Path, dry_run: bool) -> None:
    try:
        if not p.exists():
            return
        if dry_run:
            say(f"would remove: {p}")
            return
        if p.is_dir() and not p.is_symlink():
            say(f"removing: {p}")
            shutil.rmtree(p)
        else:
            say(f"removing: {p}")
            p.unlink()
    except Exception as e:
        say(f"error removing {p}: {e}")

def find_targets() -> list[Path]:
    return [p for p in ROOT.glob('modules/**/target') if p.is_dir()]

def find_lib_jars() -> list[Path]:
    lib_dir = ROOT / 'loc3x1-camel-route' / 'libs'
    return list(lib_dir.glob('*.jar')) if lib_dir.exists() else []

def find_py_caches() -> list[Path]:
    caches: list[Path] = []
    for dirpath, dirnames, _ in os.walk(ROOT):
        for dn in list(dirnames):
            if dn in ('__pycache__', '.pytest_cache'):
                caches.append(Path(dirpath) / dn)
    return caches

def main() -> int:
    parser = argparse.ArgumentParser(description='Clean runtime-generated artifacts for a fresh restart.')
    parser.add_argument('--full', action='store_true', help='Perform total cleaning, including heavy caches like node_modules and virtualenvs.')
    parser.add_argument('--dry-run', action='store_true', help='Show what would be removed without deleting.')
    args = parser.parse_args()

    say(f'project root: {ROOT}')

    paths: list[Path] = [
        ROOT / 'uploads',
        ROOT / 'tests' / 'results',
        ROOT / 'ci' / 'artifacts',
        ROOT / 'frontend' / 'dist',
        ROOT / 'results' / 'agent-runs',
        ROOT / 'doc' / 'agent-runs',
    ]

    if args.full:
        paths.extend([
            ROOT / 'frontend' / 'node_modules',
            ROOT / '.venv',
            ROOT / 'venv',
            ROOT / 'env',
            ROOT / 'ENV',
        ])

    # Module target directories
    target_dirs = find_targets()

    # Built jars in libs
    libs_jars = find_lib_jars()

    # Python caches
    py_caches = find_py_caches()

    # Remove directories and files
    for p in paths:
        remove_path(p, args.dry_run)

    for t in target_dirs:
        remove_path(t, args.dry_run)

    for j in libs_jars:
        remove_path(j, args.dry_run)

    for c in py_caches:
        remove_path(c, args.dry_run)

    # Recreate expected empty folders after non-dry-run clean
    if not args.dry_run:
        for p in [ROOT / 'uploads', ROOT / 'tests' / 'results']:
            if not p.exists():
                say(f'recreating: {p}')
                p.mkdir(parents=True, exist_ok=True)

    say('done')
    return 0

if __name__ == '__main__':
    raise SystemExit(main())