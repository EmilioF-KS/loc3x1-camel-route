#!/usr/bin/env bash
set -euo pipefail

# Local CI Runner
# Mirrors hosted CI: preflight → build → test → package
# Artefacts: ci/artifacts/* and tests/results/*

PROJECT_ROOT="$(cd "$(dirname "$0")"/.. && pwd)"
ARTIFACTS_DIR="$PROJECT_ROOT/ci/artifacts"
RESULTS_DIR="$PROJECT_ROOT/tests/results"
mkdir -p "$ARTIFACTS_DIR" "$RESULTS_DIR"

log() { echo "[local-ci] $*"; }

# Optional: activate venv if present
if [[ -d "$PROJECT_ROOT/venv" ]]; then
  log "Activating venv"
  # shellcheck disable=SC1091
  source "$PROJECT_ROOT/venv/bin/activate"
else
  log "No venv found; proceeding with system Python"
fi

# Record versions
{
  echo "Date: $(date -u)"
  echo "JAVA: $(java -version 2>&1 | head -n1)"
  echo "MAVEN: $(mvn -v 2>&1 | head -n1)"
  echo "PYTHON: $(python3 --version 2>&1)"
} > "$ARTIFACTS_DIR/versions.txt" || true

# 1) Preflight
log "Running tooling preflight"
set +e
bash "$PROJECT_ROOT/scripts/preflight.sh" 2>&1 | tee "$ARTIFACTS_DIR/preflight.log"
PRE_STATUS=${PIPESTATUS[0]}
set -e
if [[ $PRE_STATUS -ne 0 ]]; then
  log "Preflight failed; see $ARTIFACTS_DIR/preflight.log"
  exit $PRE_STATUS
fi

# 2) Build (Maven)
log "Building modules with Maven"
# Ensure we build from the multi-module aggregator POM
mvn -f "$PROJECT_ROOT/modules/pom.xml" -B -q -DskipITs=false clean verify 2>&1 | tee "$ARTIFACTS_DIR/maven-build.log"

# 3) Tests (Master Runner)
log "Running tests via scripts/test_master.sh"
bash "$PROJECT_ROOT/scripts/test_master.sh" --all 2>&1 | tee "$ARTIFACTS_DIR/test-master.log"

# 4) Package artefacts
log "Packaging artefacts"
TARBALL="$ARTIFACTS_DIR/reports.tgz"
# include JUnit reports, logs, summaries
if [[ -d "$RESULTS_DIR" ]]; then
  tar -czf "$TARBALL" -C "$PROJECT_ROOT" tests/results || true
fi

log "Done. Artefacts: $ARTIFACTS_DIR; Test reports: $RESULTS_DIR"