# Jira Tickets Alignment Report

Date: 20 October 2025
Scope: doc/jira-tickets.txt compared against doc/Loc-Camel-Route.tex, doc/master-plan.txt, and doc/comment.txt

## Summary
The current Jira tickets (LOC-000 to LOC-019) broadly reflect the project’s phases and technical stack. However, several items conflict with the agentic philosophy and generality mandates in doc/comment.txt and are partially inconsistent with the dynamic, adaptive execution model described in doc/Loc-Camel-Route.tex and doc/master-plan.txt.

## Key Findings
- Agentic generality violations:
  - Multiple tickets hardcode `sample/…` paths and LOC3X1-specific names; doc/comment.txt mandates accepting arbitrary `SOURCE_ROOT` and deriving artefacts at runtime.
  - Tests and scripts do not consistently declare `SOURCE_ROOT` discovery and parameterisation.
- Unit test references mismatched to repository:
  - Several tickets reference Markdown-based tests or non-existent test classes; repository uses Python tests: `tests/run_tests.py`, `tests/test_preflight_agent.py`, `tests/test_contract_inventory.py`, `tests/test_bpel_agent.py`, `tests/test_data_mappings_spec.py`, `tests/test_contracts_validator.py`.
- Governance gaps:
  - British English enforcement and “no mocks” posture not present in acceptance criteria across all tickets.
  - Venv-only execution inconsistently stated beyond scripts.
- Agent layer omissions:
  - No explicit tickets for Planner validation against LaTeX and agent plan, Agents CLI runner, or Evaluator/Referee final audit report.
- Evidence and artefact locations:
  - Logs sometimes target `logs/…`; standardised evidence directories per doc/comment.txt are `tests/results/{junit,html,logs}/`.
- Missing artefact deliverables:
  - `modules/orchestration/ROUTES.md` and `modules/mappings/MAPPINGS.md` not required in acceptance criteria where relevant.

## Refactoring Decisions
- Introduce `SOURCE_ROOT` parameterisation and recursive discovery for BPEL/WSDL/XSD across relevant tickets.
- Correct unit test references to existing Python tests and master runner `tests/run_tests.py`.
- Add governance acceptance (British English, no mocks, venv-only) everywhere appropriate.
- Add new tickets for Planner validation (`LOC-020`), Agents CLI runner (`LOC-021`), and Evaluator report (`LOC-022`).
- Standardise evidence/logs under `tests/results/` and require JUnit XML + HTML outputs where applicable.
- Require artefacts `ROUTES.md` and `MAPPINGS.md` in orchestration/mappings tickets.
- For module naming specificity (LOC3X1), introduce parameterised properties to avoid hardwiring and document deterministic derivation from contracts.

## Impacted Tickets and Changes (High Level)
- LOC-000: Replace smoke logs path; enforce venv; correct unit test.
- LOC-001/002/003: Add `SOURCE_ROOT` discovery; correct tests; enforce governance and evidence directories.
- LOC-004–006: Parameterise module/service identifiers; enforce venv checks; standardise artefacts.
- LOC-007–012: Add governance acceptance; ensure dynamic configuration and no secrets; correct test targets.
- LOC-009/010: Require `ROUTES.md` and `MAPPINGS.md`; align to `orchestration/spec.yaml` and `.map` discovery.
- LOC-013/014: Require tests via `tests/run_tests.py`; add “Blocked” evidence posture; recommend ≥2 fixtures for contract-level tests.
- LOC-015–019: Clarify venv vs containers; artefact archiving; correct runner references.
- Added LOC-020, LOC-021, LOC-022 for agentic layer completeness.

## Conclusion
With refactoring applied, the tickets now conform to the project’s agentic philosophy, technical architecture, and governance. They enable self-directed, adaptive operation, context-aware discovery, and deterministic evidence capture consistent with doc/Loc-Camel-Route.tex, doc/master-plan.txt, and doc/comment.txt.