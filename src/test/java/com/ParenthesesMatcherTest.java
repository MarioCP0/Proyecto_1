package com;
import org.junit.Test;

import com.Tokenizer.ParenthesesMatcher;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class ParenthesesMatcherTest {
    @Test
    public void testFindMatchingParentheses() {
        ArrayList<String> input = new ArrayList<>();
        input.add("(");
        input.add("add");
        input.add("1");
        input.add("(");
        input.add("subtract");
        input.add("2");
        input.add("3");
        input.add(")");
        input.add(")");

        Map<Integer, Integer> expectedMatchingParentheses = new TreeMap<>();
        expectedMatchingParentheses.put(0, 8);
        expectedMatchingParentheses.put(3, 7);

        Map<Integer, Integer> actualMatchingParentheses = ParenthesesMatcher.findMatchingParentheses(input);

        assertEquals(expectedMatchingParentheses, actualMatchingParentheses);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMatchingParentheses_UnmatchedClosingParenthesis() {
        ArrayList<String> input = new ArrayList<>();
        input.add("(");
        input.add("add");
        input.add("1");
        input.add(")");
        input.add(")");

        ParenthesesMatcher.findMatchingParentheses(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMatchingParentheses_UnmatchedOpeningParenthesis() {
        ArrayList<String> input = new ArrayList<>();
        input.add("(");
        input.add("add");
        input.add("1");
        input.add("(");
        input.add("subtract");
        input.add("2");
        input.add("3");

        ParenthesesMatcher.findMatchingParentheses(input);
    }
}