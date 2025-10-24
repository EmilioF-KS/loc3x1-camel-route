#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
REPORT_PATH="$PROJECT_ROOT/doc/tooling-report.md"
SMOKE_LOG_DIR="$PROJECT_ROOT/doc/.preflight"
SMOKE_LOG="$SMOKE_LOG_DIR/smoke.log"

mkdir -p "$SMOKE_LOG_DIR"

# Colours
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

pass() { echo -e "${GREEN}PASS${NC} $1"; }
fail() { echo -e "${RED}FAIL${NC} $1"; }
warn() { echo -e "${YELLOW}WARN${NC} $1"; }

# Check venv activation
VENV_STATUS="NOT ACTIVE"
if [ -n "${VIRTUAL_ENV:-}" ]; then
  VENV_STATUS="ACTIVE ($VIRTUAL_ENV)"
  pass "Virtual environment active: $VIRTUAL_ENV"
else
  warn "Virtual environment not active; scripts should run inside venv."
fi

# Helper to get version output safely
cmd_version() {
  local cmd="$1"; shift || true
  if command -v "$cmd" >/dev/null 2>&1; then
    "$cmd" "$@" 2>&1 | head -n 1
    return 0
  else
    echo "NOT FOUND"
    return 1
  fi
}

# Collect versions
JAVA_VER="$(cmd_version java -version | tr -d '\r')"
MVN_VER="$(cmd_version mvn -v | tr -d '\r')"
JQ_VER="$(cmd_version jq --version | tr -d '\r')"
YQ_VER="$(cmd_version yq --version | tr -d '\r')"
PY_VER="$(python3 -V 2>&1 || echo 'NOT FOUND')"
PIP_VER="$(cmd_version pip3 --version | tr -d '\r')"

# Python packages (minimal set)
PY_PKGS=(lxml pyyaml)
PY_PKG_STATUS=""
for pkg in "${PY_PKGS[@]}"; do
  if python3 -c "import $pkg" 2>/dev/null; then
    PY_PKG_STATUS+="- $pkg: OK\n"
  else
    PY_PKG_STATUS+="- $pkg: MISSING\n"
  fi
done

# Summarise readiness
READY=true
[[ "$JAVA_VER" == "NOT FOUND" ]] && READY=false && fail "Java not found"
[[ "$MVN_VER" == "NOT FOUND" ]] && READY=false && fail "Maven not found"
[[ "$JQ_VER" == "NOT FOUND" ]] && warn "jq not found (recommended)"
[[ "$YQ_VER" == "NOT FOUND" ]] && warn "yq not found (recommended)"
[[ "$PY_VER" == "NOT FOUND" ]] && warn "Python3 not found (required for automation)"

# Generate report
cat > "$REPORT_PATH" <<EOF
# Tooling Preflight Report

Date: $(date -Iseconds)
Project Root: $PROJECT_ROOT

## Environment
- Venv: $VENV_STATUS

## Versions
- Java: $JAVA_VER
- Maven: $(echo "$MVN_VER" | head -n 1)
- jq: $JQ_VER
- yq: $YQ_VER
- Python: $PY_VER
- pip: $PIP_VER

## Python Packages
$PY_PKG_STATUS

## Recommendations
- Ensure Java 17 is installed and on PATH.
- Ensure Maven is installed and on PATH.
- Install jq/yq for JSON/YAML processing.
- Install Python packages via: \`pip3 install -r requirements.txt\` or \`pip3 install lxml pyyaml\`.

EOF

# Attempt smoke build if pom.xml exists
if [[ -f "$PROJECT_ROOT/pom.xml" ]]; then
  echo "Running smoke build... (logs at $SMOKE_LOG)"
  (cd "$PROJECT_ROOT" && mvn -q -DskipTests package) >"$SMOKE_LOG" 2>&1 || READY=false
  if $READY; then
    pass "Smoke build succeeded"
  else
    fail "Smoke build failed; see $SMOKE_LOG"
  fi
  echo -e "\n## Smoke Build\n- Log: $SMOKE_LOG\n- Status: $( $READY && echo PASS || echo FAIL )\n" >> "$REPORT_PATH"
else
  warn "No pom.xml found — skipping smoke build."
  echo -e "\n## Smoke Build\n- Status: SKIPPED (no pom.xml)\n" >> "$REPORT_PATH"
fi

# Final summary
if $READY; then
  pass "Preflight ready. Report: $REPORT_PATH"
else
  fail "Preflight incomplete. Report: $REPORT_PATH"
fi