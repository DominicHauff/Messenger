package app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

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
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
