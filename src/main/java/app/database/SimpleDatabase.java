package app.database;

import app.messages.Mailbox;
import app.messages.SimpleMailbox;
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
                System.err.println("cannot create directory " + pathToUsersDir + "because: " + e);
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
        boolean emptyDir = SimpleFileManager.emptyDirectory(file);
        boolean deleteDir = SimpleFileManager.deleteFile(file.getPath());
        if (!(emptyDir && deleteDir)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String getUserPassword(String username) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(usersDir.getPath() + "/" + username + "/password"));
        } catch (FileNotFoundException e) {
            System.err.println("password is not set yet (or user does not exist xD)");
            return null;
        }
        String password = scanner.nextLine();
        scanner.close();
        return password;
    }

    @Override
    public void setUserPassword(String username, String password) {
        File passwordFile = SimpleFileManager.getOrCreateFile(this.usersDir + "/" + username + "/password");
        if (passwordFile == null) throw new IllegalStateException();
        boolean success = SimpleFileManager.writeContent(passwordFile.getPath(), password);
        if (!success) throw new IllegalStateException();
    }

    @Override
    public Mailbox getUserMailbox(String username) {
        if (!this.userExists(username)) return null;
        File mailbox = SimpleFileManager.getOrCreateFile(this.usersDir.getPath() + "/" + username + "/mailbox");
        if (mailbox == null) return null;
        return new SimpleMailbox(mailbox);
    }
}
