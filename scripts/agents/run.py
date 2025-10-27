#!/usr/bin/env python3
"""
Agents CLI Runner (LOC-021)

Orchestrates agent stages:
- env: Preflight Agent
- plan: Planner Agent
- contracts: Contract Inventory + Validator + CXF/JAXB Codegen
- bpel: BPEL Analysis and Spec generation

Extensible to: schema, integration, mapping, tests, eval.
Persists run metadata under doc/agent-runs/.
"""
import argparse
import json
import os
import sys
from datetime import datetime
from pathlib import Path
import importlib.util
import subprocess

# Ensure project root is on sys.path before importing internal modules
PROJECT_ROOT = Path(__file__).resolve().parents[2]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from scripts.utils.paths import (
    PROJECT_ROOT as _PROJECT_ROOT,
    RESULTS_DIR,
    REPORTS_DIR,
    RUNS_DIR,
    PREVIEW_DIR,
    META_DIR,
    ensure_results_dirs,
)

def ensure_dirs():
    ensure_results_dirs()

# Stage implementations

def venv_guard(strict: bool = False) -> None:
    venv = os.environ.get("VIRTUAL_ENV", "")
    if not venv:
        msg = "Virtual environment not active; recommended per governance."
        if strict:
            print(f"ERROR: {msg}", file=sys.stderr)
            sys.exit(1)
        else:
            print(f"WARN: {msg}")
    else:
        print(f"✓ Venv active: {venv}")

def import_module_from_path(name: str, path: Path):
    spec = importlib.util.spec_from_file_location(name, str(path))
    module = importlib.util.module_from_spec(spec)
    assert spec and spec.loader
    spec.loader.exec_module(module)
    return module

def stage_env() -> dict:
    """Run Preflight Agent checks and write agent-checks.md."""
    preflight = import_module_from_path('preflight_agent', PROJECT_ROOT / 'scripts' / 'preflight_agent.py')
    preflight.main()
    report_path = PREVIEW_DIR / "agent-checks.md"
    return {
        "stage": "env",
        "outputs": {"agent_checks_md": str(report_path)},
        "ok": report_path.exists(),
    }


def stage_plan() -> dict:
    """Run Planner Agent to emit agent-plan.json and agent-plan.log."""
    planner = import_module_from_path('planner_agent', PROJECT_ROOT / 'scripts' / 'planner_agent.py')
    rc = 0
    try:
        planner.main()
    except SystemExit as e:
        # Planner enforces governance and may exit non-zero; capture gracefully
        rc = int(e.code) if isinstance(e.code, int) else 1
    outputs = {
        "agent_plan_json": str(META_DIR / "agent-plan.json"),
        "agent_plan_log": str(REPORTS_DIR / "agent-plan.log"),
    }
    return {
        "stage": "plan",
        "outputs": outputs,
        "rc": rc,
        "ok": (META_DIR / "agent-plan.json").exists() and rc == 0,
    }


def stage_contracts(source_root: Path | None = None) -> dict:
    """Generate contract inventory and run validator, then perform CXF/JAXB codegen and compile."""
    gen_mod = import_module_from_path('generate_contract_inventory', PROJECT_ROOT / 'scripts' / 'generate_contract_inventory.py')
    ContractInventoryGenerator = getattr(gen_mod, 'ContractInventoryGenerator')
    generator = ContractInventoryGenerator(str(PROJECT_ROOT))
    # Allow overriding scan directory via SOURCE_ROOT
    if source_root:
        generator.sample_dir = Path(source_root)
        generator.inventory["metadata"]["scan_directory"] = str(generator.sample_dir)
    generator.scan_files()
    inv_json = generator.generate_json()
    inv_md = generator.generate_markdown()

    # Validate and write report
    validator_mod = import_module_from_path('contracts_validator', PROJECT_ROOT / 'scripts' / 'contracts_validator.py')
    result = validator_mod.validate_contracts(PROJECT_ROOT)
    report_out = PREVIEW_DIR / "contracts-checks.md"
    validator_mod.write_report(result, report_out)

    # Perform CXF/JAXB codegen based on inventory
    pom_updater = PROJECT_ROOT / "scripts" / "update_contracts_pom_from_inventory.py"
    contracts_pom = PROJECT_ROOT / "modules" / "contracts" / "pom.xml"
    codegen_log = PREVIEW_DIR / "contracts-codegen.log"

    # 1) Update contracts POM from inventory
    update_cmd = [sys.executable, str(pom_updater)]
    update_proc = subprocess.run(update_cmd, cwd=str(PROJECT_ROOT), capture_output=True, text=True)

    # 2) Run maven generate-sources
    gen_cmd = [
        "mvn",
        "-f",
        str(contracts_pom),
        "generate-sources",
        "-q",
    ]
    gen_proc = subprocess.run(gen_cmd, cwd=str(PROJECT_ROOT), capture_output=True, text=True)

    # 3) Compile generated sources
    compile_cmd = [
        "mvn",
        "-f",
        str(contracts_pom),
        "-q",
        "-DskipTests",
        "compile",
    ]
    compile_proc = subprocess.run(compile_cmd, cwd=str(PROJECT_ROOT), capture_output=True, text=True)

    # Write combined log
    try:
        codegen_log.write_text(
            """
=== update_contracts_pom_from_inventory.py ===
stdout:
{update_stdout}
stderr:
{update_stderr}

=== mvn generate-sources ===
stdout:
{gen_stdout}
stderr:
{gen_stderr}

=== mvn compile -DskipTests ===
stdout:
{compile_stdout}
stderr:
{compile_stderr}
""".format(
                update_stdout=update_proc.stdout,
                update_stderr=update_proc.stderr,
                gen_stdout=gen_proc.stdout,
                gen_stderr=gen_proc.stderr,
                compile_stdout=compile_proc.stdout,
                compile_stderr=compile_proc.stderr,
            ),
            encoding="utf-8",
        )
    except Exception as e:
        print(f"WARN: Failed to write codegen log: {e}")

    # Determine overall success
    ok = (
        result.get("counts", {}).get("match", False)
        and Path(inv_json).exists()
        and Path(inv_md).exists()
        and contracts_pom.exists()
        and update_proc.returncode == 0
        and gen_proc.returncode == 0
        and compile_proc.returncode == 0
    )

    return {
        "stage": "contracts",
        "inputs": {"source_root": str(source_root or generator.sample_dir)},
        "outputs": {
            "inventory_json": str(inv_json),
            "inventory_md": str(inv_md),
            "validator_report_md": str(report_out),
            "contracts_pom": str(contracts_pom),
            "codegen_log": str(codegen_log),
        },
        "summary": result.get("summary", {}),
        "rc": {
            "update_pom": update_proc.returncode,
            "generate_sources": gen_proc.returncode,
            "compile": compile_proc.returncode,
        },
        "ok": ok,
    }


def stage_schema() -> dict:
    """Generate canonical schema map and governance checklist."""
    gov_mod = import_module_from_path('schema_governance', PROJECT_ROOT / 'scripts' / 'schema_governance.py')
    result = gov_mod.generate(PROJECT_ROOT)
    return {
        "stage": "schema",
        "outputs": {
            "canonical_map_yaml": result.get("canonical_map"),
            "governance_md": result.get("governance_md"),
        },
        "summary": result.get("summary", {}),
        "ok": result.get("ok", False),
    }


def stage_scaffold() -> dict:
    """Run Maven scaffold verification, patch parent modules, and build."""
    scaffold_mod = import_module_from_path('scaffold_maven', PROJECT_ROOT / 'scripts' / 'scaffold_maven.py')
    result = scaffold_mod.main()
    return {
        "stage": "scaffold",
        "outputs": result.get("outputs", {}),
        "ok": result.get("ok", False),
        "changed_parent_pom": result.get("changed_parent_pom", False),
    }


def stage_bpel(root: Path | None = None, bpel_file: Path | None = None, use_agent: bool = True) -> dict:
    """Run BPEL analysis to generate Markdown and spec YAML."""
    script = PROJECT_ROOT / "scripts" / "analyze_bpel.py"
    out_md = REPORTS_DIR / "bpel-analysis.md"
    out_spec = PROJECT_ROOT / "orchestration" / "spec.yaml"

    cmd = [
        sys.executable,
        str(script),
        "--output", str(out_md),
        "--spec", str(out_spec),
    ]
    if use_agent:
        cmd.append("--use-agent")
    if root:
        cmd.extend(["--root", str(root)])
    if bpel_file:
        cmd.extend(["--bpel", str(bpel_file)])

    rc = subprocess.run(cmd, cwd=str(PROJECT_ROOT)).returncode

    return {
        "stage": "bpel",
        "inputs": {
            "root": str(root or PROJECT_ROOT),
            "bpel": str(bpel_file or "auto"),
            "use_agent": use_agent,
        },
        "outputs": {
            "bpel_analysis_md": str(out_md),
            "spec_yaml": str(out_spec),
        },
        "ok": rc == 0 and out_md.exists() and out_spec.exists(),
    }


def stage_build() -> dict:
    """Run bootstrap and full Maven build (venv-guarded scripts)."""
    bootstrap = PROJECT_ROOT / "scripts" / "bootstrap_java.sh"
    build = PROJECT_ROOT / "scripts" / "build_all.sh"
    logs_dir = PROJECT_ROOT / "tests" / "results" / "logs"
    boot_log = logs_dir / "bootstrap-java.log"
    build_log = logs_dir / "build.log"

    boot_proc = subprocess.run(["bash", str(bootstrap)], cwd=str(PROJECT_ROOT))
    build_proc = subprocess.run(["bash", str(build)], cwd=str(PROJECT_ROOT))

    ok = boot_proc.returncode == 0 and build_proc.returncode == 0 and boot_log.exists() and build_log.exists()
    return {
        "stage": "build",
        "outputs": {
            "bootstrap_log": str(boot_log),
            "build_log": str(build_log),
        },
        "rc": {
            "bootstrap": boot_proc.returncode,
            "build": build_proc.returncode,
        },
        "ok": ok,
    }


def stage_clients() -> dict:
    """Run outbound CXF client against inbound service and capture evidence."""
    run_script = PROJECT_ROOT / "scripts" / "run_clients.sh"
    logs_dir = PROJECT_ROOT / "tests" / "results" / "logs"
    arts_dir = PROJECT_ROOT / "tests" / "results" / "artifacts" / "clients"
    run_log = logs_dir / "run-clients.log"
    client_out = logs_dir / "client-run.out"

    proc = subprocess.run(["bash", str(run_script)], cwd=str(PROJECT_ROOT))
    ok = proc.returncode == 0 and run_log.exists()
    outputs = {
        "run_log": str(run_log),
        "client_out": str(client_out),
        "request_meta": str(arts_dir / "client-ping.request.meta.txt"),
        "response_meta": str(arts_dir / "client-ping.response.meta.txt"),
    }
    return {
        "stage": "clients",
        "outputs": outputs,
        "rc": proc.returncode,
        "ok": ok,
    }


def stage_services() -> dict:
    """Run inbound Spring Boot + CXF service and probe WSDL."""
    run_script = PROJECT_ROOT / "scripts" / "run_services.sh"
    logs_dir = PROJECT_ROOT / "tests" / "results" / "logs"
    run_log = logs_dir / "run-services.log"
    svc_out = logs_dir / "inbound-service.out"

    proc = subprocess.run(["bash", str(run_script)], cwd=str(PROJECT_ROOT))
    ok = proc.returncode == 0 and run_log.exists() and svc_out.exists()
    # Update only the services stage outputs to include SOAP evidence paths
    outputs = {
        "run_log": str(run_log),
        "service_out": str(svc_out),
        "wsdl_probe": f"http://localhost:{os.environ.get('INBOUND_PORT', '8085')}/services/BasicService?wsdl",
        "soap_request": str(logs_dir / "soap-ping.xml"),
        "soap_response": str(logs_dir / "soap-ping.response.xml"),
    }
    return {
        "stage": "services",
        "outputs": outputs,
        "rc": proc.returncode,
        "ok": ok,
    }


def stage_tests() -> dict:
    """Execute integration tests via master runner (venv-guarded script)."""
    tests_script = PROJECT_ROOT / "scripts" / "test_integrations.sh"
    logs_dir = PROJECT_ROOT / "tests" / "results" / "logs"
    test_log = logs_dir / "integration-tests.log"
    junit_dir = PROJECT_ROOT / "tests" / "results" / "junit"
    html_dir = PROJECT_ROOT / "tests" / "results" / "html"

    proc = subprocess.run(["bash", str(tests_script)], cwd=str(PROJECT_ROOT))
    ok = proc.returncode == 0 and test_log.exists()
    return {
        "stage": "tests",
        "outputs": {
            "integration_tests_log": str(test_log),
            "junit_dir": str(junit_dir),
            "html_dir": str(html_dir),
        },
        "rc": proc.returncode,
        "ok": ok,
    }


def stage_reviewer() -> dict:
    """Produce reviewer/interpreter explanation report from deterministic artifacts.

    - Gathers artifacts: evaluation report, agent plan, summary, preflight checks, BPEL analysis.
    - Writes doc/reviewer-report.md with Executive Narrative, Evidence Alignment, Decisions, Risks.
    - Never gates; ok=True if report is written and core artifacts exist.
    """
    from pathlib import Path as _Path
    import json as _json
    import re as _re
    import datetime as _dt

    report_md = REPORTS_DIR / "reviewer-report.md"
    eval_md = REPORTS_DIR / "evaluation-report.md"
    plan_json = META_DIR / "agent-plan.json"
    summary_txt = PROJECT_ROOT / "tests" / "results" / "html" / "summary.txt"
    preflight_md = PREVIEW_DIR / "agent-checks.md"
    bpel_md = REPORTS_DIR / "bpel-analysis.md"
    spec_yaml = PROJECT_ROOT / "orchestration" / "spec.yaml"

    gating_status = "UNKNOWN"
    blockers = []
    tests_scanned = None
    failing_suites = None

    if eval_md.exists():
        text = eval_md.read_text(encoding="utf-8")
        m_gate = _re.search(r"Gating status:\s*(\w+)", text)
        m_fail = _re.search(r"Failing suites:\s*(\d+)", text)
        m_scan = _re.search(r"Tests scanned:\s*(\d+)", text)
        if m_gate: gating_status = m_gate.group(1)
        if m_fail: failing_suites = int(m_fail.group(1))
        if m_scan: tests_scanned = int(m_scan.group(1))
        # Collect blockers section lines
        blk = _re.findall(r"## Blockers\n([\s\S]*?)\n## ", text)
        if blk:
            blockers = [ln.strip() for ln in blk[0].splitlines() if ln.strip() and ln.strip().lower() != "none"]

    plan_ok = None
    overall_ok = None
    sources = []
    if plan_json.exists():
        try:
            pj = _json.loads(plan_json.read_text(encoding="utf-8"))
            overall_ok = pj.get("validation", {}).get("overall_ok")
            plan_ok = pj.get("validation", {}).get("checks", {}).get("sources_ok")
            sources = list((pj.get("plan", {}).get("sources", {}) or {}).values())
        except Exception:
            pass

    lines = [
        "# Reviewer/Interpreter Report",
        f"Generated: {_dt.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}",
        "",
        "## Executive Narrative",
        f"Overall gating status from evaluation: {gating_status}.",
        f"Tests scanned: {tests_scanned if tests_scanned is not None else 'n/a'}; failing suites: {failing_suites if failing_suites is not None else 'n/a' }.",
        f"Planner governance acceptance: {'ACCEPTED' if overall_ok else 'UNKNOWN'}.",
        "",
        "## Evidence Alignment",
        f"- Evaluation report: `{eval_md}` {'OK' if eval_md.exists() else 'MISSING'}",
        f"- Summary: `{summary_txt}` {'OK' if summary_txt.exists() else 'MISSING'}",
        f"- Agent plan: `{plan_json}` {'OK' if plan_json.exists() else 'MISSING'}",
        f"- Preflight checks: `{preflight_md}` {'OK' if preflight_md.exists() else 'MISSING'}",
        f"- BPEL analysis: `{bpel_md}` {'OK' if bpel_md.exists() else 'MISSING'}",
        f"- Orchestration spec: `{spec_yaml}` {'OK' if spec_yaml.exists() else 'MISSING'}",
        "",
        "## Decisions Trace",
        "- Planner sources considered:",
        *( [f"  - {s}" for s in sources] if sources else ["  - (none) "] ),
        "- Gating relies on deterministic tests; reviewer does not modify code.",
        "",
        "## Risks",
        *(blockers if blockers else ["None observed beyond evaluation blockers."]),
        "",
        "## Appendix",
        f"Artifacts directory: `{PROJECT_ROOT / 'tests' / 'results'}`",
    ]

    # Prefer local Ollama client by default; fallback to HTTP endpoint; then deterministic
    try:
        import os as _os
        _use_llm = _os.environ.get("REVIEWER_USE_LLM", "1") == "1"
        if _use_llm:
            try:
                import ollama as _oll
                def _select_model() -> str:
                    resp = _oll.list()
                    models = [m.get('model') or m.get('name') for m in resp.get('models', []) if m.get('model') or m.get('name')]
                    preferred = [
                        "qwen3-coder:latest",
                        "qwen2.5-coder:latest",
                        "qwen2.5-coder:1.5b-base",
                    ]
                    for p in preferred:
                        if p in models:
                            return p
                    # Any qwen2.5-coder variant
                    for m in models:
                        if 'qwen2.5-coder' in (m or '').lower():
                            return m
                    # Fallback: any qwen model or first available
                    for m in models:
                        if 'qwen' in (m or '').lower():
                            return m
                    if models:
                        return models[0]
                    raise RuntimeError("No local models available via Ollama")

                _model = _select_model()
                _prompt = (
                    "You are an explainability-only reviewer. Summarise evaluation status, blockers, and risks from the following context, "
                    "then provide concise recommendations for remediation.\n"
                    "Context:\n"
                    f"- Gating: {gating_status}\n"
                    f"- Failing suites: {failing_suites}\n"
                    f"- Tests scanned: {tests_scanned}\n"
                    f"- Blockers: {', '.join(blockers) if blockers else 'None'}\n"
                    f"- Planner overall_ok: {overall_ok}\n"
                    f"- Sources count: {len(sources)}\n\n"
                    "Output format:\n"
                    "Observations:\n- <bullet points>\n"
                    "Recommendations:\n- <bullet points>\n"
                )
                _resp = _oll.generate(model=_model, prompt=_prompt, options={"temperature": 0.1, "top_p": 0.9}, stream=False)
                _text = (_resp.get("response") or "").strip()
                if _text:
                    lines.extend(["", "## LLM Narrative", _text])
                else:
                    lines.extend(["", "## LLM Narrative", "(LLM responded empty; proceeding with deterministic report)"])
            except Exception as _e:
                # Fallback to HTTP call to /api/generate using available qwen coder tags
                import urllib.request as _urlreq
                import urllib.error as _urlerr
                import json as _json2
                _host = _os.environ.get("OLLAMA_HOST", "http://localhost:11434").rstrip("/")
                # Try listing tags to select model
                _model = _os.environ.get("REVIEWER_MODEL", "")
                try:
                    _tags_req = _urlreq.Request(f"{_host}/api/tags")
                    with _urlreq.urlopen(_tags_req, timeout=10) as _tags_resp:
                        _tags = _json2.loads(_tags_resp.read().decode("utf-8")).get("models", [])
                        _names = [t.get("model") or t.get("name") for t in _tags]
                        for cand in ["qwen3-coder:latest", "qwen2.5-coder:latest", "qwen2.5-coder:1.5b-base"]:
                            if cand in _names:
                                _model = cand
                                break
                        if not _model:
                            for n in _names:
                                if "qwen2.5-coder" in (n or "").lower():
                                    _model = n
                                    break
                        if not _model and _names:
                            _model = _names[0]
                except Exception:
                    if not _model:
                        _model = "qwen2.5-coder:latest"
                _endpoint = f"{_host}/api/generate"
                _prompt = (
                    "You are an explainability-only reviewer. Summarise evaluation status, blockers, and risks from the following context, "
                    "then provide concise recommendations for remediation.\n"
                    "Context:\n"
                    f"- Gating: {gating_status}\n"
                    f"- Failing suites: {failing_suites}\n"
                    f"- Tests scanned: {tests_scanned}\n"
                    f"- Blockers: {', '.join(blockers) if blockers else 'None'}\n"
                    f"- Planner overall_ok: {overall_ok}\n"
                    f"- Sources count: {len(sources)}\n\n"
                    "Output format:\n"
                    "Observations:\n- <bullet points>\n"
                    "Recommendations:\n- <bullet points>\n"
                )
                _payload = _json2.dumps({"model": _model, "prompt": _prompt, "stream": False, "options": {"temperature": 0.1}}).encode("utf-8")
                _req = _urlreq.Request(_endpoint, data=_payload, headers={"Content-Type": "application/json"})
                try:
                    with _urlreq.urlopen(_req, timeout=60) as _resp:
                        _data = _json2.loads(_resp.read().decode("utf-8"))
                        _text = (_data.get("response") or "").strip()
                        if _text:
                            lines.extend(["", "## LLM Narrative", _text])
                        else:
                            lines.extend(["", "## LLM Narrative", "(LLM responded empty; proceeding with deterministic report)"])
                except Exception:
                    lines.extend(["", "## LLM Narrative", "(LLM enrichment unavailable: proceeding with deterministic report)"])
    except Exception:
        # Do not block report generation
        pass

    try:
        report_md.write_text("\n".join(lines), encoding="utf-8")
    except Exception as e:
        print(f"WARN: Failed to write reviewer report: {e}")

    ok = report_md.exists()

    return {
        "stage": "reviewer",
        "outputs": {
            "reviewer_report_md": str(report_md),
            "evaluation_report_md": str(eval_md),
            "agent_plan_json": str(plan_json),
            "summary_txt": str(summary_txt),
        },
        "ok": ok,
    }


def stage_evaluate() -> dict:
    """Execute full test suite via master runner and produce evaluation report.

    - Runs scripts/test_master.sh --all to generate JUnit and summary artifacts.
    - Writes doc/evaluation-report.md summarising pass/fail counts and blockers.
    - Returns gating status based on exit code and failure counts.
    """
    from pathlib import Path as _Path
    import subprocess as _subprocess
    import os as _os
    import datetime as _dt

    project_root = PROJECT_ROOT
    results_dir = project_root / "tests" / "results"
    html_dir = results_dir / "html"
    junit_dir = results_dir / "junit"
    logs_dir = results_dir / "logs"
    summary_file = html_dir / "summary.txt"
    report_md = REPORTS_DIR / "evaluation-report.md"

    run_cmd = ["bash", str(project_root / "scripts" / "test_master.sh"), "--all"]
    proc = _subprocess.run(run_cmd, cwd=str(project_root))

    failures = 0
    tests_scanned = 0
    if summary_file.exists():
        text = summary_file.read_text(encoding="utf-8")
        import re as _re
        m_fail = _re.search(r"Failing suites:\s*(\d+)", text)
        m_scan = _re.search(r"Tests scanned reports:\s*(\d+)", text)
        if m_fail:
            failures = int(m_fail.group(1))
        if m_scan:
            tests_scanned = int(m_scan.group(1))

    gating_ok = (proc.returncode == 0) and (failures == 0)

    # Build Markdown report
    ts = _dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    blockers = []
    if not gating_ok:
        blockers.append(f"Failing suites: {failures}")
        if proc.returncode != 0:
            blockers.append(f"Master runner exit code: {proc.returncode}")
        if not summary_file.exists():
            blockers.append("Missing summary.txt artifact")

    report_lines = [
        "# Evaluation Report",
        f"Generated: {ts}",
        "",
        "## Inputs",
        f"Run command: `{run_cmd}`",
        f"JUnit dir: `{junit_dir}`",
        f"Summary: `{summary_file}`",
        "",
        "## Results",
        f"Tests scanned: {tests_scanned}",
        f"Failing suites: {failures}",
        f"Runner exit code: {proc.returncode}",
        f"Gating status: {'PASS' if gating_ok else 'FAIL'}",
        "",
        "## Blockers",
        *(blockers if blockers else ["None"]),
        "",
        "## Logs",
        f"Master log dir: `{logs_dir}`",
    ]
    try:
        report_md.write_text("\n".join(report_lines), encoding="utf-8")
    except Exception as e:
        print(f"WARN: Failed to write evaluation report: {e}")

    return {
        "stage": "evaluate",
        "outputs": {
            "evaluation_report_md": str(report_md),
            "summary_txt": str(summary_file),
            "junit_dir": str(junit_dir),
            "logs_dir": str(logs_dir),
        },
        "rc": proc.returncode,
        "ok": gating_ok and report_md.exists(),
    }


def persist_run(metadata: dict) -> Path:
    ensure_dirs()
    ts = datetime.now().strftime("%Y%m%d-%H%M%S")
    out = RUNS_DIR / f"run-{metadata.get('stage','unknown')}-{ts}.json"
    out.write_text(json.dumps(metadata, indent=2), encoding="utf-8")
    print(f"✓ Persisted run metadata: {out}")
    return out


def main():
    parser = argparse.ArgumentParser(
        description="Agents CLI Runner",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s --all                          # Run env → plan → contracts → schema → scaffold → bpel
  %(prog)s --contracts                    # Run contracts stage only
  %(prog)s --schema                       # Run schema governance stage only
  %(prog)s --scaffold                     # Run Maven scaffold stage only
  %(prog)s --bpel                         # Run bpel analysis stage only
  %(prog)s --build                        # Run build (bootstrap + package) stage only
  %(prog)s --tests                        # Run integration tests stage only
  %(prog)s --services                     # Run inbound services stage only
  %(prog)s --clients                      # Run clients stage only
  %(prog)s --agent plan                   # Run specific agent stage by name
  %(prog)s --from contracts --all         # Start from contracts, then continue
  %(prog)s --contracts --source-root sample/AnotherFixture
        """,
    )
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument("--all", action="store_true", help="Run Env → Plan → Contracts → Schema → Scaffold → BPEL")
    group.add_argument("--contracts", action="store_true", help="Run contracts stage only")
    group.add_argument("--schema", action="store_true", help="Run schema governance stage only")
    group.add_argument("--scaffold", action="store_true", help="Run Maven scaffold stage only")
    group.add_argument("--bpel", action="store_true", help="Run BPEL analysis stage only")
    group.add_argument("--build", action="store_true", help="Run build (bootstrap + package) stage only")
    group.add_argument("--tests", action="store_true", help="Run integration tests stage only")
    group.add_argument("--services", action="store_true", help="Run inbound services stage only")
    group.add_argument("--clients", action="store_true", help="Run clients stage only")
    # Move --agent into the mutually exclusive group so it satisfies required
    group.add_argument("--agent", choices=[
        "env","plan","contracts","schema","scaffold","bpel","build","services","clients","tests","evaluate","reviewer"
    ], help="Run a specific stage by name")
    
    parser.add_argument("--from", dest="from_stage", choices=["env", "plan", "contracts", "schema", "scaffold", "bpel", "build", "services", "clients", "tests"], help="Start from a specific stage for --all")
    parser.add_argument("--source-root", help="Override contract discovery root (defaults to sample/)")
    parser.add_argument("--bpel-file", help="Explicit BPEL file to analyze")
    parser.add_argument("--strict-venv", action="store_true", help="Exit if venv not active")
    parser.add_argument("--no-agent", action="store_true", help="Disable agent use for BPEL analysis")
    parser.add_argument("--evaluate", action="store_true", help="Run evaluator/referee stage to produce evaluation report")
    parser.add_argument("--reviewer", action="store_true", help="Run reviewer/interpreter stage to produce explanation report")

    args = parser.parse_args()

    ensure_dirs()
    venv_guard(strict=args.strict_venv)

    source_root = Path(args.source_root).resolve() if args.source_root else None
    bpel_path = Path(args.bpel_file).resolve() if args.bpel_file else None

    runs = []
    # Handle --agent generic selector
    if args.agent:
        st = args.agent
        if st == "env":
            r = stage_env()
        elif st == "plan":
            r = stage_plan()
        elif st == "contracts":
            r = stage_contracts(source_root)
        elif st == "schema":
            r = stage_schema()
        elif st == "scaffold":
            r = stage_scaffold()
        elif st == "bpel":
            r = stage_bpel(root=source_root or PROJECT_ROOT, bpel_file=bpel_path, use_agent=not args.no_agent)
        elif st == "build":
            r = stage_build()
        elif st == "services":
            r = stage_services()
        elif st == "clients":
            r = stage_clients()
        elif st == "tests":
            r = stage_tests()
        elif st == "evaluate":
            r = stage_evaluate()
        elif st == "reviewer":
            r = stage_reviewer()
        else:
            raise SystemExit(f"Unknown agent stage: {st}")
        persist_run(r)
        runs.append(r)
    elif args.build:
        r = stage_build()
        persist_run(r)
        runs.append(r)
    elif args.tests:
        r = stage_tests()
        persist_run(r)
        runs.append(r)
    elif args.services:
        r = stage_services()
        persist_run(r)
        runs.append(r)
    elif args.clients:
        r = stage_clients()
        persist_run(r)
        runs.append(r)
    elif args.all:
        order = ["env", "plan", "contracts", "schema", "scaffold", "bpel", "build", "services", "clients", "tests"]
        start_idx = 0
        if args.from_stage:
            try:
                start_idx = order.index(args.from_stage)
            except ValueError:
                start_idx = 0
        for st in order[start_idx:]:
            if st == "env":
                r = stage_env()
            elif st == "plan":
                r = stage_plan()
            elif st == "contracts":
                r = stage_contracts(source_root)
            elif st == "schema":
                r = stage_schema()
            elif st == "scaffold":
                r = stage_scaffold()
            elif st == "bpel":
                r = stage_bpel(root=source_root or PROJECT_ROOT, bpel_file=bpel_path, use_agent=not args.no_agent)
            elif st == "build":
                r = stage_build()
            elif st == "services":
                r = stage_services()
            elif st == "tests":
                r = stage_tests()
            else:
                continue
            persist_run(r)
            runs.append(r)

    # Print a concise summary
    print("\n=== Run Summary ===")
    for r in runs:
        print(f"- {r['stage']}: ok={r.get('ok')} outputs={r.get('outputs')}")


if __name__ == "__main__":
    main()