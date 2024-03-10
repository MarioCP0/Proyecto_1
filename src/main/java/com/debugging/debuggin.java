package com.debugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class debuggin {
    public static void main(String[] args) {
        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList("(","+","2","3",")"));
        LinkedList<AST<?>> LogicalOrder = new LinkedList<AST<?>>(); //Orden logico del programa
        // Print all the Tree
        LogicalOrder.add(ASTGenerator(tokens));
        System.out.println(LogicalOrder);
    }
    private static AST<String> ASTGenerator(ArrayList<String> CurrentList){
        AST<String> CurrentAST;

        switch (CurrentList.get(1)) {
            case "defun":
                CurrentAST = new AST<String>(CurrentList.get(2));
                CurrentList.remove(2); //Elimina el nombre de la funcion
                // TODO: Implement the rest of the function
                return CurrentAST;
            default:
                CurrentAST = new AST<String>(CurrentList.get(1)); //Operator

                Node<String> LeftNode = new Node<String>(CurrentList.get(2));
                Node<String> RightNode = new Node<String>(CurrentList.get(3));
                
                CurrentAST.addChild(LeftNode);
                CurrentAST.addChild(RightNode);
                return CurrentAST;
        }

    }
}
