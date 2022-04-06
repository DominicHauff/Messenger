package app;

import app.messages.Message;

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

    /**
     * This method provides functionality for logging into an already existing user account.
     * @param username the username for the account to log into
     * @param password the associated password for this account
     * @return {@code true} if the provided args match an existing account, {@code false} else
     */
    boolean login(String username, String password);

    /**
     * This method logs out the currently logged in account. If no user is logged in, this method does nothing.
     */
    void logout();

    /**
     * This method can be used to send messages to other users.
     *
     * @param receiver the user that shall receive this method
     * @param message the message to send as a string
     * @return {@code true} if the message was transferred successfully, {@code false} else
     */
    boolean send(String receiver, String message);

    /**
     * This method allows users to permanently delete received messages in their mailbox.
     *
     * @param index The message index inside their mailbox
     */
    void delete(int index);

    /**
     * This method creates a list of all messages inside the mailbox of a user.
     *
     * @return a {@link List} of all messages inside the mailbox of the logged-in user
     */
    List<Message> list();

    /**
     * This method creates a list of all unread messages inside the mailbox of a user.
     *
     * @return a {@link List} of all unread messages inside the mailbox of the logged-in user
     */
    List<Message> listUnread();

    /**
     * This method allows a logged-in user to read a single message stored in their mailbox.
     *
     * @param index the index of the message inside their mailbox the user wants to read
     * @return the message selected by the provided index
     */
    Message read(int index);
}
