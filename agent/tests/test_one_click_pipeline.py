import os
from pathlib import Path
import subprocess


def test_one_click_generates_project(tmp_path: Path):
    out = "generated/result"
    cmd = ["python3", "-m", "agent.scripts.one_click", "--input", "CHUBB/Dependencies", "--output", out]
    proc = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    assert proc.returncode == 0, proc.stdout
    assert os.path.isdir(out)
    assert os.path.isfile(os.path.join(out, "pom.xml"))
    assert os.path.isfile(os.path.join(out, "src/main/resources/routes/loc-service.yaml"))
    assert os.path.isfile(os.path.join(out, "src/main/resources/openapi.yaml"))
    java_ctrl_dir = os.path.join(out, "src/main/java/com/example/controller")
    assert os.path.isdir(java_ctrl_dir)
    selected_dir = os.path.join(out, "src/main/resources/contracts/selected")
    assert os.path.isdir(selected_dir)

