package com;

import java.util.ArrayList;

import com.InterpreterClasses.*;
import com.ParsingEstructures.AST;
import com.Tokenizer.*;
import java.util.HashMap;

public class LispInterpreter {
    public static void main(String[] args) { //just connects everything
        LispReader reader = new LispReader();
        LispTokenizer tokenizer = new LispTokenizer();

        tokenizer.tokenize(reader.ReadLispFile(args[0]));
        ArrayList<ArrayList<String>> tokens = tokenizer.getExpressions();

        HashMap<String, ArrayList<String>> nestedExpressions = tokenizer.getExpressionMap();

        ParserEnv parser = new ParserEnv(nestedExpressions);
        Evaluator evaluator = new Evaluator(parser.getFunctions(), parser.getVariables());

        for (ArrayList<String> token : tokens) {
            parser.Parsing(token);
        }

        for (AST<String> ast : parser.getLogicalOrder()) { //didn't add a print function to the evaluator soo take this 
            System.out.println(evaluator.evaluate(ast));
        }

    }
}