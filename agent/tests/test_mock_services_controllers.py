import os
import subprocess
import time
import urllib.request
import urllib.error

import pytest

from agent.src.scaffold import scaffold_mock_services


BASE = "generated/mock-services-result"


def _has_cmd(cmd: str) -> bool:
    try:
        subprocess.check_call(["bash", "-lc", f"command -v {cmd} >/dev/null 2>&1"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        return True
    except Exception:
        return False


@pytest.mark.timeout(120)
def test_mock_controllers_exist_and_endpoint_responds():
    if not _has_cmd("mvn"):
        pytest.skip("maven not available")
    out = BASE + "test"
    scaffold_mock_services(out, group_id="com.example")
    subprocess.check_call(["mvn", "-DskipTests", "package"], cwd=out)
    p = subprocess.Popen(["mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--server.port=8091"], cwd=out)
    try:
        ok = False
        for _ in range(60):
            try:
                urllib.request.urlopen("http://localhost:8091/actuator/health", timeout=1).read()
                ok = True
                break
            except Exception:
                time.sleep(1)
        assert ok, "mock app did not start"
        meta = os.path.join(out, "MOCK_CONTROLLERS.txt")
        assert os.path.isfile(meta)
        lines = open(meta, "r", encoding="utf-8").read().strip().splitlines()
        assert lines and lines[0] != "none"
        # Take first endpoint
        endpoint = lines[0].split("->")[1].split("::")[0].strip()
        req = urllib.request.Request(
            "http://localhost:8091" + endpoint + "?validate=false",
            data=b"<root/>",
            headers={"Content-Type": "application/xml"},
            method="POST",
        )
        resp = urllib.request.urlopen(req, timeout=5)
        body = resp.read().decode("utf-8")
        assert resp.status == 200
        assert "<status>OK</status>" in body
    finally:
        try:
            p.terminate()
        except Exception:
            pass
