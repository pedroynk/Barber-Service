#!/bin/sh

echo "Starting the application..."
exec mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"