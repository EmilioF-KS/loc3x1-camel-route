# Master Plan (Agentic Runtime) — Loc-Camel-Route

This document refactors the master plan to align with the agentic system defined in `doc/Loc-Camel-Route.tex`. It treats work as runtime capabilities executed by agents orchestrated via Ollama + LangGraph, not as manual steps assigned to human roles. All outcomes are evidence-driven and auditable.

## Operating Model
- Agents operate a closed-loop cycle: Discover → Validate → Synthesise → Verify → Harden → Monitor → Adapt.
- Agents generate artefacts and decisions as evidence: JSON/YAML specs, build outputs, logs, reports, and test results.
- No mocks or synthetic data. Unavailable endpoints are recorded as Blocked, not simulated.
- All automation runs inside the project virtual environment (`venv`); scripts assert activation.

## Agent Roster and Responsibilities
- Environment Installer: tooling preflight, JDK/Maven readiness, `doc/tooling-report.md`.
- Planner: reads `.tex` and inventories; emits `doc/agent-plan.json` with phases and acceptance criteria.
- Contract Reader: scans WSDL/XSD; emits `contracts/inventory.json` and `contracts/validation-report.md`.
- BPEL Parser: emits `orchestration/spec.yaml` and `doc/bpel-analysis.md` with `data_mappings`.
- Schema Aligner: produces `contracts/canonical-map.yaml` and `contracts/alignment-report.md`.
- Integration Builder (CXF/Camel): scaffolds inbound service and clients; generates routes per orchestration spec.
- Mapping Builder (MapStruct): deterministic transformations replacing BPEL assigns; unit tests with no mocks.
- Test Designer: contract and integration tests; stores evidence under `tests/results/`.
- Evaluator/Referee: runs builds and tests; emits `doc/evaluation-report.md` and gates releases.

## Runtime Playbooks (Capabilities)
- Preflight: Ensure toolchain (Java 17, Maven, jq/yq, Python packages) and emit `doc/tooling-report.md`.
- Contracts Discovery: Enumerate WSDL/XSD; verify `SimpleFault.xsd` usage; update `contracts/inventory.json`.
- BPEL Orchestration Extraction: Identify inbound ops and partner call order (CRP11X1 → CRP10X1 → LOC3X1M).
- Schema Governance: Select canonical schemas; maintain `contracts/canonical-map.yaml`; enforce namespace/version consistency.
- Code Generation: Trigger CXF/JAXB from contracts; store generated artefacts in modules.
- Maven Scaffold: Parent `pom.xml` and modules (`contracts`, `services/loc3x1b`, `clients/{loc3x1m,crp11x1,crp10x1}`, `mappings`, `orchestration`, `tests`); Java 17 toolchain; pluginManagement.
- Camel Orchestration: Define routes mirroring BPEL; externalise endpoints/timeouts; SimpleFault error handling.
- Deterministic Mappings: Implement MapStruct mappers from `data_mappings` with unit tests.
- Testing: Contract compliance and integration against real endpoints; mark Blocked when unavailable.
- Evaluation: Aggregate evidence; gate release.

## Evidence and Artefact Locations
- `doc/`: plans, reports, LaTeX, evaluation summaries.
- `contracts/`: inventory, canonical maps, validation reports.
- `orchestration/`: `spec.yaml`, analysis notes, route documentation.
- `modules/`: Maven modules for services, clients, mappings, orchestration, tests.
- `tests/`: test plan, JUnit XML, HTML, logs under `tests/results/`.

## Governance
- British English; no secrets committed; configuration by environment-only inputs.
- venv-only execution; scripts assert activation and exit otherwise.
- All agent decisions logged with context, alternatives, implementation details, and success metrics.
- Gradual rollout, automatic rollback on failure, and human override controls.

## Acceptance Criteria (Agent Layer)
- `doc/agent-plan.json` exists and matches `Loc-Camel-Route.tex` architecture.
- `contracts/inventory.json` and `contracts/canonical-map.yaml` complete and validated.
- `orchestration/spec.yaml` reflects BPEL invokes and error paths.
- Maven scaffold builds (`mvn -q -DskipTests package`) across all modules with Java 17.
- Inbound LOC3X1B service starts and exposes WSDL; CXF clients generated for partners.
- Camel routes implement the BPEL-derived sequence with SimpleFault handling.
- MapStruct mappings implemented and unit tests pass (no mocks).
- Integration tests against real endpoints succeed or are recorded as Blocked with evidence.
- Evaluation report produced; release gating wired to agent outputs.

## CI/CD and Containers (Agent-Orchestrated)
- Local runner scripts invoked by agents: preflight, code generation, build, test, package.
- Hosted CI configuration aligns with local runner; archives artefacts deterministically.
- Multi-stage Dockerfiles for modules; Compose services for inbound service, clients, orchestration, FastAPI.

## Frontend and API Hooks
- React dashboard for evidence and orchestration controls (optional).
- FastAPI for evidence metadata and agent orchestration hooks (`POST /agents/run`, `POST /orchestrations/run`).

## Task Execution Policy
- Operate strictly within `venv`; enforce environment-only configuration.
- Deterministic generation from contracts; avoid manual drift.
- Every agent capability ends with tests and evidence capture.

## Note on Previous Plan Structure
This refactor supersedes human-assigned phased tasks. Where the original `master-plan.txt` used "Assigned to: <Role>", this document uses "Responsible Agent: <Agent>" and expresses work as runtime agent capabilities. The required outcomes and artefacts remain the same but are produced dynamically by agents according to `doc/Loc-Camel-Route.tex`.