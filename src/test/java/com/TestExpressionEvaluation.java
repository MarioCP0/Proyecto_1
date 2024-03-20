package com;

import org.junit.Test;

import com.InterpreterClasses.Evaluator;
import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class TestExpressionEvaluation {
    @Test
    public void testSimpleExpressions() {
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "3", "4")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables() );
        System.out.println(evaluator.evaluate(parser.getLogicalOrder().get(0))); //TODO: Tenes que cambiar cosas del programa, esto solo fue para que se viera bonis
        // EL EVALUATOR, EL TESTO NO CAMBIAR

        /*
         *          te va dolver algo tal que asi el parser
         *                     +
         *                   /   \
         *                  3     4
         *          El resultado debe de ser 7 :D ()
         */ 
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("7.0"));
    }
    @Test
    public void testExpressionsEvaluation() {
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);
        NestedLists.put("#", new ArrayList<String>(Arrays.asList("+", "2", "3")));
        NestedLists.put("$", new ArrayList<String>(Arrays.asList("+", "7", "3")));

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "#", "$")));
        System.out.println("NestedLists: " + NestedLists.toString());
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        /*
         *          te va dolver algo tal que asi el parser
         *                      +
         *                    /   \
         *                  +      +
         *                 / \    / \
         *                2   3  7   3    
         *          El resultado debe de ser 15 :D ()
         */ 
        System.out.println(evaluator.evaluate(parser.getLogicalOrder().get(0)));
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("15.0"));
    }
    @Test 
    public void TestExpressionOneAfterTheOther(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "3", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("+", "3", "4")));

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        System.out.println(evaluator.evaluate(parser.getLogicalOrder().get(0)));
        System.out.println(evaluator.evaluate(parser.getLogicalOrder().get(1)));
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("6.0"));
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(1)).equals("7.0"));

    }
    @Test
    public void TestingWithSetVariables(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "x", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "y", "4")));
        tokens.add(new ArrayList<String>(Arrays.asList("+", "x", "y")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(2)).equals("7.0"));

    }
    @Test
    public void TestingWithSetVariablesButTwice(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "x", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "x", "4")));
        tokens.add(new ArrayList<String>(Arrays.asList("*", "x", "x")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(2)).equals("16.0"));
    }
    @Test
    public void CondTestTValue(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("=", "4", "3")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("=", "3", "4")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList("t", "25")));
        NestedLists.put("#", new ArrayList<String>(Arrays.asList("#3", "32")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("#4", "1")));
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("cond", "#", "#2", "#5")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("25"));
    }
    @Test
    public void CondTestFValue(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("=", "3", "3")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("=", "3", "4")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList("t", "25")));
        NestedLists.put("#", new ArrayList<String>(Arrays.asList("#3", "32")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("#4", "1")));
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("cond", "#", "#2", "#5")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("32"));
    }
    @Test
    public void CondTestTValueWithNestedList(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("=", "4", "3")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("=", "3", "4")));
        NestedLists.put("#8", new ArrayList<String>(Arrays.asList("+", "5", "5")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList("t", "#8")));
        NestedLists.put("#", new ArrayList<String>(Arrays.asList("#3", "32")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("#4", "1")));
        ParserEnv parser = new ParserEnv(NestedLists);
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("cond", "#", "#2", "#5")));
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("10.0"));
    }

    @Test 
    public void TryingSimpleFunction(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("defun", "sum", "#1", "#2")));
        NestedLists.put("#1", new ArrayList<String>(Arrays.asList("a", "b")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("+", "a", "b")));
        tokens.add(new ArrayList<String>(Arrays.asList("sum", "3", "4")));
        ParserEnv parser = new ParserEnv(NestedLists);
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        System.out.println("NestedLists: " + NestedLists.toString());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("7.0"));
    }
    @Test
    public void ConditionalFunction(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("defun", "max", "#1", "#2")));
        NestedLists.put("#1", new ArrayList<String>(Arrays.asList("a", "b")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("cond", "#3", "#4")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList(">", "a", "b")));
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("#5", "5.0")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("t", "b")));
        tokens.add(new ArrayList<String>(Arrays.asList("max", "4.0", "3.0")));
        ParserEnv parser = new ParserEnv(NestedLists);
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        System.out.println("NestedLists: " + NestedLists.toString());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("5.0"));
    }

    @Test
    public void TheFACTORIAL(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("defun", "factorial", "#1")));
        NestedLists.put("#1", new ArrayList<String>(Arrays.asList("n")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("cond", "#3", "#4")));
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("=", "n", "0")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("t","#6")));
        NestedLists.put("#6", new ArrayList<String>(Arrays.asList("*", "n", "#5")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList("factorial", "-", "n", "1")));
        tokens.add(new ArrayList<String>(Arrays.asList("factorial", "5")));
        ParserEnv parser = new ParserEnv(NestedLists);
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        System.out.println("NestedLists: " + NestedLists.toString());
        for (AST<String> ast : parser.getLogicalOrder()){
            System.out.println(evaluator.evaluate(ast));
        }
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals("120.0"));
    }
}

