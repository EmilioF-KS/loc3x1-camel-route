import threading
import time
from typing import Any

# Simple in-memory store for demo purposes. Replace with a real queue and persistence later.
RUNS: dict[str, dict[str, Any]] = {}


def _worker(run_id: str, input_path: str, options: dict):
    RUNS[run_id] = {"status": "running", "progress": 0, "inputPath": input_path, "options": options}
    # Simulate staged progress; replace with real LangGraph orchestration
    for p in range(0, 101, 10):
        RUNS[run_id]["progress"] = p
        time.sleep(0.5)
    RUNS[run_id]["status"] = "completed"


def start_run(run_id: str, input_path: str, options: dict):
    RUNS[run_id] = {"status": "queued", "progress": 0, "inputPath": input_path, "options": options}
    t = threading.Thread(target=_worker, args=(run_id, input_path, options), daemon=True)
    t.start()


def get_run_status(run_id: str) -> dict | None:
    return RUNS.get(run_id)