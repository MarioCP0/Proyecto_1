package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;


public class ParserEnv {
    HashMap<String, AST<?>> Functions = new HashMap<String, AST<?>>();
    HashMap<String, String> Variables = new HashMap<String, String>(); 

    public LinkedList<AST<?>> Parsing(ArrayList<String> tokens){
        LinkedList<AST<?>> LogicalOrder = new LinkedList<AST<?>>(); //Orden logico del programa
        ArrayList<String> CurrentList = new ArrayList<String>(); //Lista actual
        for (int i = 0; i < tokens.size(); i++) {
            if (CurrentList.isEmpty() && tokens.get(i).equals("(")){ //Si la lista esta vacia y el token es un parentesis
                CurrentList.add(tokens.get(i));
            }
            else if (tokens.get(i).equals("(")){ 
                CurrentList.add(tokens.get(i));
            }
            else if (!tokens.get(i).equals(")")){ // default
                CurrentList.add(tokens.get(i));
            }
            else if (tokens.get(i).equals(")")){
                CurrentList.add(tokens.get(i));
                if (EndList(tokens.get(i+1))){ // si se termino la lista, en teoria tiene que haber un espacio
                    DeleateSpaces(CurrentList);
                    switch (CurrentList.get(2)) { //Determina el tipo de lista
                        case "defun":
                            SetFunction(CurrentList);
                            break;
                        case "setq":
                            SetVariable(CurrentList);
                            break;
                        default:
                            LogicalOrder.add(ASTGenerator(CurrentList));
                            break;
                    }
                    CurrentList.clear();
                }
            }
        }
        return LogicalOrder;
    }

    public AST<String> ASTGenerator(ArrayList<String> CurrentList){ // The public is for testing purposes (Change it later to private)
        AST<String> CurrentAST;
        switch (CurrentList.get(1)) {
            case "defun":
                CurrentAST = new AST<String>(CurrentList.get(2));
                CurrentList.remove(2); //Elimina el nombre de la funcion
                for (String token : CurrentList) {
                    // TODO: Implement the rest of the function
                    
                }
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

    private void SetVariable(ArrayList<String> CurrentList){
        String Variable = CurrentList.get(1); // Nombre de la variable
        String Value = CurrentList.get(2); // Valor de la variable
        Variables.put(Variable, Value); // Agrega la variable al diccionario
    }

    private void SetFunction(ArrayList<String> CurrentList){
        Functions.put(CurrentList.get(2), ASTGenerator(CurrentList));
    }

    private boolean EndList(String token) { //Determina si el numero de parentesis es par y por consecuencia si la lista esta cerrada
        return token.equals(" ");
    }

    private void DeleateSpaces(ArrayList<String> CurrentList) {
        for (int i = 0; i < CurrentList.size(); i++) { 
            if (CurrentList.get(i).equals(" ")){
                CurrentList.remove(i);
            }
        }
    }
}
