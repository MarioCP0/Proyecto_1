package com;

import org.junit.Test;

import com.TokenizerClasses.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

public class TokenizerTest {

    @Test
    public void testTokenizeBasic() {
        String input = "(+ 1 2)";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "2", ")"));
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeNestedExpressions() {
        String input = "(+ 1 (* 2 3) 4 )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "66870885","4", ")"));
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeExtraSpaces() {
        String input = "(  +  1   2   )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "2", ")"));
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyExpression() {
        String input = "()";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", ")"));
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyString() {
        String input = "";
        ArrayList<String> expected = new ArrayList<>();
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testTokenHashMap() {
        String input = "(+ 1 (* 2 3) 4 )";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("(", "+", "1", "66870885","4", ")"));
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> actual = tokenizer.tokenize(input);
        assertEquals(expected, actual);
        
        ArrayList<String> expression1 = tokenizer.expresionesAnhidadas.get("66870885");
        ArrayList<String> expectedExpression1 = new ArrayList<>(Arrays.asList("(","*", "2", "3", ")"));
        assertEquals(expectedExpression1, expression1);
    }
}
