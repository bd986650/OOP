package ru.nsu.belov;

import java.util.Map;

/**
 * add.
 */
public class Add extends Expression {
    final Expression left;
    final Expression right;

    /**
     * add.
     *
     * @param left  Expr.
     * @param right Expr.
     */
    public Add(Expression left, Expression right) {
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
        return "(" + left.print() + "+" + right.print() + ")";
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
        return left.eval(variables) + right.eval(variables);
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
        return new Add(left.derivative(variable), right.derivative(variable));
    }
}
