#!/usr/bin/env python3
import argparse
import os
import signal
import sys
import time
from pathlib import Path
from subprocess import Popen

PROJECT_ROOT = Path(__file__).resolve().parent
RESULTS_DIR = Path(os.environ.get("RESULTS_DIR", PROJECT_ROOT / "results"))

API_HOST = "0.0.0.0"
DEFAULT_API_PORT = 8001
FRONTEND_PORT = 5173  # Vite default


def say(msg: str) -> None:
    print(f"[start] {msg}")


def ensure_env() -> None:
    # Optional virtualenv activation for subprocesses
    venv_path = PROJECT_ROOT / ".venv"
    if venv_path.exists():
        os.environ["VIRTUAL_ENV"] = str(venv_path)
        os.environ["PATH"] = f"{venv_path}/bin:{os.environ['PATH']}"
        say("Using virtualenv .venv for Python processes")

    # Ensure results subdirs exist
    from scripts.utils.paths import ensure_results_dirs
    ensure_results_dirs()

    # Enable pipeline runs (consistent with previous start script)
    os.environ["ENABLE_PIPELINE_RUNS"] = "true"


def run_api(port: int) -> Popen:
    say(f"Starting API on http://localhost:{port}")
    # Install dependencies quietly if missing
    try:
        import uvicorn  # noqa: F401
    except ImportError:
        say("Installing API requirements...")
        os.system(f"{sys.executable} -m pip install --quiet -r '{PROJECT_ROOT}/server/requirements.txt'")

    cmd = [sys.executable, "-m", "uvicorn", "server.main:app", "--host", API_HOST, "--port", str(port)]
    return Popen(cmd, cwd=PROJECT_ROOT)


def run_frontend() -> Popen | None:
    # Check npm availability
    if not shutil_which("npm"):
        say("npm not found; skipping frontend. Install Node.js to enable.")
        return None
    say(f"Starting frontend on http://localhost:{FRONTEND_PORT}")
    return Popen(["npm", "run", "dev"], cwd=PROJECT_ROOT / "frontend")


def run_services() -> Popen | None:
    script = PROJECT_ROOT / "scripts" / "run_services.sh"
    if not script.exists():
        say("run_services.sh not found; skipping services.")
        return None
    say("Starting backend services via scripts/run_services.sh")
    return Popen(["bash", str(script)], cwd=PROJECT_ROOT)


def shutil_which(cmd: str) -> str | None:
    from shutil import which
    return which(cmd)


def main() -> int:
    parser = argparse.ArgumentParser(description="Start API, frontend, and optional backend services")
    parser.add_argument("--api-port", type=int, default=DEFAULT_API_PORT, help="API port (default 8001)")
    parser.add_argument("--no-frontend", action="store_true", help="Do not start the frontend dev server")
    parser.add_argument("--no-services", action="store_true", help="Do not start backend services")
    args = parser.parse_args()

    ensure_env()

    procs: list[Popen] = []
    try:
        # API
        api_proc = run_api(args.api_port)
        procs.append(api_proc)

        # Frontend
        if not args.no_frontend:
            fe_proc = run_frontend()
            if fe_proc:
                procs.append(fe_proc)

        # Services
        if not args.no_services:
            svc_proc = run_services()
            if svc_proc:
                procs.append(svc_proc)

        say("\nEverything starting. Preview URLs:")
        say(f"- API:      http://localhost:{args.api_port}")
        say(f"- Frontend: http://localhost:{FRONTEND_PORT} (if started)")
        say("Press Ctrl+C to stop all.")

        # Keep the script alive while children run
        while True:
            # Simple heartbeat
            time.sleep(2)
            # If API dies, exit and kill children
            if api_proc.poll() is not None:
                say("API process exited; shutting down others.")
                break
        return 0
    except KeyboardInterrupt:
        say("Interrupted; stopping processes...")
        return 0
    finally:
        for p in procs:
            try:
                if p.poll() is None:
                    p.send_signal(signal.SIGINT)
                    time.sleep(0.5)
                    p.terminate()
            except Exception:
                pass


if __name__ == "__main__":
    raise SystemExit(main())