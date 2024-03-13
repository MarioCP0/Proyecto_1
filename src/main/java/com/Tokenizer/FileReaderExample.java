package com.Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// TODO: meter todas las cosas (caracteres) en una lista 
public class FileReaderExample {

    public static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void main(String[] args) {
        String filePath = "Dirección de el archivo .txt"; // Coloca la ruta de tu archivo aquí
        String fileContent = readFile(filePath);
        System.out.println("Contenido del archivo:");
        System.out.println(fileContent);
    }
}
