package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;

/**
 * Evaluador para árboles de sintaxis abstracta (AST) con soporte para variables y funciones.
 * Esta clase está diseñada para interpretar y evaluar expresiones basadas en ASTs proporcionados,
 * con soporte para operaciones aritméticas, evaluaciones condicionales y llamadas a funciones.
 */
public class Evaluator {
    /*
     *        Algo asi va ordenado la funcion
     * function[nombre de funcion] = AST de la funcion
     */
    private HashMap<String, AST<String>> functions;
    /*
     *      Ordenamiento de variable
     * Variables[nombre de la variable] = lista linkeada con los valores
     */
    private HashMap<String, LinkedList<String>> variables;
    private HashMap<String, Integer> TimeVariableHaveBeenSet = new HashMap<String, Integer>(); //Bastante simple solo (key:variable, value:en que iteracion de las seteada va)
    private HashMap<String, String> functionVariables = new HashMap<String, String>(); //son los parametros de la funcion

    public Evaluator(HashMap<String, AST<String>> functions, HashMap<String, LinkedList<String>> variables) {
        this.functions = functions;
        this.variables = variables;
    }
        /**
     * Evalúa un AST basado en la operación raíz, gestionando asignaciones de variables y delegando
     * otras operaciones a métodos de evaluación específicos.
     *
     * @param ast El AST a evaluar.
     * @return El resultado de la evaluación como un String.
     */
    public String evaluate(AST<String> ast) { //mira el root para ver que clase de metodo es (casi todo lleva al default, podria cambiar esto, pero)
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
    /**
     * Evalúa una expresión en un AST, delegando operaciones a métodos específicos según el tipo de
     * @param ast El AST de la expresión a evaluar.
     * @return El resultado de la evaluación de la expresión.
     */
    private String evaluateExpression(AST<String> ast) {
        Node<String> root = ast.getRoot();
        ArrayList<AST<String>> children = ast.getChildren();
        
        if (functionVariables.containsKey(root.getData())) { // devuelve el valor del parametro de la funcion
            return functionVariables.get(root.getData());
        }
        if (root.getData().equals("cond")){ //llama a la funcion del condicional
            return EvaluatingCond(ast);
        }
        if (functions.containsKey(root.getData())) { //evalua la funcion
            return EvaluatingFunction(functions.get(root.getData()), ast);
        }
        if (variables.containsKey(root.getData())) { //devuelve el valor de la variable
            return variables.get(root.getData()).get(TimeVariableHaveBeenSet.get(root.getData()));
        }
        /*
         * Operaciones aritmeticas
         * resultado (raiz de la operacion)= los hijos evaluados  #No esta tan bonito como los del parser
         */
        if (root.getData().equals("+")) {
            float result = 0;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result += Float.parseFloat(evaluate(child));
                }else{
                    result += Float.parseFloat(evaluateExpression(child));
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("-")) {
            float result = Float.parseFloat(evaluate(ast.getChildren().get(0)));
            ast.getChildren().remove(0);
            for (AST<String> child : children) {
                // Intercalate the negative sign
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result -= Float.parseFloat(evaluate(child));
                } 
                else {
                    result -= Float.parseFloat(evaluateExpression(child));
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("*")) {
            float result = 1;
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result *= Float.parseFloat(evaluate(child));
                } 
                else {
                    result *= Float.parseFloat(evaluateExpression(child));
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("/")) {
            float result = Float.parseFloat(evaluate(ast.getChildren().get(0)));
            ast.getChildren().remove(0);
            for (AST<String> child : children) {
                if (child.getRoot().getData().matches("[+\\-*/]")) {                    
                    result /= Float.parseFloat(evaluate(child));
                }
                else {
                    result /= Float.parseFloat(evaluateExpression(child));
                }
            }
            return Float.toString(result);
        }
        if (root.getData().equals("quote")|| root.getData().equals("'")) {
            String NotEvaluated = "";
            for (AST<String> child : children) {
                NotEvaluated += child.getRoot().getData() + " ";
            }
            return NotEvaluated;
        }
        return root.getData();
    }
    /**
     * Evalúa una expresión condicional, evaluando las condiciones y retornando el resultado de la
     * primera condición que se cumpla.
     *
     * @param ast El AST de la expresión condicional.
     * @return El resultado de la evaluación de la expresión condicional.
     */
    private String EvaluatingCond(AST<String> ast) {
        for (AST<String> child : ast.getChildren()){
            switch (child.getRoot().getData()) {
                /*
                 * Condicionales
                 * t caso general #Como el default para los bobos
                 * <, >, =, <=, >=, atom #casos especificos
                 */
                case "t":
                    return evaluateExpression(child.getChildren().get(0));
                case "<":
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) < Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                    break;
                case ">":
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) > Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                    break;
                case "=":
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) == Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                    break;
                case "<=":
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) <= Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                    break;
                case ">=":
                    if (Float.parseFloat(evaluateExpression(child.getChildren().get(0))) >= Float.parseFloat(evaluateExpression(child.getChildren().get(1)))){
                        return evaluateExpression(child.getChildren().get(2));
                    }
                    break;
                case "atom":
                    if (evaluateExpression(child.getChildren().get(0)).matches("[0-9]+")){
                        return evaluateExpression(child.getChildren().get(1));
                    }
                    break;
            }
        }
        return null;
    }
    /**
     * Evalúa una llamada a función configurando un nuevo ámbito con las variables apropiadas
     * y evaluando el cuerpo de la función.
     *
     * @param functionAST El AST de la función a llamar.
     * @param AstForArguments El AST que contiene los argumentos para la llamada a la función.
     * @return El resultado de la evaluación de la llamada a la función.
     */
    private String EvaluatingFunction(AST<String> functionAST, AST<String> AstForArguments) {
        // Crear un nuevo entorno para la funcion
        for (int i = 0; i < functionAST.getChildren().size()-1; i++) {
            functionVariables.put(functionAST.getChildren().get(i).getRoot().getData(), evaluateExpression(AstForArguments.getChildren().get(i)));
        }
        return evaluateExpression(functionAST.getChildren().get(functionAST.getChildren().size()-1));

    }
    private void TimeVariableSet(String key) {
        TimeVariableHaveBeenSet.put(key, TimeVariableHaveBeenSet.get(key) + 1);
    }

}


