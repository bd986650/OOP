package ru.nsu.belov;

import java.util.Map;

/**
 * sub.
 */
class Sub extends Expression {
    final Expression left;
    final Expression right;

    /**
     * sub.
     *
     * @param left  expr.
     * @param right expr.
     */
    public Sub(Expression left, Expression right) {
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
        return "(" + left.print() + "-" + right.print() + ")";
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
        return left.eval(variables) - right.eval(variables);
    }

    /**
     * derivative.
     *
     * @param variable string.
     *
     * @return expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }
}
