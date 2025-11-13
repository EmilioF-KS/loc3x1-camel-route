# Project Backlog — Agentic Pipeline (LangGraph + Ollama) to Generate a9-Style Camel Project

This backlog defines all tasks required to implement an agentic pipeline that ingests a “Dependencies”-style folder and produces an output equivalent in structure and functionality to `CHUBB/00-Emilio-Logic/a9.location-orchestration-rest`. Each ticket includes clear acceptance criteria, dependencies, and testing requirements. Follow CI/CD best practices and ensure each ticket is completed and tested before starting dependent tasks.

Key outcomes:

- Generate a Spring Boot + Apache Camel YAML project mirroring `a9` structure.
- Provide deterministic conversion for well-defined steps; use LLM assistance only where required.
- Include Dockerization, `requirements.txt` for the Python agent, CI/CD pipelines, and executable end-to-end tests.

Conventions:

- Status values: `To Do`, `In Progress`, `Blocked`, `Done`.
- Priorities: `P1` (critical), `P2` (high), `P3` (medium).
- IDs: `AGENT-XXX` for agent pipeline tickets; `GEN-XXX` for generated Java project tickets; `OPS-XXX` for operations/CI/CD.
- Formats: Orchestration and route DSL are YAML-first; planning artifacts (manifest, graph, contracts, DTO) are JSON. Orchestration plan is produced as YAML with a JSON mirror for tests/tooling.

Ticket fields template:

- Name
- Status / Priority / Dependencies
- Description
- Inputs
- Expected Outputs
- Acceptance Criteria (checkboxes)
- Tests
- Artifacts
- CI/CD Hooks
- Estimate

---

## Epic: Governance & Bootstrapping

### AGENT-001 — Define repository structure and contribution guidelines

- Status: To Do
- Priority: P2
- Dependencies: None
- Description: Define repo layout for `agent/` (Python), `generated/` (output projects), `docs/`, and `ci/`. Add CONTRIBUTING.md, code-style rules, PR checklist, and branching strategy.
- Inputs: N/A
- Expected Outputs: Contribution guidelines and repository structure definition.
- Acceptance Criteria:
  - [ ] Repository has clear directories: `agent/`, `docs/`, `ci/`, `examples/`, `generated/`.
  - [ ] CONTRIBUTING.md and coding standards documented (Python and Java).
  - [ ] PR checklist includes tests, lint, schema validations, and route DSL checks.
- Tests: N/A (documentation verification).
- Artifacts: `CONTRIBUTING.md`, `README.md` (root), repo tree.
- CI/CD Hooks: Lint and test gates enforced for PRs.
- Estimate: 2d

### AGENT-002 — Secrets and configuration management policy

- Status: To Do
- Priority: P2
- Dependencies: AGENT-001
- Description: Define how endpoints, credentials, and environment configs are handled. Use `.env` + secret manager stubs; never commit secrets.
- Inputs: N/A
- Expected Outputs: Root `config.md` documenting env keys and policy; `generated/.env.template` for downstream project.
- Acceptance Criteria:
  - [ ] Root `config.md` documents environment keys and policy; backend runs without `.env` via defaults.
  - [ ] `generated/.env.template` lists downstream project env keys (no secrets).
  - [ ] Secret handling and environment variables mapping documented.
- Tests: Verify no plaintext secrets in repo via CI secret scan.
- Artifacts: `config.md` (root), `generated/.env.template`.
- CI/CD Hooks: Secret scan stage enabled.
- Estimate: 1d

### AGENT-003 — Environment matrix and tooling setup

- Status: To Do
- Priority: P2
- Dependencies: AGENT-001
- Description: Define supported OS/architecture, Python and Java versions, Maven, Docker.
- Inputs: N/A
- Expected Outputs: Preflight script and environment docs.
- Acceptance Criteria:
  - [ ] Document versions (root README or config): Python 3.11+, Java 17, Maven 3.9+, Docker 24+.
  - [ ] Preflight checks script (`agent/scripts/preflight.sh`) validates toolchain presence.
- Tests: Preflight script runs and reports pass/fail.
- Artifacts: `agent/scripts/preflight.sh` and README update noting environment matrix.
- CI/CD Hooks: Preflight in pipeline init stage.
- Estimate: 1d

---

## Epic: Exploration Agent (Inventory & Mapping)

### AGENT-010 — File discovery and artifact classification

- Status: Done
- Priority: P1
- Dependencies: AGENT-003
- Description: Recursively discover files (BPEL, Mediation XML, WSDL, XSD, IBM `map` XML, sample payloads). Classify by type.
- Inputs: Path to input folder (e.g., `CHUBB/Dependencies/`).
- Expected Outputs: Artifact manifest JSON with types and paths.
- Acceptance Criteria:
  - [X] JSON manifest lists all artifacts with type and path.
  - [X] Supports `Dependencies/` input folder; handles nested imports.
  - [X] Integration run on `Dependencies/` produces manifest with >90% coverage.
- Tests:
  - Unit: parse synthetic sample tree, verify type classification.
  - Integration: run against `CHUBB/Dependencies/`, produce manifest.
- Artifacts: `agent/manifest.json` (example output), `agent/src/inventory.py`.
- CI/CD Hooks: Unit tests executed on PR.
- Estimate: 2d

### AGENT-011 — Namespace resolution and import graph builder

- Status: In Progress
- Priority: P1
- Dependencies: AGENT-010
- Description: Build a dependency graph linking WSDL/XSD/BPEL/Mediation `import` chains and record namespace → file mappings.
- Inputs: Artifact manifest.
- Expected Outputs: Graph JSON and namespace resolution table.
- Acceptance Criteria:
  - [ ] Graph includes nodes (schema, wsdl, bpel, mediation, map) with edges (`imports`, `portType`, `message`, `mapRef`).
  - [ ] Namespace table resolves prefixes and URIs without conflicts.
  - [ ] Cycles detected and reported.
- Tests: Unit tests for import graph; integration run on Dependencies to verify connectivity.
- Artifacts: `agent/src/graph.py`, `agent/graph.json` (example).
- CI/CD Hooks: Graph validation step.
- Estimate: 2d

### AGENT-012 — Contract extraction (operations, messages, types)

- Status: To Do
- Priority: P1
- Dependencies: AGENT-011
- Description: Extract WSDL operations, message parts, and XSD complexTypes relevant to orchestrations.
- Inputs: Graph JSON, WSDLs, XSDs.
- Expected Outputs: Contract JSON listing operations, messages, types.
- Acceptance Criteria:
  - [ ] Contract JSON lists operations and DTO type plans.
  - [ ] Handles LOC3X1 and related provider contracts.
  - [ ] Missing contracts reported with actionable messages.
- Tests: Unit parse of WSDL/XSD samples; integration with workspace WSDLs.
- Artifacts: `agent/contracts.json`, `agent/src/contracts.py`.
- CI/CD Hooks: Contract extraction tests.
- Estimate: 2d

---

## Epic: Deterministic Converters

### AGENT-020 — XSD → Java DTO generation plan

- Status: To Do
- Priority: P1
- Dependencies: AGENT-012
- Description: Produce DTO generation plan and validation rules matching target EI schemas.
- Inputs: Contract JSON, XSDs.
- Expected Outputs: DTO plan JSON with classes, fields, constraints.
- Acceptance Criteria:
  - [ ] DTO plan specifies classes, fields, constraints and package naming.
  - [ ] Matches schemas used by `a9` equivalent routes.
- Tests: Validate sample XML against generated DTO schemas (planning stage stubs ok).
- Artifacts: `agent/dto_plan.json`.
- CI/CD Hooks: DTO plan validation.
- Estimate: 1d

### AGENT-021 — BPEL/Mediation → orchestration plan (normalized)

- Status: Done
- Priority: P1
- Dependencies: AGENT-011
- Description: Convert BPEL/Mediation flow to a normalized plan: inputs, callouts, transforms, error paths, logging.
- Inputs: BPEL and Mediation XML, Graph JSON.
- Expected Outputs: Orchestration plan YAML (authoritative) with JSON mirror.
- Acceptance Criteria:
  - [x] Plan includes sequence, branches, provider calls (CRP10X1, CRP11X1), mapping steps.
  - [x] Exported as YAML used by route generation; JSON mirror retained for programmatic use.
  - [x] Error and logging behavior captured.
- Tests: Unit on synthetic flows; integration on `LocationRetrievalLOC3X1Process.bpel` and `LocationRetrievalLOC3X1Mediation.xml`.
- Artifacts: `agent/orchestration_plan.yaml`, `agent/orchestration_plan.json`, `agent/src/orchestration.py`.
- CI/CD Hooks: Orchestration plan checks.
- Estimate: 3d

### AGENT-022 — Mapping synthesis from contracts → XSLT (deterministic)

- Status: In Progress
- Priority: P1
- Dependencies: AGENT-012 (contracts), AGENT-021 (orchestration plan)
- Description: Synthesize field-to-field mappings directly from `Dependencies` contracts (XSD/WSDL) and orchestration context; emit XSLT without relying on pre-existing IBM `.map` files.
- Inputs: XSD types (source/destination), WSDL operation context, orchestration plan.
- Expected Outputs: XSLT files implementing deterministic copy/move based on shared field names and structural alignment.
- Acceptance Criteria:
  - [x] Mappings are derived programmatically from XSDs (no reliance on curated Emilio maps).
  - [x] XSLT preserves element paths and validates against destination XSDs.
  - [ ] Coverage metrics reported: number of fields mapped vs. unmapped.
- Tests: Unit tests on synthesis; integration tests applying XSLT to synthetic inputs and validating vs. target XSDs.
- Artifacts: `agent/output/xslt/*.xsl`, `agent/src/map_synthesis.py`, `agent/src/map_move_to_xslt.py`.
- CI/CD Hooks: Synthesis and validation in CI.
- Estimate: 3d

### AGENT-024 — Mapping coverage and heuristics (AI-assisted)

- Status: To Do
- Priority: P2
- Dependencies: AGENT-022
- Description: Improve mapping synthesis using heuristics/AI (synonyms, type-based hints, namespace joins) to map non-identical fields.
- Inputs: XSDs, DTO plan, naming heuristics, optional embeddings.
- Expected Outputs: Increased coverage of synthesized mappings with rationale traces.
- Acceptance Criteria:
  - [x] Coverage improves over deterministic baseline; unmapped list emitted.
  - [x] Explainable mapping decisions with confidence scores.
- Tests: Evaluation on representative contracts; regression safety checks.
- Artifacts: `agent/src/map_heuristics.py`, `agent/synthesized_maps.json` (with coverage stats).
- Estimate: 4d

### AGENT-023 — Route skeleton synthesis (Camel YAML DSL)

- Status: To Do
- Priority: P1
- Dependencies: AGENT-021
- Description: Generate Camel YAML route skeletons from orchestration plan: platform-http endpoints, HTTP provider calls, error handling, logging.
- Inputs: Orchestration plan YAML (preferred) or JSON mirror.
- Expected Outputs: Camel YAML route files.
- Acceptance Criteria:
  - [ ] YAML routes validate against Camel YAML DSL schema.
  - [ ] Structure mirrors `a9` conventions (controllers, helper routes, provider wiring).
  - [ ] No manual edits required to build.
- Tests: YAML schema lint; snapshot tests for route structure.
- Artifacts: `agent/output/routes/*.yaml`, `agent/src/route_synthesis.py`.
- CI/CD Hooks: Route lint checks.
- Estimate: 3d

---

## Epic: LLM-Assisted Nodes (Bounded, Reviewable)

### AGENT-030 — Translate IBM `map:custom` Java blocks to processors

- Status: Done
- Priority: P2
- Dependencies: AGENT-022
- Description: Use LLM to propose Java processor implementations for custom map code (string trims, conditionals, side-effect free transformations).
- Inputs: IBM maps with `map:custom` blocks.
- Expected Outputs: Java processors and rationales.
- Acceptance Criteria:
  - [x] Generated processors compile, unit tests pass on sample payloads.
  - [x] Human-readable rationale stored for each conversion.
  - [x] Processor outputs validated against expected XML.
- Tests: Unit tests per processor; golden tests comparing outputs to expected XML.
- Artifacts: `agent/output/java_processors/*.java`, rationales in `docs/processors.md`.
- CI/CD Hooks: Processor unit tests.
- Estimate: 3d

### AGENT-031 — Complex mediation branches interpretation

- Status: In Progress
- Priority: P3
- Dependencies: AGENT-021
- Description: Where deterministic mapping is insufficient, generate suggested routing logic; require review gate.
- Inputs: Orchestration plan, mediation XML.
- Expected Outputs: Suggested logic docs and stubs.
- Acceptance Criteria:
  - [x] Suggestions documented; reviewer checkbox required before inclusion.
  - [x] Fallback deterministic stubs included when uncertain.
- Tests: Scenario tests verifying branch behaviors using sample payloads.
- Artifacts: `docs/mediation_notes.md`, stub routes (`agent/output/routes/mediation-suggestions-stub.yaml`).
- CI/CD Hooks: Manual approval gate.
- Estimate: 2d

---

## Epic: Project Scaffold (a9-Style Output)

### GEN-100 — Generate a9-structure Spring Boot + Camel YAML project

- Status: In Progress
- Priority: P1
- Dependencies: AGENT-023, AGENT-020, AGENT-022, AGENT-027, AGENT-033
- Description: Scaffold project with structure identical to `a9.location-orchestration-rest`: `pom.xml`, `src/main/resources/routes/*.yaml`, controllers, config, helper XSLT.
- Inputs: Routes YAML, XSLTs, DTO plan.
- Expected Outputs: a9-style project scaffold.
- Acceptance Criteria:
  - [x] Directory layout and `pom.xml` match `a9` conventions (Java 17, Spring Boot 3.3.x, Camel 4.7.x, YAML DSL, XSLT, Jackson, actuator).
  - [x] Sample payloads and README included.
  - [x] Project builds and routes validate.
- Tests: Build `mvn -DskipTests` succeeds; route lint passes.
- Artifacts: `generated/result/**`.
- CI/CD Hooks: Java build stage and YAML validation.
- Estimate: 2d

### GEN-101 — Controllers and platform-http endpoints

- Status: To Do
- Priority: P1
- Dependencies: GEN-100, AGENT-034
- Description: Create controllers mirroring `a9` endpoints and bind platform-http routes.
- Inputs: Route definitions and DTOs.
- Expected Outputs: Controllers wired to routes and OpenAPI definition.
- Acceptance Criteria:
  - [ ] Endpoints for location retrieval and helper routes exposed.
  - [ ] OpenAPI UI accessible locally.
- Tests: Controller unit tests; endpoint smoke tests.
- Artifacts: `src/main/java/.../controllers/*.java`.
- CI/CD Hooks: Java unit tests stage.
- Estimate: 2d

### GEN-102 — Provider wiring (CRP10X1, CRP11X1)

- Status: To Do
- Priority: P2
- Dependencies: GEN-100, AGENT-021
- Description: Configure external provider URIs, timeouts, retries via properties.
- Inputs: Provider endpoints and policies.
- Expected Outputs: Properties and wiring to routes.
- Acceptance Criteria:
  - [ ] Providers reachable via configurable URIs; graceful timeouts and retry policy.
  - [ ] Fallback stubs for offline runs.
- Tests: Integration tests with mocked providers.
- Artifacts: `application.yaml`, provider stubs.
- CI/CD Hooks: Integration test stage.
- Estimate: 2d

### GEN-103 — XSLT helper route integration

- Status: To Do
- Priority: P2
- Dependencies: GEN-100, AGENT-022
- Description: Integrate generated XSLTs into Camel routes for request/reply mapping.
- Inputs: XSLT files and routes.
- Expected Outputs: Validated XML transformations.
- Acceptance Criteria:
  - [ ] XSLT transformations produce expected XML structures validated against XSD.
  - [ ] Namespaces handled correctly.
- Tests: Transformation unit tests; schema validation tests.
- Artifacts: `src/main/resources/xslt/*.xsl`.
- CI/CD Hooks: XML schema validation stage.
- Estimate: 2d

### GEN-104 — Error handling and logging policy

- Status: To Do
- Priority: P2
- Dependencies: GEN-100
- Description: Add consistent error handling, DLQ or error responses, and structured logging.
- Inputs: Service routes and logging framework.
- Expected Outputs: Error handling pattern and logging configuration.
- Acceptance Criteria:
  - [ ] Errors mapped to meaningful HTTP responses; logs include correlation IDs.
  - [ ] Health endpoints via actuator.
- Tests: Fault injection tests; log format checks.
- Artifacts: logging config.
- CI/CD Hooks: Fault scenario tests.
- Estimate: 1d

## Epic: End-to-End Automation & Codegen

### AGENT-025 — Deterministic BPEL → Camel semantics translator

- Status: To Do
- Priority: P1
- Dependencies: AGENT-021
- Description: Translate IBM BPEL control flow and variables into Camel YAML DSL (sequence, flow/parallel, switch/choice, pick, receive/invoke/reply, assign/variables, faults) to eliminate placeholders and preserve orchestration semantics.
- Inputs: BPEL files, normalized orchestration plan.
- Expected Outputs: Camel YAML routes that reflect BPEL semantics deterministically.
- Acceptance Criteria:
  - [ ] Covers ≥95% of BPEL constructs present in `LocationRetrievalLOC3X1Process.bpel`.
  - [ ] Variables/assignments mapped to headers/properties consistently.
  - [ ] Faults/exceptions mapped to error handlers with HTTP responses.
  - [ ] YAML validates against Camel DSL schema.
- Tests: Unit patterns for BPEL constructs; integration on workspace BPEL.
- Artifacts: `agent/src/orchestration.py` (semantic extraction), `agent/src/route_synthesis.py` (DSL generation).
- CI/CD Hooks: BPEL→Camel regression tests.
- Estimate: 4d

### AGENT-026 — Deterministic Mediation XML → Camel processors and routes

- Status: To Do
- Priority: P1
- Dependencies: AGENT-021, AGENT-030
- Description: Convert IBM Mediation XML branches/filters/enrich/aggregate patterns into Camel route choices, filters, enrich, aggregation, and processors deterministically.
- Inputs: Mediation XML, orchestration plan.
- Expected Outputs: Camel YAML covering mediation semantics; minimal LLM assistance.
- Acceptance Criteria:
  - [ ] Common mediation patterns translated without manual edits.
  - [ ] Branch logic validated with sample payloads.
  - [ ] YAML validates against Camel DSL.
- Tests: Scenario tests for mediation branches; snapshot of generated routes.
- Artifacts: `agent/src/mediation_suggestions.py` (deterministic mode), `agent/src/route_synthesis.py`.
- CI/CD Hooks: Mediation translation tests.
- Estimate: 3d

### AGENT-027 — WSDL/XSD → Java DTOs and CXF client codegen integration

- Status: To Do
- Priority: P1
- Dependencies: AGENT-012
- Description: Integrate JAXB and CXF Maven plugins to generate Java DTOs and SOAP client stubs directly from WSDL/XSD contracts; wire into the generated project.
- Inputs: WSDLs, XSDs from `Dependencies/`.
- Expected Outputs: Generated sources under `generated/` or project `target/generated-sources` with package naming aligned to contracts.
- Acceptance Criteria:
  - [ ] DTOs and client stubs generated and compiled during `mvn clean package`.
  - [ ] Package names and namespaces consistent with target contracts.
  - [ ] Coverage report lists operations/types generated.
- Tests: Build-time verification; unit tests instantiating DTOs.
- Artifacts: `pom.xml` plugin config, `agent/src/contracts.py` updates as needed.
- CI/CD Hooks: Codegen verification stage.
- Estimate: 3d

### AGENT-028 — IBM proprietary `.map` converter → XSLT (if applicable)

- Status: To Do
- Priority: P2
- Dependencies: AGENT-022
- Description: Add parser for proprietary IBM `.map` formats (non-XML) and convert to XSLT, complementing existing XML `map:move` support.
- Inputs: IBM `.map` files.
- Expected Outputs: XSLT files in `agent/output/xslt/*.xsl`.
- Acceptance Criteria:
  - [ ] `.map` files parsed and translated deterministically to XSLT.
  - [ ] Transformations validated against destination XSDs.
- Tests: Unit parsing; integration transforms on sample inputs.
- Artifacts: `agent/src/map_move_to_xslt.py` (extended), docs on limitations.
- CI/CD Hooks: Map conversion validation.
- Estimate: 3d

### AGENT-029 — Automatic binding inference and route wiring

- Status: To Do
- Priority: P1
- Dependencies: AGENT-012, AGENT-022, AGENT-027
- Description: Infer bindings for controllers, routes, XSLT steps, and provider URIs from contracts and orchestration; generate complete wiring so the project builds and runs without placeholders.
- Inputs: Contracts JSON, orchestration plan, synthesized XSLTs.
- Expected Outputs: Fully wired Camel YAML and Spring components.
- Acceptance Criteria:
  - [ ] All routes reference existing beans/XSLTs and DTOs with no TODOs.
  - [ ] Provider URIs configurable via properties; defaults provided.
  - [ ] Build passes without manual edits.
- Tests: Wiring validation; smoke tests on endpoints.
- Artifacts: `agent/src/route_synthesis.py`, scaffold updates.
- CI/CD Hooks: Wiring correctness checks.
- Estimate: 3d

### AGENT-033 — One‑click generator CLI (IBM path → runnable project)

- Status: To Do
- Priority: P1
- Dependencies: AGENT-010, AGENT-012, AGENT-021, AGENT-022, AGENT-023, AGENT-027, AGENT-029
- Description: Implement a single command to run discovery → contracts → orchestration plan → map/XSLT synthesis → route synthesis → scaffold → controllers → provider wiring → build.
- Inputs: IBM `Dependencies/` path.
- Expected Outputs: `generated/a9-like-project` ready to build and run.
- Acceptance Criteria:
  - [ ] `python -m agent.scripts.scaffold_project --input <IBM path> --output generated/a9-like-project --auto` completes end‑to‑end.
  - [ ] `mvn clean package` succeeds on the generated project.
  - [ ] Service runs locally; platform‑http endpoint returns expected structure for `happy_path_example.xml`.
- Tests: End‑to‑end pipeline tests; smoke tests.
- Artifacts: `agent/scripts/scaffold_project.py` updates, `generated/a9-like-project/**`.
- CI/CD Hooks: One‑click pipeline check.
- Estimate: 2d

### AGENT-034 — Controller generator and OpenAPI

- Status: To Do
- Priority: P2
- Dependencies: AGENT-012, AGENT-023, AGENT-027
- Description: Auto‑generate Spring controllers and OpenAPI spec from contracts; wire platform‑http endpoints to Camel routes.
- Inputs: Contracts JSON, route YAML.
- Expected Outputs: Controllers and OpenAPI definition.
- Acceptance Criteria:
  - [ ] Controllers compile and expose endpoints used by routes.
  - [ ] OpenAPI UI accessible locally.
- Tests: Controller unit tests; OpenAPI smoke.
- Artifacts: `src/main/java/.../controllers/*.java`, OpenAPI file.
- CI/CD Hooks: Controller unit tests.
- Estimate: 2d

### AGENT-035 — Maven assembly and dependency management

- Status: To Do
- Priority: P2
- Dependencies: GEN-100, AGENT-027
- Description: Ensure `pom.xml` includes Camel 4.x, Spring Boot 3.3.x, CXF codegen, JAXB/Jakarta, XSLT, Jackson; configure plugin executions for codegen and resource packaging.
- Inputs: Project scaffold, contracts.
- Expected Outputs: Reproducible builds with generated sources and resources.
- Acceptance Criteria:
  - [ ] `mvn clean package` passes consistently across environments.
  - [ ] Generated sources included in compilation.
- Tests: Build reproducibility checks; plugin execution tests.
- Artifacts: `pom.xml` updates.
- CI/CD Hooks: Build verification.
- Estimate: 1d

#### Delivery Plan & Sequencing (Automation Roadmap)

Milestones define a logical and chronological path to full automation. Each milestone has gating dependencies; retake tasks ensure previously refactored tickets are completed professionally under this epic.

- M0 — Preflight and Contracts Ready
  - Scope: Inputs discovered; contracts built; minimal scaffold verified.
  - Tickets: AGENT-010..012, GEN-100 (baseline scaffold already working).
  - Gate: Manifest and contracts exist; agent tests pass.

- M1 — Deterministic Translators
  - Scope: Deterministic BPEL and Mediation translation feeding route synthesis.
  - Tickets: AGENT-025, AGENT-026; updates flow into `route_synthesis.py`.
  - Gate: Generated YAML validates against Camel DSL; coverage ≥95% of constructs in workspace.

- M2 — DTO/Client Codegen & Build Wiring
  - Scope: WSDL/XSD codegen integrated and compiled; Maven plugins in place.
  - Tickets: AGENT-027, AGENT-035.
  - Retake: GEN-100 — integrate generated sources and verify reproducible builds.
  - Gate: `mvn clean package` compiles generated DTOs/clients without manual edits.

- M3 — Binding Inference & Controllers
  - Scope: Automatic wiring for controllers, routes, XSLTs, provider URIs; controller/OpenAPI generation.
  - Tickets: AGENT-029, AGENT-034.
  - Retake: GEN-101 — ensure generated controllers bind platform-http endpoints and OpenAPI UI is live.
  - Gate: Endpoints run locally; smoke checks pass using happy path input.

- M4 — One‑Click Generator & E2E Validation
  - Scope: Single command executes discovery → contracts → plan → maps/XSLTs → routes → scaffold → controllers → build.
  - Tickets: AGENT-033.
  - Retake: OPS-202 — run smoke tests on one‑click generated project; OPS-300 — CI covers AGENT-025..035.
  - Gate: One‑click pipeline completes green; CI stages green on generated project.

Retake of Refactored Tickets (tracked under this epic)
- GEN-100 — retake after AGENT-027, AGENT-033: ensure scaffold integrates codegen outputs and one‑click readiness.
- GEN-101 — retake after AGENT-034: controllers and OpenAPI generated and compiled; endpoints wired.
- OPS-202 — retake after AGENT-033: smoke tests run against one‑click generated project.
- OPS-300 — retake after AGENT-025..035: agent CI includes new translators, codegen, wiring checks.

Notes
- “Retake” means re-run and complete the ticket with the new dependencies satisfied; do not mark as Done until milestone gates pass.
- Keep acceptance criteria strict: build reproducibility, schema/DSL validation, and green CI stages are required to mark milestones as complete.

---

## Epic: Validation & Testing

### OPS-200 — Schema validation suite (XML/XSD)

- Status: To Do
- Priority: P1
- Dependencies: GEN-103
- Description: Validate all input/output XML against target XSDs from `Dependencies/Schemas`.
- Inputs: XML samples and target XSDs.
- Expected Outputs: Validation reports.
- Acceptance Criteria:
  - [ ] CI stage fails if any XML violates schema.
  - [ ] Clear reports identifying element path mismatches.
- Tests: Unit schema validation on samples; integration with end-to-end runs.
- Artifacts: `ci/scripts/validate_xml.py`.
- CI/CD Hooks: Schema validation stage.
- Estimate: 2d

### OPS-201 — Camel YAML DSL linting

- Status: To Do
- Priority: P1
- Dependencies: GEN-100, GEN-101
- Description: Lint and validate YAML routes against Camel DSL schema.
- Inputs: Route YAML files.
- Expected Outputs: Lint reports.
- Acceptance Criteria:
  - [ ] CI fails on invalid DSL; reports line/column.
- Tests: Lint tests on sample routes.
- Artifacts: `ci/scripts/lint_routes.sh`.
- CI/CD Hooks: Route lint stage.
- Estimate: 1d

### OPS-202 — Build and smoke tests

- Status: To Do
- Priority: P1
- Dependencies: GEN-100, AGENT-033
- Description: Run `mvn clean package`; execute smoke tests with `happy_path_example.xml`.
- Inputs: Generated project and sample input XML.
- Expected Outputs: Build artifacts and smoke test report.
- Acceptance Criteria:
  - [ ] Build succeeds.
  - [ ] Smoke tests return expected `loc3x1_result.xml` structure.
- Tests: Maven build; integration smoke tests.
- Artifacts: `ci/pipelines/java_build.yml`.
- CI/CD Hooks: Build + smoke stage.
- Estimate: 1d

### OPS-203 — Golden-path tests (input→output comparisons)

- Status: To Do
- Priority: P2
- Dependencies: OPS-200, OPS-202
- Description: Define golden inputs and expected outputs; compare element-by-element.
- Inputs: Golden input XML and expected output XML.
- Expected Outputs: Pass/fail results with diffs.
- Acceptance Criteria:
  - [ ] Test suite passes.
  - [ ] Diffs captured on failure and actionable.
- Tests: Golden tests for main flow and submaps.
- Artifacts: `generated/tests/golden/**`.
- CI/CD Hooks: Golden test stage.
- Estimate: 2d

---

## Epic: CI/CD & Automation

### OPS-300 — Agent pipeline CI

- Status: To Do
- Priority: P1
- Dependencies: AGENT-010..023, AGENT-025..035
- Description: CI for Python agent (lint, unit tests, type checks).
- Inputs: Agent repository.
- Expected Outputs: CI workflow and green runs.
- Acceptance Criteria:
  - [ ] Runs `pytest`, `ruff/flake8`, `mypy`; caches dependencies.
- Tests: Agent unit tests.
- Artifacts: `.github/workflows/agent-ci.yml` (or Jenkinsfile).
- CI/CD Hooks: Full agent CI.
- Estimate: 1d

### OPS-301 — Generated Java project CI

- Status: To Do
- Priority: P1
- Dependencies: GEN-100..104
- Description: CI for generated project (build, unit/integration tests, route lint, schema validation).
- Inputs: Generated project repository.
- Expected Outputs: CI pipeline with all stages.
- Acceptance Criteria:
  - [ ] Pipeline stages green; artifacts uploaded.
- Tests: Java unit/integration tests; YAML lint; schema validation.
- Artifacts: `.github/workflows/java-ci.yml`.
- CI/CD Hooks: Full Java CI.
- Estimate: 2d

### OPS-302 — Release pipelines and artifact publishing

- Status: To Do
- Priority: P2
- Dependencies: OPS-300, OPS-301
- Description: Define versioning, release tags, publish agent image and Java JAR/Docker image.
- Inputs: CI workflows and Dockerfiles.
- Expected Outputs: Release artifacts and images.
- Acceptance Criteria:
  - [ ] Tagged releases create images and artifacts.
  - [ ] Changelog updated automatically.
- Tests: Dry-run releases in staging.
- Artifacts: `.github/workflows/release.yml`, `CHANGELOG.md`.
- CI/CD Hooks: Release pipeline.
- Estimate: 2d

---

## Epic: Dockerization & Deployment

### OPS-400 — Agent Dockerfile and requirements.txt

- Status: To Do
- Priority: P1
- Dependencies: AGENT-010..023
- Description: Containerize the Python agent; define dependencies in `requirements.txt`.
- Acceptance Criteria:
  - `agent/Dockerfile` builds successfully.
  - `agent/requirements.txt` includes: `langchain`, `langgraph`, `pydantic`, `lxml`, `xmlschema`, `networkx`, `pyyaml`, `requests`, `rich`, `pytest`, `mypy`, `ruff`.
- Tests: Build image; run agent unit tests inside container.
- Artifacts: `agent/Dockerfile`, `agent/requirements.txt`.
- CI/CD Hooks: Agent container build stage.
- Estimate: 1d

### OPS-401 — Generated Java service Dockerfile

- Status: To Do
- Priority: P2
- Dependencies: GEN-100..104
- Description: Containerize generated Spring Boot + Camel project.
- Acceptance Criteria:
  - `Dockerfile` produces runnable image; healthcheck via actuator.
  - Configurable env vars for providers.
- Tests: Build image; run smoke test container; check health.
- Artifacts: `generated/a9-like-project/Dockerfile`.
- CI/CD Hooks: Java container build stage.
- Estimate: 1d

### OPS-402 — Docker Compose for local E2E

- Status: To Do
- Priority: P2
- Dependencies: OPS-400, OPS-401
- Description: Compose file to run agent and generated service together; mount inputs/outputs.
- Acceptance Criteria:
  - `docker-compose.yml` launches both services; agent produces a9-style output and triggers tests.
- Tests: E2E workflow with compose up.
- Artifacts: `compose/docker-compose.yml`.
- CI/CD Hooks: Optional compose validation job.
- Estimate: 1d

---

## Epic: Operationalization & Quality

### OPS-500 — Logging, metrics, and tracing

- Status: To Do
- Priority: P3
- Dependencies: GEN-104, OPS-300
- Description: Structured logs for agent and service; basic metrics; optional trace IDs.
- Acceptance Criteria:
  - Log formats documented; correlation IDs propagated.
  - Metrics endpoints or logs parsable.
- Tests: Log format unit tests; metrics presence checks.
- Artifacts: logging config, docs.
- CI/CD Hooks: N/A.
- Estimate: 1d

### OPS-501 — Security scans (SAST/deps/license)

- Status: To Do
- Priority: P3
- Dependencies: OPS-300, OPS-301
- Description: Enable static analysis and dependency/license compliance.
- Acceptance Criteria:
  - CI stages run scans; issues reported.
- Tests: CI scan reports.
- Artifacts: Scan configs.
- CI/CD Hooks: Security stages.
- Estimate: 1d

---

## Epic: Documentation & Runbooks

### DOC-600 — End-to-end architecture and usage guide

- Status: To Do
- Priority: P2
- Dependencies: AGENT-010..023, GEN-100..104
- Description: Document pipeline, inputs, outputs, how to run locally and in CI.
- Acceptance Criteria:
  - Guide includes steps to produce a9-style project and run tests.
- Tests: N/A.
- Artifacts: `docs/USAGE.md`.
- CI/CD Hooks: Link check.
- Estimate: 2d

### DOC-601 — Troubleshooting and playbook

- Status: To Do
- Priority: P3
- Dependencies: DOC-600
- Description: Common failures, schema mismatches, namespacing issues, provider faults, remediation steps.
- Acceptance Criteria:
  - Clear table of symptoms and fixes.
- Tests: N/A.
- Artifacts: `docs/TROUBLESHOOTING.md`.
- CI/CD Hooks: N/A.
- Estimate: 1d

---

## Epic: Pilot & Generalization

### PILOT-700 — Pilot run on LOC3X1 inputs

- Status: To Do
- Priority: P1
- Dependencies: All AGENT- and GEN- epics through validation
- Description: Run the pipeline on workspace `Dependencies` to generate an output project and verify.
- Acceptance Criteria:
  - Output project mirrors `a9.location-orchestration-rest` structure.
  - Build, schema validation, route lint, smoke and golden tests all pass.
- Tests: Full E2E run.
- Artifacts: `generated/a9-like-project/**`, CI reports.
- CI/CD Hooks: Pilot pipeline.
- Estimate: 3d

### PILOT-701 — Agents refine for perfect runnability

- Status: To Do
- Priority: P1
- Dependencies: PILOT-700
- Description: Iterate to address runtime issues, adjust processors/routes for correctness.
- Acceptance Criteria:
  - No runtime errors under happy path and key edge cases.
- Tests: Post-pilot regression tests.
- Artifacts: Updated processors/routes.
- CI/CD Hooks: Regression test stage.
- Estimate: 2d

---

## Global Acceptance for Project Completion

- The pipeline ingests a “Dependencies”-style folder and outputs a project structurally equivalent to `a9.location-orchestration-rest`.
- Generated project builds (`mvn clean package`), routes lint cleanly, smoke tests and schema validations pass.
- Dockerized agent and generated service run locally via compose; CI/CD pipelines green.
- Unit tests present per ticket; dependent tasks only start after predecessors are Done.
- Best practices in place: secrets management, versioning, code style, scanning, logging.

---

## Notes

- Deterministic-first approach: parsing, graph building, move-map conversions, route skeletons.
- LLM-only for `map:custom` and ambiguous mediation branches; gated with tests and manual review.
- Aim for idempotent runs and reproducible outputs via fixed templates and schemas.

## Epic: UI & API Interface

This epic delivers a minimal yet professional user interface and backend API to run the agentic pipeline: input folder selection, activation button to start runs, and live progress bars.

### UI-500 — Frontend scaffold (React + TypeScript + Vite)

- Status: To Do
- Priority: High
- Dependencies: None
- Description: Create a React TypeScript app using Vite, with routing, state management (Zustand or Redux Toolkit), UI library (MUI or Tailwind), and environment configuration support.
- Inputs: Project environment variables, build scripts.
- Expected Outputs: Bootstrapped React app with base layout and configuration.
- Acceptance Criteria:
  - [ ] Project builds and runs locally with `npm run dev`.
  - [ ] Base layout includes header, content area, and footer.
  - [ ] ESLint/Prettier/Vitest configured.
- Tests: Vitest smoke test for app rendering.
- Artifacts: `frontend/` scaffold, `package.json`, `vite.config.ts`, `tsconfig.json`.
- CI/CD Hooks: Node setup, lint, test, build steps.
- Estimate: 4h

### UI-501 — Input Folder Selector component

- Status: To Do
- Priority: High
- Dependencies: UI-500
- Description: Implement a component to select an input folder path. Validate readability and optional schema presence. Persist last used path.
- Inputs: User-selected path (string), optional validation rules.
- Expected Outputs: Validated path value in app state.
- Acceptance Criteria:
  - [ ] Path can be typed or selected via file picker.
  - [ ] Validation messages shown for invalid or unreadable paths.
  - [ ] Persist last used path in local storage.
- Tests: Component unit tests for validation and persistence.
- Artifacts: `frontend/src/components/InputPathSelector.tsx`.
- CI/CD Hooks: Include component tests in CI.
- Estimate: 6h

### UI-502 — Activation Button (Run Pipeline)

- Status: To Do
- Priority: High
- Dependencies: UI-501, API-510
- Description: Add an activation button that triggers backend run creation with the selected path and configuration, disabling during active runs.
- Inputs: Selected path, optional flags (dry-run, verbose).
- Expected Outputs: New run ID and initial status.
- Acceptance Criteria:
  - [ ] Button triggers `POST /runs` with payload `{ inputPath, options }`.
  - [ ] UI shows run ID and initial status.
  - [ ] Button disabled while a run is active.
- Tests: Integration test with mocked API.
- Artifacts: `frontend/src/components/RunButton.tsx`.
- CI/CD Hooks: Integration tests included.
- Estimate: 4h

### UI-503 — Progress Bars & Live Status (SSE/WebSocket)

- Status: To Do
- Priority: High
- Dependencies: API-512, API-511
- Description: Display stage-wise progress bars for pipeline nodes. Subscribe to server-sent events or WebSocket for live updates.
- Inputs: Run ID to subscribe, SSE/WebSocket endpoint.
- Expected Outputs: Real-time status display with stage progress percentages.
- Acceptance Criteria:
  - [ ] UI subscribes to `GET /runs/{id}/stream` and updates progress.
  - [ ] Progress bars reflect major stages (Intake, Inventory, Contracts, etc.).
  - [ ] Error states display actionable messages.
- Tests: Mocked SSE/WebSocket tests for UI updates.
- Artifacts: `frontend/src/components/ProgressPanel.tsx`.
- CI/CD Hooks: Include UI event tests.
- Estimate: 8h

### UI-504 — Results & Download panel

- Status: To Do
- Priority: Medium
- Dependencies: GEN-100..104, API-510
- Description: Show run results, logs, and provide download links to generated artifacts (e.g., Spring Boot project archive).
- Inputs: Run ID.
- Expected Outputs: Artifacts list and download actions.
- Acceptance Criteria:
  - [ ] Panel lists artifacts with size and type.
  - [ ] Download works for archives and reports.
  - [ ] Logs view supports search and filter.
- Tests: UI tests for artifact listing and downloads (mocked).
- Artifacts: `frontend/src/components/ResultsPanel.tsx`.
- CI/CD Hooks: Include results panel tests.
- Estimate: 6h

### API-510 — Backend API: create run

- Status: To Do
- Priority: High
- Dependencies: None
- Description: Implement `POST /runs` to create a run with payload `{ inputPath, options }`, validate path and enqueue job.
- Inputs: JSON body with `inputPath` and `options`.
- Expected Outputs: Run record `{ id, status: 'queued' }`.
- Acceptance Criteria:
  - [ ] Validates input path existence and permissions.
  - [ ] Enqueues job and returns run ID.
  - [ ] Error responses standardized.
- Tests: Unit tests for validation; integration test with fake queue.
- Artifacts: `backend/app/routes/runs.py` (FastAPI router), `backend/app/main.py` (app factory).
- CI/CD Hooks: Backend tests and coverage.
- Estimate: 5h

### API-511 — Backend: job orchestration bridge to LangGraph

- Status: To Do
- Priority: High
- Dependencies: API-510
- Description: Bridge API runs to the LangGraph pipeline, manage lifecycle, capture stage updates and final artifacts.
- Inputs: Run ID and input path.
- Expected Outputs: Job handle and status updates.
- Acceptance Criteria:
  - [ ] Starts LangGraph pipeline with provided inputs.
  - [ ] Emits stage updates to event stream.
  - [ ] Persists final results and logs.
- Tests: Orchestration unit tests with mocked pipeline.
- Artifacts: `backend/app/services/orchestrator.py`.
- CI/CD Hooks: Include orchestrator tests.
- Estimate: 8h

### API-512 — Backend: status & logs, SSE stream

- Status: To Do
- Priority: High
- Dependencies: API-511
- Description: Implement `GET /runs/{id}` for status, `GET /runs/{id}/logs` for logs, and `GET /runs/{id}/stream` for SSE updates.
- Inputs: Run ID.
- Expected Outputs: Status JSON, logs text, SSE event stream.
- Acceptance Criteria:
  - [ ] Status endpoint returns stage, progress, and timestamps.
  - [ ] Logs endpoint paginates and filters.
  - [ ] SSE stream sends structured events with stage and percent.
- Tests: Integration tests for endpoints and SSE behavior.
- Artifacts: `backend/app/routes/status.py`, `backend/app/routes/stream.py`.
- CI/CD Hooks: Include endpoint tests.
- Estimate: 8h

### API-513 — Security, CORS, auth token

- Status: To Do
- Priority: Medium
- Dependencies: API-510
- Description: Configure CORS, optional bearer token auth for production, rate limiting.
- Inputs: Environment config.
- Expected Outputs: Secured endpoints with CORS and auth checks.
- Acceptance Criteria:
  - [ ] CORS configured for frontend origin.
  - [ ] Token-auth gate enabled via env flag.
  - [ ] Rate limiting applied to sensitive endpoints.
- Tests: Security unit tests and config toggles.
- Artifacts: `backend/app/middleware/security.py`.
- CI/CD Hooks: Security tests included.
- Estimate: 6h

### TEST-520 — Frontend unit tests (Vitest + React Testing Library)

- Status: To Do
- Priority: Medium
- Dependencies: UI-500..504
- Description: Add unit tests for path selector, run button, progress panel, and results panel.
- Inputs: Component props and mocked services.
- Expected Outputs: Passing unit test suite.
- Acceptance Criteria:
  - [ ] Coverage ≥ 80% on core components.
  - [ ] Tests cover validation, activation, and live updates.
- Tests: Vitest suite.
- Artifacts: `frontend/src/__tests__/`.
- CI/CD Hooks: Include vitest in CI.
- Estimate: 6h

### TEST-521 — Backend tests (unit + integration)

- Status: To Do
- Priority: Medium
- Dependencies: API-510..513
- Description: Unit and integration tests for run creation, orchestration, status, logs, and SSE.
- Inputs: Mocked job queue, mock pipeline.
- Expected Outputs: Passing backend test suite.
- Acceptance Criteria:
  - [ ] Coverage ≥ 80% across routes and services.
  - [ ] SSE integration test verifies event flow.
- Tests: `pytest` with FastAPI TestClient.
- Artifacts: `backend/tests/`.
- CI/CD Hooks: Include backend tests in CI.
- Estimate: 8h

## Repository Directory Structure Recommendation

Use the following structure to separate concerns and streamline CI/CD:

- `frontend/` — React TypeScript UI
  - `src/components/` — UI components (InputPathSelector, RunButton, ProgressPanel, ResultsPanel)
  - `src/pages/` — App pages and routing
  - `src/services/` — API client and SSE helpers
  - `src/__tests__/` — Component tests (Vitest)
  - `public/` — Static assets
- `backend/` — FastAPI server (Python)
  - `app/main.py` — FastAPI app factory and startup
  - `app/routes/` — REST/SSE endpoints (runs, status, stream)
  - `app/services/` — Orchestrator and LangGraph bridge
  - `app/middleware/` — Security and CORS
  - `tests/` — Unit/integration tests (pytest)
- `agent/` — LangGraph pipeline code
  - `nodes/` — Deterministic and LLM-assisted nodes
  - `schemas/` — JSON schemas and XSDs
  - `tests/` — Agent unit tests (pytest)
- `generated/` — Output Spring Boot + Camel YAML project (a9-style)
  - `src/main/resources/routes/` — Camel YAML routes
  - `src/main/resources/xslt/` — XSLTs
  - `src/main/java/` — DTOs and controllers
- `docs/` — Documentation and runbooks
  - `jira-tickets.md` — Backlog and tickets
- `scripts/` — Helper scripts (CLI runners, packaging)
- `.github/workflows/` — CI/CD pipelines
- `docker/` — Dockerfiles and compose
- `requirements.txt` — Agent dependencies (pinned)
- `package.json` — Frontend dependencies
- `README.md` — Project overview and quick start

## Dockerization

### DOCK-450 — Containerize agent (Dockerfile + requirements.txt)

- Status: To Do
- Priority: High
- Dependencies: OPS-300
- Description: Create a production-grade Dockerfile for the agent, pin dependencies in `requirements.txt`, run as non-root, and include healthcheck.
- Inputs: Agent codebase and dependency list.
- Expected Outputs: Dockerfile and pinned `requirements.txt`; built image.
- Acceptance Criteria:
  - [ ] `docker build` succeeds; image size optimized.
  - [ ] `requirements.txt` pinned; reproducible builds.
  - [ ] Non-root user; sensible `ENTRYPOINT`.
  - [ ] Healthcheck script returns success when agent is responsive.
- Tests: Container smoke test that processes a sample input folder end-to-end.
- Artifacts: `docker/agent.Dockerfile`, `requirements.txt`, `scripts/healthcheck.sh`.
- CI/CD Hooks: Build and push on tags; vulnerability scanning.
- Estimate: 6h

### DOCK-451 — Containerize generated Spring Boot project

- Status: To Do
- Priority: Medium
- Dependencies: GEN-100..104, OPS-301
- Description: Provide Dockerfile for the generated Spring Boot project with actuator healthcheck and configuration via environment variables.
- Inputs: Generated Spring Boot project.
- Expected Outputs: Dockerfile and built image.
- Acceptance Criteria:
  - [ ] Image starts; actuator health endpoint passes.
  - [ ] Configuration via env vars for provider URIs and timeouts.
  - [ ] Graceful shutdown and minimal base image.
- Tests: Container smoke test hitting health endpoint.
- Artifacts: `docker/generated.Dockerfile`, `docker-compose.yaml` (optional).
- CI/CD Hooks: Build and publish on release; healthcheck in pipeline.
- Estimate: 5h
- `.venv/` — Shared Python virtual environment (agent + backend)

### DEV-530 — Python venv setup and tooling

- Status: To Do
- Priority: High
- Dependencies: OPS-300
- Description: Establish a single, shared Python virtual environment at repo root (`.venv`) for agent and FastAPI backend. Configure tooling and developer scripts.
- Inputs: Python 3.11+ installed locally, `requirements.txt`.
- Expected Outputs: Created `.venv` with installed dependencies and helper scripts.
- Acceptance Criteria:
  - [ ] `.venv` created via `python3 -m venv .venv`.
  - [ ] Dependencies installed with `pip install -r requirements.txt`.
  - [ ] Helper scripts `scripts/venv-activate.sh` and `scripts/dev-run.sh` provided.
  - [ ] Documented in `README.md` with activation steps.
- Tests: Smoke run of agent and backend within the venv.
- Artifacts: `.venv/`, `scripts/venv-activate.sh`, `scripts/dev-run.sh`, `README.md` updates.
- CI/CD Hooks: CI uses `pip install -r requirements.txt` without venv; dev uses venv.
- Estimate: 3h

### OPS-303 — Configuration management (env with sane defaults)

- Status: To Do
- Priority: Medium
- Dependencies: API-510..513
- Description: Implement `config.py` to read configuration from environment variables with safe defaults. `.env.example` optional; keep if helpful for onboarding, but not required.
- Inputs: Environment variables and defaults.
- Expected Outputs: `config.py` with typed getters and validation.
- Acceptance Criteria:
  - [ ] `config.py` reads env vars with defaults (e.g., provider URIs, tokens, CORS origins).
  - [ ] Application runs without `.env` by relying on defaults.
  - [ ] Optional `.env.example` lists keys for onboarding, excludes secrets.
- Tests: Unit tests for config defaults and overrides.
- Artifacts: `backend/app/config.py`, `.env.example` (optional).
- CI/CD Hooks: No secret injection; rely on pipeline env.
- Estimate: 4h
