package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import com.ParsingEstructures.AST;
import com.ParsingEstructures.Node;


public class ParserEnv {
    HashMap<String, AST<?>> Functions = new HashMap<String, AST<?>>();
    HashMap<String, String> Variables = new HashMap<String, String>(); 

    public LinkedList<AST<?>> Parsing(ArrayList<String> tokens){
        LinkedList<AST<?>> LogicalOrder = new LinkedList<AST<?>>(); //Orden logico del programa
        ArrayList<String> CurrentList = new ArrayList<String>(); //Lista actual
        int ListCounter = 0; //Contador de listas
        for (int i = 0; i < tokens.size(); i++) { // Iteracion en la lista, para determinar que es cada cosa


            //Estas Condicionales su proposito es agregar las cosas a la pilaactual y ir Llevando la cuenta de los parentesis
            if (CurrentList.isEmpty() && tokens.get(i).equals("(")){
                CurrentList.add(tokens.get(i));
                ListCounter++;
            }
            else if (PairParentesis(ListCounter) && tokens.get(i).equals("(")){
                CurrentList.add(tokens.get(i));
                ListCounter++;
            }
            else if (!PairParentesis(ListCounter) && !tokens.get(i).equals(")")){
                CurrentList.add(tokens.get(i));
            }
            else if (!PairParentesis(ListCounter) && tokens.get(i).equals(")")){
                CurrentList.add(tokens.get(i));
                ListCounter++;
            }

            // Determinando que es una lista cerrada decide en cual de las 3 estructuras se va a meter
            if (PairParentesis(ListCounter)){
                switch (CurrentList.get(2)) {
                    case "defun":
                        SetFunction(CurrentList);
                        CurrentList.clear();
                        break;
                    case "setq":
                        SetVariable(CurrentList);
                        CurrentList.clear();
                        break;
                    default:
                        LogicalOrder.add(ASTGenerator(CurrentList));
                        break;
                }
            }
        }
        return LogicalOrder;
    }

    private AST<String> ASTGenerator(ArrayList<String> CurrentList){
        AST<String> CurrentAST;

        switch (CurrentList.get(1)) {
            case "defun":
                CurrentAST = new AST<String>(CurrentList.get(2));
                while (!CurrentList.isEmpty()) {

                }
                return CurrentAST;
            default:
                CurrentAST = new AST<String>(CurrentList.get(1));
                while (!CurrentList.isEmpty()) {
                    // TODO: Implementar el AST para la lista logica, un root, 2 hijos, de ahi cada node tendra sus hijo (Siendo dos cada uno)
                }
                return CurrentAST;
                
        }

    }

    private void SetVariable(ArrayList<String> CurrentList){
        String Variable = CurrentList.get(1);
        String Value = CurrentList.get(2);
        Variables.put(Variable, Value);
    }

    private void SetFunction(ArrayList<String> CurrentList){
        Functions.put(CurrentList.get(2), ASTGenerator(CurrentList));
    }

    private boolean PairParentesis(int ListCounter) { //Determina si el numero de parentesis es par y por consecuencia si la lista esta cerrada
        return ListCounter%2 == 0;
    }
}
