package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindPrimeTest {
    @Test
    void isPrimeTest() {
        assertTrue(FindPrime.isPrime(7));
        assertFalse(FindPrime.isPrime(10));
        assertTrue(FindPrime.isPrime(2));
    }
}