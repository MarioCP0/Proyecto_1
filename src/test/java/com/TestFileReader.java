package com;

import java.util.ArrayList;
import java.util.Arrays;
import com.Tokenizer.*;
public class TestFileReader {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(
                "(", "defun", "fibonacci", "(", "n", ")", "(", "cond", "(", "<=", "n", "1", ")", "n", ")",
                "(", "t", "(", "+", "(", "fibonacci", "(", "-", "n", "1", ")", ")", "(", "fibonacci", "(", "-", "n", "2", ")", ")", ")", ")"
        ));

        FileReaderExample lector = new FileReaderExample(); 
        String filename = "your_file_name.txt"; // Replace "your_file_name.txt" with your actual file name

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(lector.readTokensFromFile(filename).get(i))){
                
            }
        }


    }
}

