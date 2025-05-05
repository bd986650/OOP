package ru.nsu.belov;

import java.io.File;

public class RepositoryManager {
    private static final String REPOSITORIES_PATH = "./reps";

    public void cleanRepositories() {
        File repositoriesDir = new File(REPOSITORIES_PATH);
        if (repositoriesDir.exists()) {
            deleteDirectory(repositoriesDir);
        }
        repositoriesDir.mkdirs();
    }

    private boolean deleteDirectory(File dir) {
        if (!dir.exists()) {
            return false;
        }

        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDirectory(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
} 