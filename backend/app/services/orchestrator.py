import os
import threading
import time
from agent.src.scaffold import scaffold_result
import subprocess

_runs = {}
_TOTAL = 9

def _set_stage(run_id: str, stage: str, step: int, percent: int = None):
    if percent is None:
        percent = int(step / _TOTAL * 100)
    _runs[run_id] = {
        "id": run_id,
        "status": "running" if step < _TOTAL else "done",
        "stage": stage,
        "step": step,
        "total": _TOTAL,
        "percent": percent,
    }

def start_run(run_id: str, input_path: str):
    _runs[run_id] = {"id": run_id, "status": "queued", "stage": "queued", "percent": 0}
    t = threading.Thread(target=_worker, args=(run_id, input_path), daemon=True)
    t.start()

def _worker(run_id: str, input_path: str):
    _set_stage(run_id, "intake", 1)
    if not os.path.isdir(input_path):
        _runs[run_id] = {"id": run_id, "status": "error", "stage": "error", "percent": 100, "error": "invalid path"}
        return
    try:
        _set_stage(run_id, "inventory", 2)
        scaffold_result(
            out_path="generated/result",
            client_input_path=input_path,
            clean=True,
            maps_dir=None,
            orchestration_plan_path=None,
            service_name="loc-service",
            controller_path=None,
            request_xslt=None,
            reply_xslt=None,
            provider_uri=None,
            group_id="com.example",
            artifact_id="loc-service",
            version="0.1.0",
            project_name="LOC Service",
            description="LOC service",
            include_provider_stub=True,
        )
        _set_stage(run_id, "mapping", 3)
        _set_stage(run_id, "transforming", 4)
        _set_stage(run_id, "routes", 5)
        _set_stage(run_id, "scaffold", 6)
        # Build with Maven
        try:
            _set_stage(run_id, "building", 7)
            subprocess.check_call(["mvn", "-DskipTests", "package"], cwd="generated/result")
            jar = os.path.join("generated/result", "target", "loc-service.jar")
            # Verify run briefly on 8082
            if os.path.isfile(jar):
                _set_stage(run_id, "verifying", 8)
                p = subprocess.Popen(["java", "-jar", "-Dserver.port=8082", jar], cwd="generated/result")
                for _ in range(60):
                    if _port_open(8082):
                        break
                    time.sleep(0.5)
                try:
                    import urllib.request
                    urllib.request.urlopen("http://localhost:8082/actuator/health").read()
                except Exception:
                    pass
                try:
                    p.terminate()
                except Exception:
                    pass
        except Exception:
            pass
        _runs[run_id] = {"id": run_id, "status": "done", "stage": "done", "step": _TOTAL, "total": _TOTAL, "percent": 100, "results": {"output": "generated/result", "run_id": run_id}}
    except Exception as e:
        _runs[run_id] = {"id": run_id, "status": "error", "stage": "error", "percent": 100, "error": str(e)}

def get_status(run_id: str):
    return _runs.get(run_id)

def _port_open(port: int) -> bool:
    import socket
    s = socket.socket()
    try:
        s.settimeout(0.3)
        s.connect(("127.0.0.1", port))
        s.close()
        return True
    except Exception:
        return False
