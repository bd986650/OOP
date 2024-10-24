package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test number
 */
class NumberTest {
    /**
     * test print
     */
    @Test
    public void testPrint() {
        Expression num = new Number(5);
        assertEquals("5", num.print());
    }

    /**
     * test eval
     */
    @Test
    public void testEval() {
        Expression num = new Number(5);
        assertEquals(5, num.eval(new HashMap<>()));
    }

    /**
     * test deriv
     */
    @Test
    public void testDerivative() {
        Expression num = new Number(5);
        assertEquals("0", num.derivative("x").print());
    }
}
