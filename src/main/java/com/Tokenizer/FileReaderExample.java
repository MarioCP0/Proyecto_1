package com.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class FileReaderExample {

    public String readExpressionsFromFile(String filePath) {
        StringBuilder expressions = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                expressions.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expressions.toString();
    }

    public static void main(String[] args) {
        FileReaderExample fileReaderExample = new FileReaderExample();
        String expressions = fileReaderExample.readExpressionsFromFile("lisp_files\\tokens.lisp");
        System.out.println(expressions);
    }
}
