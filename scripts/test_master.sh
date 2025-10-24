#!/usr/bin/env bash
set -euo pipefail

# Master Test Runner
# Executes unit tests by ordinal (LOC-XXX), by section (contract, services, clients, orchestration, mappings), or all.
# Produces JUnit XML and summary reports in tests/results.
# Usage:
#   scripts/test_master.sh --all
#   scripts/test_master.sh --section <contract|services|clients|orchestration|mappings>
#   scripts/test_master.sh --ordinal <LOC-\d{3}>
# Options:
#   --watch        Re-run on changes if supported by section tooling
#   --help         Show help

PROJECT_ROOT="$(cd "$(dirname "$0")"/.. && pwd)"
RESULTS_DIR="$PROJECT_ROOT/tests/results"
LOG_DIR="$RESULTS_DIR/logs"
JUNIT_DIR="$RESULTS_DIR/junit"
HTML_DIR="$RESULTS_DIR/html"

mkdir -p "$RESULTS_DIR" "$LOG_DIR" "$JUNIT_DIR" "$HTML_DIR"

print_help() {
  cat <<'EOF'
Master Test Runner

Execute tests by:
  --all                        Run all unit tests
  --section <name>             Run tests for a section: contract | services | clients | orchestration | mappings
  --ordinal <LOC-###>          Run the specific test suite tagged by ordinal

Options:
  --watch                      Watch mode if supported
  --help                       Show this help

Examples:
  scripts/test_master.sh --all
  scripts/test_master.sh --section contract
  scripts/test_master.sh --ordinal LOC-003
EOF
}

SECTION=""
ORDINAL=""
WATCH="false"

while [[ $# -gt 0 ]]; do
  case "$1" in
    --all)
      SECTION="all"; shift ;;
    --section)
      SECTION="$2"; shift 2 ;;
    --ordinal)
      ORDINAL="$2"; shift 2 ;;
    --watch)
      WATCH="true"; shift ;;
    --help|-h)
      print_help; exit 0 ;;
    *)
      echo "Unknown argument: $1" >&2
      print_help; exit 1 ;;
  esac
done

if [[ -z "$SECTION" && -z "$ORDINAL" ]]; then
  echo "Error: specify --all, --section, or --ordinal" >&2
  print_help; exit 1
fi

# Utility: run Maven tests with surefire/failsafe and capture JUnit
run_maven_tests() {
  local module_dir="$1"
  local mvn_args=("-q" "-DskipITs=false")
  if [[ "$WATCH" == "true" ]]; then
    echo "Watch mode requested; Maven watch not supported natively. Proceeding without watch." >&2
  fi
  (
    cd "$module_dir"
    mvn -B -DtrimStackTrace=false "${mvn_args[@]}" test 2>&1 | tee "$LOG_DIR/$(basename "$module_dir")-test.log"
  )
  # Copy surefire reports
  if [[ -d "$module_dir/target/surefire-reports" ]]; then
    rsync -a "$module_dir/target/surefire-reports/" "$JUNIT_DIR/$(basename "$module_dir")/" || true
  fi
  if [[ -d "$module_dir/target/failsafe-reports" ]]; then
    rsync -a "$module_dir/target/failsafe-reports/" "$JUNIT_DIR/$(basename "$module_dir")/" || true
  fi
}

# Section mapping: adjust as project evolves
run_section() {
  local section="$1"
  case "$section" in
    contract)
      # contracts/tests
      if [[ -d "$PROJECT_ROOT/modules/contracts" ]]; then
        run_maven_tests "$PROJECT_ROOT/modules/contracts"
      else
        echo "contracts module not found; skipping" >&2
      fi
      ;;
    services)
      for mod in inbound services; do
        if [[ -d "$PROJECT_ROOT/modules/$mod" ]]; then
          run_maven_tests "$PROJECT_ROOT/modules/$mod"
        fi
      done
      ;;
    clients)
      if [[ -d "$PROJECT_ROOT/modules/clients" ]]; then
        run_maven_tests "$PROJECT_ROOT/modules/clients"
      fi
      ;;
    orchestration)
      for mod in orchestration routes; do
        if [[ -d "$PROJECT_ROOT/modules/$mod" ]]; then
          run_maven_tests "$PROJECT_ROOT/modules/$mod"
        fi
      done
      ;;
    mappings)
      if [[ -d "$PROJECT_ROOT/modules/mappings" ]]; then
        run_maven_tests "$PROJECT_ROOT/modules/mappings"
      fi
      ;;
    all)
      for mod in contracts inbound services clients orchestration routes mappings; do
        if [[ -d "$PROJECT_ROOT/modules/$mod" ]]; then
          run_maven_tests "$PROJECT_ROOT/modules/$mod"
        fi
      done
      ;;
    *)
      echo "Unknown section: $section" >&2
      exit 1
      ;;
  esac
}

# Ordinal execution: map LOC-XXX to modules or test selectors
run_ordinal() {
  local ordinal="$1"
  # Example mapping: maintain a registry file for precise routing
  local registry="$PROJECT_ROOT/tests/ordinal-map.txt"
  if [[ ! -f "$registry" ]]; then
    echo "Warning: $registry not found; falling back to full test run" >&2
    run_section all
    return
  fi
  local target_module
  target_module="$(grep -E "^$ordinal\s+" "$registry" | awk '{print $2}')"
  if [[ -z "$target_module" ]]; then
    echo "Ordinal $ordinal not found in registry; running all" >&2
    run_section all
    return
  fi
  if [[ -d "$PROJECT_ROOT/modules/$target_module" ]]; then
    run_maven_tests "$PROJECT_ROOT/modules/$target_module"
  else
    echo "Mapped module $target_module not found; running all" >&2
    run_section all
  fi
}

# Main dispatch
if [[ -n "$SECTION" && "$SECTION" != "all" ]]; then
  run_section "$SECTION"
elif [[ "$SECTION" == "all" ]]; then
  run_section all
elif [[ -n "$ORDINAL" ]]; then
  run_ordinal "$ORDINAL"
fi

# Summarise results: count failures
FAILURES=$(grep -R "Failures: [1-9]" "$JUNIT_DIR" 2>/dev/null | wc -l || true)
TESTS=$(grep -R "Tests run:" "$JUNIT_DIR" 2>/dev/null | wc -l || true)
SUMMARY_FILE="$HTML_DIR/summary.txt"
{
  echo "Tests scanned reports: $TESTS"
  echo "Failing suites: $FAILURES"
  echo "Reports dir: $JUNIT_DIR"
} > "$SUMMARY_FILE"

echo "Master tests completed. Summary at $SUMMARY_FILE"