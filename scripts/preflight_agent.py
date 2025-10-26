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

from scripts.utils.paths import PREVIEW_DIR, ensure_results_dirs
REPORT_PATH = PREVIEW_DIR / "agent-checks.md"


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


# New helper: choose best model name from available list
def select_best_model(available_names):
    preferred = [
        "qwen3-coder:latest",
        "qwen2.5-coder:latest",
        "qwen2.5-coder:1.5b-base",
    ]
    for p in preferred:
        if p in available_names:
            return p
    for n in available_names:
        if "qwen2.5-coder" in (n or "").lower():
            return n
    for n in available_names:
        if "qwen" in (n or "").lower():
            return n
    return available_names[0] if available_names else "qwen3-coder:latest"


# New check: verify Ollama service and ensure selected model exists (pull if missing)
def check_ollama_models(selected_model: str):
    import json as _json
    import urllib.request as _urlreq
    import urllib.error as _urlerr

    host = os.environ.get("OLLAMA_HOST", "http://localhost:11434").rstrip("/")
    tags_url = f"{host}/api/tags"

    try:
        with _urlreq.urlopen(tags_url, timeout=10) as resp:
            tags = _json.loads(resp.read().decode("utf-8")).get("models", [])
            names = [t.get("model") or t.get("name") for t in tags]
            if not names:
                names = []
            exists = True
            status = True
            details = f"Service OK; models: {', '.join(names) if names else '(none)'}"
            suggest = ""

            # Choose a best model if not explicitly provided
            model = selected_model or select_best_model(names)
            have_model = model in names
            if not have_model:
                # Try to pull the model
                try:
                    pull_url = f"{host}/api/pull"
                    payload = _json.dumps({"name": model}).encode("utf-8")
                    req = _urlreq.Request(pull_url, data=payload, headers={"Content-Type": "application/json"})
                    # Pull may stream; allow longer timeout
                    with _urlreq.urlopen(req, timeout=300) as p:
                        _ = p.read()  # ignore streaming details
                    # Re-check tags
                    with _urlreq.urlopen(tags_url, timeout=15) as r2:
                        names2 = [m.get("model") or m.get("name") for m in _json.loads(r2.read().decode("utf-8")).get("models", [])]
                        have_model = model in names2
                        details = details + f"; attempted pull: {model}; present={have_model}"
                    if not have_model:
                        status = False
                        suggest = f"Run: ollama pull {model} and ensure ollama serve is running"
                except Exception as e:
                    status = False
                    suggest = f"Failed to pull {model}: {e}. Ensure ollama serve is running then ollama pull {model}."
                    details = details + f"; pull error: {e}"
            else:
                details = details + f"; selected model present: {model}"

            return {
                "tool": "ollama-model",
                "exists": exists,
                "status": status,
                "details": details,
                "suggest": suggest,
            }
    except Exception as e:
        return {
            "tool": "ollama-service",
            "exists": False,
            "status": False,
            "details": f"Service unreachable at {tags_url}: {e}",
            "suggest": "Start Ollama: ollama serve (or launch the Ollama app)",
        }


def write_report(results):
    ensure_results_dirs()
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
    # Discover available models if service is up; otherwise default selection
    selected_model = "qwen3-coder:latest"
    try:
        import json as _json
        import urllib.request as _urlreq
        host = os.environ.get("OLLAMA_HOST", "http://localhost:11434").rstrip("/")
        with _urlreq.urlopen(f"{host}/api/tags", timeout=5) as resp:
            tags = _json.loads(resp.read().decode("utf-8")).get("models", [])
            names = [t.get("model") or t.get("name") for t in tags]
            if names:
                selected_model = select_best_model(names)
    except Exception:
        pass

    results = [
        check_brew(),
        check_java(),
        check_python(),
        check_ollama(),
        check_langgraph(),
        check_ollama_models(selected_model),
    ]
    write_report(results)


if __name__ == "__main__":
    main()