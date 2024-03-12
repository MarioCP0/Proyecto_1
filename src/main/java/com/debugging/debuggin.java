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
        NestedLists.put("jasdk", new ArrayList<String>(Arrays.asList(">", "a", "b"))); 
        NestedLists.put("jasdk3", new ArrayList<String>(Arrays.asList("jasdk", "c"))); //The C is being added to the children of jasdk when it should be at the same level
        NestedLists.put("jasdk2", new ArrayList<String>(Arrays.asList(">", "d", "f")));
        NestedLists.put("jasdk4", new ArrayList<String>(Arrays.asList("jasdk2", "g"))); //The G is being added to the children of jasdk2 when it should be at the same level

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("cond", "jasdk3", "jasdk4")));
        ParserEnv parser = new ParserEnv(NestedLists);

        for (ArrayList<String> token : tokens){
            parser.Parsing(token);
        }


}
}