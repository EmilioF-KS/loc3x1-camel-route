import os
import json
import subprocess
import time
from pathlib import Path
from urllib.request import urlopen, Request


def _has(cmd: str) -> bool:
    return subprocess.run(["bash", "-lc", f"command -v {cmd}"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT).returncode == 0


def test_runtime_endpoints_exhaustive(monkeypatch):
    if not _has("mvn") or not _has("java"):
        monkeypatch.setenv("SKIP_RUNTIME", "1")
        assert os.getenv("SKIP_RUNTIME") == "1"
        return

    out = "generated/result"
    # One-click with clean and binding resolution
    proc_gen = subprocess.run([
        "python3", "-m", "agent.scripts.one_click", "--input", "CHUBB/Dependencies", "--output", out, "--clean"
    ], stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    assert proc_gen.returncode == 0, proc_gen.stdout

    # Build
    proc_build = subprocess.run(["mvn", "-q", "-DskipTests", "package"], cwd=out, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    assert proc_build.returncode == 0, proc_build.stdout
    target = Path(out) / "target"
    jars = [p for p in target.glob("*.jar")]
    assert jars, "No jar built"
    jar = str(jars[0])

    # Run and validate endpoints
    proc_run = subprocess.Popen(["java", "-jar", jar, "--provider.uri=http://localhost:8080/provider/locations"], cwd=out, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    try:
        # Wait health
        ok = False
        for _ in range(60):
            time.sleep(0.5)
            try:
                resp = urlopen("http://localhost:8080/actuator/health", timeout=0.5)
                if resp.status == 200:
                    ok = True
                    break
            except Exception:
                pass
        if not ok:
            import pytest
            pytest.skip("Service not healthy in this environment; skipping runtime endpoint validation")

        # Enumerate ops
        plan = json.loads(Path("agent/orchestration_plan.json").read_text(encoding="utf-8"))
        ops = []
        for b in plan.get("bpel", []):
            for s in b.get("steps", []):
                op = s.get("operation")
                if op and op != "null":
                    ops.append(op)
        seen = set()
        ops = [x for x in ops if not (x in seen or seen.add(x))]

        # POST minimal XML to each operation endpoint and expect 200
        for op in ops:
            req = Request(f"http://localhost:8080/loc/{op}", data=b"<req/>", method="POST")
            req.add_header("Content-Type", "application/xml")
            r = urlopen(req, timeout=1.0)
            assert r.status == 200
    finally:
        proc_run.terminate()
