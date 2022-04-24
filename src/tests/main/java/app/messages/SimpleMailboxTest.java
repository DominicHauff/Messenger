package app.messages;

import app.util.SimpleFileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMailboxTest {

    @TempDir
    static File tmpDir;
    static SimpleMailbox mailbox;
    static LocalDateTime timeStamp;
    static Message m1;
    static Message m2;
    static Message m3;
    static Message m4;
    static Message m5;

    @BeforeEach
    void init() {
        SimpleFileManager.emptyDirectory(tmpDir);
        mailbox = new SimpleMailbox(tmpDir);
        timeStamp = LocalDateTime.now();
        m1 = new Message(timeStamp.plus(1, ChronoUnit.SECONDS), "s1", "message 1", false);
        m2 = new Message(timeStamp.plus(2, ChronoUnit.SECONDS), "s2", "message 2", false);
        m3 = new Message(timeStamp.plus(3, ChronoUnit.SECONDS), "s3", "message 3", false);
        m4 = new Message(timeStamp.plus(4, ChronoUnit.SECONDS), "s4", "message 4", false);
        m5 = new Message(timeStamp.plus(5, ChronoUnit.SECONDS), "s5", "message 5", false);
    }

    @Test
    void receive() {
        LocalDateTime timeStamp = LocalDateTime.now();
        Message message = new Message(timeStamp, "lorenz", "hallo mein Name ist Günther", false);
        mailbox.receive(message);
        Path path = new File(tmpDir.getPath() + "/1").toPath();
        assertTrue(Files.exists(path));
        assertDoesNotThrow(
                () -> {
                    List<String> messageLoad = Files.readAllLines(path);
                    String read = "false";
                    String sender = "lorenz";
                    String content = "hallo mein Name ist Günther";
                    assertEquals(read, messageLoad.get(0));
                    assertEquals(sender, messageLoad.get(1));
                    assertEquals(timeStamp.toString(), messageLoad.get(2));
                    assertEquals(content, messageLoad.get(3));
                }
        );
    }

    @Test
    void get() {
        mailbox.receive(m1);
        mailbox.receive(m2);

        assertEquals("message 1", mailbox.get(0).content());
        assertEquals("message 2", mailbox.get(1).content());
    }

    @Test
    void getAll() {
        mailbox.receive(m1);
        mailbox.receive(m2);
        mailbox.receive(m3);
        mailbox.receive(m4);
        mailbox.receive(m5);

        assertEquals(5, mailbox.getAll().size());
        assertEquals(List.of(m1, m2, m3, m4, m5), mailbox.getAll());
    }

    @Test
    void delete() {
        mailbox.receive(m1);
        assertTrue(new File(tmpDir.getPath() + "/1").exists());
        mailbox.delete(0);
        assertFalse(new File(tmpDir.getPath() + "/1").exists());
    }

    @Test
    void writeNewLine() {
        SimpleFileManager.createFile(tmpDir.getPath() + "/gaming");
        File gaming = new File(tmpDir.getPath() + "/gaming");

        SimpleFileManager.writeContent(gaming.getPath(), "gaming\nmy\nass\nhurts");
        assertDoesNotThrow(
                () -> assertEquals(4, Files.readAllLines(gaming.toPath()).size())
        );
    }
}