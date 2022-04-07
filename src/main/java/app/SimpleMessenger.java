package app;

import app.database.DataBase;
import app.messages.Message;

import java.util.List;

public class SimpleMessenger implements Messenger {
    private final DataBase dataBase;
    private String loggedInUser;

    public SimpleMessenger(DataBase dataBase) {
        this.dataBase = dataBase;
        this.loggedInUser = null;
    }

    /**
     * This method provides functionality for creating a new user account for this messaging application
     * Note that the username functions as a unique identifier thus there cannot be another account with the same
     * username.
     *
     * @param username the username for the new account
     * @param password the password fo the account
     * @return {@code true} if the account got created successfully, {@code false} else.
     */
    @Override
    public boolean create(String username, String password) {
        if (username == null || password == null) return false;

        if (this.dataBase.userExists(username)) {
            this.dataBase.addUser(username);
            return true;
        }

        return false;
    }

    /**
     * This method provides functionality for logging into an already existing user account.
     * @param username the username for the account to log into
     * @param password the associated password for this account
     * @return {@code true} if the provided args match an existing account, {@code false} else
     */
    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        if (!this.dataBase.userExists(username)) return false;
        if (!this.dataBase.getUserPassword(username).equals(password)) return false;
        if (this.loggedInUser != null) return false;
        this.loggedInUser = username;
        return true;
    }

    /**
     * This method logs out the currently logged in account. If no user is logged in, this method does nothing.
     */
    @Override
    public void logout() {
        this.loggedInUser = null;
    }

    /**
     * This method can be used to send messages to other users.
     *
     * @param receiver the user that shall receive this method
     * @param message the message to send as a string
     * @return {@code true} if the message was transferred successfully, {@code false} else
     */
    @Override
    public boolean send(String receiver, String message) {
        return false;
    }

    /**
     * This method allows users to permanently delete received messages in their mailbox.
     *
     * @param index The message index inside their mailbox
     */
    @Override
    public void delete(int index) {

    }

    /**
     * This method creates a list of all messages inside the mailbox of a user.
     *
     * @return a {@link List} of all messages inside the mailbox of the logged-in user
     */
    @Override
    public List<Message> list() {
        return null;
    }

    /**
     * This method creates a list of all unread messages inside the mailbox of a user.
     *
     * @return a {@link List} of all unread messages inside the mailbox of the logged-in user
     */
    @Override
    public List<Message> listUnread() {
        return null;
    }

    /**
     * This method allows a logged-in user to read a single message stored in their mailbox.
     *
     * @param index the index of the message inside their mailbox the user wants to read
     * @return the message selected by the provided index
     */
    @Override
    public Message read(int index) {
        return null;
    }
}
