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

    ArrayList<String> actualTokens = Tokenizer.addTokensToArrayList(input, new ArrayList<String>());

    assertEquals(expectedTokens, actualTokens);
}

@Test
public void testAddTokensToArrayList_EmptyInput() {
    String input = "";
    ArrayList<String> expectedTokens = new ArrayList<String>();

    ArrayList<String> actualTokens = Tokenizer.addTokensToArrayList(input, new ArrayList<String>());

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
    ArrayList<String> updatedList1 = new ArrayList<>();
    updatedList1.add("add 1 2");
    updatedList1.add("subtract 5 3");
    expectedUpdatedExpresionesAnidadas.put("nested1", updatedList1);

    ArrayList<String> updatedList2 = new ArrayList<>();
    updatedList2.add("multiply 2 3");
    updatedList2.add("divide 10 2");
    expectedUpdatedExpresionesAnidadas.put("nested2", updatedList2);

    // Call the method to remove parentheses
    Map<String, ArrayList<String>> actualUpdatedExpresionesAnidadas = Tokenizer.removeParentheses(expresionesAnidadas);

    // Assert the expected and actual updated maps are equal
    assertEquals(expectedUpdatedExpresionesAnidadas, actualUpdatedExpresionesAnidadas);
}

@Test
public void testAddExpressionToMap_ValidIndices() {
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

    // Call the method to add the expression to the map
    Tokenizer.addExpressionToMap(1, 3);

    // Create the expected updated list of tokens
    ArrayList<String> expectedTokensAsArray = new ArrayList<>();
    expectedTokensAsArray.add("(");
    expectedTokensAsArray.add("add");
    expectedTokensAsArray.add("1");
    expectedTokensAsArray.add("2");
    expectedTokensAsArray.add(")");

    // Create the expected updated map of nested expressions
    Map<String, ArrayList<String>> expectedExpresionesAnhidadas = new HashMap<>();
    ArrayList<String> expectedExpression = new ArrayList<>();
    expectedExpression.add("1");
    expectedExpression.add("2");
    expectedExpresionesAnhidadas.put(String.valueOf(expectedExpression.hashCode()), expectedExpression);

    // Assert the expected and actual updated lists of tokens are equal
    assertEquals(expectedTokensAsArray, tokensAsArray);

    // Assert the expected and actual updated maps of nested expressions are equal
    assertEquals(expectedExpresionesAnhidadas, expresionesAnhidadas);

    // Assert that the matching parentheses map is empty
    assertTrue(matchingParentheses.isEmpty());
}

@Test(expected = IllegalArgumentException.class)
public void testAddExpressionToMap_InvalidStartIndex() {
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

    // Call the method with an invalid start index
    Tokenizer.addExpressionToMap(-1, 3);
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

    // Call the method with an invalid end index
    Tokenizer.addExpressionToMap(1, 5);
}

@Test
public void testTokenize() {
    Tokenizer tokenizer = new Tokenizer();

    // Test case 1: Valid input with nested expressions
    String input1 = "(add 1 (subtract 5 3))";
    ArrayList<String> expectedTokens1 = new ArrayList<String>();
    expectedTokens1.add("(");
    expectedTokens1.add("add");
    expectedTokens1.add("1");
    expectedTokens1.add("(");
    expectedTokens1.add("subtract");
    expectedTokens1.add("5");
    expectedTokens1.add("3");
    expectedTokens1.add(")");
    expectedTokens1.add(")");
    String testinput1 = String.valueOf(input1.hashCode());

    ArrayList<String> actualTokens1 = tokenizer.tokenize(testinput1);

    assertEquals(expectedTokens1, actualTokens1);

    // Test case 2: Valid input without nested expressions
    String input2 = "(multiply 2 3)";
    ArrayList<String> expectedTokens2 = new ArrayList<String>();
    expectedTokens2.add("(");
    expectedTokens2.add("multiply");
    expectedTokens2.add("2");
    expectedTokens2.add("3");
    expectedTokens2.add(")");

    ArrayList<String> actualTokens2 = tokenizer.tokenize(input2);

    assertEquals(expectedTokens2, actualTokens2);

    // Test case 3: Empty input
    String input3 = "";
    ArrayList<String> expectedTokens3 = new ArrayList<String>();

    ArrayList<String> actualTokens3 = tokenizer.tokenize(input3);

    assertEquals(expectedTokens3, actualTokens3);
}

}