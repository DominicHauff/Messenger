package app.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TempDirTest {

    @TempDir
    static File tempDir;

    static String pathToTempDir;

    @BeforeAll
    static void init() {
        System.out.println("-".repeat(40));
        System.out.println("running testfile: " + TempDirTest.class.getSimpleName());
        pathToTempDir = tempDir.getPath();
        System.out.printf("path to tempDir: %s%n", pathToTempDir);
    }

    @Test
    void testTempDir() {
        assertTrue(tempDir.exists());
        assertTrue(tempDir.isDirectory());
    }

    @Test
    @Order(1)
    void createFileInFirstTest()  {
        File file = new File(pathToTempDir + "/file");
        assertDoesNotThrow(file::createNewFile);
        assertTrue(file.exists());
        assertTrue(file.setWritable(true));
    }

    @Test
    @Order(2)
    void writeToPreviousCreatedFile() {
        String testContent = "some content";
        File fileToWriteTo = new File(pathToTempDir + "/file");
        assertTrue(fileToWriteTo.exists());
        assertDoesNotThrow(() -> {
            FileWriter writer = new FileWriter(fileToWriteTo);
            writer.write(testContent);
            writer.close();
        });

        assertDoesNotThrow(() -> {
            List<String> allLinesInFile = Files.readAllLines(fileToWriteTo.toPath());
            assertEquals(1, allLinesInFile.size());
            assertEquals(testContent, allLinesInFile.get(0));
        });
    }

}
