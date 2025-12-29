#!/usr/bin/env bash
set -euo pipefail

red() { printf "\033[31m%s\033[0m\n" "$1"; }
green() { printf "\033[32m%s\033[0m\n" "$1"; }
yellow() { printf "\033[33m%s\033[0m\n" "$1"; }

PASS=true

echo "== Preflight checks =="

# Python
if command -v python3 >/dev/null 2>&1; then
  PY_VER=$(python3 -c 'import sys; print(f"{sys.version_info.major}.{sys.version_info.minor}")')
  echo "Python: ${PY_VER}"
  if python3 -c 'import sys; exit(0) if (sys.version_info.major, sys.version_info.minor) >= (3,11) else exit(1)'; then
    green "Python >= 3.11 OK"
  else
    red "Python >= 3.11 REQUIRED"
    PASS=false
  fi
else
  red "python3 not found"
  PASS=false
fi

# Java
if command -v java >/dev/null 2>&1; then
  JAVA_VER=$(java -version 2>&1 | head -n1)
  echo "${JAVA_VER}"
  # heuristic check for 17
  if java -version 2>&1 | grep -q '"17\.'; then
    green "Java 17 OK"
  else
    yellow "Java 17 recommended"
  fi
else
  red "java not found"
fi

# Maven
if command -v mvn >/dev/null 2>&1; then
  MVN_VER=$(mvn -v 2>&1 | head -n1)
  echo "${MVN_VER}"
  green "Maven present"
else
  yellow "mvn not found (needed for generated project builds)"
fi

# Docker
if command -v docker >/dev/null 2>&1; then
  DOCKER_VER=$(docker --version)
  echo "${DOCKER_VER}"
  green "Docker present"
else
  yellow "docker not found (optional)"
fi

if [ "$PASS" = true ]; then
  green "Preflight checks passed"
  exit 0
else
  red "Preflight checks failed"
  exit 1
fi