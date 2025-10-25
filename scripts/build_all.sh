#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${VIRTUAL_ENV:-}" ]]; then
  echo "ERROR: Virtual environment not active; activate venv before running." >&2
  exit 1
fi

LOG_DIR="tests/results/logs"
mkdir -p "$LOG_DIR"
LOG_FILE="$LOG_DIR/build.log"

{
  echo "=== Build All Modules ==="
  date
  echo "Building Maven aggregator at modules/pom.xml"
  mvn -f modules/pom.xml -q -DskipTests package
  echo "=== Build completed ==="
} | tee "$LOG_FILE"

echo "✓ Build completed; log at $LOG_FILE"