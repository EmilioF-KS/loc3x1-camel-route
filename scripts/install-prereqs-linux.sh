#!/usr/bin/env bash
set -e
if command -v apt-get >/dev/null 2>&1; then
  sudo apt-get update
  sudo apt-get install -y openjdk-17-jdk maven nodejs npm
else
  echo "Use your distro package manager to install: openjdk-17, maven, nodejs, npm"
  exit 1
fi
echo "Then run: python run.py"

