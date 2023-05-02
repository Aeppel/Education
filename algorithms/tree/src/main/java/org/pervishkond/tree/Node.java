package org.pervishkond.tree;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    protected int number;
    protected Colors color = Colors.RED;
    protected Node nodeRoot;

    public Node() {
    }

    protected Node(int number) {
        this.number = number;
    }


    protected StringBuilder showTree(StringBuilder result) {
        if (this.getLeftNode() != null) {
            result.append(this.getLeftNode().showTree(result));
        }
        result.append(" ").append(this.number);
        if (this.getRightNode() != null) {
            result.append(this.getRightNode().showTree(result));
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

    protected void deleteRecoloring() {
        Node parent = searchParentNode(nodeRoot);
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
            if (this.getLeftNode() != null) {
                this.getLeftNode().setColor(Colors.RED);
            } else this.getRightNode().setColor(Colors.RED);
        }
    }


    protected Node searchParentNode(Node nodeRoot) {
        Node parentNode = nodeRoot;
        if (this == nodeRoot) {
            return null;
        }
        if ((parentNode.getLeftNode() == this) || (parentNode.getRightNode() == this)) {
            return parentNode;
        }
        if (this.number < parentNode.number) {
            if (parentNode.getLeftNode() != null) {
                parentNode = this.searchParentNode(parentNode.getLeftNode());
            }
        } else {
            if (parentNode.getRightNode() != null) {
                parentNode = this.searchParentNode(parentNode.getRightNode());
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

    protected void setLeftNode(Node node) {
        this.leftNode = node;
    }

    protected void setRightNode(Node node) {
        this.rightNode = node;
    }

    protected void setLeftNodeRightChild(Node node) {
        this.leftNode.rightNode = node;
    }

    protected void setRightNodeLeftChild(Node node) {
        this.rightNode.leftNode = node;
    }


    protected Node getLeftNode() {
        return this.leftNode;
    }

    protected Node getRightNode() {
        return this.rightNode;
    }


    protected Node getLeftGrandchildOfRightChild() {
        return this.rightNode.leftNode;
    }

    protected Node getRightGrandchildOfRightChild() {
        return this.rightNode.rightNode;
    }

    protected Node getLeftGrandchildOfLeftChild() {
        return this.leftNode.leftNode;
    }

    protected Node getRightGrandchildOfLeftChild() {
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
    protected boolean isLeftNodeBlackTooOrDifferentColor(Node node) {
        return (node.leftNode != null) && (node.color != node.getLeftNode().color) || (node.getLeftNode() != null) && (node.getLeftNode().color == Colors.BLACK);
    }

    protected boolean isRightNodeBlackTooOrDifferentColor(Node node) {
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

    protected boolean isMore(Node node, Node checkNode) {
        return checkNode.number > node.number && nodeRoot.getLeftNode() != null;
    }

    protected boolean isLess(Node node, Node checkNode) {
        return checkNode.number < node.number && nodeRoot.getRightNode() != null;
    }

    protected boolean isEqual(Node node, Node checkNode) {
        return checkNode.number == node.number;

    }

    protected boolean IsNotNullAndRed(Node node) {
        return isNotNull(node) && isRed(node);
    }

}
