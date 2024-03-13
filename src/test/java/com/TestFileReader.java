package com;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.Tokenizer.*;

public class TestFileReader {
    @Test
    public void TestForFileReader() {
        ArrayList<String> expectedTokens = new ArrayList<>(Arrays.asList(
                "(", "defun", "fibonacci", "(", "n", ")", "(", "cond", "(", "<=", "n", "1", ")", "n", ")",
                "(", "t", "(", "+", "(", "fibonacci", "(", "-", "n", "1", ")", ")", "(", "fibonacci", "(", "-", "n", "2", ")", ")", ")", ")"
        ));

        FileReaderExample reader = new FileReaderExample();
        String filename = "tokens.lisp";

        ArrayList<ArrayList<String>> tokensFromFile = reader.readTokensFromFile(filename);

        assertTrue(Integer.valueOf(expectedTokens.size()).equals(tokensFromFile.size()));
            

        for (int i = 0; i < expectedTokens.size(); i++) {
            if (!expectedTokens.get(i).equals(tokensFromFile.get(0).get(i))) {
                System.out.println("Token mismatch at index " + i + ": Expected '" + expectedTokens.get(i) + "', Found '" + tokensFromFile.get(0).get(i) + "'");
            }
        }
    }
}


