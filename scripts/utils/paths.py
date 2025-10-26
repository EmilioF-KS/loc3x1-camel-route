#!/usr/bin/env python3
from pathlib import Path
import os

# Project root
PROJECT_ROOT = Path(__file__).resolve().parents[2]

# Allow override via env var, default to PROJECT_ROOT / "results"
_env_results = os.environ.get("RESULTS_DIR", "").strip()
RESULTS_DIR = Path(_env_results).resolve() if _env_results else (PROJECT_ROOT / "results")

# Subdirectories for structured outputs
REPORTS_DIR = RESULTS_DIR / "reports"
RUNS_DIR = RESULTS_DIR / "agent-runs"
PREVIEW_DIR = RESULTS_DIR / "preflight"
META_DIR = RESULTS_DIR / "meta"


def ensure_results_dirs() -> None:
    for d in [RESULTS_DIR, REPORTS_DIR, RUNS_DIR, PREVIEW_DIR, META_DIR]:
        d.mkdir(parents=True, exist_ok=True)