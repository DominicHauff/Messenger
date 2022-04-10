package app.database;

import app.messages.Mailbox;
import app.messages.Message;

import java.util.List;

public interface DataBase {

    boolean userExists(String username);

    void addUser(String username);

    void deleteUser(String username);

    String getUserPassword(String username);

    void setUserPassword(String username, String password);

    Mailbox getUserMailbox(String username);

}
