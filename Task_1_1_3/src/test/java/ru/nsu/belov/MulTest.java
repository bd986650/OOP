package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {
    @Test
    public void testPrint() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.print());
    }

    @Test
    public void testEval() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(10, mul.eval(vars));
    }

    @Test
    public void testDerivative() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("((0*x)+(2*1))", mul.derivative("x").print());
    }
}