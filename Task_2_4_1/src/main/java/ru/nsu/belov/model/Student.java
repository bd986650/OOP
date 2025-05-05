package ru.nsu.belov.model;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String id;
    private final String username;
    private final String repository;
    private final String group;

    public Student(String id, String username, String repository, String group) {
        this.id = id;
        this.username = username;
        this.repository = repository;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRepository() {
        return repository;
    }

    public String getGroup() {
        return group;
    }

    public static Map<String, Student> fromConfig(Map<String, Map<String, Object>> config) {
        Map<String, Student> students = new HashMap<>();
        config.forEach((id, data) -> 
            students.put(id, new Student(
                id,
                (String) data.get("username"),
                (String) data.get("repository"),
                (String) data.get("group")
            ))
        );
        return students;
    }
} 