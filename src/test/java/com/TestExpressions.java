package com;

import org.junit.Test;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.Evaluator;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class TestExpressions {
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
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables(), parser.getLogicalOrder());
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
        assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals(15));              
        
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

        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables(), parser.getLogicalOrder());
        // assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals(55));
        // Gimme the parse of the function
        AST<String> parsedExpression = parser.getFunctions().get("fib"); 
        for (AST<String> child : parsedExpression.getChildren()){
            System.out.println("Child: " + child.getRoot().getData());
            for (AST<String> grandChild : child.getChildren()){
                System.out.println("GrandChild: " + grandChild.getRoot().getData());
                for (AST<String> grandGrandChild : grandChild.getChildren()){
                    System.out.println("GrandGrandChild: " + grandGrandChild.getRoot().getData());
                    for (AST<String> grandGrandGrandChild : grandGrandChild.getChildren()){
                        System.out.println("GrandGrandGrandChild: " + grandGrandGrandChild.getRoot().getData());
                        for (AST<String> grandGrandGrandGrandChild : grandGrandGrandChild.getChildren()){
                            System.out.println("GrandGrandGrandGrandChild: " + grandGrandGrandGrandChild.getRoot().getData());
                            for (AST<String> grandGrandGrandGrandGrandChild : grandGrandGrandGrandChild.getChildren()){
                                System.out.println("GrandGrandGrandGrandGrandChild: " + grandGrandGrandGrandGrandChild.getRoot().getData());
                            }
                        }
                    }
                }
            }
        }

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

