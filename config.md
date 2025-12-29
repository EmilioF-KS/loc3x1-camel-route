# Configuration Management Policy

This document defines how configuration and secrets are handled for the agent (Python + FastAPI) and the generated Spring Boot + Camel YAML project.

Goals:
- No secrets committed to the repository.
- Prefer environment variables for configuration, with safe defaults where reasonable.
- Keep onboarding simple: optional `.env.template` lists keys only (no values).

## Agent & Backend (FastAPI)

Environment variables read by `backend/app/config.py` and `api.py`:

- `HOST` — API bind host (default `0.0.0.0`)
- `PORT` — API bind port (default `8000`)
- `RELOAD` — Enable auto-reload in dev (`true`/`false`, default `true`)
- `CORS_ORIGINS` — Allowed origins for CORS (default `*`)
- `AUTH_TOKEN` — Optional bearer token for protected endpoints (do not commit)
- `PROVIDER_URI_CRP10X1` — Provider endpoint for CRP10X1 (default `http://localhost:9001`)
- `PROVIDER_URI_CRP11X1` — Provider endpoint for CRP11X1 (default `http://localhost:9002`)
- `LOG_LEVEL` — App log level (e.g. `INFO`, `DEBUG`)
- `SSE_KEEPALIVE_MS` — SSE keepalive interval (optional)
- `OLLAMA_BASE_URL` — Base URL for LLM runtime (optional; e.g., `http://localhost:11434`)

Usage:
- Local dev: set env vars in shell, or copy `.env.template` to `.env` and fill values; never commit `.env`.
- CI/CD: define env vars in the pipeline’s secret manager.

## Generated Spring Boot Project

Suggested environment variables consumed by Spring Boot:

- `SERVER_PORT` — Service port (default `8080`)
- `PROVIDER_URI_CRP10X1` — Provider endpoint
- `PROVIDER_URI_CRP11X1` — Provider endpoint
- `TIMEOUT_MS` — Provider timeout
- `RETRIES` — Provider retry count
- `LOG_LEVEL` — Application log level (e.g. `INFO`, `DEBUG`)

Configuration binding:
- Use Spring Boot `application.yml` with `${ENV_VAR:default}` style for overrides.

## Best Practices

- Do not commit secrets. `.gitignore` already excludes `.env*` files.
- Keep `.env.template` to document keys only — no values.
- Prefer secret managers in CI/CD; use repository/environment-level secrets.
- Rotate credentials regularly and audit usage.

## CI Secret Scanning

- A secret-scan workflow runs on PRs/push to detect accidental secret commits.
- On false positives, add an allowlist entry to the scanner config and justify in the PR.