package org.pervishkond.tree;

import java.util.StringJoiner;

public class Node {
    private Node rightNode;
    private Node leftNode;
    private int number;
    private Colors color;
    private Node parent;

    public Node() {
        setColor(Colors.BLACK);
    }

    public Node(int number) {
        setColor(Colors.RED);
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

    public Node getLeftNode() {
        return this.leftNode;
    }

    public void setRightNode(Node node) {
        this.rightNode = node;
    }

    public Node getRightNode() {
        return this.rightNode;
    }

    public void setLeftGrandchildOfRightChild(Node node) {
        this.leftNode.rightNode = node;
    }

    public Node getLeftGrandchildOfRightChild() {
        return this.rightNode.leftNode;
    }

    public void setRightGrandchildOfRightChild(Node node) {
        this.rightNode.leftNode = node;
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

    protected Node(int number, Colors color) {
        setColor(color);
        this.number = number;
    }

    protected StringJoiner showTree(StringJoiner result) {
        if (Utils.isNotNull(this.getLeftNode())) {
            this.getLeftNode().showTree(result);
        }
        result.add(String.valueOf(this.number));
        if (Utils.isNotNull(this.getRightNode())) {
            this.getRightNode().showTree(result);
        }
        return result;
    }

    protected void setColor(Colors color) {
        this.color = color;
    }

    protected void recoloring() {
        if (Utils.isNull(this.getParent())) {
            this.setColor(Colors.BLACK);
        } else {
            this.setColor(Colors.RED);
        }
        if (Utils.isNotNull(this.getLeftNode())) {
            this.getLeftNode().setColor(Colors.BLACK);
        }
        if (Utils.isNotNull(this.getRightNode())) {
            this.getRightNode().setColor(Colors.BLACK);
        }
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

    protected void RecoloringAfterRemove() {
        parent = this.getParent();
        Node grandparent = parent.getParent();
        if (Utils.isNotNull(grandparent)) {
            if (grandparent.getLeftNode() == parent) {
                parent.color = grandparent.getRightNode().color;
            } else parent.color = grandparent.getLeftNode().color;
        }
        if (parent.getLeftNode() == this) {
            this.color = parent.getRightNode().color;
        } else {
            this.color = parent.getLeftNode().color;
        }
        if (Utils.IsChildren(this)) {
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
            if (Utils.isNotNull(this.getLeftNode())) {
                this.getLeftNode().setColor(Colors.RED);
            } else {
                this.getRightNode().setColor(Colors.RED);
            }
        }
    }

    protected Node checkRight(Node node) {
        Node toBalance = null;
        if (Utils.isLeftNodeBlackTooOrDifferentColor(node)) {
            toBalance = checkRight(node.getLeftNode());
        }
        if (Utils.isNull(toBalance)) {
            toBalance = node;
            if (Utils.isRightNodeBlackTooOrDifferentColor(node)) {
                toBalance = checkRight(node.getRightNode());
            }
        }
        if (Utils.isNull(toBalance) || Utils.isNoChildren(node) || Utils.isNeighborSameColor(node, toBalance)) {
            return null;
        }
        return toBalance;
    }

    protected Node checkLeft(Node node) {
        Node toBalance = null;
        if (Utils.isRightNodeBlackTooOrDifferentColor(node)) {
            toBalance = checkLeft(node.getRightNode());
        }
        if (Utils.isNull(toBalance)) {
            toBalance = node;
            if (Utils.isLeftNodeBlackTooOrDifferentColor(node)) {
                toBalance = checkLeft(node.getLeftNode());
            }
        }
        if (Utils.isNull(toBalance) || Utils.isNoChildren(node) || Utils.isNeighborSameColor(node, toBalance)) {
            return null;
        }
        return toBalance;
    }

    protected Node rotateLeft(Node parent) {
        Node nodePointer = this.getLeftNode();
        if (Utils.isLeftNodeBlack(this)) {
            this.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            this.setLeftNode(parent);
            parent.setRightNode(nodePointer);
            if (Utils.isNull(parent.getParent())) {
                parent.setParent(this);
                return this;
            } else {
                Node grandparent = parent.getParent();
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(this);
                } else {
                    grandparent.setRightNode(this);
                }
                this.setParent(grandparent);
            }
            this.getLeftNode().setParent(this);
        } else if (Utils.isRightNodeBlack(this)) {
            nodePointer = this.getRightGrandchildOfLeftChild();
            this.setLeftGrandchildOfRightChild(this);
            parent.setRightNode(this.getLeftNode());
            this.setLeftNode(nodePointer);
            this.getLeftNode().setParent(this);
            this.setParent(nodePointer);
        } else if (Utils.isNull(parent.getLeftNode()) || parent.getLeftNode().getNumber() == 0) {
            if (Utils.isNull(parent.getParent())) { // в условии: если нет родителя выше то это рутнод. Заменяем руты на пэрэнт
                parent.setRightNode(this.getLeftNode());
                this.setLeftNode(parent);
                this.getRightNode().setColor(Colors.BLACK);
                this.setColor(Colors.BLACK);
                this.getLeftNode().setParent(this);
                return this;
            } else {
                Node grandParent = parent.getParent();
                if (grandParent.getLeftNode() == parent) {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(this);
                    grandParent.getLeftNode().setParent(grandParent);
                } else {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(this);
                    grandParent.getRightNode().setParent(grandParent);
                }
                this.setLeftGrandchildOfRightChild(nodePointer);
                parent.recoloring();
            }
        }
        return null;
    }

    protected Node rotateRight(Node parent) {
        Node nodePointer = this.getRightNode();
        if (Utils.isRightNodeBlack(this)) {
            this.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            this.setRightNode(parent);
            parent.setLeftNode(nodePointer);
            if (Utils.isNull(parent.getParent())) {
                parent.setParent(this);
                return this;
            } else {
                Node grandparent = parent.getParent();
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(this);
                } else {
                    grandparent.setRightNode(this);
                }
                this.setParent(grandparent);
            }
            this.getRightNode().setParent(this);
        } else if (Utils.isLeftNodeBlack(this)) {
            nodePointer = this.getLeftGrandchildOfRightChild();
            this.setRightGrandchildOfRightChild(this);
            parent.setLeftNode(this.getRightNode());
            this.setRightNode(nodePointer);
            this.getRightNode().setParent(this);
            this.setParent(nodePointer);
        } else if (Utils.isNull(parent.getRightNode())) {
            if (Utils.isNull(parent.getParent())) {
                parent.setLeftNode(this.getRightNode());
                this.setRightNode(parent);
                this.getLeftNode().setColor(Colors.BLACK);
                this.getRightNode().setParent(this);
                return this;
            } else {
                Node grandParent = parent.getParent();
                if (grandParent.getRightNode() == parent) {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(this);
                    grandParent.getRightNode().setParent(grandParent);
                } else {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(this);
                    grandParent.getLeftNode().setParent(grandParent);
                }
                this.setRightGrandchildOfRightChild(nodePointer);
                parent.recoloring();
            }
        }
        return null;
    }
}

