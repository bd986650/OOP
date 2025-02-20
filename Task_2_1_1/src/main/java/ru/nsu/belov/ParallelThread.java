package ru.nsu.belov;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class ParallelThread {
    public static boolean hasNonPrimeParallelThreads(int[] numbers, int threadCount) throws InterruptedException {
        int chunkSize = (int) Math.ceil(numbers.length / (double) threadCount);
        List<Thread> threads = new CopyOnWriteArrayList<>();
        AtomicBoolean foundNotPrime = new AtomicBoolean(false);

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);

            Thread thread = new Thread(() -> {
                for (int j = start; j < end && !foundNotPrime.get(); j++) {
                    if (!FindPrime.isPrime(numbers[j])) {
                        foundNotPrime.set(true);
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

        return foundNotPrime.get();
    }
}