package ru.nsu.belov;

import java.util.Arrays;

class ParallelStreamSearch {
    public static boolean allPrimes(int[] numbers) {
        return Arrays.stream(numbers).parallel().allMatch(PrimeUtil::isPrime);
    }
    private ParallelStreamSearch() {
        throw new UnsupportedOperationException();
    }
}
