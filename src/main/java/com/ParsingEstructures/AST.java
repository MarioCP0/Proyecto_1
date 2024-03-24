package com.ParsingEstructures;

import java.util.ArrayList;
/**
 * Es es k-ario árbol, donde cada nodo es su propio arbol casi que y cada hijo tambien 
 * 
 * @param <T> El tipo del valor contenido por los nodos en este AST. Es como elegir
 * el sabor de tu cálculo, y te aseguro, es una elección crucial.
 */
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
}
