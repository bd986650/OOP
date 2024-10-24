package ru.nsu.belov;

import java.util.Map;

/**
 * Mul.
 */
public class Mul extends Expression {
    final Expression left;
    final Expression right;

    /**
     * Mul.
     *
     * @param left expr.
     * @param right expr.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * print.
     *
     * @return string.
     */
    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
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
        return left.eval(variables) * right.eval(variables);
    }

    /**
     * derivative.
     *
     * @param variable string.
     *
     * @return expr.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }
}
