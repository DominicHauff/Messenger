package app.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class SimpleFileManager {

    public static boolean createDir(String path) {
        File newDir = new File(path);
        if (newDir.exists()) {
            return false;
        }
        try {
            Files.createDirectory(newDir.toPath());
            return true;
        } catch (IOException ioException) {
            System.err.println("cannot create directory '" + path + "'! Because: " + ioException);
            return false;
        }
    }

    public static File getOrCreateFile(String path) {
        File file = new File(path);
        if (file.exists()) return file;
        if (!createFile(path)) return null;
        return file;
    }

    public static boolean createFile(String path) {
        File fileToCreate = new File(path);
        if (fileToCreate.exists()) {
            return false;
        }
        try {
            Files.createFile(fileToCreate.toPath());
            return fileToCreate.setWritable(true);
        } catch (IOException ioException) {
            System.err.println("cannot create file '" + path + "'!\n Because: " + ioException);
            return false;
        }
    }

    public static boolean deleteFile(String path) {
        File fileToDelete = new File(path);
        if (!fileToDelete.exists()) {
            System.err.println("cannot delete file " + path);
            return false;
        }
        try {
            Files.delete(fileToDelete.toPath());
            return true;
        } catch (IOException ioException) {
            System.err.println("cannot delete file '" + path + "'! Because: " + ioException);
            return false;
        }
    }

    public static boolean writeContent(String path, String content) {
        File fileToWrite = new File(path);
        if (!fileToWrite.exists() || !fileToWrite.canWrite()) {
            return false;
        }
        try {
            Files.writeString(fileToWrite.toPath(), content);
            return true;
        } catch (IOException ioException) {
            System.err.println("cannot write to file '" + path + "'! Because: " + ioException);
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
            } catch (IOException ioException) {
                System.err.println("cannot delete file '" + subFile.getPath() + "'! Because: " + ioException);
                return false;
            }
        }
        return true;
    }
}
