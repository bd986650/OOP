package ru.nsu.belov;

import java.util.Map;

/**
 * Var.
 */
class Variable extends Expression {
    final String name;

    /**
     * variable constructor.
     *
     * @param name string.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * print.
     *
     * @return string.
     */
    @Override
    public String print() {
        return name;
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
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new RuntimeException("Variable " + name + " not defined.");
        }
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
        return variable.equals(name) ? new Number(1) : new Number(0);
    }
}
