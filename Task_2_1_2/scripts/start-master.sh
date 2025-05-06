#!/bin/bash

# Start master node with parameters

if [ "$#" -lt 2 ]; then
    echo "Usage: ./start-master.sh <numbers_csv> <worker1> [<worker2> ...]"
    exit 1
fi

NUMBERS=$1
shift
WORKERS=$@

JAR_FILE="distributed-prime-checker.jar"
CONFIG_FILE="../config/master.properties"

java -cp $JAR_FILE -Dconfig.file=$CONFIG_FILE Main master "$NUMBERS" $WORKERS