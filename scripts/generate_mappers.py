#!/usr/bin/env python3
"""
Generate MapStruct mapper interfaces from orchestration/spec.yaml data_mappings.

The generated mappers are placed under modules/mappings/src/main/java/com/ei/camel/mappings.
This initial version emits minimal interfaces using Object types to avoid compile-time
coupling to JAXB classes; later iterations can resolve concrete types from contracts.
"""
import yaml
from pathlib import Path
import sys
from typing import Dict, Any
from datetime import datetime

PROJECT_ROOT = Path.cwd()
SPEC_PATH = PROJECT_ROOT / "orchestration/spec.yaml"
OUT_DIR = PROJECT_ROOT / "modules/mappings/src/main/java/com/ei/camel/mappings"
SUMMARY_PATH = PROJECT_ROOT / "modules/mappings/MAPPINGS.md"

JAVA_TEMPLATE = """package com.ei.camel.mappings;

import org.mapstruct.Mapper;

@Mapper
public interface {mapper_name} {{
    Object map(Object source);
}}
"""


def main() -> int:
    if not SPEC_PATH.exists():
        print(f"ERROR: Spec not found: {SPEC_PATH}", file=sys.stderr)
        return 1
    data = yaml.safe_load(SPEC_PATH.read_text(encoding="utf-8"))
    mappings = data.get("data_mappings") or []
    if not mappings:
        print("WARN: No data_mappings found in spec; nothing to generate")
        return 0
    OUT_DIR.mkdir(parents=True, exist_ok=True)
    generated_names = []
    count = 0
    for dm in mappings:
        mapper = dm.get("mapper")
        if not mapper:
            continue
        mapper_name = ''.join(ch if ch.isalnum() else '_' for ch in mapper)
        java_name = mapper_name if mapper_name.endswith("Mapper") else f"{mapper_name}Mapper"
        content = JAVA_TEMPLATE.format(mapper_name=java_name)
        out_path = OUT_DIR / f"{java_name}.java"
        out_path.write_text(content, encoding="utf-8")
        generated_names.append(java_name)
        count += 1
        print(f"✓ Generated mapper: {out_path}")
    print(f"✓ Completed MapStruct generation: {count} mappers")
    SUMMARY_PATH.parent.mkdir(parents=True, exist_ok=True)
    summary = [
        "# Mappings Summary",
        "",
        f"Generated: {datetime.now().isoformat()}",
        "",
        f"Count: {count}",
        "",
    ]
    summary.extend([f"- {name}" for name in generated_names])
    SUMMARY_PATH.write_text("\n".join(summary), encoding="utf-8")
    print(f"✓ Wrote {SUMMARY_PATH}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())