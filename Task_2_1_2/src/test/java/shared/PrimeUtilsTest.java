package ru.nsu.belov.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrimeUtilsTest {

    @Test
    void testIsPrime() {
        assertTrue(PrimeUtils.isPrime(2));
        assertTrue(PrimeUtils.isPrime(3));
        assertTrue(PrimeUtils.isPrime(5));
        assertTrue(PrimeUtils.isPrime(7));
        assertTrue(PrimeUtils.isPrime(11));
        assertTrue(PrimeUtils.isPrime(13));
        assertTrue(PrimeUtils.isPrime(17));
        assertTrue(PrimeUtils.isPrime(19));
        assertTrue(PrimeUtils.isPrime(23));
        assertTrue(PrimeUtils.isPrime(29));
        assertTrue(PrimeUtils.isPrime(31));
        assertTrue(PrimeUtils.isPrime(37));
        assertTrue(PrimeUtils.isPrime(41));
        assertTrue(PrimeUtils.isPrime(43));
        assertTrue(PrimeUtils.isPrime(47));
    }

    @Test
    void testIsNotPrime() {
        assertFalse(PrimeUtils.isPrime(1));
        assertFalse(PrimeUtils.isPrime(4));
        assertFalse(PrimeUtils.isPrime(6));
        assertFalse(PrimeUtils.isPrime(8));
        assertFalse(PrimeUtils.isPrime(9));
        assertFalse(PrimeUtils.isPrime(10));
        assertFalse(PrimeUtils.isPrime(12));
        assertFalse(PrimeUtils.isPrime(14));
        assertFalse(PrimeUtils.isPrime(15));
        assertFalse(PrimeUtils.isPrime(16));
        assertFalse(PrimeUtils.isPrime(18));
        assertFalse(PrimeUtils.isPrime(20));
        assertFalse(PrimeUtils.isPrime(21));
        assertFalse(PrimeUtils.isPrime(22));
        assertFalse(PrimeUtils.isPrime(24));
    }

    @Test
    void testHasNonPrime() {
        assertTrue(PrimeUtils.hasNonPrime(new int[]{2, 3, 4, 5}));
        assertTrue(PrimeUtils.hasNonPrime(new int[]{2, 4, 6, 8}));
        assertTrue(PrimeUtils.hasNonPrime(new int[]{1, 2, 3}));
        assertTrue(PrimeUtils.hasNonPrime(new int[]{2, 3, 4}));
        assertTrue(PrimeUtils.hasNonPrime(new int[]{3, 4, 5}));
    }

    @Test
    void testAllPrimes() {
        assertFalse(PrimeUtils.hasNonPrime(new int[]{2, 3, 5}));
        assertFalse(PrimeUtils.hasNonPrime(new int[]{2, 3, 5, 7}));
        assertFalse(PrimeUtils.hasNonPrime(new int[]{2, 3, 5, 7, 11}));
        assertFalse(PrimeUtils.hasNonPrime(new int[]{2, 3, 5, 7, 11, 13}));
        assertFalse(PrimeUtils.hasNonPrime(new int[]{2, 3, 5, 7, 11, 13, 17}));
    }
}

