package ru.nsu.belov;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.belov.model.Student;
import ru.nsu.belov.model.Task;
import ru.nsu.belov.config.StudentsConfig;
import ru.nsu.belov.config.TasksConfig;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private GroovyScriptEngine engine;
    private Binding binding;
    private StudentsConfig studentsConfig;
    private TasksConfig tasksConfig;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("exit".getBytes());
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() throws IOException {
        try {
            engine = new GroovyScriptEngine("src/main/java/ru/nsu/belov/scripts");
            binding = new Binding();
            studentsConfig = new StudentsConfig();
            tasksConfig = new TasksConfig();
            System.setOut(new PrintStream(outContent));
            System.setIn(inContent);
        } catch (IOException e) {
            fail("Failed to initialize GroovyScriptEngine: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testTaskProcessing() {
        try {
            Map<String, Student> students = Student.fromConfig(studentsConfig.getStudents());
            List<Task> tasks = Task.fromConfig(tasksConfig.getTasks());

            binding.setVariable("students", students);
            binding.setVariable("tasks", tasks);

            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> results = 
                (LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>) 
                engine.run("TaskProcessor.groovy", binding);

            for (String studentId : students.keySet()) {
                assertTrue(new File("./reps/" + studentId).exists(), 
                    "Directory for student " + studentId + " should exist");
            }

            assertNotNull(results, "Results should not be null");
            
            for (Task task : tasks) {
                assertTrue(results.containsKey(task.getId()), 
                    "Results should contain task: " + task.getId());
            }

            LinkedHashMap<String, LinkedHashMap<String, String>> taskResults = results.get("Task_1_1_1");
            assertNotNull(taskResults, "Task results should not be null");
            
            for (String studentId : students.keySet()) {
                assertTrue(taskResults.containsKey(studentId), 
                    "Task results should contain student: " + studentId);
            }

            File reportFile = new File("./result.html");
            assertTrue(reportFile.exists(), "HTML report should be created");
            
            StringBuilder html = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(reportFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    html.append(line);
                }
            }

            String content = html.toString();
            
            assertTrue(content.contains("<!DOCTYPE html>"), "Report should contain HTML doctype");
            assertTrue(content.contains("<html"), "Report should contain HTML tag");
            assertTrue(content.contains("<head>"), "Report should contain head section");
            assertTrue(content.contains("<body>"), "Report should contain body section");
            
            for (Task task : tasks) {
                assertTrue(content.contains(task.getId()), 
                    "Report should contain task: " + task.getId());
            }
            
        } catch (Exception e) {
            fail("Failed to process tasks: " + e.getMessage());
        }
    }

    @Test
    void testMainHelpCommand() {
        String input = "help\nexit";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("ex / exit"));
        assertTrue(output.contains("help"));
        assertTrue(output.contains("html"));
    }

    @Test
    void testMainHtmlCommand() {
        String input = "html\nexit";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("HTML report generated successfully!"));
    }

    @Test
    void testMainExitCommand() {
        String input = "exit";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("ex / exit"));
        assertTrue(output.contains("help"));
        assertTrue(output.contains("html"));
    }
}
