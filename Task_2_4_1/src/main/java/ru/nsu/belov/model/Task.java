package ru.nsu.belov.model;

import java.util.List;
import java.util.stream.Collectors;

public class Task {
    private final String id;

    public Task(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static List<Task> fromConfig(List<String> config) {
        return config.stream()
            .map(Task::new)
            .collect(Collectors.toList());
    }
} 