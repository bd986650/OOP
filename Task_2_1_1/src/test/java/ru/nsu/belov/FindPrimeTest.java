package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindPrimeTest {
    @Test
    void isPrimeTest() {
        assertTrue(FindPrime.isPrime(0));
        assertTrue(FindPrime.isPrime(1));
        assertTrue(FindPrime.isPrime(2));
        assertTrue(FindPrime.isPrime(3));
        assertTrue(FindPrime.isPrime(4));
        assertTrue(FindPrime.isPrime(10));
    }
}