package ru.nsu.belov.config;

import java.util.Arrays;
import java.util.List;

public class TasksConfig {
    private final List<String> tasks;

    public TasksConfig() {
        this.tasks = Arrays.asList(
            "Task_1_1_1",
            "Task_1_1_2",
            "Task_1_1_3",
            "Task_1_2_1",
            "Task_1_2_2",
            "Task_1_3_1",
            "Task_1_4_1",
            "Task_1_5_1"
        );
    }

    public List<String> getTasks() {
        return tasks;
    }
} 