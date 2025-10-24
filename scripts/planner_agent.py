#!/usr/bin/env python3
"""
Planner Agent

Emits a lightweight runtime plan artefact based on current repository state.
Outputs:
- doc/agent-plan.json: machine-readable plan summary
- doc/agent-plan.log: human-readable log

This is a scaffold aligning with the agentic master plan; it can be extended
to parse Loc-Camel-Route.tex and master-plan.agentic.md for richer planning.
"""
import json
from pathlib import Path
from datetime import datetime

PROJECT_ROOT = Path.cwd()
DOC_DIR = PROJECT_ROOT / "doc"
AGENT_PLAN_JSON = DOC_DIR / "agent-plan.json"
AGENT_PLAN_LOG = DOC_DIR / "agent-plan.log"

SPEC_PATH = PROJECT_ROOT / "orchestration/spec.yaml"
INVENTORY_JSON = PROJECT_ROOT / "contracts" / "inventory.json"
BPEL_ANALYSIS_MD = DOC_DIR / "bpel-analysis.md"
MAPPINGS_MD = PROJECT_ROOT / "modules" / "mappings" / "MAPPINGS.md"
ROUTES_MD = PROJECT_ROOT / "modules" / "orchestration" / "ROUTES.md"
MASTER_AGENTIC = DOC_DIR / "master-plan.agentic.md"


def collect_state():
    def exists(p: Path):
        try:
            return p.exists()
        except Exception:
            return False

    return {
        "generated_at": datetime.now().isoformat(),
        "sources": {
            "spec_yaml": str(SPEC_PATH),
            "inventory_json": str(INVENTORY_JSON),
            "bpel_analysis_md": str(BPEL_ANALYSIS_MD),
            "mappings_md": str(MAPPINGS_MD),
            "routes_md": str(ROUTES_MD),
            "master_plan_agentic": str(MASTER_AGENTIC),
        },
        "presence": {
            "spec_yaml": exists(SPEC_PATH),
            "inventory_json": exists(INVENTORY_JSON),
            "bpel_analysis_md": exists(BPEL_ANALYSIS_MD),
            "mappings_md": exists(MAPPINGS_MD),
            "routes_md": exists(ROUTES_MD),
            "master_plan_agentic": exists(MASTER_AGENTIC),
        },
        "next_actions": [
            "If spec is absent, run analyze_bpel.py to produce orchestration/spec.yaml",
            "If inventory is absent, run generate_contract_inventory.py",
            "Run generate_routes.py to materialize routes and parent/client POMs",
            "Run generate_mappers.py to emit mapper interfaces and MAPPINGS.md",
            "Run preflight_agent.py and scripts/test_master.sh for smoke checks",
        ],
    }


def write_json(plan):
    DOC_DIR.mkdir(parents=True, exist_ok=True)
    AGENT_PLAN_JSON.write_text(json.dumps(plan, indent=2), encoding="utf-8")


def write_log(plan):
    lines = [
        "# Agent Runtime Plan",
        "",
        f"Generated: {plan['generated_at']}",
        "",
        "## Sources",
    ]
    for k, v in plan["sources"].items():
        lines.append(f"- {k}: {v}")
    lines.append("")
    lines.append("## Presence")
    for k, v in plan["presence"].items():
        lines.append(f"- {k}: {'OK' if v else 'MISSING'}")
    lines.append("")
    lines.append("## Next Actions")
    for step in plan["next_actions"]:
        lines.append(f"- {step}")
    AGENT_PLAN_LOG.write_text("\n".join(lines), encoding="utf-8")


def main():
    plan = collect_state()
    write_json(plan)
    write_log(plan)
    print(f"✓ Wrote {AGENT_PLAN_JSON}")
    print(f"✓ Wrote {AGENT_PLAN_LOG}")


if __name__ == "__main__":
    main()