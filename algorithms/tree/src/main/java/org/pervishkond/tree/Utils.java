package org.pervishkond.tree;

class Utils {

    public static boolean isNull(Node node) {
        return node == null;
    }

    public static boolean isNotNull(Node node) {
        return node != null;
    }

    //------------------Блок условий optionToBalance--------------------------------
    public static boolean isRightBrotherRed(Node parent) {
        return parent.getRightNode() != null && parent.getRightNode().getColor() == Colors.RED;
    }

    public static boolean isLeftBrotherRed(Node parent) {
        return parent.getLeftNode() != null && parent.getLeftNode().getColor() == Colors.RED;
    }

    //------------------Блок условий на RotateLeft/Right-----------------------------
    public static boolean isLeftNodeBlack(Node node) {
        return node.getLeftNode() == null || node.getLeftNode().getColor() == Colors.BLACK;
    }

    public static boolean isRightNodeBlack(Node node) {
        return node.getRightNode() == null || node.getRightNode().getColor() == Colors.BLACK;
    }

    //------------------Блок условий CheckLeft/Right---------------------------------
    public static boolean isLeftNodeBlackTooOrDifferentColor(Node node) {
        return (node.leftNode != null) && (node.getColor() != node.getLeftNode().getColor()) || (node.getLeftNode() != null) && (node.getLeftNode().getColor() == Colors.BLACK);
    }

    public static boolean isRightNodeBlackTooOrDifferentColor(Node node) {
        return (node.getRightNode() != null) && (node.getColor() != node.getRightNode().getColor()) || (node.getRightNode() != null) && (node.getRightNode().getColor() == Colors.BLACK);
    }

    public static boolean isNoChildren(Node node) {
        return (isNull(node.getLeftNode()) && (isNull(node.getRightNode())));
    }

    public static boolean isNeighborSameColor(Node node, Node badNode) {
        return (node.getRightNode() == null) && (badNode.getColor() != node.getLeftNode().getColor()) || (node.getLeftNode() == null) && (badNode.getColor() != node.getRightNode().getColor());
    }

    public static boolean isRed(Node node) {
        return node.getColor() == Colors.RED;
    }

    public static boolean isBlack(Node node) {
        return node.getColor() == Colors.BLACK;
    }

    public static boolean isMore(Node node, Node checkNode) {
        return checkNode.getNumber() > node.getNumber() && checkNode.getLeftNode() != null;
    }

    public static boolean isLess(Node node, Node checkNode) {
        return checkNode.getNumber() < node.getNumber() && checkNode.getRightNode() != null;
    }

    public static boolean isEquals(Node addNode, Node checkNode) {
        return addNode == null || addNode.getNumber() == checkNode.getNumber();
    }

    public static boolean isNotNullAndRed(Node node) {
        return isNotNull(node) && isRed(node);
    }

    public static void put(Node toPut) {
        if (toPut.getParent().getNumber() > toPut.getNumber()) {
            toPut.getParent().setLeftNode(toPut);
        } else {
            toPut.getParent().setRightNode(toPut);
        }
    }

}


