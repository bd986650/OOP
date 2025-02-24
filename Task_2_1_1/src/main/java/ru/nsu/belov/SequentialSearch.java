package ru.nsu.belov;

/**
 * Sequential class.
 */
class SequentialSearch {
    public static boolean allPrimes(int[] numbers) {
        for (int num : numbers) {
            if (!PrimeUtil.isPrime(num)) {
                return false;
            }
        }
        return true;
    }
    private SequentialSearch() {
        throw new UnsupportedOperationException();
    }
}
