package ru.nsu.belov;

/**
 * Find prime number class.
 */
public class PrimeUtil {
    /**
     * main function of PrimeUtil.
     *
     * @param num main input
     * @return boolean value
     */
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

    /**
     * private constructor for git push review.
     */
    private PrimeUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
