package com.debugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class debuggin {
    public static void main(String[] args) {
        ParserEnv parser = new ParserEnv();
        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "2", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("+", "7", "3")));
        for (ArrayList<String> token : tokens) {
            parser.Parsing(token);
        }
        for (AST<String> ast : parser.getLogicalOrder()) {
            Node<String> root = ast.getRoot();
            System.out.println(root.getData());
        }
    }
}
