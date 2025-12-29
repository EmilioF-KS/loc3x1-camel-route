import shutil
import subprocess
import os
from typing import List, Tuple


def javac_available() -> bool:
    return shutil.which("javac") is not None


def compile_java_files(files: List[str], output_dir: str | None = None) -> Tuple[bool, str]:
    if not javac_available():
        return False, "javac not available"
    cmd = ["javac"]
    if output_dir:
        os.makedirs(output_dir, exist_ok=True)
        cmd += ["-d", output_dir]
    cmd += files
    try:
        res = subprocess.run(cmd, capture_output=True, text=True)
        success = res.returncode == 0
        output = (res.stdout or "") + (res.stderr or "")
        return success, output
    except Exception as e:
        return False, str(e)


__all__ = ["javac_available", "compile_java_files"]