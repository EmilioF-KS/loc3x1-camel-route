#!/usr/bin/env python3
"""CLI to run artifact discovery and generate manifest JSON.

Usage:
  python agent/scripts/discover.py --input CHUBB/Dependencies --output agent/manifest.json --overwrite
"""

from pathlib import Path
import argparse
import sys

# Ensure repository root is on sys.path so 'agent' package is importable
ROOT = Path(__file__).resolve().parents[2]
if str(ROOT) not in sys.path:
    sys.path.append(str(ROOT))

from agent.src.inventory import main as inventory_main


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Discover artifacts and generate manifest JSON")
    parser.add_argument("--input", required=True, help="Path to input folder (e.g., CHUBB/Dependencies)")
    parser.add_argument("--output", default="agent/manifest.json", help="Output manifest file path")
    parser.add_argument("--overwrite", action="store_true", help="Overwrite output file if exists")
    return parser.parse_args()


def run() -> Path:
    args = parse_args()
    out_path = inventory_main(args.input, args.output)
    print(f"Manifest written to {out_path}")
    return out_path


if __name__ == "__main__":
    run()