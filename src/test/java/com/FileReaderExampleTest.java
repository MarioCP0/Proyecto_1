package com;

import org.junit.Before;
import org.junit.Test;

import com.Tokenizer.FileReaderExample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileReaderExampleTest {
    private FileReaderExample fileReaderExample;
    private String testFilePath;

    @Before
    public void setUp() {
        fileReaderExample = new FileReaderExample();
        testFilePath = "lisp_files\\tokens.lisp";
    }

    @Test
    public void testReadExpressionsFromFile() throws IOException {
        // Create a test file with some expressions
        File testFile = new File(testFilePath);
        FileWriter writer = new FileWriter(testFile);
        writer.write("(+ 1 2)\n(* 3 4)\n(- 5 6)");
        writer.close();

        // Read expressions from the test file
        String expressions = fileReaderExample.readExpressionsFromFile(testFilePath);

        // Verify the expected expressions
        String expectedExpressions = "(+ 1 2)(* 3 4)(- 5 6)";
        assertEquals(expectedExpressions, expressions);

        // Delete the test file
        testFile.delete();
    }
}