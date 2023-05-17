package org.pervishkond.tree;

import java.util.StringJoiner;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    private int number;
    private Colors color;

    private Node parent;

    public Node() {
        setColor(Colors.BLACK);
    }

    protected Node(int number) {
        setColor(Colors.RED);
        this.number = number;
    }

    protected Node(int number, Colors color) {
        setColor(color);
        this.number = number;
    }


    protected StringJoiner showTree(StringJoiner result) {
        if (this.getLeftNode() != null) {
            result.merge(this.getLeftNode().showTree(result));
        }
        result.add(String.valueOf(this.number));
        if (this.getRightNode() != null) {
            result.merge(this.getRightNode().showTree(result));
        }
        return result;
    }

    protected void setColor(Colors color) {
        this.color = color;
    }

    protected void recoloring(Node nodeRoot) {
        this.setColor(Colors.RED);
        if (this.getLeftNode() != null) {
            this.getLeftNode().setColor(Colors.BLACK);
        }
        if (this.getRightNode() != null) {
            this.getRightNode().setColor(Colors.BLACK);
        }
        nodeRoot.setColor(Colors.BLACK);
    }

    protected void reverseRecoloring() {
        this.setColor(Colors.RED);
        if (Utils.isNotNull(this.getLeftNode())) {
            this.getLeftNode().setColor(Colors.BLACK);
        }
        if (Utils.isNotNull(this.getRightNode())) {
            this.getRightNode().setColor(Colors.BLACK);
        }
    }

    protected void deleteRecoloring(Node nodeRoot) {
        Node parent = this.getParent();
        Node grandparent = parent.getParent();
        if (Utils.isNotNull(grandparent)) {
            if (grandparent.getLeftNode() == parent) {
                parent.color = grandparent.getRightNode().color;
            } else parent.color = grandparent.getLeftNode().color;
        }
        if (parent.getLeftNode() == this) {
            this.color = parent.getRightNode().color;
        } else this.color = parent.getLeftNode().color;
        if (Utils.isNotNull(this.getLeftNode()) && Utils.isNotNull(this.getRightNode())) {
            if (Utils.isRed(parent)) {
                this.setColor(Colors.BLACK);
            } else {
                this.setColor(Colors.RED);
            }
            if (Utils.isBlack(parent.getLeftNode())) {
                this.setColor(Colors.BLACK);
            }
            this.getRightNode().setColor(Colors.BLACK);
            this.getLeftNode().setColor(Colors.BLACK);
        } else {
            if (this.getLeftNode() != null) {
                this.getLeftNode().setColor(Colors.RED);
            } else this.getRightNode().setColor(Colors.RED);
        }
    }

    //    protected Node searchParentNode(Node nodeRoot) {
//        Node parentNode = nodeRoot;
//        if (this == nodeRoot) {
//            return null;
//        }
//        if ((parentNode.getLeftNode() == this) || (parentNode.getRightNode() == this)) {
//            return parentNode;
//        }
//        if (this.number < parentNode.number) {
//            if (parentNode.getLeftNode() != null) {
//                parentNode = this.searchParentNode(parentNode.getLeftNode());
//            }
//        } else {
//            if (parentNode.getRightNode() != null) {
//                parentNode = this.searchParentNode(parentNode.getRightNode());
//            }
//        }
//        return parentNode;
//    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public Node getParent() {
        return parent;
    }

    public int getNumber() {
        return number;
    }

    public Colors getColor() {
        return color;
    }

    public void setLeftNode(Node node) {
        this.leftNode = node;
    }

    public void setRightNode(Node node) {
        this.rightNode = node;
    }

    public void setLeftNodeRightChild(Node node) {
        this.leftNode.rightNode = node;
    }

    public void setRightNodeLeftChild(Node node) {
        this.rightNode.leftNode = node;
    }


    public Node getLeftNode() {
        return this.leftNode;
    }

    public Node getRightNode() {
        return this.rightNode;
    }


    public Node getLeftGrandchildOfRightChild() {
        return this.rightNode.leftNode;
    }

    public Node getRightGrandchildOfRightChild() {
        return this.rightNode.rightNode;
    }

    public Node getLeftGrandchildOfLeftChild() {
        return this.leftNode.leftNode;
    }

    public Node getRightGrandchildOfLeftChild() {
        return this.leftNode.rightNode;
    }
}

