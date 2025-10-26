from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from fastapi import UploadFile, File, Form
from pathlib import Path
from typing import List, Optional
import uvicorn
import os
import uuid
import threading
import subprocess
import shutil
import zipfile
from datetime import datetime

PROJECT_ROOT = Path(__file__).resolve().parents[1]
RESULTS_DIR = PROJECT_ROOT / "tests" / "results"
SUMMARY_FILE = RESULTS_DIR / "html" / "summary.txt"
LOG_DIR = RESULTS_DIR / "logs"
UPLOADS_DIR = PROJECT_ROOT / "uploads"
UPLOADS_DIR.mkdir(exist_ok=True)
ENABLE_RUNS = os.getenv("ENABLE_PIPELINE_RUNS", "false").lower() in ("1", "true", "yes")

# Simple in-memory run tracking
RUNS = {}

app = FastAPI(title="Camel Route Evidence API")

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "http://localhost:8080",
        "http://127.0.0.1:8080",
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def root():
    return {"status": "ok", "endpoints": ["/api/summary", "/api/logs", "/api/logs/{name}", "/health", "/api/run", "/api/runs", "/api/run/{id}/status", "/api/run/{id}/results/summary", "/api/run/{id}/results/logs", "/api/run/{id}/results/logs/{name}"]}

@app.get("/health")
def health():
    return {"status": "ok"}

# --------------------------
# Pipeline Run Orchestration
# --------------------------

def _run_pipeline(run_id: str, source_root: Path):
    RUNS[run_id]["state"] = "running"
    RUNS[run_id]["progress"] = 10
    RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
    run_dir = UPLOADS_DIR / run_id
    run_dir.mkdir(exist_ok=True)
    run_log = run_dir / "pipeline.out.log"

    # Ensure results/logs dir exists for tee outputs
    (RESULTS_DIR / "logs").mkdir(parents=True, exist_ok=True)

    env = os.environ.copy()
    env["SOURCE_ROOT"] = str(source_root)

    try:
        RUNS[run_id]["progress"] = 25
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
        # Run Preflight tests
        for step in RUNS[run_id].get("steps", []):
            if step["id"] == "preflight":
                step["state"] = "running"
                break
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
        with open(run_log, "a") as lf:
            proc1 = subprocess.run([
                "bash", "-lc",
                f"python3 tests/run_tests.py --preflight 2>&1 | tee '{LOG_DIR}/python-preflight.log'"
            ], cwd=str(PROJECT_ROOT), env=env, stdout=lf, stderr=subprocess.STDOUT)
        # Update step state
        for step in RUNS[run_id].get("steps", []):
            if step["id"] == "preflight":
                step["state"] = "completed" if proc1.returncode == 0 else "failed"
                break
        RUNS[run_id]["progress"] = 60
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
        # Run BPEL agent tests
        for step in RUNS[run_id].get("steps", []):
            if step["id"] == "bpel":
                step["state"] = "running"
                break
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
        with open(run_log, "a") as lf:
            proc2 = subprocess.run([
                "bash", "-lc",
                f"python3 tests/run_tests.py --bpel 2>&1 | tee '{LOG_DIR}/python-bpel.log'"
            ], cwd=str(PROJECT_ROOT), env=env, stdout=lf, stderr=subprocess.STDOUT)
        for step in RUNS[run_id].get("steps", []):
            if step["id"] == "bpel":
                step["state"] = "completed" if proc2.returncode == 0 else "failed"
                break
        # Copy test results into the run directory for isolation
        results_copy = run_dir / "results"
        if RESULTS_DIR.exists():
            if results_copy.exists():
                shutil.rmtree(results_copy)
            shutil.copytree(RESULTS_DIR, results_copy)
        # Finalize
        RUNS[run_id]["progress"] = 100
        ok = (proc1.returncode == 0 and proc2.returncode == 0)
        RUNS[run_id]["state"] = "completed" if ok else "completed_with_errors"
        RUNS[run_id]["return_code"] = 0 if ok else 1
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()
    except Exception as e:
        RUNS[run_id]["state"] = "failed"
        RUNS[run_id]["error"] = str(e)
        RUNS[run_id]["updated_at"] = datetime.utcnow().isoformat()

@app.get("/api/runs")
def list_runs():
    return [
        {
            "id": rid,
            "state": meta.get("state"),
            "progress": meta.get("progress"),
            "created_at": meta.get("created_at"),
            "updated_at": meta.get("updated_at"),
            "source_root": meta.get("source_root"),
            "return_code": meta.get("return_code"),
        }
        for rid, meta in RUNS.items()
    ]

@app.post("/api/run")
def start_run(bundle: Optional[UploadFile] = File(None), source_root: Optional[str] = Form(None)):
    if not ENABLE_RUNS:
        raise HTTPException(status_code=501, detail="Pipeline runs disabled in this environment")

    run_id = uuid.uuid4().hex
    run_dir = UPLOADS_DIR / run_id
    run_dir.mkdir(exist_ok=True)
    created_at = datetime.utcnow().isoformat()

    # Resolve source root from either uploaded bundle or provided path
    resolved_source: Optional[Path] = None
    if bundle is not None:
        zip_path = run_dir / "bundle.zip"
        with open(zip_path, "wb") as f:
            shutil.copyfileobj(bundle.file, f)
        extract_dir = run_dir / "src"
        extract_dir.mkdir(exist_ok=True)
        try:
            with zipfile.ZipFile(zip_path, "r") as zf:
                zf.extractall(extract_dir)
        except zipfile.BadZipFile:
            raise HTTPException(status_code=400, detail="Invalid ZIP bundle")
        resolved_source = extract_dir
    elif source_root:
        candidate = Path(source_root).resolve()
        if not candidate.exists():
            raise HTTPException(status_code=400, detail="Provided source_root does not exist")
        resolved_source = candidate
    else:
        raise HTTPException(status_code=400, detail="Provide a ZIP bundle or source_root path")

    RUNS[run_id] = {
        "id": run_id,
        "state": "queued",
        "progress": 0,
        "created_at": created_at,
        "updated_at": created_at,
        "source_root": str(resolved_source),
        "steps": [
            {"id": "preflight", "name": "Preflight Agent", "state": "pending"},
            {"id": "bpel", "name": "BPEL Analysis", "state": "pending"},
        ],
    }

    t = threading.Thread(target=_run_pipeline, args=(run_id, resolved_source), daemon=True)
    t.start()

    return {"id": run_id, "state": "queued"}

@app.get("/api/run/{run_id}/status")
def get_status(run_id: str):
    meta = RUNS.get(run_id)
    if not meta:
        raise HTTPException(status_code=404, detail="Run not found")
    # Include quick metrics from copied summary if present
    run_dir = UPLOADS_DIR / run_id / "results" / "html" / "summary.txt"
    metrics = {}
    if run_dir.exists():
        txt = run_dir.read_text()
        for line in txt.splitlines():
            if line.startswith("Tests scanned reports"):
                metrics["reports_scanned"] = line.split(":")[-1].strip()
            if line.startswith("Failing suites"):
                metrics["failing_suites"] = line.split(":")[-1].strip()
    meta_with_metrics = dict(meta)
    meta_with_metrics["metrics"] = metrics
    return meta_with_metrics

@app.get("/api/run/{run_id}/results/summary")
def run_summary(run_id: str):
    path = UPLOADS_DIR / run_id / "results" / "html" / "summary.txt"
    if not path.exists():
        raise HTTPException(status_code=404, detail="Run summary not found")
    return {"path": str(path), "content": path.read_text()}

@app.get("/api/run/{run_id}/results/logs")
def run_logs(run_id: str):
    log_dir = UPLOADS_DIR / run_id / "results" / "logs"
    if not log_dir.exists():
        return []
    return sorted([p.name for p in log_dir.glob("*.log")])

@app.get("/api/run/{run_id}/results/logs/{name}")
def run_log_file(run_id: str, name: str):
    log_dir = UPLOADS_DIR / run_id / "results" / "logs"
    file_path = log_dir / name
    if not file_path.exists():
        raise HTTPException(status_code=404, detail="Run log not found")
    text = file_path.read_text()
    return {"name": name, "size": len(text), "tail": text[-2000:]}

@app.get("/api/summary")
def get_summary():
    if not SUMMARY_FILE.exists():
        raise HTTPException(status_code=404, detail="Summary not found")
    content = SUMMARY_FILE.read_text()
    return {"path": str(SUMMARY_FILE), "content": content}

@app.get("/api/logs")
def list_logs() -> List[str]:
    if not LOG_DIR.exists():
        return []
    return sorted([p.name for p in LOG_DIR.glob("*.log")])

@app.get("/api/logs/{name}")
def get_log(name: str):
    file_path = LOG_DIR / name
    if not file_path.exists():
        raise HTTPException(status_code=404, detail="Log not found")
    text = file_path.read_text()
    return {"name": name, "size": len(text), "tail": text[-2000:]}

if __name__ == "__main__":
    uvicorn.run("server.main:app", host="0.0.0.0", port=8001, reload=False)