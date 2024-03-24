package com.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



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

}
