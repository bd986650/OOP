package ru.nsu.belov;

import java.util.Arrays;

/**
 * parallel stream
 */
class ParallelStream {
    public static boolean hasNonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !FindPrime.isPrime(num));
    }
}