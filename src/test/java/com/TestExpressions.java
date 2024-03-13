package com;

import org.junit.Test;

import com.InterpreterClasses.ParserEnv;
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
        ParserEnv parser = new ParserEnv(NestedLists);
        
        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables(), parser.getLogicalOrder());
        // assertTrue(evaluator.evaluate(parser.getLogicalOrder().get(0)).equals(55));
        // Gimme the parse of the function
        System.out.println(parser.getFunctions().get("fib").getRoot().getData());

        /*
         *         te va dolver algo tal que asi el parser
         *     function["fib"] =       HASHDELOSPARAMETROS
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

