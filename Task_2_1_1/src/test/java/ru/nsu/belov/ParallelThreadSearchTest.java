package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelThreadSearchTest {
    private final int[] notPrimeArrayFirst = {1};
    private final int[] notPrimeArraySecond = {2, 5, 4};
    private final int[] emptyArray = {};

    private static int[] generatePrimeArray(int size) {
        int[] primes = new int[size];
        int count = 0;
        int number = 2;
        while (count < size) {
            if (PrimeUtil.isPrime(number)) {
                primes[count] = number;
                count++;
            }
            number++;
        }
        return primes;
    }

    @Test
    void hasNonPrimeParallelThreadsTest() throws InterruptedException {
        int[] largePrimeArray = generatePrimeArray(100000);
        int[] threadCounts = {2, 4, 8};

        for (int numThreads : threadCounts) {
            long startTime = System.currentTimeMillis();
            boolean result = ParallelThreadSearch.allPrimes(largePrimeArray, numThreads);
            long endTime = System.currentTimeMillis();
            System.out.println("Parallel execution with " + numThreads
                    + " threads time (test): " + (endTime - startTime) + " ms");

            assertTrue(result);
            assertFalse(ParallelThreadSearch.allPrimes(notPrimeArrayFirst, numThreads));
            assertFalse(ParallelThreadSearch.allPrimes(notPrimeArraySecond, numThreads));
            assertTrue(ParallelThreadSearch.allPrimes(emptyArray, numThreads));
        }
    }
}
