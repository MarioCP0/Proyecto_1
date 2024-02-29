package com;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class AST_NodeTest {
    @Test
    public void AddingRoots()
    {
        AST<String> ast = new AST<String>("root");
        Node<String> root = ast.getRoot();
        assertTrue( root.getData().equals("root") );
    }

    @Test
    public void AddingChildren()
    {
        AST<String> ast = new AST<String>("root");
        Node<String> root = ast.getRoot();
        root.addChild(new Node<String>("child1"));
        root.addChild(new Node<String>("child2"));
        assertTrue( root.getChildren().size() == 2 );
    }

    @Test
    public void AddingGrandChildren()
    {
        AST<String> ast = new AST<String>("root");
        Node<String> root = ast.getRoot();
        root.addChild(new Node<String>("child1"));
        root.addChild(new Node<String>("child2"));
        root.getChildren().get(0).addChild(new Node<String>("grandchild1"));
        root.getChildren().get(0).addChild(new Node<String>("grandchild2"));
        assertTrue( root.getChildren().get(0).getChildren().size() == 2 );
    }



}
