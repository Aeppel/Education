package org.pervishkond.tree;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    protected int number;
    protected String color = "Red";
    protected Node nodeRoot;


    public Node() {
    }

    protected Node(int number) {
        this.number = number;
    }


    protected StringBuilder showInside(Node node) {
        StringBuilder result = new StringBuilder("");
        if (leftNode != null) {
            result.append(leftNode.showInside(node.leftNode));
        }
        result.append(" ").append(this.number);
        if (rightNode != null) {
            result.append(rightNode.showInside(node.rightNode));
        }
        return result;
    }

    protected void setColor(String color) {
        this.color = color;
    }

    protected void recoloring(Node parent, Node nodeRoot) {
        parent.setColor("Red");
        if (parent.leftNode != null) {
            parent.leftNode.setColor("Black");
        }
        if (parent.rightNode != null) {
            parent.rightNode.setColor("Black");
        }
        nodeRoot.setColor("Black");
    }

    protected Node whoIsParent(Node node, Node nodeRoot) {
        Node parentNode = nodeRoot;
        if (node == nodeRoot) {
            return null;
        }
        if ((parentNode.leftNode == node) || (parentNode.rightNode == node)) {
            return parentNode;
        }
        if (node.number < parentNode.number) {
            if (parentNode.leftNode != null) {
                parentNode = whoIsParent(node, parentNode.leftNode);
            }
        } else {
            if (parentNode.rightNode != null) {
                parentNode = whoIsParent(node, parentNode.rightNode);
            }
        }
        return parentNode;
    }

    public int getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }



    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }


}
