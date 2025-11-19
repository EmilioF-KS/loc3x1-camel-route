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

