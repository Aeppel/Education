package com.pervishkond.list.linked;

public class MyLinkedList extends Node {
    protected int length;

    public MyLinkedList(int size) {
        for (int i = 0; i < size; i++) {
            add();
        }
    }

    public void add() {
        node = new Node();
        for (int i = 1; i <= length; i++) {
            node.set((i - length - 1) * -1);
        }
        length++;
    }

    protected Object get(int number) {
        Node nodePointer = node;
        for (int i = 0; i < number; i++) {
            nodePointer = nodePointer.node;
        }
        return nodePointer;
    }

    public boolean delete(int number) {
        if (number == 1) {
            Node nodePointer = node;
            node = nodePointer.node;
            return true;
        }
        Node nodePointer = node;
        Node nodeToAdd = nodePointer.node;

        for (int i = 0; i < number - 2; i++) {
            nodePointer = nodePointer.node;
            nodeToAdd = nodePointer.node;
        }
        nodePointer.node = nodeToAdd.node;
        length--;
        return true;
    }

    public void show() {
        Node nodePointer = node;
        for (int i = 0; i < length - 1; i++) {
            System.out.print(nodePointer.number + " ");
            nodePointer = nodePointer.node;
        }
    }

    public int size() {
        return length;
    }

}

