# LOC3x Pipeline — One-Click Run

## Prerequisites

- Python `3.11+`
- Java `17+`
- Maven `3.9+`
- Node.js `18+` and `npm`

Not required:
- Ollama (no local LLM models are used by the current pipeline)
- LangGraph (not used in the current implementation)

## Quick Start

- `python run.py`
- Browser opens at `http://localhost:5173`
- Click `Browse…` to select the input folder
- Click `Run Pipeline`
- When complete, click `Download Results` to save the generated project

## One‑Click Launch Details

- The launcher checks for `java`, `mvn`, `node`, and `npm`. If any are missing it prints guidance and exits.
- It creates `.venv` and installs Python dependencies from `requirements.txt`.
- It starts the backend API (`http://localhost:8000`), builds and runs the Java service on `http://localhost:8081` if needed, installs frontend dependencies, starts the UI (`http://localhost:5173`) and opens the browser.
- During a run, the backend stages include: `intake`, `inventory`, `mapping`, `transforming`, `routes`, `scaffold`, `building`, `verifying`, `done` and the UI shows progress with counts.

## OS‑Specific Installation

- macOS: `bash scripts/install-prereqs-macos.sh`
- Linux (apt): `bash scripts/install-prereqs-linux.sh`
- Windows (PowerShell as Administrator): `powershell -ExecutionPolicy Bypass -File scripts/install-prereqs-windows.ps1`

## Local Run Script

- Use `python run.py` for local launch. It includes translation, compilation and a brief run verification of the generated project.
- The UI supports folder upload or manual path entry and produces a downloadable zip of the generated service.

## CLI Usage (Terminal)

- One‑click generator:
  - `python -m agent.scripts.one_click --input CHUBB/Dependencies --output generated/result --clean --build-run`
  - Flags:
    - `--input`: path to the client inputs (e.g., `CHUBB/Dependencies`)
    - `--output`: target output folder (default `generated/result`)
    - `--clean`: remove existing output before generation
    - `--build-run`: build with Maven and briefly run to verify health

- Scaffold a project (advanced):
  - `python -m agent.scripts.scaffold_project --input CHUBB/Dependencies --out generated/result`
  - Optional flags include `--maps-dir`, `--orchestration`, `--provider-uri`, etc.

- Build and run the generated project manually:
  - `cd generated/result`
  - `mvn -DskipTests package`
  - `java -jar target/loc-service.jar --server.port=8081`

## What It Does

- Creates and uses a local Python virtual environment `.venv`
- Installs backend dependencies from `requirements.txt`
- Starts backend API on `http://localhost:8000`
- Builds and runs the Java service on `http://localhost:8081` (if not already running)
- Installs frontend dependencies and starts the UI on `http://localhost:5173`
- Runs the one‑click pipeline and verifies the generated project by building and briefly running it on `8082`

## Troubleshooting

- If the script reports missing tools, install the prerequisites and re‑run
- If the UI does not open, navigate to `http://localhost:5173` manually
- Backend docs: `http://localhost:8000/docs`
- Java health: `http://localhost:8081/actuator/health`
- If you need to install prerequisites on macOS:
  - `brew install java maven node`
  - Ensure `java -version` shows `17` or newer; set `JAVA_HOME` if needed.
Quick install scripts:
- macOS: `bash scripts/install-prereqs-macos.sh`
- Linux (apt): `bash scripts/install-prereqs-linux.sh`
