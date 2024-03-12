package com.ParsingEstructures;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    public static ArrayList<String> tokenize(String input) {
        ArrayList<String> tokens = new ArrayList<>();


        
        

        return tokens;
    }

    public static void main(String[] args) {
        String input = "(setq x 10) (setq y 20) (setq z \"Hola\") (defun add (x y) (+ x y)) (add x y)";
        ArrayList<String> tokens = tokenize(input);
        System.out.println(tokens);
    }


}
