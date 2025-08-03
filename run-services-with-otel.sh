#!/bin/bash

# === CONFIGURATION ===
OTEL_AGENT_JAR="opentelemetry-javaagent.jar"
OTEL_AGENT_URL="https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar"

# Base directory for services
BASE_DIR=$(pwd)

# Define your services and their jar names
SERVICES=(
  "api-gateway:target/api-gateway.jar"
  "order-service:target/order-service.jar"
  "user-service:target/user-service.jar"
)

# OTEL Collector endpoint
OTEL_EXPORTER_OTLP_ENDPOINT="http://localhost:4318"

# === DOWNLOAD OTEL AGENT IF NOT PRESENT ===
if [ ! -f "$OTEL_AGENT_JAR" ]; then
  echo "Downloading OpenTelemetry Java Agent..."
  curl -L -o "$OTEL_AGENT_JAR" "$OTEL_AGENT_URL"
fi

# === START EACH SERVICE ===
for SERVICE in "${SERVICES[@]}"; do
  IFS=":" read -r NAME JAR_PATH <<< "$SERVICE"
  SERVICE_DIR="$BASE_DIR/$NAME"

  if [ ! -f "$SERVICE_DIR/$JAR_PATH" ]; then
    echo "[ERROR] JAR not found: $SERVICE_DIR/$JAR_PATH"
    continue
  fi

  echo "Starting $NAME with OpenTelemetry agent..."

  java \
    -javaagent:$BASE_DIR/$OTEL_AGENT_JAR \
    -Dotel.service.name=$NAME \
    -Dotel.exporter.otlp.endpoint=$OTEL_EXPORTER_OTLP_ENDPOINT \
	-Dotel.exporter.otlp.protocol=http/protobuf \
	-Dotel.traces.exporter=otlp \
	-Dotel.logs.exporter=none \
	-Dotel.metrics.exporter=none \
	-Dotel.instrumentation.logback.enabled=true \
	-Dotel.instrumentation.logback.mdc.custom.keys=trace_id,span_id \
    -jar "$SERVICE_DIR/$JAR_PATH" \
    > "$SERVICE_DIR/$NAME.log" 2>&1 &

done

echo "All services launched with OpenTelemetry instrumentation."
