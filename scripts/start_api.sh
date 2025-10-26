#!/usr/bin/env bash
set -euo pipefail
PROJECT_ROOT="$(cd "$(dirname "$0")"/.. && pwd)"
source "$PROJECT_ROOT/.venv/bin/activate" 2>/dev/null || true
export ENABLE_PIPELINE_RUNS=true
python -m pip install --quiet -r "$PROJECT_ROOT/server/requirements.txt" || true
exec uvicorn server.main:app --host 0.0.0.0 --port 8001