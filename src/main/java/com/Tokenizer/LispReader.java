package com.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Lee archivos Lisp y los convierte en una lista de tokens.
 */
public class LispReader {

    /**
     * Lee un archivo Lisp y convierte su contenido en una lista de tokens.
     *
     * @param path La ruta del archivo a leer.
     * @return Una lista de strings, donde cada string es un token del contenido del archivo.
     */
    public ArrayList<String> ReadLispFile(String path) {
        ArrayList<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                Collections.addAll(tokens, extractTokens(line)); // Agrega los tokens extraídos a la lista.
            }
        } catch (IOException e) {
            e.printStackTrace(); // Maneja excepciones de entrada/salida.
        }
        return tokens;
    }

    /**
     * Extrae tokens de una línea de texto, separando espacios y paréntesis.
     *
     * @param input La línea de texto a tokenizar.
     * @return Un array de tokens extraídos de la línea.
     */
    private String[] extractTokens(String input) {
        ArrayList<String> tokenList = new ArrayList<>();
        StringBuilder tokenBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '(':
                case ')':
                    if (tokenBuilder.length() > 0) {
                        tokenList.add(tokenBuilder.toString()); // Agrega el token acumulado a la lista.
                        tokenBuilder.setLength(0); // Reinicia el constructor de strings.
                    }
                    tokenList.add(Character.toString(c)); // Agrega el paréntesis como token.
                    break;
                case ' ':
                    if (tokenBuilder.length() > 0) {
                        tokenList.add(tokenBuilder.toString()); // Agrega el token acumulado a la lista.
                        tokenBuilder.setLength(0); // Reinicia el constructor de strings.
                    }
                    break;
                default:
                    tokenBuilder.append(c); // Acumula caracteres para el token actual.
            }
        }
        if (tokenBuilder.length() > 0) {
            tokenList.add(tokenBuilder.toString()); // Asegura que el último token sea agregado.
        }
        return tokenList.toArray(new String[0]); // Convierte la lista de tokens a un array y lo retorna.
    }
}


