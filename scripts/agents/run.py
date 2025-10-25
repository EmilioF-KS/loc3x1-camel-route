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

PROJECT_ROOT = Path(__file__).resolve().parents[2]
DOC_DIR = PROJECT_ROOT / "doc"
RUNS_DIR = DOC_DIR / "agent-runs"
PREVIEW_DIR = DOC_DIR / ".preflight"

# Ensure project root on sys.path for module imports
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

# Imports handled via dynamic import in stage implementations


def ensure_dirs():
    RUNS_DIR.mkdir(parents=True, exist_ok=True)
    PREVIEW_DIR.mkdir(parents=True, exist_ok=True)


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


# Stage implementations

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
    report_path = DOC_DIR / ".preflight" / "agent-checks.md"
    return {
        "stage": "env",
        "outputs": {"agent_checks_md": str(report_path)},
        "ok": report_path.exists(),
    }


def stage_plan() -> dict:
    """Run Planner Agent to emit agent-plan.json and agent-plan.log."""
    planner = import_module_from_path('planner_agent', PROJECT_ROOT / 'scripts' / 'planner_agent.py')
    planner.main()
    return {
        "stage": "plan",
        "outputs": {
            "agent_plan_json": str(DOC_DIR / "agent-plan.json"),
            "agent_plan_log": str(DOC_DIR / "agent-plan.log"),
        },
        "ok": (DOC_DIR / "agent-plan.json").exists(),
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
    report_out = DOC_DIR / ".preflight" / "contracts-checks.md"
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
    out_md = DOC_DIR / "bpel-analysis.md"
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

    parser.add_argument("--from", dest="from_stage", choices=["env", "plan", "contracts", "schema", "scaffold", "bpel", "build", "services", "clients", "tests"], help="Start from a specific stage for --all")
    parser.add_argument("--source-root", help="Override contract discovery root (defaults to sample/)")
    parser.add_argument("--bpel-file", help="Explicit BPEL file to analyze")
    parser.add_argument("--strict-venv", action="store_true", help="Exit if venv not active")
    parser.add_argument("--no-agent", action="store_true", help="Disable agent use for BPEL analysis")

    args = parser.parse_args()

    ensure_dirs()
    venv_guard(strict=args.strict_venv)

    source_root = Path(args.source_root).resolve() if args.source_root else None
    bpel_path = Path(args.bpel_file).resolve() if args.bpel_file else None

    runs = []
    if args.contracts:
        r = stage_contracts(source_root)
        persist_run(r)
        runs.append(r)
    elif args.schema:
        r = stage_schema()
        persist_run(r)
        runs.append(r)
    elif args.scaffold:
        r = stage_scaffold()
        persist_run(r)
        runs.append(r)
    elif args.bpel:
        r = stage_bpel(root=source_root or PROJECT_ROOT, bpel_file=bpel_path, use_agent=not args.no_agent)
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