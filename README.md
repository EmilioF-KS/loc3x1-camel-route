# LOC3X1 Agentic Pipeline — Spring Boot + Camel YAML (a9-style)

This repository implements an agentic pipeline that ingests a `Dependencies`-style folder and generates a Spring Boot + Apache Camel YAML project mirroring `CHUBB/00-Emilio-Logic/a9.location-orchestration-rest`.

## Quick Start

1) Create Python venv and install deps:
```
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

2) Run discovery (arbitrary input path) and tests:
```
# Generate manifest from any input path
python agent/scripts/discover.py --input /absolute/path/to/your/Dependencies --output agent/manifest.json --overwrite

# Or use the convenience runner (reads --input or DEP_INPUT_DIR)
DEP_INPUT_DIR=/absolute/path/to/your/Dependencies \
bash agent/scripts/run.sh all --output agent/manifest.json --overwrite

# Run tests only
bash agent/scripts/run.sh test
```

3) Launch the backend API:
```
python api.py
```
- Health: `GET http://localhost:8000/health`
- Start a run: `POST http://localhost:8000/runs` with `{ "inputPath": "CHUBB/Dependencies" }`
- Check status: `GET http://localhost:8000/runs/{id}`
- Stream progress (SSE): `GET http://localhost:8000/runs/{id}/stream`

## Configuration
Configuration is driven by environment variables with safe defaults; no `.env` file is required.
See `config.md` in the repository root for all env keys and recommendations.

Generated projects use environment variables conventionally; see `generated/.env.template` for guidance.

## Repository Structure
- `agent/` — Python agent (LangGraph pipeline, deterministic converters)
- `backend/` — FastAPI backend exposing API endpoints and SSE
- `generated/` — Spring Boot + Camel YAML projects produced by the pipeline
- `examples/` — Example inputs and payloads
- `doc/` — Documentation (e.g., `jira-tickets.md`)
- `.github/` — PR templates and CI workflows
- `config.md` — Configuration policy and env mappings
- `requirements.txt` — Python dependencies (pinned)

## CI/CD
- PRs trigger CI checks (syntax compile, tests when present) and secret scanning.
- See `.github/workflows/ci.yml` and `.github/workflows/secret-scan.yml`.
- Integration tests read-only scan the provided input path. Set `DEP_INPUT_DIR` in CI if you use a non-default path.

## Contributing
See `CONTRIBUTING.md` for coding standards, branching strategy, and PR checklist.