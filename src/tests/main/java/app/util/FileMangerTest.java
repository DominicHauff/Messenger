package app.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static app.util.SimpleFileManager.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileMangerTest {

    @TempDir
    static File tmpDir;
    static String pathToTmpDir;

    @BeforeAll
    static void init() {
        System.out.println("-".repeat(40));
        System.out.println("running testfile: " + FileMangerTest.class.getSimpleName());
        pathToTmpDir = tmpDir.getPath();
        System.out.printf("path to tmpDir: %s%n", pathToTmpDir);
    }

    @Test
    @Order(1)
    void testCreateDir() {
        String pathToTestDir = pathToTmpDir + "/t1";
        assertTrue(createDir(pathToTestDir));
        File f1 = new File(pathToTestDir);
        assertTrue(f1.exists());
        assertTrue(f1.isDirectory());
        assertEquals(0, Objects.requireNonNull(f1.listFiles()).length);
        System.out.println("directory /t1 was created successfully");
    }

    @Test
    @Order(2)
    void testCreateFile() {
        assertTrue(createFile(pathToTmpDir + "/t1/password"));
        File f1 = new File(pathToTmpDir + "/t1/password");
        assertTrue(f1.exists());
        assertTrue(f1.isFile());
        assertTrue(f1.canRead());
        assertTrue(f1.canWrite());
        System.out.println("file ~/t1/password was created successfully");
    }

    @Test
    @Order(3)
    void testWriteContent() {
        assertTrue(writeContent(pathToTmpDir + "/t1/password", "test string"));

        File file = new File(pathToTmpDir + "/t1/password");
        assertTrue(file.exists());

        assertDoesNotThrow(() -> {
            List<String> content = Files.readAllLines(file.toPath());
            assertEquals(1, content.size(), "there should only be one line in the file");
            assertEquals("test string", content.get(0), "file content should be 'test string'");
        }, "reading from file /t1/password should not throw");
        System.out.println("write to file ~/t1/password successfully");
    }

    @Test
    @Order(4)
    void testEmptyDirectory() {
        String testDir = pathToTmpDir + "/testDir";
        File file = new File(testDir);
        assertAll(
                () -> assertTrue(createDir(testDir)),
                () -> assertTrue(createFile(testDir + "/f1")),
                () -> assertTrue(createFile(testDir + "/f2")),
                () -> assertTrue(createDir(testDir + "/d1")),
                () -> assertTrue(createFile(testDir + "/d1/f1")),
                () -> assertTrue(writeContent(testDir + "/d1/f1", "test")),
                () -> assertTrue(emptyDirectory(file)),
                () -> assertEquals(0, Objects.requireNonNull(file.listFiles()).length)
        );
    }

    @Test
    void testDeleteFile() {
        String path = pathToTmpDir + "/delete";
        assertAll(
                () -> assertTrue(createFile(path)),
                () -> assertTrue(new File(path).exists()),
                () -> assertTrue(deleteFile(path)),
                () -> assertFalse(new File(path).exists()),
                () -> assertFalse(deleteFile(path))
        );
    }

    @BeforeAll
    @Test
    static void clean() {
        emptyDirectory(new File(pathToTmpDir));
    }
}
