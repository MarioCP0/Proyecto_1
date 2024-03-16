package com.TokenizerEstructure;
import java.util.*;

/**
 * Clase que se encarga de encontrar los paréntesis correspondientes en una lista de tokens.
 */
public class ParenthesesMatcher {

    ArrayList<String> tokensAsArray = new ArrayList<String>();

    /**
     * Encuentra los paréntesis correspondientes en una lista de tokens.
     * 
     * @param input La lista de tokens.
     * @return Un mapa que contiene los índices de los paréntesis de apertura y cierre correspondientes.
     * @throws IllegalArgumentException Si hay paréntesis de cierre sin un paréntesis de apertura correspondiente,
     * o si hay paréntesis de apertura sin un paréntesis de cierre correspondiente.
     */
    public static Map<Integer, Integer> findMatchingParentheses(ArrayList<String> input) {
        // Se invierte el orden de los paréntesis para que el primer paréntesis de cierre sea el primero en la lista
        Map<Integer, Integer> matchingParentheses = new TreeMap<>(Collections.reverseOrder()); 
        Deque<Integer> stack = new ArrayDeque<>();
        

        for (int i = 0; i < input.size(); i++) {
            String token = input.get(i);
            if (token.equals("(")) {
                stack.push(i);
            } else if (token.equals(")")) {
                if (!stack.isEmpty()) {
                    matchingParentheses.put(stack.pop(), i);
                } else {
                    // Error: los paréntesis de cierre no tienen un paréntesis de apertura correspondiente
                    throw new IllegalArgumentException("Closing parenthesis without corresponding opening parenthesis.");
                }
            }
        }

        // Si todavía hay paréntesis de apertura sin paréntesis de cierre correspondientes
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Opening parenthesis without corresponding closing parenthesis.");
        }

        return matchingParentheses;
    }

}
