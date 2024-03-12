package com.debugging;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.InterpreterClasses.ParserEnv; // Add missing import statement
import com.ParsingEstructures.AST;

public class debuggin {
    public static void main(String[] args) {
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
            System.out.println("Result Main Node: " + parser.getLogicalOrder().get(i).getRoot().getData());
            for (AST<String> node : parser.getLogicalOrder().get(i).getChildren()){
                System.out.println("Result Second Node: " + node.getRoot().getData());
                for (AST<String> node2 : node.getChildren()){
                    System.out.println("Result Third Node: " + node2.getRoot().getData());
                    for (AST<String> node3 : node2.getChildren()){
                        System.out.println("Result Fourth Node: " + node3.getRoot().getData());
                    }
                }
            }
        }
}
}