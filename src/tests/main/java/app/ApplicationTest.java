package app;

import app.database.SimpleDatabase;
import app.messages.Message;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    /**
     * This test embodies the pure soul of junit testing...
     */
    @Test
    void aTest() {
        assertTrue(true); // very important test
    }

    @Test
    @Disabled("not ready yet")
    void basicInteraction() {
        Messenger messenger = new SimpleMessenger(new SimpleDatabase());

        assertTrue(messenger.create("lorenz", "password123"));
        assertTrue(messenger.create("dominic", "password123"));

        assertTrue(messenger.login("lorenz", "password123"));
        assertTrue(messenger.send("dominic", "we've been trying to reach you about your car's extended warranty"));
        assertTrue(messenger.send("dominic", "and it went like... "));
        messenger.logout();
        assertTrue(messenger.login("dominic", "password123"));
        List<Message> mailbox = messenger.listUnread();
        assertEquals(2, mailbox.size());
        assertEquals("lorenz", mailbox.get(0).sender());
        assertEquals("lorenz", mailbox.get(1).sender());
        assertEquals("we've been trying to reach you about your car's extended warranty", messenger.read(0).content());
        assertEquals(1, messenger.listUnread().size());
        assertEquals(2, messenger.list().size());
        assertEquals("and it went like... ", messenger.read(0).content());
        assertEquals(0, messenger.listUnread().size());
        assertEquals(2, messenger.list().size());
        messenger.delete(0);
        assertEquals(1, messenger.list().size());
        messenger.logout();
    }
}
