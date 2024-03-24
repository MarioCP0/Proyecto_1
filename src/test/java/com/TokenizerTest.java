package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.Tokenizer.Tokenizer;

public class TokenizerTest {


@Test
public void testAddTokensToArrayList() {
    String input = "(add 1 2)";
    ArrayList<String> expectedTokens = new ArrayList<String>();
    expectedTokens.add("(");
    expectedTokens.add("add");
    expectedTokens.add("1");
    expectedTokens.add("2");
    expectedTokens.add(")");
    Tokenizer tokenizer = new Tokenizer();
    ArrayList<String> actualTokens = tokenizer.addTokensToArrayList(input, new ArrayList<String>());

    assertEquals(expectedTokens, actualTokens);
}

@Test
public void testAddTokensToArrayList_EmptyInput() {
    String input = "";
    ArrayList<String> expectedTokens = new ArrayList<String>();
    Tokenizer tokenizer = new Tokenizer();
    ArrayList<String> actualTokens = tokenizer.addTokensToArrayList(input, new ArrayList<String>());

    assertEquals(expectedTokens, actualTokens);
}



@Test
// Add more test cases as needed@Test
public void testRemoveParentheses() {
    // Create a sample map with nested expressions
    Map<String, ArrayList<String>> expresionesAnidadas = new HashMap<>();
    ArrayList<String> nestedList1 = new ArrayList<>();
    nestedList1.add("(add 1 2)");
    nestedList1.add("(subtract 5 3)");
    expresionesAnidadas.put("nested1", nestedList1);

    ArrayList<String> nestedList2 = new ArrayList<>();
    nestedList2.add("(multiply 2 3)");
    nestedList2.add("(divide 10 2)");
    expresionesAnidadas.put("nested2", nestedList2);

    // Create the expected updated map
    Map<String, ArrayList<String>> expectedUpdatedExpresionesAnidadas = new HashMap<>();
    Tokenizer tokenizer = new Tokenizer();
    Map<String, ArrayList<String>> actualUpdatedExpresionesAnidadas = tokenizer.removeParentheses(expresionesAnidadas);
}


@Test(expected = IllegalArgumentException.class)
public void testAddExpressionToMap_InvalidStartIndex() {
    // Create a sample list of tokens
    ArrayList<String> tokensAsArray = new ArrayList<>();
    Tokenizer tokenizer = new Tokenizer();
    tokenizer.addExpressionToMap(-1, 3);
}

@Test(expected = IllegalArgumentException.class)
public void testAddExpressionToMap_InvalidEndIndex() {
    // Create a sample list of tokens
    ArrayList<String> tokensAsArray = new ArrayList<>();
    tokensAsArray.add("(");
    tokensAsArray.add("add");
    tokensAsArray.add("1");
    tokensAsArray.add("2");
    tokensAsArray.add(")");

    // Create a sample map of matching parentheses
    Map<Integer, Integer> matchingParentheses = new HashMap<>();
    matchingParentheses.put(0, 4);

    // Create a sample map of nested expressions
    Map<String, ArrayList<String>> expresionesAnhidadas = new HashMap<>();
    Tokenizer tokenizer = new Tokenizer();

    // Call the method with an invalid end index
    tokenizer.addExpressionToMap(1, 5);
}

@Test
public void testTokenize() {
    Tokenizer tokenizer = new Tokenizer();

    // Test case 1: Valid input with nested expressions
    String input1 = "(add 1 (subtract 5 3))";
    ArrayList<ArrayList<String>> expectedTokens1 = new ArrayList<ArrayList<String>>();
    ArrayList<String> innerList1 = new ArrayList<String>();
    innerList1.add("");
    innerList1.add("add");
    innerList1.add("1");
    innerList1.add("-1708822914");
    innerList1.add("");
    expectedTokens1.add(innerList1);

    ArrayList<ArrayList<String>> actualTokens1 = tokenizer.tokenize(input1);

    assertEquals(expectedTokens1, actualTokens1);
}

@Test
public void inputWithoutNestedExpressions() {
    Tokenizer tokenizer = new Tokenizer();
    String input = "(multiply 2 3)";
    ArrayList<ArrayList<String>> expectedTokens = new ArrayList<ArrayList<String>>();
    ArrayList<String> innerList = new ArrayList<String>();
    innerList.add("");
    innerList.add("multiply");
    innerList.add("2");
    innerList.add("3");
    innerList.add("");
    expectedTokens.add(innerList);

    ArrayList<ArrayList<String>> actualTokens = tokenizer.tokenize(input);

    assertEquals(expectedTokens, actualTokens);
}

@Test
public void testTokenize_EmptyInput() {
    Tokenizer tokenizer = new Tokenizer();
    String input = "";
    ArrayList<ArrayList<String>> expectedTokens = new ArrayList<ArrayList<String>>();

    ArrayList<ArrayList<String>> actualTokens = tokenizer.tokenize(input);

    assertEquals(expectedTokens, actualTokens);
}

}