package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamTest {
    private final int[] notPrimeArrayFirst = {1};
    private final int[] notPrimeArraySecond = {2, 5, 4};
    private final int[] emptyArray = {};

    private static int[] generatePrimeArray(int size) {
        int[] primes = new int[size];
        int count = 0;
        int number = 2;
        while (count < size) {
            if (FindPrime.isPrime(number)) {
                primes[count] = number;
                count++;
            }
            number++;
        }
        return primes;
    }

    @org.junit.jupiter.api.Test
    void hasNonPrimeParallelStreamTest() {
        int[] largePrimeArray = generatePrimeArray(100000);
        long startTime = System.currentTimeMillis();
        boolean result = ParallelStream.hasNonPrimeParallelStream(largePrimeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Parallel execution with parallelStream time (test): " + (endTime - startTime) + " ms");

        assertFalse(result);
        assertTrue(ParallelStream.hasNonPrimeParallelStream(notPrimeArrayFirst));
        assertTrue(ParallelStream.hasNonPrimeParallelStream(notPrimeArraySecond));
        assertFalse(ParallelStream.hasNonPrimeParallelStream(emptyArray));
    }
}
