package com.debugging;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class debuggin {
    public static void main(String[] args) {
        // Este test verifica que el parser pueda parsear una expresion simple
        HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>();
        NestedLists.put("jasdk", new ArrayList<String>(Arrays.asList(">", "2", "3")));
        NestedLists.put("jasdk1.5", new ArrayList<String>(Arrays.asList("jasdk", "2")));
        NestedLists.put("jasdk2", new ArrayList<String>(Arrays.asList(">", "7", "3")));
        NestedLists.put("jasdk2.5", new ArrayList<String>(Arrays.asList("jasdk2", "4")));

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("cond", "jasdk1.5", "jasdk2.5")));
        ParserEnv parser = new ParserEnv(NestedLists);

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }
        // System.out.println("Result Main Node: " + parser.getLogicalOrder().get(0).getRoot().getData());
        // for (AST<String> node : parser.getLogicalOrder().get(0).getChildren()){
        //     System.out.println("Result Second Node: " + node.getRoot().getData());
        //     for (AST<String> node2 : node.getChildren()){
        //         System.out.println("Result Third Node: " + node2.getRoot().getData());
        //     }
        // }
        for (int i = 0; i < parser.getLogicalOrder().size(); i++) {
            System.out.println("Linkedlist node: " + i);
            System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
            for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                System.out.println("Result Second Node: " + node.getRoot().getData());
                for (AST<String> node2 : node.getChildren()){
                    System.out.println("Result Third Node: " + node2.getRoot().getData());
                }
            }
        }
    }
}
