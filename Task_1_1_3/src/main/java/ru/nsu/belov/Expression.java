package ru.nsu.belov;

import java.util.Map;

public interface Expression {

    String print();

    int eval(Map<String, Integer> variables);

    Expression derivative(String variable);

    default int eval(String vars) {
        Map<String, Integer> variables = EvalUtils.parseVariables(vars);
        return eval(variables);
    }
}
