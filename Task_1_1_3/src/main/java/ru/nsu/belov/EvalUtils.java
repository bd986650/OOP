package ru.nsu.belov;

import java.util.HashMap;
import java.util.Map;

public class EvalUtils {
    public static Map<String, Integer> parseVariables(String vars) {
        Map<String, Integer> variables = new HashMap<>();
        String[] assignments = vars.split(";");
        for (String assignment : assignments) {
            String[] pair = assignment.split("=");
            variables.put(pair[0].trim(), Integer.parseInt(pair[1].trim()));
        }
        return variables;
    }
}
