package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DivTest {
    @Test
    public void testPrint() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", div.print());
    }

    @Test
    public void testEval() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(5, div.eval(vars));
    }

    @Test
    public void testDerivative() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(((1*2)-(x*0))/(2*2))", div.derivative("x").print());
    }
}