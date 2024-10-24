package ru.nsu.belov;

import java.util.Map;

/**
 * class Number.
 */
class Number extends Expression {
    private final int value;

    /**
     * number constructor.
     *
     * @param value int.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * print.
     *
     * @return string.
     */
    @Override
    public String print() {
        return Integer.toString(value);
    }

    /**
     * eval.
     *
     * @param variables integer.
     *
     * @return int.
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    /**
     * derivative.
     *
     * @param variable string.
     *
     * @return Expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }
}
