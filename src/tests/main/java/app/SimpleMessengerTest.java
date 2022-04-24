package app;

import app.database.SimpleDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMessengerTest {
    SimpleMessenger simpleMessenger = new SimpleMessenger(new SimpleDatabase());

    @Test
    void createUsers() {
        assertAll(
                () -> assertTrue(simpleMessenger.create("lorenz", "123")),
                () -> assertTrue(simpleMessenger.create("dominic", "321"))
        );
    }

    @Test
    void sendMessages() {

    }
}