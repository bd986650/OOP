package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SequentialSearchTest {
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
    void hasNonPrimeSequentialTest() {
        int[] largePrimeArray = generatePrimeArray(100000);
        long startTime = System.currentTimeMillis();
        boolean result = SequentialSearch.allPrimes(largePrimeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential execution time (test): " + (endTime - startTime) + " ms");

        assertTrue(result);
        assertFalse(SequentialSearch.allPrimes(notPrimeArrayFirst));
        assertFalse(SequentialSearch.allPrimes(notPrimeArraySecond));
        assertTrue(SequentialSearch.allPrimes(emptyArray));
    }
}
