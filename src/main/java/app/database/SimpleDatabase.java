package app.database;

import app.messages.Mailbox;
import app.util.SimpleFileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class SimpleDatabase implements DataBase {

    private static final String DEFAULT_USER_DIR = "src/main/resources/users";
    private final File usersDir;

    public SimpleDatabase(String pathToUsersDir) {
        this.usersDir = new File(pathToUsersDir);
        if (!usersDir.exists()) {
            try {
                Files.createDirectory(usersDir.toPath());
            } catch (IOException e) {
                System.err.println("cannot create directory ~/resources/users");
                throw new IllegalStateException();
            }
        }
    }

    public SimpleDatabase() {
        this(DEFAULT_USER_DIR);
    }

    @Override
    public boolean userExists(String username) {
        return Arrays.stream(Objects.requireNonNull(usersDir.listFiles()))
                .anyMatch(file -> file.getName().equals(username));
    }

    @Override
    public void addUser(String username) {
        if (!SimpleFileManager.createDir(usersDir.getPath() + "/" + username)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void deleteUser(String username) {
        File file = new File(usersDir.getPath() + "/" + username);
        boolean deleteDir = SimpleFileManager.deleteFile(file.getPath());
        boolean emptyDir = SimpleFileManager.emptyDirectory(file);
        if (!(emptyDir && deleteDir)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String getUserPassword(String username) {
        if (!this.userExists(username)) return null;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(DEFAULT_USER_DIR + "/" + username + "/password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return scanner.nextLine();
    }

    @Override
    public void setUserPassword(String username, String password) {
        File userDir = SimpleFileManager.getOrCreateFile(this.usersDir + "/" + username + "/password");
        if (userDir == null) throw new IllegalStateException();
        boolean success = SimpleFileManager.writeContent(userDir.getPath(), password);
        if (!success) throw new IllegalStateException();
    }

    @Override
    public Mailbox getUserMailbox(String username) {

        return null;
    }
}
