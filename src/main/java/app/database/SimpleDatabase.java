package app.database;

import app.messages.Message;

import java.util.List;

public class SimpleDatabase implements DataBase {


    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public void addUser(String username) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public String getUserPassword(String username) {
        return null;
    }

    @Override
    public void setUserPassword(String username) {

    }

    @Override
    public List<Message> getUserMailbox(String username) {
        return null;
    }
}
