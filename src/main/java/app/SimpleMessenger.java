package app;

import app.messages.Message;

import java.util.List;

public class SimpleMessenger implements Messenger {
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
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean send(String receiver, String message) {
        return false;
    }

    @Override
    public void delete(int index) {

    }

    @Override
    public List<Message> list() {
        return null;
    }

    @Override
    public List<Message> listUnread() {
        return null;
    }

    @Override
    public Message read(int index) {
        return null;
    }
}
