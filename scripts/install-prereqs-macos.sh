#!/usr/bin/env bash
set -e
if ! command -v brew >/dev/null 2>&1; then
  echo "Homebrew required: https://brew.sh"
  exit 1
fi
brew update
brew install openjdk@17 maven node
echo "Ensure JAVA_HOME points to JDK 17: export JAVA_HOME=\$(/usr/libexec/java_home -v 17)"
echo "Then run: python run.py"

