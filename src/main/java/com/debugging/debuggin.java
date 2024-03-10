package com.debugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.ParsingEstructures.AST;

public class debuggin {
    public static void main(String[] args) {
        LinkedList<AST<?>> LogicalOrder = new LinkedList<AST<?>>(); //Orden logico del programa
        ArrayList<String> CurrentList = new ArrayList<String>(); //Lista actual
        int ListCounter = 0; //Contador de listas
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList("(", "defun", "sum", "(", "a", "b", ")", "(", "+", "a", "b", ")", ")","(", "sum", "1", "2", ")"));
        for (int i = 0; i < tokens.size(); i++) {
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
            else{
                break;
            }
        }
        for (String string : CurrentList) {
            System.out.println(string);
        }
    }
    
    private static boolean PairParentesis(int ListCounter) { //Determina si el numero de parentesis es par y por consecuencia si la lista esta cerrada
        return ListCounter%2 == 0;
    }
}
