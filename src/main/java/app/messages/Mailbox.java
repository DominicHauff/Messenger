package app.messages;

import java.util.List;

public interface Mailbox {

    void receive(Message message);

    Message get(int index);

    List<Message> getAll();

    void delete(int index);

}
