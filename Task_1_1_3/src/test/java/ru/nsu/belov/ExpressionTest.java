package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test eval.
 */
class ExpressionTest {
    /**
     * test eval.
     */
    @Test
    public void testEvalWithStringVariables() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(23, expr.eval("x = 10"));
    }

    /**
     * test eval.
     */
    @Test
    public void testEvalWithMultipleVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(15, expr.eval("x = 5; y = 10"));
    }
}
