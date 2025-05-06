package ru.nsu.belov;

import ru.nsu.belov.master.DistributedPrimeCheckerMaster;
import ru.nsu.belov.worker.DistributedPrimeCheckerWorker;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  As master: java Main master <numbers> <worker1> [<worker2> ...]");
            System.out.println("  As worker: java Main worker [port]");
            return;
        }

        String mode = args[0];

        if ("worker".equalsIgnoreCase(mode)) {
            String[] workerArgs = Arrays.copyOfRange(args, 1, args.length);
            DistributedPrimeCheckerWorker.main(workerArgs);
        } else if ("master".equalsIgnoreCase(mode)) {
            if (args.length < 3) {
                System.out.println("Master requires numbers and at least one worker address");
                return;
            }

            try {
                int[] numbers = Arrays.stream(args[1].split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                String[] workerAddresses = Arrays.copyOfRange(args, 2, args.length);

                DistributedPrimeCheckerMaster master = new DistributedPrimeCheckerMaster();
                boolean result = master.hasNonPrime(numbers, workerAddresses);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Unknown mode: " + mode);
        }
    }
}


// ./gradlew test --tests "*" --info