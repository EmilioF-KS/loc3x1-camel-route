#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
LOG_DIR="$SCRIPT_DIR/logs"
mkdir -p "$LOG_DIR"
TS="$(date +%Y%m%d-%H%M%S)"
LOG_FILE="$LOG_DIR/test_apps_$TS.log"
echo "START $(date -Iseconds)" | tee -a "$LOG_FILE"
if ! command -v java >/dev/null 2>&1; then echo "java not found" | tee -a "$LOG_FILE"; exit 100; fi
if ! command -v mvn >/dev/null 2>&1; then echo "mvn not found" | tee -a "$LOG_FILE"; exit 101; fi
DIR_ARG="${1:-}"
if [ -z "$DIR_ARG" ]; then
  BASE="$ROOT_DIR/generated"
  if [ ! -d "$BASE" ]; then echo "generated directory not found" | tee -a "$LOG_FILE"; exit 102; fi
  N=$(ls -1 "$BASE" 2>/dev/null | sed -n 's/^result\([0-9][0-9]*\)$/\1/p' | sort -n | tail -1)
  if [ -z "${N:-}" ]; then echo "no resultN found" | tee -a "$LOG_FILE"; exit 103; fi
  APP_DIR="$BASE/result$N"
else
  APP_DIR="$DIR_ARG"
fi
if [ ! -d "$APP_DIR" ]; then echo "app directory not found: $APP_DIR" | tee -a "$LOG_FILE"; exit 104; fi
cd "$APP_DIR"
echo "Building in $APP_DIR" | tee -a "$LOG_FILE"
if ! mvn clean package -DskipTests >> "$LOG_FILE" 2>&1; then echo "build failed" | tee -a "$LOG_FILE"; exit 200; fi
PORT="${PORT:-8087}"
echo "Starting app on port $PORT" | tee -a "$LOG_FILE"
nohup mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=$PORT" >> "$LOG_FILE" 2>&1 &
PID=$!
READY=0
for i in $(seq 1 60); do
  if command -v curl >/dev/null 2>&1; then
    if curl -sf "http://localhost:$PORT/actuator/health" >/dev/null 2>&1; then READY=1; break; fi
  else
    sleep 1
  fi
  sleep 1
done
if [ "$READY" -ne 1 ]; then
  echo "health check failed" | tee -a "$LOG_FILE"
  kill "$PID" >/dev/null 2>&1 || true
  exit 300
fi
echo "health OK" | tee -a "$LOG_FILE"
sleep 5
kill "$PID" >/dev/null 2>&1 || true
rm -f nohup.out
echo "END $(date -Iseconds)" | tee -a "$LOG_FILE"
exit 0
