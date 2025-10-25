#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${VIRTUAL_ENV:-}" ]]; then
  echo "ERROR: Virtual environment not active; activate venv before running." >&2
  exit 1
fi

LOG_DIR="tests/results/logs"
mkdir -p "$LOG_DIR"
LOG_FILE="$LOG_DIR/bootstrap-java.log"

{
  echo "=== Bootstrap Java Environment ==="
  date
  echo "VIRTUAL_ENV=$VIRTUAL_ENV"
  echo "--- java -version ---"
  java -version 2>&1 || echo "java not found"
  echo "--- mvn -v ---"
  mvn -v || echo "maven not found"
  echo "--- python3 --version ---"
  python3 --version || echo "python3 not found"
  echo "--- pip packages ---"
  python3 -c 'import lxml, yaml; print("lxml:", lxml.__version__); print("PyYAML:", yaml.__version__)' 2>&1 || echo "Required Python packages missing"
  echo "=== Completed ==="
} | tee "$LOG_FILE"

echo "✓ Bootstrap completed; log at $LOG_FILE"