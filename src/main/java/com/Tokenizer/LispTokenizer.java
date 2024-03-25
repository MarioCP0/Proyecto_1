package com.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tokeniza expresiones Lisp en un formato estructurado que separa las expresiones
 * en componentes individuales y las mapea para facilitar el acceso.
 */
public class LispTokenizer {

    /**
     * Mapea cada expresión a un identificador único.
     */
    private static HashMap<String, ArrayList<String>> expressionMap = new HashMap<>();

    /**
     * Almacena la lista de expresiones como listas de cadenas.
     */
    private static ArrayList<ArrayList<String>> expressionList = new ArrayList<>();

    /**
     * Tokeniza una lista de tokens en expresiones Lisp.
     *
     * @param tokens Lista de tokens a tokenizar
     */
    public static void tokenize(List<String> tokens) {
        List<String> currentExpression = new ArrayList<>();
        int expressionCounter = 0;
        for (String token : tokens) {
            if (")".equals(token)) {
                List<String> subExpression = new ArrayList<>();
                String key = "expr" + expressionCounter++; //maybe ain't the best hashin' but it meh, fuck it
                for (int i = currentExpression.size() - 1; i >= 0; i--) {
                    String currentToken = currentExpression.get(i);
                    if ("(".equals(currentToken)) {
                        currentExpression.remove(i);
                        currentExpression.add(key);
                        break;
                    } else {
                        subExpression.add(0, currentToken);
                        currentExpression.remove(i);
                    }
                }
                expressionMap.put(key, (ArrayList<String>) subExpression);
            } else {
                currentExpression.add(token);
            }
        }
        for (String token : currentExpression) {
            expressionList.add(expressionMap.getOrDefault(token, new ArrayList<>(Collections.singletonList(token))));
        }
    }

    /**
     * Obtiene el mapa de expresiones.
     *
     * @return El mapa de expresiones
     */
    public static HashMap<String, ArrayList<String>> getExpressionMap() {
        HashMap<String, ArrayList<String>> expressions = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : expressionMap.entrySet()) {
            expressions.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return expressions;
    }

    /**
     * Obtiene la lista de expresiones.
     *
     * @return La lista de expresiones
     */
    public static ArrayList<ArrayList<String>> getExpressions() {
        ArrayList<ArrayList<String>> expressions = new ArrayList<>();
        for (List<String> expression : expressionList) {
            expressions.add(new ArrayList<>(expression));
        }
        return expressions;
    }
}

