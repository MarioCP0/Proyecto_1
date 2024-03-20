package com;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.Tokenizer.Tokenizer;

public class TokenizerTest {

    @Test
    public void testTokenize() {
        Tokenizer tokenizer = new Tokenizer();
        String input = "(add 1 2)";
        ArrayList<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("(");
        expectedTokens.add("add");
        expectedTokens.add("1");
        expectedTokens.add("2");
        expectedTokens.add(")");

        ArrayList<String> actualTokens = tokenizer.tokenize(input);

        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testTokenize_EmptyInput() {
        Tokenizer tokenizer = new Tokenizer();
        String input = "";
        ArrayList<String> expectedTokens = new ArrayList<>();

        ArrayList<String> actualTokens = tokenizer.tokenize(input);

        assertEquals(expectedTokens, actualTokens);
    }

    // Add more test cases as needed

}