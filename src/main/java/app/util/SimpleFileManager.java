package app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class SimpleFileManager {

    public static boolean createDir(String path) {
        File newUser = new File(path);
        if (newUser.exists()) {
            return false;
        }
        return newUser.mkdir();
    }

    public static boolean createFile(String path) {
        File fileToCreate = new File(path);
        if (fileToCreate.exists()) {
            return false;
        }
        try {
            return fileToCreate.createNewFile() && fileToCreate.setWritable(true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeContent(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean emptyDirectory(File directory) {
        if (directory == null) return false;
        File[] subFiles;
        if ((subFiles = directory.listFiles()) == null) return false;
        for (File subFile : subFiles) {
            if (subFile.isDirectory()) {
                if (!emptyDirectory(subFile)) return false;
            }
            try {
                Files.delete(subFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
