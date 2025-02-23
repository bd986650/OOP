package ru.nsu.belov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parallel thread class.
 */
public class ParallelThread {
    private static volatile boolean foundNotPrime = false;

    /**
     * Main func of this class.
     * @param numbers array
     * @param threadCount int
     * @return boolean value
     * @throws InterruptedException throws
     */
    public static boolean hasNonPrimeParallelThreads(int[] numbers, int threadCount)
            throws InterruptedException {
        int chunkSize = (int) Math.ceil(numbers.length / (double) threadCount);

        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

        foundNotPrime = false;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);

            Thread thread = new Thread(() -> {
                for (int j = start; j < end && !foundNotPrime; j++) {
                    if (!PrimeUtil.isPrime(numbers[j])) {
                        foundNotPrime = true;
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

        return foundNotPrime;
    }
}