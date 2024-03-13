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
        ArrayList<AST<String>> children = ast.getChildren();

        switch (root.getData()) {
            case "defun":
                // evaluar funciones
                break;
            case "setq":
                // evaluar variable
                break;
            default:
                // Evaluación de otras expresiones
                return evaluateExpression(ast);
        }

        return ""; // resultado
    }

    private String evaluateExpression(AST<String> ast) {
        Node<String> root = ast.getRoot();
        ArrayList<AST<String>> children = ast.getChildren();

        String operator = root.getData();
        LinkedList<Integer> operands = new LinkedList<>();

        // TODO: que esto tenga recursividad, osea que pueda evaluar expresiones anidadas

        
        for (AST<String> child : children) {
            String data = child.getRoot().getData();
            if (isNumeric(data)) {
                operands.add(Integer.parseInt(data));
            } else if (variables.containsKey(data)) {
                operands.add(Integer.parseInt(variables.get(data).getFirst())); //TODO: cambiar esto 
            } else {
                // cualquier otro caso
            }
        }

        // Realiza operación en base a el operando
        switch (operator) {
            case "+":

            case "-":

                break;
            case "*":

                break;

            default:

                break;
        }

        return "";
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}


