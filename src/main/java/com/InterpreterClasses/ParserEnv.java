package com.InterpreterClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.ParsingEstructures.AST;


public class ParserEnv {
    /*
     *        Algo asi va ordenado la funcion
     * function[nombre de funcion] = AST de la funcion
     */
    private HashMap<String, AST<String>> Functions = new HashMap<String, AST<String>>();
    /*
     *      Ordnamiento de variable
     * Variables[nombre de la variable] = lista linkeada con los valores
     */
    private HashMap<String, LinkedList<String>> Variables = new HashMap<String, LinkedList<String>>();
    /*
     *      Ordenamiento de las expresiones
     * LogicalOrder = lista linkeada con las expresiones de nivel mayor, que no setea nada
     */ 
    private LinkedList<AST<String>> LogicalOrder = new LinkedList<AST<String>>();
    /*
     *     Ordenamiento de las listas anidadas
     * NestedLists[nombre de la lista] = lista con los elementos de la lista anidada
     */
    public HashMap<String, ArrayList<String>> NestedLists = new HashMap<String, ArrayList<String>>(); //public for debugging

    // Getters
    public HashMap<String, AST<String>> getFunctions() {return Functions;} 

    public HashMap<String, LinkedList<String>> getVariables() {return Variables;}

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
                LogicalOrder.add(ASTGenerator(CurrentList));
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
                 *   Function[Function name] =      HashName de los parametros
                 *              /        /       |                   \                    \        
                 *          param1 param2    ...                primer operando    segundo operando        #parametros con el primer operando
                 */
                
                CurrentList.remove(0); 
                CurrentAST = new AST<String>(CurrentList.get(1));
                CurrentList.remove(0);

                for (String token : NestedLists.get(CurrentList.get(0))){ // Agrega los parametros
                    CurrentAST.addChild(new AST<String>(token));
                }
                CurrentList.remove(0); // Dejamos todo vacio hasta llegar al primer parentesis (la regla de oro del cero de pedro) NOTE: me invente el termino
    
                for (String token: CurrentList){
                    if (NestedLists.containsKey(token)){
                        CurrentAST.addChild(ASTGenerator(NestedLists.get(token)));
                    }
                    else{
                        CurrentAST.addChild(new AST<String>(token));
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
            case "setq":
                /*
                 *                            en el orden logico solo saldra esto
                 *                                         setq
                 *                                          |
                 *                                         Variable
                 *                                          |
                 *                                     Numero de vez que se vuelva a setear   #Para buscar el valor en el hashmap y dentro de la lista linkeada
                 */
                if (Variables.containsKey(CurrentList.get(1))){
                    // Si la variable ya existe, le mete la nueva cantidad de veces que se setea
                    CurrentAST = new AST<String>(CurrentList.get(0));
                    CurrentAST.addChild(new AST<String>(CurrentList.get(1)));
                    CurrentAST.addChild(new AST<String>(String.valueOf(Variables.get(CurrentList.get(1)).size())));
                    SetVariable(CurrentList);
                }
                else{
                    // Si la variable no existe, la crea y le mete el valor
                    CurrentAST = new AST<String>(CurrentList.get(0));
                    CurrentAST.addChild(new AST<String>(CurrentList.get(1)));
                    CurrentAST.addChild(new AST<String>(String.valueOf(0)));
                    SetVariable(CurrentList);
                }
            default:
                /*
                 * La logica basica de los ADT es sencilla
                 *              operador        #Este es el nodo raiz
                 *      /       |       \       \
                 *    hijo1  hijo2     hijo3  hijo4 #Suvecivamente
                 */
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

        // Check if the variable already exists
        if (Variables.containsKey(Variable)) {
            Variables.get(Variable).add(Value); // Lo mete a la lista linkeada 
        } else {
            // si la variable no existe, crea una lista linkeada y le mete el valor
            LinkedList<String> values = new LinkedList<>();
            values.add(Value);
            Variables.put(Variable, values);
        }
    }

}

