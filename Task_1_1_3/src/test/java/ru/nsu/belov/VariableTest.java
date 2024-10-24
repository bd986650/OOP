package ru.nsu.belov;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test var.
 */
class VariableTest {
    /**
     * test print.
     */
    @Test
    public void testPrint() {
        Expression var = new Variable("x");
        assertEquals("x", var.print());
    }

    /**
     * test eval.
     */
    @Test
    public void testEval() {
        Expression var = new Variable("x");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(10, var.eval(vars));
    }

    /**
     * test deriv.
     */
    @Test
    public void testDerivative() {
        Expression var = new Variable("x");
        assertEquals("1", var.derivative("x").print());

        Expression varY = new Variable("y");
        assertEquals("0", varY.derivative("x").print());
    }
}
