package org.pervishkond.tree;

public class Node {
    protected Node rightNode;
    protected Node leftNode;
    protected int number;
    protected int color = 1;


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


//    public Boolean checkingSize(Node parent) {
//        int left = 0;
//        int right = 0;
//        Node nodePointer = parent.leftNode;
//        while (nodePointer != null) {
//            left++;
//            nodePointer = nodePointer.leftNode;
//
//        }
//        nodePointer = parent.rightNode;
//        while (nodePointer != null) {
//            right++;
//            nodePointer = nodePointer.rightNode;
//        }
//        if (right - left == 2) {
//            return true;
//        } else if (left - right == 2) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean optionsToBalance(Node node) {
//        Node parent = whoIsParent(node);
//        if (parent.leftNode == node) {
//            if (checkingSize(parent)) {
//                rotateRight(node, parent);
//                return false;
//            }
//            if (parent.rightNode != node && parent.rightNode != null) {
//                recoloring(parent);
//            } else {
//                rotateRight(node, parent);
//            }
//            Node checkingBadNode = checkLeft();
//            if (checkingBadNode != null) {
//                optionsToBalance(checkingBadNode);
//            }
//
//        } else if (parent.rightNode == node) {
//            if (checkingSize(parent)) {
//                rotateLeft(node, parent);
//                return false;
//            }
//            if (parent.leftNode != null && parent.leftNode != node) {
//                recoloring(parent);
//            } else {
//                rotateLeft(node, parent);
//            }
//            Node checkingBadNode = checkRight();
//            if (checkingBadNode != null) {
//                optionsToBalance(checkingBadNode);
//            }
//        }
//        return true;
//    }
//
//
//    public void recoloring(Node parent) {
//        if ((parent.leftNode != null) && (parent.rightNode != null)) {
//            if (parent == this) {
//                parent.leftNode.color = 0;
//                parent.rightNode.color = 0;
//
//            } else if (parent.color == 0) {
//                parent.color = 1;
//                parent.leftNode.color = 0;
//                parent.rightNode.color = 0;
//            } else {
//                parent.leftNode.color = 1;
//                parent.rightNode.color = 1;
//            }
//
//
//        }
//    }
//
////    public void rotateLeft(Node node, Node parent) {
////        Node nodePointer = parent.rightNode;
////        if (node.leftNode == null) {
////            if (nodePointer.rightNode != null) {
////                parent.leftNode = new Node();
////                parent.leftNode.number = parent.number;
////                if (parent.leftNode.leftNode != null) {
////                    parent.leftNode.leftNode = parent.leftNode.leftNode.leftNode;
////                } else {
////                    parent.leftNode.leftNode = null;
////                }
////                parent.number = parent.rightNode.number;
////                parent.rightNode = node.rightNode;
////            }
////
////
////        } else if (node.rightNode == null) {
////            Node rotationElement = node.leftNode;
////            parent.leftNode = new Node();
////            parent.leftNode.number = parent.number;
////            parent.number = rotationElement.number;
////            parent.rightNode.leftNode = null;
//////
////        } else {
////            nodePointer = parent.leftNode;
////            parent.leftNode = new Node();
////            parent.leftNode.rightNode = node.leftNode;
////            parent.leftNode.leftNode = nodePointer;
////            parent.leftNode.number = parent.number;
////            parent.number = parent.rightNode.number;
////            parent.rightNode = node.rightNode;
////        }
//
//
//    }
//
//    public void rotateRight(Node node, Node parent) {
//        Node nodePointer = parent.leftNode;
//        if (node.rightNode == null) {
//            if (nodePointer.leftNode != null) {
//                parent.rightNode = new Node();
//                parent.rightNode.number = parent.number;
//                if (parent.rightNode.rightNode != null) {
//                    parent.rightNode.rightNode = parent.rightNode.rightNode.rightNode;
//                } else {
//                    parent.rightNode.rightNode = null;
//                }
//                parent.number = parent.leftNode.number;
//                parent.leftNode = node.leftNode;
//            }
//
//        } else if (node.leftNode == null) {
////            Node rootNode = parent;
////            Node rotationElement = node.rightNode;
////            parent = rotationElement;
////            parent.rightNode = new Node();
////            parent.rightNode = rootNode;
////            parent.leftNode.rightNode = null;
////            parent.leftNode = rootNode.leftNode;
//            Node rotationElement = node.rightNode;
//            parent.rightNode = new Node();
//            parent.rightNode.number = parent.number;
//            parent.number = rotationElement.number;
//            parent.leftNode.rightNode = null;
//        } else {
//            nodePointer = parent.rightNode;
//            parent.rightNode = new Node();
//            parent.rightNode.leftNode = node.rightNode;
//            parent.rightNode.rightNode = nodePointer;
//            parent.rightNode.number = parent.number;
//            parent.number = parent.leftNode.number;
//            parent.leftNode = node.leftNode;
//        }
//
//
//    }


}
