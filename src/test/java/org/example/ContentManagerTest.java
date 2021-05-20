package org.example;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ContentManagerTest {
    private static ContentManager contentManager;
    private static String filePath;

    @BeforeClass
    public static void init() {
        contentManager = new ContentManager();
        ClassLoader classLoader = ContentManagerTest.class.getClassLoader();
        filePath = Objects.requireNonNull(classLoader.getResource("sample.txt")).getPath();
    }

    @Test
    public void testReadFileContent() throws FileNotFoundException {
        String content = contentManager.readFileContent(filePath);
        assertNotNull(content);
    }

    @Test
    public void testValidateContent() {
        String content = "Adonis Julius Archer\nShelby Nathan Yoder";
        assertTrue(contentManager.validateContent(content));
        String invalidContent = "Adonis Julius Archer\nShelby.Nathan@ Yoder123";
        assertFalse(contentManager.validateContent(invalidContent));
    }

    @Test
    public void testSortNames() {
        String content = "Shelby Nathan Yoder\nMikayla Lopez\nJune Lopez\nAdonis Julius Archer";
        String expected = "Adonis Julius Archer\nJune Lopez\nMikayla Lopez\nShelby Nathan Yoder";
        assertEquals(expected, contentManager.sortNames(content));
    }

    @Test
    public void testSaveResult() throws FileNotFoundException {
        String content = "Adonis Julius Archer";
        contentManager.saveResult(content, "out/sample.txt");
    }
}