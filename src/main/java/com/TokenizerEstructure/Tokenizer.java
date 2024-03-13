package com.TokenizerEstructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tokenizer {

    public static boolean isParenthesesClosed = true;
    public static Map<String, ArrayList<String>> expresionesAnhidadas = new HashMap<String, ArrayList<String>>() ;
    public static int leftParentheses = 0;
    public static int rightParentheses = 0;
    public static int nestedExpressions = 0;

    public static ArrayList<String> tokenize(String input) {
        
        ArrayList<String> tokens = new ArrayList<>();

        String[] split = input.split("\\s+|(?<=\\()|(?=\\))");
        
        for (int i = 0; i < split.length; i++) {
            String token = split[i];

            if (!isParenthesesClosed) {
                
                if (token.contains("(")) {
                    ArrayList<String> value = new ArrayList<String>();
                    value.add(token);
                    leftParentheses++;
                    
                    while (!isParenthesesClosed) {
                        i++;
                        if (i >= split.length) {
                            break;
                        }
                        token = split[i];
                        value.add(token);
                        if (token.contains(")")) {
                            nestedExpressions++;
                            String key = "Expression" + nestedExpressions;
                            rightParentheses++;
                            isParenthesesClosed = true;
                            expresionesAnhidadas.put(key, value);
                            tokens.add(key);
                            token = token.replace(")", "");
                        }
                    }
                }
                isParenthesesClosed = false;
            }

            if (token.equals("(")) {
                isParenthesesClosed = false;
                tokens.add("(");
                token = token.replace("(", "");
                leftParentheses++;
            }
            else if (token.equals(")")) {
                isParenthesesClosed = true;
                tokens.add(")");
                token = token.replace(")", "");
                rightParentheses++;
            }
            else  {
                if (token != ""){
                    tokens.add(token);
                }
                
            }

        }

        return tokens;
    }

    public static void main(String[] args) {
        String input = "(defun add (x y) (+ x y)";
        ArrayList<String> tokens = tokenize(input);
        System.out.println(tokens);
    }


}
