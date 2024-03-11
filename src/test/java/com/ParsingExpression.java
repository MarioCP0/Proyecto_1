package com;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class ParsingExpression {

    @Test
    public void ParsingSingleExpressionTest()
    {
        // Este test verifica que el parser pueda parsear una expresion simple
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "2", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("+", "7", "3")));

        // Crear el AST
        AST<String> CurrentAST = new AST<String>("+");
        CurrentAST.addChild(new AST<String>("2"));
        CurrentAST.addChild(new AST<String>("3"));

        AST<String> CurrentAST2 = new AST<String>("+");
        CurrentAST2.addChild(new AST<String>("7"));
        CurrentAST2.addChild(new AST<String>("3"));

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        for (int i = 0; i < parser.getLogicalOrder().size(); i++) {
            assertTrue(parser.getLogicalOrder().get(i).getRoot().getData().equals("+"));
            System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
            for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                assertTrue(node.getRoot().getData().equals("2") || node.getRoot().getData().equals("3") || node.getRoot().getData().equals("7"));
                System.out.println("Result Second Node: " + node.getRoot().getData());
            }
        }


    }

    @Test
    public void CheckingVariables()
    {
        // Este test verifica que el parser pueda parsear una expresion simple
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "x", "2")));
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "y", "3")));

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        assertTrue(parser.getVariables().get("x").equals("2"));
        assertTrue(parser.getVariables().get("y").equals("3"));

    }

    @Test
    public void CheckingNestedLists()
    {
        // Este test verifica que el parser pueda parsear una expresion simple
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        ParserEnv parser = new ParserEnv(NestedLists);
        NestedLists.put("#", new ArrayList<String>(Arrays.asList("+", "2", "3")));
        NestedLists.put("$", new ArrayList<String>(Arrays.asList("+", "7", "3")));

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "#", "$")));

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        for (int i = 0; i < parser.getLogicalOrder().size(); i++) {
            assertTrue(parser.getLogicalOrder().get(i).getRoot().getData().equals("+"));
            System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
            for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                assertTrue(node.getRoot().getData().equals("+"));
                System.out.println("Result Second Node: " + node.getRoot().getData());
                for (AST<String> node2 : node.getChildren()){
                    assertTrue(node2.getRoot().getData().equals("2") || node2.getRoot().getData().equals("3") || node2.getRoot().getData().equals("7"));
                    System.out.println("Result Third Node: " + node2.getRoot().getData());
                }
            }
        }
    }
}
