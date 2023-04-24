package org.pervishkond.tree;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    protected int number;
    //    protected String color = "Red";

    protected Colors color = Colors.RED;
    protected Node nodeRoot;

    public Node() {
    }

    protected Node(int number) {
        this.number = number;
    }


    protected StringBuilder showTree(StringBuilder result) {
        if (this.leftNode != null) {
            result.append(this.leftNode.showTree(result));
        }
        result.append(" ").append(this.number);
        if (this.rightNode != null) {
            result.append(this.rightNode.showTree(result));
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
        if (isNotNull(this.getLeftNode())) {
            this.getLeftNode().setColor(Colors.BLACK);
        }
        if (isNotNull(this.getRightNode())) {
            this.getRightNode().setColor(Colors.BLACK);
        }
    }

    protected void recoloringAfterRemoving() {
        Node parent = this.searchParentNode(this.nodeRoot);
        Node grandparent = parent.searchParentNode(nodeRoot);
        if (isNotNull(grandparent)) {
            if (grandparent.getLeftNode() == parent) {
                parent.color = grandparent.getRightNode().color;
            } else parent.color = grandparent.getLeftNode().color;
        }
        if (parent.getLeftNode() == this) {
            this.color = parent.getRightNode().color;
        } else this.color = parent.getLeftNode().color;
        if (isNotNull(this.getLeftNode()) && isNotNull(this.getRightNode())) {
            if (isRed(parent)) {
                this.setColor(Colors.BLACK);
            } else {
                this.setColor(Colors.RED);
            }
            if (isBlack(parent.getLeftNode())) {
                this.setColor(Colors.BLACK);
            }
            this.getRightNode().setColor(Colors.BLACK);
            this.getLeftNode().setColor(Colors.BLACK);
        } else {
            if (this.getLeftNode() != null) this.getLeftNode().setColor(Colors.RED);
            else this.getRightNode().setColor(Colors.RED);
        }
    }


    protected Node searchParentNode(Node nodeRoot) {
        Node parentNode = nodeRoot;
        if (this == nodeRoot) {
            return null;
        }
        if ((parentNode.leftNode == this) || (parentNode.rightNode == this)) {
            return parentNode;
        }
        if (this.number < parentNode.number) {
            if (parentNode.leftNode != null) {
                parentNode = this.searchParentNode(parentNode.leftNode);
            }
        } else {
            if (parentNode.rightNode != null) {
                parentNode = this.searchParentNode(parentNode.rightNode);
            }
        }
        return parentNode;
    }

    public int getNumber() {
        return number;
    }

    public Colors getColor() {
        return color;
    }


    protected Node getLeftNode() {
        return this.leftNode;
    }

    protected Node getRightNode() {
        return this.rightNode;
    }

    protected Node getRightNodeLeftChild() {
        return this.rightNode.leftNode;
    }

    protected Node getRightNodeRightChild() {
        return this.rightNode.rightNode;
    }

    protected Node getLeftNodeLeftChild() {
        return this.leftNode.leftNode;
    }

    protected Node getLeftNodeRightChild() {
        return this.leftNode.rightNode;
    }

    protected boolean isNull(Node node) {
        return node == null;
    }

    protected boolean isNotNull(Node node) {
        return node != null;
    }

    // Блок условий optionToBalance
    protected boolean isRightBrotherRed(Node parent) {
        return parent.getRightNode() != null && parent.getRightNode().color == Colors.RED;
    }

    protected boolean isLeftBrotherRed(Node parent) {
        return parent.getLeftNode() != null && parent.getLeftNode().color == Colors.RED;
    }

    //Блок условий на RotateLeft/Right

    protected boolean isLeftNodeBlack(Node node) {
        return node.getLeftNode() == null || node.getLeftNode().color == Colors.BLACK;
    }

    protected boolean isRightNodeBlack(Node node) {
        return node.getRightNode() == null || node.getRightNode().color == Colors.BLACK;
    }

    //Блок условий CheckLeft/Right
    protected boolean isGoingLeft(Node node) {
        return (node.leftNode != null) && (node.color != node.getLeftNode().color) || (node.getLeftNode() != null) && (node.getLeftNode().color == Colors.BLACK);
    }

    protected boolean isGoingRight(Node node) {
        return (node.getRightNode() != null) && (node.color != node.getRightNode().color) || (node.getRightNode() != null) && (node.getRightNode().color == Colors.BLACK);
    }

    protected boolean isNoChildren(Node node) {
        return (isNull(node.getLeftNode()) && (isNull(node.getRightNode())));
    }

    protected boolean isNeighborSameColor(Node node, Node badNode) {
        return (node.getRightNode() == null) && (badNode.color != node.getLeftNode().color) || (node.getLeftNode() == null) && (badNode.color != node.getRightNode().color);
    }

    protected boolean isRed(Node node) {
        return node.color == Colors.RED;
    }

    protected boolean isBlack(Node node) {
        return node.color == Colors.BLACK;
    }

    protected Boolean hasChild(Node node, Node parent) {
        if (parent.getLeftNode() == node) {
            return false;
        } else if (parent.getRightNode() == node) {
            return false;
        } else return true;
    }

}
