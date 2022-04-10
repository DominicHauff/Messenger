package app.messages;

import java.io.File;
import java.util.List;

public class SimpleMailbox implements Mailbox {

    private final File directory;

    public SimpleMailbox(File directory) {
        this.directory = directory;
    }

    @Override
    public void receive(Message message) {

    }

    @Override
    public Message get(int index) {
        return null;
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public void delete(int index) {

    }
}
