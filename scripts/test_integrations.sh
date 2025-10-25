#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${VIRTUAL_ENV:-}" ]]; then
  echo "ERROR: Virtual environment not active; activate venv before running." >&2
  exit 1
fi

RESULTS_DIR="tests/results"
LOG_DIR="$RESULTS_DIR/logs"
mkdir -p "$LOG_DIR" "$RESULTS_DIR/junit" "$RESULTS_DIR/html"
LOG_FILE="$LOG_DIR/integration-tests.log"

{
  echo "=== Integration Tests ==="
  date
  echo "Running Python test master: tests/run_tests.py --all"
  python3 tests/run_tests.py --all
  echo "=== Completed ==="
} | tee "$LOG_FILE"

echo "✓ Tests completed; log at $LOG_FILE"