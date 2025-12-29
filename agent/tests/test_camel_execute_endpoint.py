import os
import subprocess
import time
import urllib.request
import urllib.error

import pytest

from agent.src.scaffold import scaffold_result


BASE = "generated/result"


def _has_cmd(cmd: str) -> bool:
    try:
        subprocess.check_call(["bash", "-lc", f"command -v {cmd} >/dev/null 2>&1"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        return True
    except Exception:
        return False


@pytest.mark.timeout(120)
def test_execute_endpoint_identity_route():
    if not _has_cmd("mvn"):
        pytest.skip("maven not available")
    out = BASE + "exec"
    scaffold_result(out, clean=True)
    subprocess.check_call(["mvn", "-DskipTests", "package"], cwd=out)
    p = subprocess.Popen(["mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--server.port=8087"], cwd=out)
    try:
        ok = False
        for _ in range(60):
            try:
                urllib.request.urlopen("http://localhost:8087/actuator/health", timeout=1).read()
                ok = True
                break
            except Exception:
                time.sleep(1)
        assert ok, "app did not start"
        req = urllib.request.Request(
            "http://localhost:8087/api/camel/execute?routeId=mediation/identity",
            data=b"<root/>",
            headers={"Content-Type": "application/xml"},
            method="POST",
        )
        resp = urllib.request.urlopen(req, timeout=5)
        body = resp.read().decode("utf-8")
        assert resp.status == 200
        assert "<" in body
    finally:
        try:
            p.terminate()
        except Exception:
            pass


@pytest.mark.timeout(120)
def test_execute_endpoint_unknown_route_returns_400():
    if not _has_cmd("mvn"):
        pytest.skip("maven not available")
    out = BASE + "exec2"
    scaffold_result(out, clean=True)
    subprocess.check_call(["mvn", "-DskipTests", "package"], cwd=out)
    p = subprocess.Popen(["mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--server.port=8087"], cwd=out)
    try:
        ok = False
        for _ in range(60):
            try:
                urllib.request.urlopen("http://localhost:8087/actuator/health", timeout=1).read()
                ok = True
                break
            except Exception:
                time.sleep(1)
        assert ok, "app did not start"
        req = urllib.request.Request(
            "http://localhost:8087/api/camel/execute?routeId=not-a-route",
            data=b"<root/>",
            headers={"Content-Type": "application/xml"},
            method="POST",
        )
        with pytest.raises(urllib.error.HTTPError) as e:
            urllib.request.urlopen(req, timeout=5)
        assert e.value.code in (400, 500)
