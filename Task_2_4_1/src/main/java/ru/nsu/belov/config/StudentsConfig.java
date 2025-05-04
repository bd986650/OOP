package ru.nsu.belov.config;

import java.util.HashMap;
import java.util.Map;

public class StudentsConfig {
    private final Map<String, Map<String, Object>> students;

    public StudentsConfig() {
        this.students = new HashMap<>();
        
        Map<String, Object> danilBelov = new HashMap<>();
        danilBelov.put("username", "Danil Belov");
        danilBelov.put("repository", "https://github.com/bd986650/OOP");
        danilBelov.put("group", "23217");
        students.put("DanilBelov", danilBelov);

        Map<String, Object> artemMikiyanskii = new HashMap<>();
        artemMikiyanskii.put("username", "Artem Mikiyanskii");
        artemMikiyanskii.put("repository", "https://github.com/Artem-MilkyWay/OOP");
        artemMikiyanskii.put("group", "23217");
        students.put("ArtemMikiyanskii", artemMikiyanskii);

        Map<String, Object> nikitaAbramkin = new HashMap<>();
        nikitaAbramkin.put("username", "Nikita Abramkin");
        nikitaAbramkin.put("repository", "https://github.com/Bu1nk/OOP");
        nikitaAbramkin.put("group", "23217");
        students.put("NikitaAbramkin", nikitaAbramkin);

        Map<String, Object> kolyaPronin = new HashMap<>();
        kolyaPronin.put("username", "Kolya Pronin");
        kolyaPronin.put("repository", "https://github.com/KolyaPronin/OOP");
        kolyaPronin.put("group", "23217");
        students.put("KolyaPronin", kolyaPronin);
    }

    public Map<String, Map<String, Object>> getStudents() {
        return students;
    }
} 