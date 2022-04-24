package app.messages;

import app.util.SimpleFileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleMailbox implements Mailbox {

    private final File userDirectory;

    public SimpleMailbox(File userDirectory) {
        this.userDirectory = userDirectory;
    }

    @Override
    public void receive(Message message) {
        StringBuilder messageLoad = new StringBuilder();
        messageLoad.append(message.read()).append("\n");
        messageLoad.append(message.sender()).append("\n");
        messageLoad.append(message.timeStamp()).append("\n");
        messageLoad.append(message.content()).append("\n");
        String file = userDirectory.getPath() + "/" + (getMaxIndex() + 1);
        SimpleFileManager.createFile(file);
        SimpleFileManager.writeContent(file, messageLoad.toString());
    }

    @Override
    public Message get(int index) {
        return index >= this.getAll().size() || index < 0 ? null : this.getAll().get(index);
    }

    @Override
    public List<Message> getAll() {
        return Arrays.stream(Objects.requireNonNull(userDirectory.listFiles()))
                .map(SimpleMailbox::fileToMessage).sorted().collect(Collectors.toList());
    }

    @Override
    public void delete(int index) {
        Message messageToDelete = this.get(index);
        if (messageToDelete == null) return;
        SimpleFileManager.deleteFile(Arrays.stream(Objects.requireNonNull(userDirectory.listFiles()))
                .filter(file -> fileToMessage(file).equals(messageToDelete))
                .findFirst().orElseThrow().getPath());
    }

    private int getMaxIndex() {
        try {
            return Files.list(userDirectory.toPath())
                    .map(Path::toFile)
                    .map(File::getName)
                    .map(Integer::parseInt)
                    .max(Integer::compare)
                    .orElse(0);

        } catch (IOException e) {
            System.err.println("cannot find max index, because: " + e);
            throw new IllegalStateException();
        }
    }

    private static Message fileToMessage(File file) {
        try {
            List<String> messageLoad = Files.readAllLines(file.toPath());
            boolean read = Boolean.parseBoolean(messageLoad.get(0));
            String sender = messageLoad.get(1);
            LocalDateTime timeStamp = LocalDateTime.parse(messageLoad.get(2));
            StringBuilder content = new StringBuilder();
            for (int i = 3; i < messageLoad.size(); i++) {
                content.append(messageLoad.get(i)).append("\n");
            }
            return new Message(timeStamp, sender, content.toString().trim(), read);
        } catch (IOException e) {
            System.err.println("cannot find file, because: " + e);
            throw new IllegalStateException();
        }
    }
}
