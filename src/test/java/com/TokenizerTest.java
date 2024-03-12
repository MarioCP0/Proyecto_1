package com;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.ParsingEstructures.Tokenizer;

public class TokenizerTest {

    @Test
    public void testTokenize() {
        String input = "(setq x 10) (setq y 20) (setq z \"Hola\") (defun add (x y) (+ x y)) (add x y)";
        ArrayList<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("(");
        expectedTokens.add("setq");
        expectedTokens.add("x");
        expectedTokens.add("10");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add("(");
        expectedTokens.add("setq");
        expectedTokens.add("y");
        expectedTokens.add("20");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add("(");
        expectedTokens.add("setq");
        expectedTokens.add("z");
        expectedTokens.add("\"Hola\"");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add("(");
        expectedTokens.add("defun");
        expectedTokens.add("add");
        expectedTokens.add("(");
        expectedTokens.add("x");
        expectedTokens.add("y");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add("(");
        expectedTokens.add("+");
        expectedTokens.add("x");
        expectedTokens.add("y");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add(")");
        expectedTokens.add("");
        expectedTokens.add("(");
        expectedTokens.add("add");
        expectedTokens.add("x");
        expectedTokens.add("y");
        expectedTokens.add(")");
        expectedTokens.add("");

        ArrayList<String> actualTokens = Tokenizer.tokenize(input);

        assertEquals(expectedTokens, actualTokens);
    }
}