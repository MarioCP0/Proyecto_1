package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

// TODO: needs a lot of fucking refactoring fr
// TODO: add documentations to all of this
public class Evaluator {

    private HashMap<String, AST<String>> functions;
    private HashMap<String, LinkedList<String>> variables;
    private HashMap<String, Integer> TimeVariableHaveBeenSet = new HashMap<String, Integer>();
    private HashMap<String, String> functionVariables = new HashMap<String, String>();

    public Evaluator(HashMap<String, AST<String>> functions, HashMap<String, LinkedList<String>> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    public String evaluate(AST<String> ast) {
        Node<String> root = ast.getRoot();
        switch (root.getData()) {
            case "setq":    
                if (TimeVariableHaveBeenSet.containsKey(ast.getChildren().get(0).getRoot().getData())){
                    TimeVariableSet(ast.getChildren().get(0).getRoot().getData());
                } else {
                    TimeVariableHaveBeenSet.put(ast.getChildren().get(0).getRoot().getData(), 0);
                }
                break;
            default:
                return evaluateExpression(ast);
        }

        return null; // resultado
    }

    // TODO: Refactor the / and the - (it needs to intercalated one from the other)
    private String evaluateExpression(AST<String> ast) {
        Node<String> root = ast.getRoot();
        ArrayList<AST<String>> children = ast.getChildren();

        if (functionVariables.containsKey(root.getData())) {
            return functionVariables.get(root.getData());
        }
        if (root.getData().equals("cond")){
            return EvaluatingCond(ast);
        }
        if (functions.containsKey(root.getData())) {
            return EvaluatingFunction(functions.get(root.getData()), ast);
        }
        if (root.getData().equals("+")) {
            float result = 0;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result += Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result += Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                }
                else if (functionVariables.containsKey(child.getRoot().getData())) {
                    result += Float.parseFloat(functionVariables.get(child.getRoot().getData()));       
                }
                else if (functions.containsKey(child.getRoot().getData())) {
                    result += Float.parseFloat(EvaluatingFunction(functions.get(child.getRoot().getData()), child));
                }
                else {
                    result += Float.parseFloat(child.getRoot().getData());
                }
                System.out.println("Result: " + result);
            }
            return Float.toString(result);
        }
        if (root.getData().equals("-")) {
            float result = 0;
            for (AST<String> child : children) {
                // Intercalate the negative sign
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result -= Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    result -= Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                }
                else if (functionVariables.containsKey(child.getRoot().getData())) {
                    result -= Float.parseFloat(functionVariables.get(child.getRoot().getData()));
                }
                else if (functions.containsKey(child.getRoot().getData())) {
                    result -= Float.parseFloat(EvaluatingFunction(functions.get(child.getRoot().getData()), child));
                }
                else {
                    result -= Float.parseFloat(child.getRoot().getData());
                }
                System.out.println("Result: " + result);
            }
            return Float.toString(result);
        }
        if (root.getData().equals("*")) {
            float result = 1;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result *= Float.parseFloat(evaluateExpression(child));
                } else if (variables.containsKey(child.getRoot().getData())) {
                    System.out.println("Value: " + variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                    result *= Float.parseFloat(variables.get(child.getRoot().getData()).get(TimeVariableHaveBeenSet.get(child.getRoot().getData())));
                }
                else if (functionVariables.containsKey(child.getRoot().getData())) {
                    result *= Float.parseFloat(functionVariables.get(child.getRoot().getData()));                   
                }
                else if (functions.containsKey(child.getRoot().getData())) {
                    result *= Float.parseFloat(EvaluatingFunction(functions.get(child.getRoot().getData()), child));
                }
                else {
                    result *= Float.parseFloat(child.getRoot().getData());
                }
                System.out.println("Result: " + result);
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
                }
                else if (functionVariables.containsKey(child.getRoot().getData())) {  
                    result /= Float.parseFloat(functionVariables.get(child.getRoot().getData()));              
                }
                else if (functions.containsKey(child.getRoot().getData())) {
                    result /= Float.parseFloat(EvaluatingFunction(functions.get(child.getRoot().getData()), child));
                }
                else {
                    result /= Float.parseFloat(child.getRoot().getData());
                }
                System.out.println("Result: " + result);
            }
            return Float.toString(result);
        }
        return root.getData();
    }

    // TODO: REFACTOR ALL THE COND (ONE TEST WORKS BUT NEEDS TO BE TESTED MORE)
    private String EvaluatingCond(AST<String> ast) {
        for (AST<String> child : ast.getChildren()){
            if (child.getRoot().getData().equals("t")){
                System.out.println("T: " + child.getChildren().get(0).getRoot().getData());
                return evaluateExpression(child.getChildren().get(0));
            }
            if (child.getRoot().getData().equals("=")){
                if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    if (Float.parseFloat(functionVariables.get(child.getChildren().get(0).getRoot().getData())) == Float.parseFloat(functionVariables.get(child.getChildren().get(1).getRoot().getData()))){ //TODO: change this for having case variable number and like that
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && !functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) == Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else{
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) == Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
            }
            if (child.getRoot().getData().equals(">=")){
                if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    if (Float.parseFloat(functionVariables.get(child.getChildren().get(0).getRoot().getData())) >= Float.parseFloat(functionVariables.get(child.getChildren().get(1).getRoot().getData()))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else{
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) >= Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
            }
            if (child.getRoot().getData().equals("<=")){
                if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    if (Float.parseFloat(functionVariables.get(child.getChildren().get(0).getRoot().getData())) <= Float.parseFloat(functionVariables.get(child.getChildren().get(1).getRoot().getData()))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else{
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) <= Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
            }
            if (child.getRoot().getData().equals(">")){
                if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    if (Float.parseFloat(functionVariables.get(child.getChildren().get(0).getRoot().getData())) > Float.parseFloat(functionVariables.get(child.getChildren().get(1).getRoot().getData()))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else{
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) > Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
            }
            if (child.getRoot().getData().equals("<")){
                if (functionVariables.containsKey(child.getChildren().get(0).getRoot().getData()) && functionVariables.containsKey(child.getChildren().get(1).getRoot().getData())) {
                    System.out.println("Key: " + child.getChildren().get(0).getRoot().getData());
                    System.out.println("Value: " + functionVariables.get(child.getChildren().get(0).getRoot().getData()));
                    if (Float.parseFloat(functionVariables.get(child.getChildren().get(0).getRoot().getData())) < Float.parseFloat(functionVariables.get(child.getChildren().get(1).getRoot().getData()))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
                else{
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) < Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                }
            }
        }
        return null;
    }

    private String EvaluatingFunction(AST<String> functionAST, AST<String> AstForArguments) {
        // Crear un nuevo entorno para la funcion
        for (int i = 0; i < functionAST.getChildren().size()-1; i++) {
            functionVariables.put(functionAST.getChildren().get(i).getRoot().getData(), AstForArguments.getChildren().get(i).getRoot().getData());
        }
        return evaluateExpression(functionAST.getChildren().get(functionAST.getChildren().size()-1));

    }
    private void TimeVariableSet(String key) {
        TimeVariableHaveBeenSet.put(key, TimeVariableHaveBeenSet.get(key) + 1);
    }

}


