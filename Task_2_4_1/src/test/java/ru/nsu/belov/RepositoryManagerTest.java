package ru.nsu.belov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryManagerTest {
    private RepositoryManager repositoryManager;
    private File repositoriesDir;

    @BeforeEach
    void setUp() {
        repositoryManager = new RepositoryManager();
        repositoriesDir = new File("./reps");
    }

    @Test
    void testCleanRepositoriesWhenDirectoryDoesNotExist() {
        if (repositoriesDir.exists()) {
            repositoryManager.cleanRepositories();
        }

        assertFalse(repositoriesDir.exists());

        repositoryManager.cleanRepositories();

        assertTrue(repositoriesDir.exists());
        assertTrue(repositoriesDir.isDirectory());
    }

    @Test
    void testCleanRepositoriesWhenDirectoryExists() throws IOException {
        File studentDir = new File(repositoriesDir, "testStudent");
        File taskDir = new File(studentDir, "Task_1_1_1");
        File testFile = new File(taskDir, "test.txt");

        taskDir.mkdirs();
        testFile.createNewFile();

        assertTrue(studentDir.exists());
        assertTrue(taskDir.exists());
        assertTrue(testFile.exists());

        repositoryManager.cleanRepositories();

        assertFalse(studentDir.exists());
        assertFalse(taskDir.exists());
        assertFalse(testFile.exists());
        assertTrue(repositoriesDir.exists());
        assertTrue(repositoriesDir.isDirectory());
        assertEquals(0, repositoriesDir.list().length);
    }

    @Test
    void testCleanRepositoriesWithNestedDirectories() throws IOException {
        File studentDir = new File(repositoriesDir, "testStudent");
        File taskDir = new File(studentDir, "Task_1_1_1");
        File nestedDir = new File(taskDir, "nested");
        File testFile1 = new File(taskDir, "test1.txt");
        File testFile2 = new File(nestedDir, "test2.txt");

        nestedDir.mkdirs();
        testFile1.createNewFile();
        testFile2.createNewFile();

        assertTrue(studentDir.exists());
        assertTrue(taskDir.exists());
        assertTrue(nestedDir.exists());
        assertTrue(testFile1.exists());
        assertTrue(testFile2.exists());

        repositoryManager.cleanRepositories();

        assertFalse(studentDir.exists());
        assertFalse(taskDir.exists());
        assertFalse(nestedDir.exists());
        assertFalse(testFile1.exists());
        assertFalse(testFile2.exists());
        assertTrue(repositoriesDir.exists());
        assertTrue(repositoriesDir.isDirectory());
        assertEquals(0, repositoriesDir.list().length);
    }
} 