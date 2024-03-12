package com.ParsingEstructures;

import java.util.Stack;



public class TokenStack {

    public static String[] reservedWords = {"defun", "cond", "quote", "setq", "and", "or", "atom", "list", "equal", "+", "-", "*", "/", "<", ">", "<=", ">=", "=", "(", ")"};
    public static boolean isParenthesesClosed = false;

    public static void main(String[] args) {
        String input = "(setq x 10) (setq y 20) (setq z \"Hola\") (defun add (x y) (+ x y)) (add x y)";
        Stack<String> stack = new Stack<>();

        StringBuilder currentExpression = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            char previousToken = (i > 0) ? input.charAt(i - 1) : '\0';

            if (c == '(') {
                if (currentExpression.length() > 1) {
                    
                    stack.push(currentExpression.toString().trim());
                    currentExpression.setLength(0);
                }
            }

            currentExpression.append(c);

            if (c == ')') {
                stack.push(currentExpression.toString().trim());
                currentExpression.setLength(0);
            }
        }

        // If there's any remaining expression, add it to the stack
        if (currentExpression.length() > 0) {
            stack.push(currentExpression.toString().trim());
        }

        // Print the expressions in the stack
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    public static boolean isReservedWord(char token) {
        for (String reservedWord : reservedWords) {
            if (String.valueOf(token).equals(reservedWord)) {
                return true;
            }
        }
        return false;
    }
}
