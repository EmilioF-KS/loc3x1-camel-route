#!/usr/bin/env bash
set -euo pipefail

# venv guard
if [[ -z "${VIRTUAL_ENV:-}" ]]; then
  echo "ERROR: Virtual environment not active. Please 'source venv/bin/activate' and rerun." >&2
  exit 1
fi

# Generate CXF/JAXB sources from WSDLs in sample/
MVN_CMD="mvn -f modules/contracts/pom.xml -q generate-sources"
echo "Running: $MVN_CMD"
RESULTS_DIR="${RESULTS_DIR:-$(cd "$(dirname "$0")/.." && pwd)/results}"
PREVIEW_DIR="$RESULTS_DIR/preflight"
mkdir -p "$PREVIEW_DIR"
$MVN_CMD | tee "$PREVIEW_DIR/contracts-codegen.log"

echo "Contracts code generation completed. See $PREVIEW_DIR/contracts-codegen.log"