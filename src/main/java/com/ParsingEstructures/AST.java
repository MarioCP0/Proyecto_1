package com.ParsingEstructures;

public class AST<T> {
    private Node<T> root;

    public AST(T data) {
        this.root = new Node<T>(data);
    }

    public Node<T> getRoot() {
        return root;
    }

}
