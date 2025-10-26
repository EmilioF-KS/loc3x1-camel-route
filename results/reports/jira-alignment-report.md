# Jira Tickets Alignment Report

Date: 26 October 2025
Scope: doc/jira-tickets.txt compared against doc/master-plan.txt, doc/master-plan.agentic.md, doc/Loc-Camel-Route.tex, orchestration/spec.yaml, contracts/inventory.json, doc/bpel-analysis.md, modules/*, tests/*, scripts/*

## Summary
Tickets LOC-000–LOC-019 largely match the implemented state. Most items are Implemented with evidence present. A few discrepancies remain that require small corrections to fully satisfy governance and generality requirements.

## Confirmed Implementations
- Preflight reporting: `scripts/preflight.sh` and `doc/tooling-report.md` exist; venv and tool versions captured.
- Contract inventory: `contracts/inventory.json` and `contracts/inventory.md` present with WSDL/XSD discovery and operations.
- BPEL analysis: `doc/bpel-analysis.md` and `orchestration/spec.yaml` generated; partner links, inbound ops, sequence.
- Canonical schema governance: `contracts/canonical-map.yaml` and validator tests present.
- Maven scaffold: `modules/` multi-module project with `pom.xml` per module; aggregator present.
- Inbound service (LOC3X1B) and outbound clients (LOC3X1M, CRP11X1, CRP10X1): modules and CXF/JAXB artefacts present.
- Camel orchestration: routes present; `modules/orchestration/ROUTES.md` exists.
- Fault handling: SimpleFault.xsd imported and validated across contracts.
- Tests: Python-based test suite (`tests/run_tests.py` + individual tests) present; evidence directories scaffolded.

## Discrepancies and Corrections
- Tooling preflight:
  - `pyyaml` missing in `doc/tooling-report.md` → install in venv and re-run preflight.
  - Smoke build skipped due to root `pom.xml` detection → update preflight to detect aggregator under `modules/` and run `mvn -q -DskipTests package` there.
- Orchestration spec:
  - Process name mismatch: `orchestration/spec.yaml` uses `LocationRetrievalLOC3X1BProcess` while BPEL file is `LocationRetrievalLOC3X1Process` → regenerate/spec-fix to use the actual BPEL process name deterministically.
  - Mapping `source_file` entries include external `uploads/...` paths → normalise to repository-relative, discovered paths under `SOURCE_ROOT`.
- Generality in scripts/tests:
  - Discovery logic sometimes assumes `sample/` fixed root → ensure all scripts/tests accept `SOURCE_ROOT` env/CLI and discover recursively.
  - Tests should exercise ≥2 fixtures to catch hardcoding; add secondary fixture set.
- Governance:
  - Enforce British English and "no mocks" in acceptance criteria across tickets; record unavailable endpoints as "Blocked" with evidence.
- Evaluation:
  - Final evaluation report flags runner exit code 1 in one pass → re-run after preflight fixes and mappings normalisation.

## Ticket-Level Notes
- LOC-000 Preflight: Implemented with minor gaps (pyyaml, aggregator smoke build). Update script and rerun.
- LOC-001/002 BPEL/Orchestration: Implemented. Fix process name and path normalisation.
- LOC-003 Planner Agent: Implemented (`results/meta/agent-plan.json`).
- LOC-004–006 Services/Clients: Implemented; ensure parameterised configuration and no secrets.
- LOC-007–012 Camel/Runtime/CI: Implemented; confirm governance acceptance and evidence directories.
- LOC-013/014 Contract Validators: Implemented; maintain ≥2 fixtures and Blocked evidence posture.
- LOC-015–019 Packaging/Runbook/Audit: Partially present; quality gates and full release packaging remain pending.

## Actions
- Patch `scripts/preflight.sh` to detect aggregator `pom.xml` under `modules/` and install `pyyaml` in venv.
- Regenerate or edit `orchestration/spec.yaml` to fix process name and normalise mapping paths.
- Update tests and scripts to honour `SOURCE_ROOT` and add a second fixture set.
- Re-run tests via `tests/run_tests.py --all`; publish JUnit and HTML under `tests/results/`.

## Conclusion
The repository aligns with the majority of tickets and the agentic master plan. After addressing minor discrepancies, the system will fully meet governance and generality requirements with deterministic outputs and complete evidence capture.