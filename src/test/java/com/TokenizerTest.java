package com;

import org.junit.Test;

import com.TokenizerEstructure.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

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
        String input = "(+ 1 (* 2 3) 4 )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "Expression1","4", ")"));
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

    @Test
    public void testTokenHashMap() {
        String input = "(+ 1 (* 2 3) 4 )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "Expression1","4", ")"));
        ArrayList<String> actual = Tokenizer.tokenize(input);
        assertEquals(expected, actual);
        
        ArrayList<String> expression1 = Tokenizer.expresionesAnhidadas.get("Expression1");
        ArrayList<String> expectedExpression1 = new ArrayList<>(Arrays.asList("(","*", "2", "3", ")"));
        assertEquals(expectedExpression1, expression1);
    }
}
