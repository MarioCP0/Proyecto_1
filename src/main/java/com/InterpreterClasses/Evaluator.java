package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

public class Evaluator {

    private HashMap<String, AST<String>> functions;
    private HashMap<String, LinkedList<String>> variables;
    private HashMap<String, Integer> TimeVariableHaveBeenSet = new HashMap<String, Integer>();

    // TODO: HACER
    public Evaluator(HashMap<String, AST<String>> functions, HashMap<String, LinkedList<String>> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    public String evaluate(AST<String> ast) {
        Node<String> root = ast.getRoot();
        // TODO: Implement setq and cond
        switch (root.getData()) {
            case "setq":    
                if (TimeVariableHaveBeenSet.containsKey(ast.getChildren().get(0).getRoot().getData())){
                    TimeVariableSet(ast.getChildren().get(0).getRoot().getData());
                } else {
                    TimeVariableHaveBeenSet.put(ast.getChildren().get(0).getRoot().getData(), 0);
                }
                break;
            case "cond":
                // Evaluar cuando es condicional
            default:
                return evaluateExpression(ast);
        }

        return null; // resultado
    }

    // TODO: Needs Refactoring, put for evaluation of functions
    public String evaluateExpression(AST<String> ast) {
        Node<String> root = ast.getRoot();
        ArrayList<AST<String>> children = ast.getChildren();

        if (root.getData().equals("+")) {
            float result = 0;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result += Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result += Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                } else {
                    result += Float.parseFloat(child.getRoot().getData());
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("-")) {
            float result = 0;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result -= Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result -= Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                } else {
                    result -= Float.parseFloat(child.getRoot().getData());
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("*")) {
            float result = 1;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result *= Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result *= Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                } else {
                    result *= Float.parseFloat(child.getRoot().getData());
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("/")) {
            float result = 1;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result /= Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result /= Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                } else {
                    result /= Float.parseFloat(child.getRoot().getData());
                }
            }
            return Float.toString(result);
        }
        return null;
    }

    public void TimeVariableSet(String key) {
        TimeVariableHaveBeenSet.put(key, TimeVariableHaveBeenSet.get(key) + 1);
    }

}


