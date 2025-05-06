# Distributed Prime Checker

A distributed system for checking if an array contains non-prime numbers.

## Architecture

- **Master Node**: Distributes work and aggregates results
- **Worker Nodes**: Perform actual prime number checking
- **Shared Utilities**: Common network and prime checking functions

## Requirements

- Java 11+
- Gradle 7+

## Building

```bash
gradle build
```

## Running

### Start Worker Node

```bash
java -jar build/libs/Task_2_1_2-1.0-SNAPSHOT.jar worker [port]
```

Example:
```bash
java -jar build/libs/Task_2_1_2-1.0-SNAPSHOT.jar worker 12345
```

### Start Master Node

```bash
java -jar build/libs/Task_2_1_2-1.0-SNAPSHOT.jar master <numbers_csv> <worker1> [<worker2> ...]
```

Examples:

1. Check array with composite numbers:
```bash
java -jar build/libs/Task_2_1_2-1.0-SNAPSHOT.jar master "6,8,7,13,5,9,4" localhost:12345
```
Expected output: `Result: true`

2. Check array with only prime numbers:
```bash
java -jar build/libs/Task_2_1_2-1.0-SNAPSHOT.jar master "20319251,6997901,6997927,6997937,17858849,6997967,6998009,6998029,6998039,20165149,6998051,6998053" localhost:12345
```
Expected output: `Result: false`

## Deployment

1. Build distribution package:

```bash
gradle buildDist
```

2. Deploy to remote hosts:

```bash
scripts/deploy.sh build/dist/distributed-prime-checker.jar worker1.example.com worker2.example.com
```

## Configuration

Edit files in `config/` directory:

- `master.properties` - Master node settings
- `worker.properties` - Worker node settings