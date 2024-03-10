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
        // TODO: Add later
    }

    @Test
    public void AddingGrandChildren()
    {
        // TODO: Add later
    }



}
