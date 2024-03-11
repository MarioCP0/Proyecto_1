package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;


public class ParserEnv {
    private HashMap<String, AST<String>> Functions = new HashMap<String, AST<String>>();
    private HashMap<String, String> Variables = new HashMap<String, String>(); 
    private LinkedList<AST<String>> LogicalOrder = new LinkedList<AST<String>>();

    // Getters
    public HashMap<String, AST<String>> getFunctions() {return Functions;} 

    public HashMap<String, String> getVariables() {return Variables;}

    public LinkedList<AST<String>> getLogicalOrder() {return LogicalOrder;}

    public void Parsing(ArrayList<String> CurrentList){
        switch (CurrentList.get(0)) {//Determina el tipo de lista
            case "defun":
                SetFunction(CurrentList);
                break;
            case "setq":
                SetVariable(CurrentList);
                break;
            case "atom":
                SetVariable(CurrentList);
                break;
            default:
                LogicalOrder.add(ASTGenerator(CurrentList));
                break;
        }
    }
    

    public AST<String> ASTGenerator(ArrayList<String> CurrentList){ // The public is for testing purposes (Change it later to private)
        AST<String> CurrentAST = null; // Initialize CurrentAST with a default value
        switch (CurrentList.get(1)) {
            case "defun":
                // TODO: Implementar la creacion de funciones
                break;
            default:
                CurrentAST = new AST<String>(CurrentList.get(0)); // Crea el nodo raiz
                for (int i = 1; i < CurrentList.size(); i++) {
                    CurrentAST.addChild(CurrentList.get(i)); // Agrega los nodos hijos solo a la raiz
                }
                break;
        }
        return CurrentAST;
    }

    private void SetVariable(ArrayList<String> CurrentList){
        String Variable = CurrentList.get(1); // Nombre de la variable
        String Value = CurrentList.get(2); // Valor de la variable
        Variables.put(Variable, Value); // Agrega la variable al diccionario
    }

    private void SetFunction(ArrayList<String> CurrentList){
        Functions.put(CurrentList.get(2), ASTGenerator(CurrentList));
    }
}
