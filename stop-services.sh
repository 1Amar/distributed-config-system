#!/bin/bash
echo "Stopping all microservices..."

services=("user-service.jar" "order-service.jar" "api-gateway.jar" "config-server.jar" "service-registry.jar")

for jar in "${services[@]}"; do
  pid=$(jps -l | grep "$jar" | awk '{print $1}')
  if [ -n "$pid" ]; then
    echo "Stopping $jar (PID $pid)..."
    taskkill //PID "$pid" //F > /dev/null 2>&1
  else
    echo "$jar is not running."
  fi
done

echo "All requested services processed."
