#!/usr/bin/env python3
"""
Environment Preflight Agent

Checks for required tooling and suggests actions:
- Homebrew
- Java (java -version)
- Python (version, packages)
- Ollama (local LLM runtime)
- LangGraph (agent framework)

Writes a concise report to doc/.preflight/agent-checks.md
"""
import os
import shutil
import subprocess
from pathlib import Path
from datetime import datetime

REPORT_PATH = Path.cwd() / "doc" / ".preflight" / "agent-checks.md"


def run_cmd(cmd):
    try:
        out = subprocess.check_output(cmd, stderr=subprocess.STDOUT, text=True)
        return 0, out.strip()
    except subprocess.CalledProcessError as e:
        return e.returncode, (e.output or "").strip()
    except FileNotFoundError:
        return 127, "not found"


def check_brew():
    exists = shutil.which("brew") is not None
    code, out = run_cmd(["brew", "--version"]) if exists else (127, "not found")
    return {
        "tool": "brew",
        "exists": exists,
        "status": code == 0,
        "details": out,
        "suggest": "Install Homebrew: https://brew.sh" if not exists else "",
    }


def check_java():
    exists = shutil.which("java") is not None
    code, out = run_cmd(["java", "-version"]) if exists else (127, "not found")
    return {
        "tool": "java",
        "exists": exists,
        "status": code == 0,
        "details": out,
        "suggest": "brew install java" if not exists else "",
    }


def check_python():
    import sys
    return {
        "tool": "python",
        "exists": True,
        "status": True,
        "details": f"Python {sys.version.split()[0]}",
        "suggest": "",
    }


def check_ollama():
    exists = shutil.which("ollama") is not None
    code, out = run_cmd(["ollama", "--version"]) if exists else (127, "not found")
    return {
        "tool": "ollama",
        "exists": exists,
        "status": code == 0,
        "details": out,
        "suggest": "brew install ollama && ollama serve" if not exists else "",
    }


def check_langgraph():
    try:
        import langgraph  # noqa: F401
        return {"tool": "langgraph", "exists": True, "status": True, "details": "import ok", "suggest": ""}
    except Exception as e:
        return {"tool": "langgraph", "exists": False, "status": False, "details": str(e), "suggest": "pip install langgraph"}


def write_report(results):
    REPORT_PATH.parent.mkdir(parents=True, exist_ok=True)
    with open(REPORT_PATH, "w", encoding="utf-8") as f:
        f.write("# Preflight Agent Checks\n\n")
        f.write(f"Generated: {datetime.now().isoformat()}\n\n")
        for r in results:
            f.write(f"- **{r['tool']}**: {'OK' if r['status'] else 'Missing'}\n")
            f.write(f"  - Exists: {r['exists']}\n")
            if r.get("details"):
                f.write(f"  - Details: {r['details']}\n")
            if r.get("suggest"):
                f.write(f"  - Suggest: {r['suggest']}\n")
            f.write("\n")
    print(f"✓ Wrote {REPORT_PATH}")


def main():
    results = [
        check_brew(),
        check_java(),
        check_python(),
        check_ollama(),
        check_langgraph(),
    ]
    write_report(results)


if __name__ == "__main__":
    main()