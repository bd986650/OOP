package ru.nsu.belov;

public class PrimeUtil {
    public static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private PrimeUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
}
