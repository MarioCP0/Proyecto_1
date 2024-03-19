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
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());
        // TODO: CHAMBEAR
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
    public void THEFINALBOSS_THEFIBONACCI(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("defun", "fib", "#1", "#2")));
        NestedLists.put("#1", new ArrayList<String>(Arrays.asList("n")));
        NestedLists.put("#2", new ArrayList<String>(Arrays.asList("cond", "#3", "#4", "#5")));
        NestedLists.put("#3", new ArrayList<String>(Arrays.asList("=", "n", "0")));
        NestedLists.put("#4", new ArrayList<String>(Arrays.asList("=", "n", "1")));
        NestedLists.put("#5", new ArrayList<String>(Arrays.asList("t", "#6")));
        NestedLists.put("#6", new ArrayList<String>(Arrays.asList("+", "#7", "#8")));
        NestedLists.put("#7", new ArrayList<String>(Arrays.asList("fib", "#9")));
        NestedLists.put("#8", new ArrayList<String>(Arrays.asList("fib", "#10")));
        NestedLists.put("#9", new ArrayList<String>(Arrays.asList("-", "n", "1")));
        NestedLists.put("#10", new ArrayList<String>(Arrays.asList("-", "n", "2")));
        
        ParserEnv parser = new ParserEnv(NestedLists);
        System.out.println("NestedLists: " + NestedLists.toString());
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());

        /*
         *         te va dolver algo tal que asi el parser
         *     function["fib"] =         HASHDELOSPARAMETROS
         *                              /               \    
         *                             n               cond
         *                                     /        |       \
         *                                    =         =       t
         *                                  / | \     / \ \     |
         *                                n  0  0   n  1  1     +
         *                                                     / \         
         *                                                   fib  fib
         *                                                   |    |              
         *                                                   -    -
         *                                                 / \   / \
         *                                                n  1  n  2 
         */
        }

    }

