package main.java.app;

import main.java.app.messages.Message;

import java.util.List;

/**
 * This interface holds all basic functions for a messenger. It is very good.
 *
 * @author LorenzHeinrich, DominicHauff
 * @version 1.0
 */
public interface Messenger {

    /**
     * This method provides functionality for creating a new user account for this messaging application
     * Note that the username functions as a unique identifier thus there cannot be another account with the same
     * username.
     *
     * @param username the username for the new account
     * @param password the password fo the account
     * @return {@code true} if the account got created successfully, {@code false} else.
     */
    boolean create(String username, String password);
    boolean login(String username, String password);
    void logout();
    boolean send(String receiver, String message);
    void delete(int index);
    List<Message> list();
    List<Message> listUnread();
    Message read(int index);
}
