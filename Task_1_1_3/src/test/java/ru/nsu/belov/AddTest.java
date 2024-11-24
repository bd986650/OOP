package ru.nsu.belov;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AddTest {
    @Test
    public void testPrint() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.print());
    }

    @Test
    public void testEval() {
        Expression add = new Add(new Number(3), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(13, add.eval(vars));
    }

    @Test
    public void testDerivative() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(0+1)", add.derivative("x").print());
    }
}
