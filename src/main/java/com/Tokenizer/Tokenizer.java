package com.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * La clase Tokenizer se utiliza para tokenizar una cadena de entrada en una
 * lista de tokens.
 * También se encarga de encontrar y almacenar las expresiones anidadas en un
 * HashMap.
 * Los tokens se almacenan en un ArrayList.
 */
public class Tokenizer {

    public static boolean isParenthesesClosed = true;
    public static ArrayList<String> tokensAsArray = new ArrayList<>();
    public static Map<String, ArrayList<String>> expresionesAnhidadas = new HashMap<String, ArrayList<String>>();
    public static Map<Integer, Integer> matchingParentheses = new TreeMap<>();
    public static boolean continueProgram = true;

    /**
     * Tokeniza una cadena de entrada en una lista de tokens.
     * 
     * @param input la cadena de entrada a tokenizar
     * @return una lista de tokens
     */
    public ArrayList<ArrayList<String>> tokenize(String input) {

        if (input == null || input.isEmpty()) { // Se verifica si la cadena de entrada es nula o vacía
            return new ArrayList<>();
        }

        ArrayList<String> tokens = new ArrayList<>(); // Aquí se guardan los tokens
        addTokensToArrayList(input, tokensAsArray); // Se agregan los tokens a un tokenAsArray

        while (continueProgram) {

            matchingParentheses = ParenthesesMatcher.findMatchingParentheses(tokensAsArray); // Se buscan los paréntesis
                                                                                             // anidados

            // Create a copy of the matchingParentheses map
            Map<Integer, Integer> tempMathchingParentheses = new TreeMap<>(Collections.reverseOrder());
            tempMathchingParentheses.putAll(matchingParentheses);

            Map.Entry<Integer, Integer> entry = tempMathchingParentheses.entrySet().iterator().next(); // Se obtiene el
                                                                                                       // primer par de
                                                                                                       // paréntesis
                                                                                                       // anidados
            Integer start = entry.getKey(); // Se obtiene el índice del paréntesis de apertura
            Integer end = entry.getValue(); // Se obtiene el índice del paréntesis de cierre

            addExpressionToMap(start, end); // Se agrega la expresión anidada al HashMap expresionesAnhidadas

            if (matchingParentheses.isEmpty()) {
                continueProgram = false;
            }
        }

        // Se eliminan los paréntesis de las expresiones anidadas
        expresionesAnhidadas = removeParentheses(expresionesAnhidadas);

        tokens.addAll(tokensAsArray);

        ArrayList<ArrayList<String>> realValues = expressionValues(tokens);

        return realValues;
    }

    /**
        * Obtiene los valores de las expresiones en una lista de tokens.
        * 
        * @param tokens la lista de tokens que contiene las expresiones
        * @return una lista de listas de cadenas que representa los valores de las expresiones
        */
    public ArrayList<ArrayList<String>> expressionValues(ArrayList<String> tokens) {
        ArrayList<ArrayList<String>> values = new ArrayList<>();
        for (String token : tokens) {
            if (expresionesAnhidadas.containsKey(token)) {
                values.add(expresionesAnhidadas.get(token));
            } else {
                ArrayList<String> value = new ArrayList<>();
                value.add(token);
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Agrega todos los tokens de entrada a un ArrayList.
     * 
     * @param input         La cadena de entrada que contiene los tokens.
     * @param tokensAsArray El ArrayList donde se agregarán los tokens.
     * @return El ArrayList con los tokens agregados.
     */
    public  ArrayList<String> addTokensToArrayList(String input, ArrayList<String> tokensAsArray) { // Este método
                                                                                                          // agrega
                                                                                                          // todos los
                                                                                                          // tokens a un
                                                                                                          // ArrayList
        String[] split = input.split("\\s+|(?<=\\()|(?=\\))");

        for (String s : split) {
            if (s.equals("")) {
                continue;
            }
            tokensAsArray.add(s);
        }
        return tokensAsArray;
    }

    /**
     * Agrega una expresión anidada al mapa de expresiones.
     * 
     * @param start el índice de inicio de la expresión en la lista de tokens
     * @param end   el índice de fin de la expresión en la lista de tokens
     * @throws IllegalArgumentException si los índices de inicio o fin son inválidos
     */
    public  void addExpressionToMap(int start, int end) {
        if (start < 0 || start >= tokensAsArray.size() || end < 0 || end >= tokensAsArray.size()) { // aqui se verifica
                                                                                                    // que los indices
                                                                                                    // sean validos
            throw new IllegalArgumentException("Invalid start or end index");
        }

        ArrayList<String> expression = new ArrayList<>(); // en esta lista se guardan la expresion anidada , que se
                                                          // agregara al HashMap
        List<Integer> indicesToRemove = new ArrayList<>(); // los valores que se eliminaran del ArrayList tokensAsArray
                                                           // original

        for (int i = start; i <= end; i++) { // se recorre el ArrayList tokensAsArray basado en los indices start y end
                                             // dados por el HashMap matchingParentheses
            expression.add(tokensAsArray.get(i)); // se agrega el token al ArrayList expression
            indicesToRemove.add(i); // Se agrega a los valores que se removeran
        }

        String key = String.valueOf(expression.hashCode()); // se genera un key para el HashMap expresionesAnhidadas
        expresionesAnhidadas.put(key, expression); // se agrega la expresion anidada al HashMap expresionesAnhidadas

        // Se eliminan los tokens de tokensAsArray que se agregaron a expression
        for (int j = indicesToRemove.size() - 1; j >= 0; j--) {
            tokensAsArray.remove((int) indicesToRemove.get(j));
        }

        tokensAsArray.add(start, key); // se agrega el Hash code a la posicion donde estaba la expresión anidada
        matchingParentheses.clear(); // se limpia el HashMap matchingParentheses para que se busque el match de
                                     // parentesis con el nuevo ArrayList tokensAsArray
        matchingParentheses = ParenthesesMatcher.findMatchingParentheses(tokensAsArray); // se busca el match de
                                                                                         // parentesis con el nuevo
                                                                                         // ArrayList tokensAsArray

    }

    /**
     * Elimina los paréntesis de una expresión anidada.
     * 
     * @param expresionesAnidadas el mapa de expresiones anidadas
     * @return un nuevo mapa con las expresiones anidadas sin paréntesis
     */

    public  Map<String, ArrayList<String>> removeParentheses(Map<String, ArrayList<String>> expresionesAnidadas) {
        Map<String, ArrayList<String>> updatedExpresionesAnidadas = new HashMap<>();

        for (Map.Entry<String, ArrayList<String>> entry : expresionesAnidadas.entrySet()) {
            ArrayList<String> updatedList = new ArrayList<>();
            for (String s : entry.getValue()) {
                String updatedValue = s.replace("(", "").replace(")", "");
                updatedList.add(updatedValue);
            }
            updatedExpresionesAnidadas.put(entry.getKey(), updatedList);
        }

        return updatedExpresionesAnidadas;
    }

    /**
     * Detects if an expression is nested or not.
     * 
     * @param expression the expression to check
     * @return true if the expression is nested, false otherwise
     */
    public boolean isNestedExpression(String expression) {
        int openParenthesesCount = 0;
        int closeParenthesesCount = 0;

        for (char c : expression.toCharArray()) {
            if (c == '(') {
                openParenthesesCount++;
            } else if (c == ')') {
                closeParenthesesCount++;
            }
        }

        return openParenthesesCount > 0 && closeParenthesesCount > 0;
    }
    
    public Map<String, ArrayList<String>> getExpresionesAnhidadas() {
        return expresionesAnhidadas;
    }

    public static void main (String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        String input = "(multiply 2 3)" ;
        ArrayList<ArrayList<String>> actualTokens1 = tokenizer.tokenize(input);
        
        System.out.println(actualTokens1);
    }

}
