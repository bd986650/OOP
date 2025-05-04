package ru.nsu.belov;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import java.util.Scanner;
import java.util.Map;
import java.util.List;
import ru.nsu.belov.model.Student;
import ru.nsu.belov.model.Task;
import ru.nsu.belov.config.StudentsConfig;
import ru.nsu.belov.config.TasksConfig;

public class Main {
    private static void printCommands() {
        String commands = """
                    \"ex / exit\" - stop working
                    \"help\" - calling help
                    \"html\" - generate html result""";
        System.out.println(commands);
    }

    public static void main(String[] args) {
        printCommands();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case "ex":
                case "exit":
                    System.exit(0);
                    break;
                case "help":
                    printCommands();
                    break;
                case "html":
                    try {
                        GroovyScriptEngine engine =
                                new GroovyScriptEngine("src/main/java/ru/nsu/belov/scripts");
                        Binding binding = new Binding();

                        StudentsConfig studentsConfig = new StudentsConfig();
                        TasksConfig tasksConfig = new TasksConfig();

                        Map<String, Student> students = Student.fromConfig(studentsConfig.getStudents());
                        List<Task> tasks = Task.fromConfig(tasksConfig.getTasks());

                        binding.setVariable("students", students);
                        binding.setVariable("tasks", tasks);

                        engine.run("TaskProcessor.groovy", binding);
                        System.out.println("HTML report generated successfully!");
                        printCommands();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    printCommands();
            }
        }
    }
}