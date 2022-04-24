package app.database;

import app.util.SimpleFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDatabaseTest {
    @TempDir
    static File tmpDir;
    static SimpleDatabase database;
    private File file;

    @BeforeEach
    void setUp() {
        SimpleFileManager.emptyDirectory(tmpDir);
        database = new SimpleDatabase(tmpDir.getPath());
    }

    @Test
    void userExists() {
        assertFalse(database.userExists("lenz"));
        database.addUser("lenz");
        assertTrue(database.userExists("lenz"));
    }

    @Test
    void addUser() {
        File user1 = new File(tmpDir.getPath() + "/ronz");
        database.addUser("ronz");
        assertTrue(user1.exists());
        assertTrue(user1.isDirectory());
        assertThrows(IllegalStateException.class, () -> database.addUser("ronz"));
    }

    @Test
    void deleteUser() {
        database.addUser("snons");
        assertTrue(database.userExists("snons"));
        database.deleteUser("snons");
        assertFalse(database.userExists("snons"));
    }

    @Test
    void getUserPassword() {
        database.addUser("theRealNic");
        assertNull(database.getUserPassword("theRealNic"));
        database.setUserPassword("theRealNic", "password");
        assertEquals("password", database.getUserPassword("theRealNic"));
        assertDoesNotThrow(
                () -> {
                    file = new File(tmpDir.getPath() + "/theRealNic/password");
                    assertAll(
                            () -> assertTrue(file.exists()),
                            () -> assertTrue(file.isFile()),
                            () -> assertEquals("password", Files.readAllLines(file.toPath()).get(0))
                    );
                }
        );
    }

    @Test
    void setUserPassword() {
        database.addUser("lorax");
        database.setUserPassword("lorax", "iLoveTrees69");
        File file = new File(tmpDir.getPath() + "/lorax/password");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertDoesNotThrow(
                () -> {
                    List<String> s1 = Files.readAllLines(file.toPath());
                    assertAll(
                            () -> assertEquals(1, s1.size()),
                            () -> assertEquals("iLoveTrees69", s1.get(0))
                    );
                    database.setUserPassword("lorax", "newPassword");
                    List<String> s2 = Files.readAllLines(file.toPath());
                    assertEquals("newPassword", database.getUserPassword("lorax"));
                    assertAll(
                            () -> assertEquals(1, s2.size()),
                            () -> assertEquals("newPassword", s2.get(0))
                    );
                }
        );
    }

    @Test
    void getUserMailbox() {
        database.addUser("nil");
        database.setUserPassword("nil", "shrek");
        assertNotNull(database.getUserMailbox("nil"));
    }
}