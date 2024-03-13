package com.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class FileReaderExample {

    public ArrayList<ArrayList<String>> readTokensFromFile(String filePath) {
        ArrayList<ArrayList<String>> tokens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokensArray = line.trim().split("\\s+");
                // lista
                tokens.add(new ArrayList<>(Arrays.asList(tokensArray)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }
}

