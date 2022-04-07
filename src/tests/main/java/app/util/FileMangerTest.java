package app.util;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

import static app.util.FileManager.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileMangerTest {
    private static final String TMP_DIR = "src/tests/resources/tmp";

    @Test
    @Order(1)
    void testCreateDir() {
        assertTrue(createDir(TMP_DIR + "/t1"));
        File f1 = new File("src/tests/resources/tmp/t1");
        assertTrue(f1.exists());
        assertTrue(f1.isDirectory());
        assertEquals(0, Objects.requireNonNull(f1.listFiles()).length);
    }

    @Test
    @Order(2)
    void testCreateFile() {
        assertTrue(createFile(TMP_DIR + "/t1/password"));
        File f1 = new File(TMP_DIR + "/t1/password");
        assertTrue(f1.exists());
        assertTrue(f1.isFile());
        assertTrue(f1.canRead());
        assertTrue(f1.canWrite());
    }

    @Test
    @Order(3)
    void testWriteContent() throws FileNotFoundException {
        assertTrue(writeContent(TMP_DIR + "/t1/password", "test string"));

        File file = new File(TMP_DIR + "/t1/password");
        assertTrue(file.exists());

        Scanner scanner = new Scanner(file);
        assertEquals("test string", scanner.nextLine());
    }

    @Test
    @Order(4)
    void testDeleteFile() {
        assertTrue(createDir(TMP_DIR + "/clean"));
        assertTrue(createFile(TMP_DIR + "/clean1"));
        assertTrue(createFile(TMP_DIR + "/clean2"));
        assertTrue(createFile(TMP_DIR + "/clean/f1"));
        assertTrue(writeContent(TMP_DIR + "/clean/f1", "test"));
        //assertTrue(emptyDirectory(new File(TMP_DIR)));
        //assertEquals(0, Objects.requireNonNull(new File(TMP_DIR).listFiles()).length);
    }

    @BeforeAll
    @Test
    static void clean() {
        emptyDirectory(new File(TMP_DIR));
    }
}
