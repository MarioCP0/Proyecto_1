package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;


public class ParserEnv {
    private HashMap<String, AST<String>> Functions = new HashMap<String, AST<String>>();
    private HashMap<String, String> Variables = new HashMap<String, String>(); 
    private LinkedList<AST<String>> LogicalOrder = new LinkedList<AST<String>>();
    public HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>(); //public for debugging

    // Getters
    public HashMap<String, AST<String>> getFunctions() {return Functions;} 

    public HashMap<String, String> getVariables() {return Variables;}

    public LinkedList<AST<String>> getLogicalOrder() {return LogicalOrder;}

    // Constructor
    public ParserEnv(HashMap<String, ArrayList<String>> NestedLists){
        this.NestedLists = NestedLists; // el hashmap tiene toda aquella lista anidada que tenga el programa
        // Talvez sea mas lento, pero me quita de encima el spagehtti code
    }

    public void Parsing(ArrayList<String> CurrentList){ // this parse big list per big list
        switch (CurrentList.get(0)) {//Determina el tipo de lista
            case "defun":
                Functions.put(CurrentList.get(1), ASTGenerator(CurrentList));
                break;
            case "setq":
                SetVariable(CurrentList);
                break;
            case "atom":
                SetVariable(CurrentList);
                break;
            case "cond":
                LogicalOrder.add(ASTGenerator(CurrentList));
                break; // imma fucking imbecil fr
            default:
                LogicalOrder.add(ASTGenerator(CurrentList));
                break;
        }
    }
    

    public AST<String> ASTGenerator(ArrayList<String> CurrentList){ // The public is for testing purposes (Change it later to private)
        AST<String> CurrentAST = null; // Initialize CurrentAST with a default value
        switch (CurrentList.get(0)) {
            case "defun":
                /*
                 *  ADT GOES LIKE THIS 
                 *                          HASH del pArametro #TEMAS DE COMPLEJIDAD
                 *     /        /       |                   \                    \        
                 *   param1 param2    ...           primer operando de parentesis    segundo operando        #parametros con el primer operando
                 */
                boolean ParamatersAdded = false;

                CurrentList.remove(0);                                               
                CurrentAST = new AST<String>(CurrentList.get(1));
                CurrentList.remove(0);
    
                for (String token: CurrentList){
                    if (!ParamatersAdded){
                        // Add all 
                        ParamatersAdded = true;
                    }
                    if (ParamatersAdded && NestedLists.containsKey(token)){
                        for (String parameter : NestedLists.get(token)){
                            CurrentAST.addChild(new AST<String>(parameter));
                        }
                    }
                }
                break;
            case "cond":
                /*
                 * en este caso, el primer elemento si sera la funcion cond, por lo que se crea el nodo raiz
                 * luego para el orden de la condicional va de la siguiente forma:
                 *                           cond
                 *           /                 \                   \
                 *      comparador         comparador...   #asi sucesivamente
                 *      /  \   \
                 *    ...  ...  resultado   #cosas que se comparan
                 */
                
                CurrentAST = new AST<String>(CurrentList.get(0)); // Crea el nodo raiz
                CurrentList.remove(0); // Elimina el primer elemento de la lista
                for (String token : CurrentList){
                    CurrentAST.addChild(ASTGenerator(NestedLists.get(token)));
                }
                break;
            default:
                /*
                 * La logica basica de los ADT es sencilla
                 *              operador #Este es el nodo raiz
                 *      /       |       \       \
                 *    hijo1  hijo2     hijo3  hijo4 #Suvecivamente
                 */
                System.out.println("CurrentList: " + CurrentList.toString());
                if (NestedLists.containsKey(CurrentList.get(0))){
                    CurrentAST = ASTGenerator(NestedLists.get(CurrentList.get(0)));
                }
                else{
                    CurrentAST = new AST<String>(CurrentList.get(0));
                }
                CurrentList.remove(0); // Elimina el primer elemento de la lista
                for (String token : CurrentList){
                    if (NestedLists.containsKey(token)){
                        // Print the token that follows
                        CurrentAST.addChild(ASTGenerator(NestedLists.get(token)));
                    }
                    else{
                        CurrentAST.addChild(new AST<String>(token));
                    }
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
}

