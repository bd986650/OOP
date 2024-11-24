package ru.nsu.belov;

import java.util.Map;

class Variable implements Expression {
    final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new RuntimeException("Variable " + name + " not defined.");
        }
    }

    @Override
    public Expression derivative(String variable) {
        return variable.equals(name) ? new Number(1) : new Number(0);
    }
}
