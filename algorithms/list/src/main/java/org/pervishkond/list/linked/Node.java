package org.pervishkond.list.linked;

public class Node {
    protected Node node;


    protected int number = 1;

    protected Node() {

    }

    protected Node(Node node, int number) {
        this.node = node;
        this.number = number;
    }


    protected void set(int number) {
        node = new Node(node, number + 1);
    }


}