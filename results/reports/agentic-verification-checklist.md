# Agentic Verification Checklist

Use this checklist to verify that Jira tickets and implementation reflect agentic system requirements across planning, execution, testing, and governance.

## Governance
- British English used across documentation and logs.
- No mocks anywhere; unavailable endpoints marked "Blocked" with evidence.
- Secrets not committed; configuration via environment variables only.
- All automation and scripts assert active `venv` before execution.

## Generality and Discovery
- `SOURCE_ROOT` accepted via CLI/env; default inferred when absent.
- Recursive discovery of `*.bpel`, `*.wsdl`, `*.xsd` under `SOURCE_ROOT` with deterministic selection if multiple.
- No hardwired service names, paths, namespaces, or constants; parameters derived at runtime.
- Tests run with ≥2 distinct `SOURCE_ROOT` fixtures to detect hardcoding.

## Agents and Orchestration
- Planner agent validates `results/meta/agent-plan.json` against LaTeX and master plan.
- CLI runner exists: `scripts/agents/run.py` with `--all`, `--agent <name>`, `--from <stage>`.
- Orchestration spec `orchestration/spec.yaml` generated/validated from BPEL when absent; includes invokes and error paths.
- Integration Builder and Mapping Builder produce `modules/orchestration/ROUTES.md` and `modules/mappings/MAPPINGS.md`.

## Technical Consistency
- Maven multi-module scaffold parameterises service identifiers and derives packages/namespaces from contracts.
- CXF/JAXB generation verifies signatures and namespaces against contracts.
- Camel routes mirror BPEL sequence (CRP11X1 → CRP10X1 → LOC3X1M) with fault handling to `SimpleFault.xsd`.
- MapStruct mappings deterministically replace BPEL assigns, derived from `.map` artefacts.

## Testing and Evidence
- Master test runner `tests/run_tests.py` executes discoverable tests; tickets reference real Python tests.
- JUnit XML and HTML outputs stored under `tests/results/{junit,html}/`; logs under `tests/results/logs/`.
- Contract compliance tests validate signatures/schemas; integration tests use real endpoints or mark "Blocked".
- Evaluation report produced: `doc/evaluation-report.md` summarising pass/fail and blockers.

## CI/CD and Containers
- CI pipeline executes preflight → build → tests → package deterministically; archives artefacts.
- Containers externalise configuration via env vars; no secrets in images or compose files.
- Health endpoints checked; logs collected and structured.