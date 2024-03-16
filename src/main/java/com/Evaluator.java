package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class Evaluator {

    private HashMap<String, AST<String>> functions;
    private HashMap<String, LinkedList<String>> variables;

    // TODO: HACER
    public Evaluator(HashMap<String, AST<String>> functions, HashMap<String, LinkedList<String>> variables, LinkedList<AST<String>> LogicalOrder) {
        this.functions = functions;
        this.variables = variables;
    }

    public String evaluate(AST<String> ast) {
        Node<String> root = ast.getRoot();

        switch (root.getData()) {
            case "defun":
                // evaluar funciones
                break;
            case "setq":
                // evaluar variable
                break;
            case "cond":
                // Evaluar cuando es condicional
            default:
                return evaluateExpression(ast);
        }

        return null; // resultado
    }

    public String evaluateExpression(AST<String> ast) {
        Node<String> root = ast.getRoot();
        ArrayList<AST<String>> children = ast.getChildren();

        // THIS NEEDS TO BE CHANGED for for the time
        if (root.getData().equals("+")) {
            // TODO chequear si hay mas arboles anidados
            System.out.println("Evaluating +" + children.get(0) + " " + children.get(1));
            int result = Integer.parseInt(children.get(0).getRoot().getData()) + Integer.parseInt(children.get(1).getRoot().getData());
            return Integer.toString(result);
        } 
        return null;
    }

}


