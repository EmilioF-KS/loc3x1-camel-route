#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
LOG_DIR="${ROOT_DIR}/tests/results/logs"
ART_DIR="${ROOT_DIR}/tests/results/artifacts/clients"
mkdir -p "$LOG_DIR" "$ART_DIR"

PORT=${INBOUND_PORT:-8085}
WSDL=${BASIC_SERVICE_WSDL:-"http://localhost:${PORT}/services/BasicService?wsdl"}
PING_MSG=${PING_MSG:-"client"}
CONNECT_MS=${CLIENT_CONNECT_TIMEOUT_MS:-5000}
RECEIVE_MS=${CLIENT_RECEIVE_TIMEOUT_MS:-5000}

INBOUND_LOG="$LOG_DIR/inbound-service.out"
CLIENT_LOG="$LOG_DIR/client-run.out"
RUN_LOG="$LOG_DIR/run-clients.log"

function log() { echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a "$RUN_LOG"; }

log "Building clients module"
( mvn -q -DskipTests clean package -f "$ROOT_DIR/modules/clients/locationretrievalloc3x1b/pom.xml" )

# Build and start inbound service for client call
log "Building inbound module"
( mvn -q -DskipTests package -f "$ROOT_DIR/modules/inbound/pom.xml" )
log "Ensuring port ${PORT} is free"
PIDS=$(lsof -ti tcp:"$PORT" || true)
if [[ -n "$PIDS" ]]; then
  log "Killing processes on port ${PORT}: $PIDS"; kill $PIDS || true; sleep 1;
fi
JAR=$(ls -1 "$ROOT_DIR/modules/inbound/target"/inbound-*.jar | head -n 1 || true)
if [[ -z "$JAR" ]]; then
  log "ERROR: Inbound jar not found"; exit 2;
fi
log "Starting inbound service on port ${PORT}"
INBOUND_PORT="$PORT" java -jar "$JAR" --server.port="$PORT" > "$INBOUND_LOG" 2>&1 &
PID=$!
log "Inbound PID=${PID}"
log "Waiting for WSDL..."
for i in {1..20}; do
  sleep 1
  if curl -s "http://localhost:${PORT}/services/BasicService?wsdl" | grep -q "definitions"; then
    log "WSDL published"; break
  fi
done

log "Running CXF client against WSDL: ${WSDL} with msg: ${PING_MSG}"
(
  cd "$ROOT_DIR/modules/clients/locationretrievalloc3x1b" && \
  BASIC_SERVICE_WSDL="$WSDL" PING_MSG="$PING_MSG" CLIENT_CONNECT_TIMEOUT_MS="$CONNECT_MS" CLIENT_RECEIVE_TIMEOUT_MS="$RECEIVE_MS" \
  mvn -q -DskipTests exec:java | tee "$CLIENT_LOG"
)

# Capture success evidence
SOAP_REQ="$ART_DIR/client-ping.request.meta.txt"
SOAP_RES="$ART_DIR/client-ping.response.meta.txt"
{
  echo "wsdl=${WSDL}";
  echo "msg=${PING_MSG}";
  echo "connect_timeout_ms=${CONNECT_MS}";
  echo "receive_timeout_ms=${RECEIVE_MS}";
} > "$SOAP_REQ"

# Extract reply line
grep -E "^Ping reply=" "$CLIENT_LOG" | sed 's/^Ping reply=//' > "$SOAP_RES" || true

if grep -q "✓ CXF client Ping succeeded" "$CLIENT_LOG"; then
  log "Client Ping succeeded; evidence: $SOAP_REQ, $SOAP_RES"
else
  log "Client Ping failed; see log: $CLIENT_LOG"; exit 2
fi

# Stop inbound service
log "Stopping inbound service"
kill "$PID" || true

log "Done"