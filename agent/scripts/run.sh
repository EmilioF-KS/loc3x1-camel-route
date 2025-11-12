#!/usr/bin/env bash
set -euo pipefail

usage() {
  cat <<'USAGE'
Usage:
  agent/scripts/run.sh discover --input <path> [--output <file>] [--overwrite]
  agent/scripts/run.sh test
  agent/scripts/run.sh all --input <path> [--output <file>] [--overwrite]

Notes:
  - Paths are not hardcoded; pass any input directory via --input.
  - You can also set DEP_INPUT_DIR env var for convenience.
USAGE
}

cmd=${1:-}
shift || true

case "${cmd}" in
  discover)
    python agent/scripts/discover.py "$@"
    ;;
  test)
    pytest -q
    ;;
  all)
    # Allow env var default for input path
    INPUT_SET=false
    ARGS=()
    while [[ $# -gt 0 ]]; do
      case $1 in
        --input)
          INPUT_SET=true
          ARGS+=("$1" "$2")
          shift 2
          ;;
        --output|--overwrite)
          ARGS+=("$1")
          [[ $1 == --output ]] && ARGS+=("$2") && shift
          shift || true
          ;;
        *)
          ARGS+=("$1")
          shift
          ;;
      esac
    done

    if [[ "$INPUT_SET" == false ]]; then
      if [[ -n "${DEP_INPUT_DIR:-}" ]]; then
        ARGS+=("--input" "${DEP_INPUT_DIR}")
      else
        echo "Error: --input not provided and DEP_INPUT_DIR not set" >&2
        usage
        exit 1
      fi
    fi

    python agent/scripts/discover.py "${ARGS[@]}"
    pytest -q
    ;;
  *)
    usage
    exit 1
    ;;
esac