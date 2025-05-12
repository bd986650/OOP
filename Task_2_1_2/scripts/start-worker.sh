#!/bin/bash

# Start worker node with optional port parameter

PORT=${1:-12345}

JAR_FILE="distributed-prime-checker.jar"
CONFIG_FILE="../config/worker.properties"

java -cp $JAR_FILE -Dconfig.file=$CONFIG_FILE Main worker $PORT