package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prime Util test for main function + private constructor
 */
class PrimeUtilTest {

    @Test
    void isPrimeTest() {
        assertTrue(PrimeUtil.isPrime(7));
        assertFalse(PrimeUtil.isPrime(10));
        assertTrue(PrimeUtil.isPrime(2));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<PrimeUtil> constructor = PrimeUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class,
                constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("This is a utility class and cannot be instantiated",
                exception.getCause().getMessage());
    }
}