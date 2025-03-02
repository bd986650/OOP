package ru.nsu.belov;

import java.util.ArrayList;
import java.util.List;

public class ParallelThreadSearch {
    private static volatile boolean allPrimes = false;

    public static boolean allPrimes(int[] numbers, int threadCount)
            throws InterruptedException {
        int chunkSize = (int) Math.ceil(numbers.length / (double) threadCount);

        List<Thread> threads = new ArrayList<>();

        allPrimes = true;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);

            Thread thread = new Thread(() -> {
                for (int j = start; j < end && allPrimes; j++) {
                    if (!PrimeUtil.isPrime(numbers[j])) {
                        allPrimes = false;
                        break;
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return allPrimes;
    }

    private ParallelThreadSearch() {
        throw new UnsupportedOperationException();
    }
}
