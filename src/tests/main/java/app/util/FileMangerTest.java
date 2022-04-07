package app.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Stack;

import static app.util.FileManager.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileMangerTest {
    private static final String TMP_DIR = "src/tests/resources/tmp";

    @Test
    @Order(1)
    void testCreateDir() {

        assertTrue(createDir("src/tests/resources/tmp/t1"));
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
        assertTrue(f1.canWrite());
    }

    @AfterAll
    static void cleanUpTmp() {
        File tmp = new File(TMP_DIR);
        Stack<File> filesToDelete = new Stack<>();
        Stack<File> childDirs = new Stack<>();
        File[] files = tmp.listFiles();
        if (files == null) return;
        Arrays.stream(files).forEach(filesToDelete::push);
        Arrays.stream(files).filter(File::isDirectory).forEach(childDirs::push);
        while (!childDirs.isEmpty()) {
            File dir = childDirs.pop();
            File[] children = dir.listFiles();
            assertNotNull(children);
            Arrays.stream(children).forEach(filesToDelete::push);
            Arrays.stream(children).filter(File::isDirectory).forEach(childDirs::push);
        }
        while (!filesToDelete.isEmpty()) {
            filesToDelete.pop().delete();
        }
    }

}
