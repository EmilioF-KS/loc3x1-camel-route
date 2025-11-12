#!/usr/bin/env python3
import argparse
import os
from agent.src.map_custom_to_processors import discover_custom_blocks, write_processors, rationale_for_block


def main():
    parser = argparse.ArgumentParser(description="Generate Java processors from IBM map:custom blocks")
    parser.add_argument("--map-file", required=True, help="Path to IBM map XML file containing map:custom blocks")
    parser.add_argument("--output-dir", required=True, help="Directory to write generated Java processors")
    parser.add_argument("--rationales", help="Optional file to append rationales per processor")
    args = parser.parse_args()

    blocks = discover_custom_blocks(args.map_file)
    outputs = write_processors(blocks, args.output_dir)
    print(f"Generated {len(outputs)} processors in {args.output_dir}")

    if args.rationales:
        os.makedirs(os.path.dirname(args.rationales) or ".", exist_ok=True)
        with open(args.rationales, "a", encoding="utf-8") as f:
            for b in blocks:
                f.write(rationale_for_block(b) + "\n\n")
        print(f"Appended rationales to {args.rationales}")


if __name__ == "__main__":
    main()