package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {
    @Test
    public void testPrint() {
        Expression num = new Number(5);
        assertEquals("5", num.print());
    }

    @Test
    public void testEval() {
        Expression num = new Number(5);
        assertEquals(5, num.eval(new HashMap<>()));
    }

    @Test
    public void testDerivative() {
        Expression num = new Number(5);
        assertEquals("0", num.derivative("x").print());
    }
}