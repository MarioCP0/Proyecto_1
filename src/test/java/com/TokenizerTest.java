package com;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

import com.ParsingEstructures.Tokenizer;

public class TokenizerTest {

    @Test
    public void testTokenizeBasic() {
        String input = "(+ 1 2)";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "2", ")"));
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeNestedExpressions() {
        String input = "(+ 1 (* 2 3))";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "Expression1", ")"));
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeExtraSpaces() {
        String input = "(  +  1   2   )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "2", ")"));
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyExpression() {
        String input = "()";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", ")"));
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyString() {
        String input = "";
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }
}
