# Camel Route Project

A multi-module project for orchestrating integration flows, generating contracts, routes, and running a local API and frontend. This repo centralizes generated artifacts under `results/` for reliable CI and local runs.

## Overview

- `agents/` — Python agents for analysis (e.g., BPEL).
- `modules/` — Java Maven modules (clients, services, mappings, orchestration, routes).
- `scripts/` — Utilities for building, generating, and validation.
- `server/` — Python FastAPI application.
- `frontend/` — Vite + React frontend.
- `results/` — Centralized outputs:
  - `results/reports/` — Markdown reports (reviewer, evaluation, BPEL analysis, tooling).
  - `results/preflight/` — Preflight checks, scaffold logs, contracts codegen logs.
  - `results/meta/` — Plans and run metadata (e.g., `agent-plan.json`).
  - `results/agent-runs/` — Per-run JSON snapshots.

## Prerequisites

- Python 3.10+
- Node.js 18+
- Java 17 + Maven
- Optional: Ollama for local LLM analysis (BPEL agent can fallback to deterministic regex)

## Quick Start

1. Create and activate a virtualenv (optional):
   - `python3 -m venv .venv && source .venv/bin/activate`
2. Install API dependencies:
   - `python -m pip install -r server/requirements.txt`
3. Start everything with the unified runner:
   - `python start.py`
   - API: `http://localhost:8001`
   - Frontend: `http://localhost:5173`

Options:
- `python start.py --no-frontend` — Start only API (and optional services)
- `python start.py --no-services` — Skip Java services
- `python start.py --api-port 8001` — Change API port

## Running Tests

- `python -m unittest discover -s tests -v`

## Cleaning Artifacts

Use the root-level clean script to remove generated outputs and caches:
- `python clean.py` — Standard clean (uploads, tests/results, results/agent-runs)
- `python clean.py --full` — Also remove heavy caches (node_modules, virtualenvs)
- `python clean.py --dry-run` — Show actions without deleting

## Results Directory Override

You can override `results/` location with `RESULTS_DIR` env var. Tools and agents will write to `${RESULTS_DIR}/reports`, `${RESULTS_DIR}/preflight`, `${RESULTS_DIR}/meta`, and `${RESULTS_DIR}/agent-runs`.

## Notes

- Legacy `doc/` reports have been redirected to `results/`. Remaining `doc/` LaTeX files and curated documentation stay intact.
- If Ollama is installed, LLM-based analyzers can be used; otherwise, deterministic analysis paths are preferred for CI reliability.