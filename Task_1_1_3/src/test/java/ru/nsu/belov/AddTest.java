package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * add test
 */
class AddTest {
    /**
     * test print
     */
    @Test
    public void testPrint() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.print());
    }

    /**
     * test eval
     */
    @Test
    public void testEval() {
        Expression add = new Add(new Number(3), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(13, add.eval(vars));
    }

    /**
     * test deriv
     */
    @Test
    public void testDerivative() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(0+1)", add.derivative("x").print());
    }
}
