#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${VIRTUAL_ENV:-}" ]]; then
  echo "ERROR: Virtual environment not active; activate venv before running." >&2
  exit 1
fi

LOG_DIR="tests/results/logs"
mkdir -p "$LOG_DIR"
LOG_FILE="$LOG_DIR/run-services.log"
PORT=${INBOUND_PORT:-8085}

{
  echo "=== Run Inbound Services (Spring Boot + CXF) ==="
  date
  echo "Building inbound module"
  mvn -f modules/inbound/pom.xml -q -DskipTests package
  echo "Ensuring port $PORT is free"
  PIDS=$(lsof -ti tcp:"$PORT" || true)
  if [[ -n "$PIDS" ]]; then
    echo "Killing processes on port $PORT: $PIDS"
    kill $PIDS || true
    sleep 1
  fi
  echo "Starting inbound service on port $PORT"
  JAR=$(ls -1 modules/inbound/target/inbound-*.jar | head -n 1 || true)
  if [[ -z "$JAR" ]]; then
    echo "ERROR: Inbound jar not found";
    exit 2;
  fi
  INBOUND_PORT="$PORT" java -jar "$JAR" --server.port="$PORT" > "$LOG_DIR/inbound-service.out" 2>&1 &
  PID=$!
  echo "Inbound PID=$PID"
  echo "Waiting for service to start..."
  for i in {1..20}; do
    sleep 1
    if curl -s "http://localhost:$PORT/services/BasicService?wsdl" | grep -q "definitions"; then
      echo "WSDL published"; break
    fi
  done
  echo "Probing WSDL at http://localhost:$PORT/services/BasicService?wsdl"
  curl -sSf "http://localhost:$PORT/services/BasicService?wsdl" | head -n 10

  echo "Invoking SOAP Ping on http://localhost:$PORT/services/BasicService"
  SOAP_REQ="$LOG_DIR/soap-ping.xml"
  SOAP_RSP="$LOG_DIR/soap-ping.response.xml"
  cat > "$SOAP_REQ" <<'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://ei.com/contracts/basic">
  <soapenv:Header/>
  <soapenv:Body>
    <tns:Ping>
      <message>hello</message>
    </tns:Ping>
  </soapenv:Body>
</soapenv:Envelope>
EOF
  curl -sS -H "Content-Type: text/xml; charset=utf-8" --data "@${SOAP_REQ}" "http://localhost:$PORT/services/BasicService" > "$SOAP_RSP"
  head -n 10 "$SOAP_RSP"
  if grep -q "<reply>PONG:hello</reply>" "$SOAP_RSP"; then
    echo "SOAP Ping succeeded"
  else
    echo "SOAP Ping FAILED"
    exit 3
  fi

  echo "Stopping inbound service"
  kill "$PID" || true
  echo "=== Completed ==="
} | tee "$LOG_FILE"

echo "✓ Inbound services run; evidence at $LOG_FILE, $LOG_DIR/inbound-service.out, $LOG_DIR/soap-ping.xml, $LOG_DIR/soap-ping.response.xml"