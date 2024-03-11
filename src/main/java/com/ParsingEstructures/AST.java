package com.ParsingEstructures;

import java.util.ArrayList;

public class AST<T> {
    private Node<T> root;
    private ArrayList<Node<T>> children;

    public AST(T data) {
        this.root = new Node<T>(data);
        children = new ArrayList<Node<T>>();
    }

    public Node<T> getRoot() {
        return root;
    }

    public void addChild(T ChildData) {
        Node<T> newChild = new Node<T>(ChildData);
        children.add(newChild);
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

}
