#!/bin/bash

# Deploy workers to remote hosts

if [ "$#" -lt 2 ]; then
    echo "Usage: ./deploy.sh <jar_file> <host1> [<host2> ...]"
    exit 1
fi

JAR_FILE=$1
shift
HOSTS=$@

for HOST in $HOSTS; do
    echo "Deploying to $HOST..."
    scp $JAR_FILE $HOST:~/distributed-prime-checker/
    scp config/worker.properties $HOST:~/distributed-prime-checker/config/
    ssh $HOST "cd ~/distributed-prime-checker && chmod +x scripts/start-worker.sh"
    echo "Deployed to $HOST"
done