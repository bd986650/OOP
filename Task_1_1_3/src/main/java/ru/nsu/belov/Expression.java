package ru.nsu.belov;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression {
    /**
     * Вывод выражения в виде строки
     *
     * @return string
     */
    public abstract String print();

    /**
     * Вычисление выражения при заданных значениях переменных
     *
     * @param variables integer
     *
     * @return int
     */
    public abstract int eval(Map<String, Integer> variables);

    /**
     * Дифференцирование по заданной переменной
     *
     * @param variable string
     *
     * @return Expression
     */
    public abstract Expression derivative(String variable);

    /**
     * eval
     *
     * @param vars string
     *
     * @return int
     */
    public int eval(String vars) {
        Map<String, Integer> variables = new HashMap<>();
        String[] assignments = vars.split(";");
        for (String assignment : assignments) {
            String[] pair = assignment.split("=");
            variables.put(pair[0].trim(), Integer.parseInt(pair[1].trim()));
        }
        return eval(variables);
    }

}
