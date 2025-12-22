import os
import threading
import time
from agent.src.scaffold import scaffold_result, scaffold_mock_services
import subprocess

_runs = {}
_TOTAL = 10

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
        n = _next_number()
        out_main = f"generated/result{n}"
        out_mock = f"generated/mock-services-result{n}"
        scaffold_result(
            out_path=out_main,
            client_input_path=input_path,
            clean=True,
            maps_dir=None,
            orchestration_plan_path=None,
            service_name=f"loc-service{n}",
            controller_path=None,
            request_xslt=None,
            reply_xslt=None,
            provider_uri=None,
            group_id="com.example",
            artifact_id=f"loc-service{n}",
            version="1.0.0",
            project_name=f"loc-service{n}",
            description=f"LOC service {n}",
            include_provider_stub=True,
        )
        _set_stage(run_id, "mock-services", 3)
        scaffold_mock_services(
            out_path=out_mock,
            group_id="com.example",
            artifact_id=f"mock-services-result{n}",
            version="1.0.0",
            project_name=f"mock-services-result{n}",
        )
        _set_stage(run_id, "mapping", 4)
        _set_stage(run_id, "transforming", 5)
        _set_stage(run_id, "routes", 6)
        _set_stage(run_id, "scaffold", 7)
        # Build with Maven
        try:
            _set_stage(run_id, "building", 8)
            subprocess.check_call(["mvn", "-DskipTests", "package"], cwd=out_main)
            jar = os.path.join(out_main, "target", f"loc-service{n}.jar")
            # Verify run briefly on 8082
            if os.path.isfile(jar):
                _set_stage(run_id, "verifying", 9)
                p = subprocess.Popen(["java", "-jar", "-Dserver.port=8082", jar], cwd=out_main)
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
        _runs[run_id] = {"id": run_id, "status": "done", "stage": "done", "step": _TOTAL, "total": _TOTAL, "percent": 100, "results": {"output": out_main, "mock": out_mock, "run_id": run_id, "number": n}}
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

def _next_number() -> int:
    base = os.path.join(os.getcwd(), "generated")
    if not os.path.isdir(base):
        return 1
    nums = []
    for name in os.listdir(base):
        if name.startswith("result") and name[6:].isdigit():
            try:
                nums.append(int(name[6:]))
            except Exception:
                pass
        if name.startswith("mock-services-result"):
            tail = name.replace("mock-services-result", "")
            if tail.isdigit():
                try:
                    nums.append(int(tail))
                except Exception:
                    pass
    return max(nums) + 1 if nums else 1
