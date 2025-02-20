package ru.nsu.belov;

/**
 * Sequential class.
 */
class Sequential {
    public static boolean hasNonPrimeSequential(int[] numbers) {
        for (int num : numbers) {
            if (!FindPrime.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
