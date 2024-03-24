package com.ParsingEstructures;
import java.util.ArrayList;

/**
 * dude, is a node like, bruh
 */
public class Node<T> {
    private T data;
    public ArrayList<AST<T>> children;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<AST<T>>();
    }

    public T getData() { 
        return data;
    }

}