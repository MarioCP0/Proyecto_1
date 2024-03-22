package com.ParsingEstructures;

import java.util.ArrayList;

public class AST<T> {
    private Node<T> root;

    public AST(T data) {
        this.root = new Node<T>(data);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void addChild(AST<T> data){
        root.children.add(data);
    }

    public ArrayList<AST<T>> getChildren() {
        return root.children;
    }

    public void PrintTree(){
        System.out.print(root.getData());
        for (AST<T> child : root.children){
            System.out.println("|");
            child.PrintTree();
        }
    }
}
