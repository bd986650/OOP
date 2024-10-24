package ru.nsu.belov;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test sub.
 */
class SubTest {
    /**
     * test print.
     */
    @Test
    public void testPrint() {
        Expression sub = new Sub(new Number(5), new Variable("x"));
        assertEquals("(5-x)", sub.print());
    }

    /**
     * test eval.
     */
    @Test
    public void testEval() {
        Expression sub = new Sub(new Number(5), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 3);
        assertEquals(2, sub.eval(vars));
    }

    /**
     * test deriv.
     */
    @Test
    public void testDerivative() {
        Expression sub = new Sub(new Number(5), new Variable("x"));
        assertEquals("(0-1)", sub.derivative("x").print());
    }
}
