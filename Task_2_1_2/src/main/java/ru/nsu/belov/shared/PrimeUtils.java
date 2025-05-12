package ru.nsu.belov.shared;

public class PrimeUtils {
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;

        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasNonPrime(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}