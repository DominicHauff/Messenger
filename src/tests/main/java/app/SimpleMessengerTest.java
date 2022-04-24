package app;

import app.database.SimpleDatabase;
import app.util.SimpleFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMessengerTest {

    @TempDir
    static File tmpDir;
    static SimpleMessenger simpleMessenger;

    @BeforeEach
    void init() {
        SimpleFileManager.emptyDirectory(tmpDir);
        simpleMessenger = new SimpleMessenger(new SimpleDatabase(tmpDir.getPath()));
    }

    @Test
    void createUsers() {
        assertAll(
                () -> assertTrue(simpleMessenger.create("lorenz", "123")),
                () -> assertTrue(simpleMessenger.create("dominic", "321"))
        );
    }

    @Test
    void sendMessages() {
        simpleMessenger.create("lorenz", "123");
        simpleMessenger.create("dominic", "321");

        assertFalse(simpleMessenger.send("dominic", "hallo"));
        assertTrue(simpleMessenger.login("lorenz", "123"));
        assertTrue(simpleMessenger.send("dominic", "hallo"));
        simpleMessenger.logout();
        assertFalse(simpleMessenger.send("lorenz", "ok"));
        assertTrue(simpleMessenger.login("dominic", "321"));
        assertTrue(simpleMessenger.send("lorenz", "ok"));
    }


}