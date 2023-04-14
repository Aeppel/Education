package org.pervishkond.tree;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    protected int number;
    protected int color = 1;
    protected Node nodeRoot;


    public Node() {
    }

    protected Node(int number) {
        this.number = number;
    }


    protected String show1(Node node) {
        String result = "";
        if (leftNode != null) {
            result = leftNode.show1(node.leftNode);
        }
        result += " " + this.number;
        if (rightNode != null) {
            result += rightNode.show1(node.rightNode);
        }
        return result;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public Node getNodeRoot() {
        return nodeRoot;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }


}
