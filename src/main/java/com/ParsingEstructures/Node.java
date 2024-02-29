package com.ParsingEstructures;
import java.util.ArrayList;

public class Node<T> {
    private T data;
    private ArrayList<Node<T>> children; 

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<Node<T>>();
    }

    public void addChild(Node<T> child) {
        children.add(child);
    }

    public T getData() {
        return data;
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

}
