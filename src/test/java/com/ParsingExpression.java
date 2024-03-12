package com;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;

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

    @Test
    public void NestedList(){
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        NestedLists.put("HAS1", new ArrayList<String>(Arrays.asList("+", "2", "3")));
        NestedLists.put("HAS2", new ArrayList<String>(Arrays.asList("+", "7", "3")));
        NestedLists.put("HAS3", new ArrayList<String>(Arrays.asList("+", "HAS1", "1")));

        ParserEnv parser = new ParserEnv(NestedLists);

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "HAS3", "HAS2")));

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }

        // Creating the Testing AST
        AST<String> CurrentAST = new AST<String>("+");
        CurrentAST.addChild(new AST<String>("+"));
        CurrentAST.getChildren().get(0).addChild(new AST<String>("+"));
        CurrentAST.getChildren().get(0).getChildren().get(0).addChild(new AST<String>("2"));
        CurrentAST.getChildren().get(0).getChildren().get(0).addChild(new AST<String>("3"));
        CurrentAST.getChildren().get(0).addChild(new AST<String>("1"));
        CurrentAST.addChild(new AST<String>("+"));
        CurrentAST.getChildren().get(1).addChild(new AST<String>("7"));
        CurrentAST.getChildren().get(1).addChild(new AST<String>("3"));


        for (int i = 0; i < parser.getLogicalOrder().size(); i++) {
            assertTrue(CurrentAST.getRoot().getData().equals(parser.getLogicalOrder().get(i).getRoot().getData()));
            System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
            for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                assertTrue(CurrentAST.getChildren().get(0).getRoot().getData().equals(node.getRoot().getData()) || CurrentAST.getChildren().get(1).getRoot().getData().equals(node.getRoot().getData()));
                System.out.println("Result Second Node: " + node.getRoot().getData());
                for (AST<String> node2 : node.getChildren()){
                    assertTrue(CurrentAST.getChildren().get(0).getChildren().get(0).getRoot().getData().equals(node2.getRoot().getData()) || CurrentAST.getChildren().get(0).getChildren().get(1).getRoot().getData().equals(node2.getRoot().getData()) || CurrentAST.getChildren().get(1).getChildren().get(0).getRoot().getData().equals(node2.getRoot().getData()) || CurrentAST.getChildren().get(1).getChildren().get(1).getRoot().getData().equals(node2.getRoot().getData()));
                    System.out.println("Result Third Node: " + node2.getRoot().getData());
                    for (AST<String> node3 : node2.getChildren()){
                        assertTrue(CurrentAST.getChildren().get(0).getChildren().get(0).getChildren().get(0).getRoot().getData().equals(node3.getRoot().getData()) || CurrentAST.getChildren().get(0).getChildren().get(0).getChildren().get(1).getRoot().getData().equals(node3.getRoot().getData()));
                        System.out.println("Result Fourth Node: " + node3.getRoot().getData());
                    }
                }
            }
        }

    }

    @Test
    public void ConditionalTest(){
                // Este test verifica que el parser pueda parsear una expresion simple
                HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
                NestedLists.put("COMPARE1", new ArrayList<String>(Arrays.asList(">", "a", "b"))); 
                NestedLists.put("jasdk3", new ArrayList<String>(Arrays.asList("COMPARE1", "c"))); //I have to add a case when the first element corresponds to a condition
                NestedLists.put("COMPARE2", new ArrayList<String>(Arrays.asList(">", "d", "f")));
                NestedLists.put("jasdk4", new ArrayList<String>(Arrays.asList("COMPARE2", "g"))); //I have to add a case when the first element corresponds to a condition
                ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
                tokens.add(new ArrayList<String>(Arrays.asList("cond", "jasdk3", "jasdk4")));
                ParserEnv parser = new ParserEnv(NestedLists);
                System.out.println("NestedLists: " + NestedLists.toString());
        
                for (ArrayList<String> token : tokens){
                    parser.Parsing(token);
                }
        
                for (int i = 0; i < parser.getLogicalOrder().size(); i++) {
                    assertTrue(parser.getLogicalOrder().get(i).getRoot().getData().equals("cond"));
                    System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
                    for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                        assertTrue(node.getRoot().getData().equals(">") || node.getRoot().getData().equals("<"));
                        System.out.println("Result Second Node: " + node.getRoot().getData());
                        for (AST<String> node2 : node.getChildren()){
                            assertTrue(node2.getRoot().getData().equals("c") || node2.getRoot().getData().equals("g") || node2.getRoot().getData().equals("a") || node2.getRoot().getData().equals("b") || node2.getRoot().getData().equals("d") || node2.getRoot().getData().equals("f"));
                            System.out.println("Result Third Node: " + node2.getRoot().getData());
                        }
                    }
                }
    }

}
