package com;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.experimental.theories.Theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.InterpreterClasses.ParserEnv;
import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class ParsingSingleExpression {

    @Test
    public void ParsingSingleExpressionTest()
    {
        ParserEnv parser = new ParserEnv();
        ArrayList<Node<String>> TestingTokens = new ArrayList<Node<String>>(Arrays.asList(new Node<String>("2"), new Node<String>("3")));
        AST<String> ast;
        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList("(","+","2","3",")"));
        ast = parser.ASTGenerator(tokens);
        assertTrue(ast.getRoot().getData().equals("+"));
        for (int i = 0; i < ast.getChildren().size(); i++) {
            assertTrue(ast.getChildren().get(i).getData().equals(TestingTokens.get(i).getData()));
        }
        
    }
    @Test
    public void ParsingSingleExpressionLogiclOrder(){
        ParserEnv parser = new ParserEnv();

        LinkedList<AST<?>> LogicOrder;

        LinkedList<AST<?>> TestingLogicOrder = new LinkedList<AST<?>>();
        AST<String> ast = new AST<String>("+");
        ArrayList<Node<String>> TestingTokens = new ArrayList<Node<String>>(Arrays.asList(new Node<String>("2"), new Node<String>("3")));
        for (Node<String> token : TestingTokens) {
            ast.addChild(token);
        }
        TestingLogicOrder.add(ast);

        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList("(","+","2","3",")"," "));
        LogicOrder = parser.Parsing(tokens);
        assertTrue(LogicOrder.size() == TestingLogicOrder.size());
        for (int i = 0; i < LogicOrder.size(); i++) {
            assertTrue(LogicOrder.get(i).getRoot().getData().equals(TestingLogicOrder.get(i).getRoot().getData()));
            for (int j = 0; j < LogicOrder.get(i).getChildren().size(); j++) {
                assertTrue(LogicOrder.get(i).getChildren().get(j).getData().equals(TestingLogicOrder.get(i).getChildren().get(j).getData()));
            }
        }
    }
}
