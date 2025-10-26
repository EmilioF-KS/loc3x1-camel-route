#!/usr/bin/env python3
"""
Planner Agent (LOC-020)

Emits a runtime plan artefact and validates governance.
Outputs:
- results/meta/agent-plan.json: machine-readable plan summary
- results/reports/agent-plan.log: human-readable log with governance validation

Validations:
- Presence of master plan sources (LaTeX and agentic md)
- Governance checks: British English mention, no mocks, venv-only execution
- Alignment of core artefacts (spec, inventory, bpel analysis)
"""
import os
import json
import re
from pathlib import Path
from datetime import datetime

from scripts.utils.paths import PROJECT_ROOT, REPORTS_DIR, META_DIR, ensure_results_dirs
AGENT_PLAN_JSON = META_DIR / "agent-plan.json"
AGENT_PLAN_LOG = REPORTS_DIR / "agent-plan.log"

SPEC_PATH = PROJECT_ROOT / "orchestration/spec.yaml"
INVENTORY_JSON = PROJECT_ROOT / "contracts" / "inventory.json"
BPEL_ANALYSIS_MD = REPORTS_DIR / "bpel-analysis.md"
MAPPINGS_MD = PROJECT_ROOT / "modules" / "mappings" / "MAPPINGS.md"
ROUTES_MD = PROJECT_ROOT / "modules" / "orchestration" / "ROUTES.md"
MASTER_AGENTIC = PROJECT_ROOT / "doc" / "master-plan.agentic.md"
MASTER_PLAN_TXT = PROJECT_ROOT / "doc" / "master-plan.txt"
LATEX_PLAN = PROJECT_ROOT / "doc" / "Loc-Camel-Route.tex"


def _exists(p: Path) -> bool:
    try:
        return p.exists()
    except Exception:
        return False


def collect_state():
    return {
        "generated_at": datetime.now().isoformat(),
        "sources": {
            "spec_yaml": str(SPEC_PATH),
            "inventory_json": str(INVENTORY_JSON),
            "bpel_analysis_md": str(BPEL_ANALYSIS_MD),
            "mappings_md": str(MAPPINGS_MD),
            "routes_md": str(ROUTES_MD),
            "master_plan_agentic": str(MASTER_AGENTIC),
            "master_plan_txt": str(MASTER_PLAN_TXT),
            "latex_plan_tex": str(LATEX_PLAN),
        },
        "presence": {
            "spec_yaml": _exists(SPEC_PATH),
            "inventory_json": _exists(INVENTORY_JSON),
            "bpel_analysis_md": _exists(BPEL_ANALYSIS_MD),
            "mappings_md": _exists(MAPPINGS_MD),
            "routes_md": _exists(ROUTES_MD),
            "master_plan_agentic": _exists(MASTER_AGENTIC),
            "master_plan_txt": _exists(MASTER_PLAN_TXT),
            "latex_plan_tex": _exists(LATEX_PLAN),
        },
        "next_actions": [
            "If spec is absent, run analyze_bpel.py to produce orchestration/spec.yaml",
            "If inventory is absent, run generate_contract_inventory.py",
            "Run generate_routes.py to materialize routes and parent/client POMs",
            "Run generate_mappers.py to emit mapper interfaces and MAPPINGS.md",
            "Run preflight_agent.py and scripts/test_master.sh for smoke checks",
        ],
    }


def governance_checks() -> dict:
    """Perform governance validations as per acceptance criteria."""
    venv_active = bool(os.environ.get("VIRTUAL_ENV", ""))
    # British English mention in master plan txt/agentic md
    british_mention = False
    for p in [MASTER_PLAN_TXT, MASTER_AGENTIC]:
        try:
            if _exists(p) and "British English" in p.read_text(encoding="utf-8"):
                british_mention = True
                break
        except Exception:
            pass

    # No mocks: search ONLY code files for known mocking frameworks/usages
    java_patterns = [r"@Mock", r"org\.mockito", r"\bMockito\b", r"\bwhen\("]
    py_patterns = [r"unittest\.mock", r"from\s+unittest\s+import\s+mock", r"\bmock\.patch\b", r"\bMagicMock\b", r"pytest\-mock"]
    js_patterns = [r"jest\.mock\(", r"\bvi\.mock\("]
    patterns = [re.compile(p, re.IGNORECASE) for p in (java_patterns + py_patterns + js_patterns)]

    code_exts = {".java", ".py", ".js", ".jsx", ".ts", ".tsx", ".sh"}
    no_mocks = True
    suspicious_paths = []

    def is_build_or_cache(path_str: str) -> bool:
        return (
            "/target/" in path_str or
            "/tests/results/" in path_str or
            "/__pycache__/" in path_str or
            path_str.endswith(".pyc")
        )

    for scan_dir in [PROJECT_ROOT / "modules", PROJECT_ROOT / "scripts", PROJECT_ROOT / "agents", PROJECT_ROOT / "tests"]:
        if not scan_dir.exists():
            continue
        try:
            for p in scan_dir.rglob("*"):
                if not p.is_file():
                    continue
                # Skip self to avoid matching pattern literals in this file
                try:
                    if p.resolve() == (PROJECT_ROOT / "scripts" / "planner_agent.py").resolve():
                        continue
                except Exception:
                    pass
                pstr = str(p)
                if is_build_or_cache(pstr):
                    continue
                ext = p.suffix.lower()
                if ext not in code_exts:
                    continue
                try:
                    txt = p.read_text(encoding="utf-8", errors="ignore")
                except Exception:
                    continue
                for pat in patterns:
                    if pat.search(txt):
                        no_mocks = False
                        suspicious_paths.append(pstr)
                        break
        except Exception:
            pass

    return {
        "venv_active": venv_active,
        "british_english_mentioned": british_mention,
        "no_mocks_detected": no_mocks,
        "mocks_files": suspicious_paths,
    }


def validation_summary(plan: dict, gov: dict) -> dict:
    """Summarise validation across sources and governance."""
    sources_ok = plan["presence"]["spec_yaml"] and plan["presence"]["inventory_json"] and plan["presence"]["bpel_analysis_md"]
    master_ok = plan["presence"]["master_plan_agentic"] or plan["presence"]["master_plan_txt"]
    latex_ok = plan["presence"]["latex_plan_tex"]

    checks = {
        "sources_ok": sources_ok,
        "master_plan_ok": master_ok,
        "latex_ok": latex_ok,
        "venv_active": gov["venv_active"],
        "british_english": gov["british_english_mentioned"],
        "no_mocks": gov["no_mocks_detected"],
    }
    overall_ok = all([
        checks["master_plan_ok"],
        checks["venv_active"],
        checks["no_mocks"],
    ])
    return {"checks": checks, "overall_ok": overall_ok}


def write_json(plan, gov, summary):
    ensure_results_dirs()
    payload = {
        "plan": plan,
        "governance": gov,
        "validation": summary,
    }
    AGENT_PLAN_JSON.write_text(json.dumps(payload, indent=2), encoding="utf-8")


def write_log(plan, gov, summary):
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
    lines.append("## Governance Checks")
    lines.append(f"- Virtual environment active: {'YES' if gov['venv_active'] else 'NO'}")
    lines.append(f"- British English mentioned in master plan: {'YES' if gov['british_english_mentioned'] else 'NO'}")
    lines.append(f"- No mocks detected in repo: {'YES' if gov['no_mocks_detected'] else 'NO'}")
    if gov["mocks_files"]:
        lines.append("- Mock occurrences:")
        for p in gov["mocks_files"][:10]:  # cap to 10 entries for brevity
            lines.append(f"  - {p}")
        if len(gov["mocks_files"]) > 10:
            lines.append(f"  - ... and {len(gov['mocks_files']) - 10} more")
    lines.append("")
    lines.append("## Validation Summary")
    for k, v in summary["checks"].items():
        lines.append(f"- {k}: {'PASS' if v else 'FAIL'}")
    lines.append("")
    lines.append(f"Overall: {'ACCEPTED' if summary['overall_ok'] else 'REVIEW REQUIRED'}")
    lines.append("")
    lines.append("## Next Actions")
    for step in plan["next_actions"]:
        lines.append(f"- {step}")
    AGENT_PLAN_LOG.write_text("\n".join(lines), encoding="utf-8")


def main():
    plan = collect_state()
    gov = governance_checks()
    summary = validation_summary(plan, gov)
    write_json(plan, gov, summary)
    write_log(plan, gov, summary)
    print(f"✓ Wrote {AGENT_PLAN_JSON}")
    print(f"✓ Wrote {AGENT_PLAN_LOG}")
    # Exit non-zero if overall governance failed (useful for CI gating)
    if not summary["overall_ok"]:
        raise SystemExit(1)


if __name__ == "__main__":
    main()