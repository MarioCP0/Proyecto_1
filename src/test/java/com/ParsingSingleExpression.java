package com;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class ParsingSingleExpression {

    // TODO: REFACTOR TESTS
    @Test
    public void ParsingSingleExpressionTest()
    {
        ParserEnv parser = new ParserEnv();

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("+", "2", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("+", "7", "3")));

        AST<String> ast_for_asserting = new AST<String>("+");
        ast_for_asserting.addChild("2");
        ast_for_asserting.addChild("3");

        AST<String> ast_for_asserting2 = new AST<String>("+");
        ast_for_asserting2.addChild("7");
        ast_for_asserting2.addChild("3");


        for (ArrayList<String> token : tokens) {
            parser.Parsing(token);
        }

        // Check if the first AST is correct
        AST<String> ast = parser.getLogicalOrder().get(0);
        Node<String> root = ast.getRoot();
        assertTrue( root.getData().equals(ast_for_asserting.getRoot().getData()) );
        assertTrue( ast.getChildren().get(0).getData().equals(ast_for_asserting.getChildren().get(0).getData()));
        assertTrue( ast.getChildren().get(1).getData().equals(ast_for_asserting.getChildren().get(1).getData()));


    }

    @Test
    public void CheckingVariables()
    {
        ParserEnv parser = new ParserEnv();

        ArrayList<ArrayList<String>>  tokens = new ArrayList<ArrayList<String>>();
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "x", "3")));
        tokens.add(new ArrayList<String>(Arrays.asList("setq", "y", "3")));

        for (ArrayList<String> token : tokens) {
            parser.Parsing(token);
        }

        assertTrue( parser.getVariables().get("x").equals("3") );
        assertTrue( parser.getVariables().get("y").equals("3") );
    }

}
