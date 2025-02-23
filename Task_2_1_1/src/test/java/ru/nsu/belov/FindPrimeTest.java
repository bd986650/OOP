package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Find prime number test.
 */
class FindPrimeTest {
    @Test
    void isPrimeTest() {
        assertTrue(PrimeUtil.isPrime(7));
        assertFalse(PrimeUtil.isPrime(10));
        assertTrue(PrimeUtil.isPrime(2));
    }
}