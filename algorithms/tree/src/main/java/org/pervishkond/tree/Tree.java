package org.pervishkond.tree;

import java.util.StringJoiner;

class Tree {

    Node nodeRoot;
    private int size = 0;

    public void add(int number) {
        if (isEmpty()) {
            nodeRoot = new Node(number, Colors.BLACK);
            size++;
        } else {
            searchPlaceToPut(new Node(number), getNodeRoot());
        }
    }

    private void rebalance(Node node) {
        if (node == getNodeRoot()) {
            getNodeRoot().recoloring(getNodeRoot());
        } else {
            Node parent = node.getParent();
            if (parent.getLeftNode() == node) {
                if (Utils.isRightBrotherRed(parent)) {
                    parent.recoloring(getNodeRoot());
                } else {
                    rotateRight(node, parent);
                }
            } else if (parent.getRightNode() == node) {
                if (Utils.isLeftBrotherRed(parent)) {
                    parent.recoloring(getNodeRoot());
                } else {
                    rotateLeft(node, parent);
                }
            } else {
                parent.recoloring(getNodeRoot());
            }
            Node NodeForBalance = checkLeft(getNodeRoot());
            if (Utils.isNotNull(NodeForBalance)) {
                rebalance(NodeForBalance);
            }
            NodeForBalance = checkRight(getNodeRoot());
            if (Utils.isNotNull(NodeForBalance)) {
                rebalance(NodeForBalance);
            }
        }
    }

    protected void rotateLeft(Node node, Node parent) {
        Node nodePointer = node.getLeftNode();
        if (Utils.isLeftNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.setLeftNode(parent);
            parent.setRightNode(nodePointer);
            if (parent == getNodeRoot()) {
                nodeRoot = node;
                node.setParent(null);
            } else {
                Node grandparent = parent.getParent();
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(node);
                } else {
                    grandparent.setRightNode(node);
                }
                node.setParent(grandparent);
            }
            node.getLeftNode().setParent(node);
        } else if (Utils.isRightNodeBlack(node)) {
            nodePointer = node.getRightGrandchildOfLeftChild();
            node.setLeftNodeRightChild(node);
            parent.setRightNode(node.getLeftNode());
            node.setLeftNode(nodePointer);
            node.getLeftNode().setParent(node);
            node.setParent(nodePointer);

        } else if (Utils.isNull(parent.getLeftNode()) || parent.getLeftNode().getNumber() == 0) {
            nodePointer = getNodeRoot();
            if (parent == getNodeRoot()) {
                nodeRoot = node;
                nodeRoot.setParent(null);
                nodePointer.setRightNode(node.getLeftNode());
                getNodeRoot().setLeftNode(nodePointer);
                getNodeRoot().getRightNode().setColor(Colors.BLACK);
                getNodeRoot().setColor(Colors.BLACK);
                nodeRoot.getLeftNode().setParent(nodeRoot);
            } else {
                Node grandParent = parent.getParent();
                if (grandParent.getLeftNode() == parent) {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(node);
                    grandParent.getLeftNode().setParent(grandParent);
                } else {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(node);
                    grandParent.getRightNode().setParent(grandParent);
                }
                node.setLeftNodeRightChild(nodePointer);
                parent.recoloring(getNodeRoot());
            }
        }
    }

    protected void rotateRight(Node node, Node parent) {
        Node nodePointer = node.getRightNode();
        if (Utils.isRightNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.setRightNode(parent);
            parent.setLeftNode(nodePointer);
            if (parent == getNodeRoot()) {
                nodeRoot = node;
                node.setParent(null);
            } else {
                Node grandparent = parent.getParent();
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(node);
                } else {
                    grandparent.setRightNode(node);
                }
                node.setParent(grandparent);
            }
            node.getRightNode().setParent(node);
        } else if (Utils.isLeftNodeBlack(node)) {
            nodePointer = node.getLeftGrandchildOfRightChild();
            node.setRightNodeLeftChild(node);
            parent.setLeftNode(node.getRightNode());
            node.setRightNode(nodePointer);
            node.getRightNode().setParent(node);
            node.setParent(nodePointer);

        } else if (Utils.isNull(parent.getRightNode())) {
            nodePointer = getNodeRoot();
            if (parent == getNodeRoot()) {
                nodeRoot = node;
                nodeRoot.setParent(null);
                nodePointer.setLeftNode(node.getRightNode());
                getNodeRoot().setRightNode(nodePointer);
                getNodeRoot().getLeftNode().setColor(Colors.BLACK);
                nodeRoot.getRightNode().setParent(nodeRoot);
            } else {
                Node grandParent = parent.getParent();
                if (grandParent.getRightNode() == parent) {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(node);
                } else {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(node);
                    grandParent.getLeftNode().setParent(grandParent);
                }
                node.setRightNodeLeftChild(nodePointer);
                parent.recoloring(getNodeRoot());
            }
        }
    }

    protected Node checkRight(Node node) {
        Node NodeForBalance = null;
        if (Utils.isLeftNodeBlackTooOrDifferentColor(node)) {
            NodeForBalance = checkRight(node.getLeftNode());
        }
        if (Utils.isNull(NodeForBalance)) {
            NodeForBalance = node;
            if (Utils.isRightNodeBlackTooOrDifferentColor(node)) {
                NodeForBalance = checkRight(node.getRightNode());
            }
        }
        if (Utils.isNull(NodeForBalance) || Utils.isNoChildren(node) || Utils.isNeighborSameColor(node, NodeForBalance)) {
            return null;
        }
        return NodeForBalance;
    }

    protected Node checkLeft(Node node) {
        Node NodeForBalance = null;
        if (Utils.isRightNodeBlackTooOrDifferentColor(node)) {
            NodeForBalance = checkLeft(node.getRightNode());
        }
        if (NodeForBalance == null) {
            NodeForBalance = node;
            if (Utils.isLeftNodeBlackTooOrDifferentColor(node)) {
                NodeForBalance = checkLeft(node.getLeftNode());
            }
        }
        if (NodeForBalance == null || Utils.isNoChildren(node) || Utils.isNeighborSameColor(node, NodeForBalance)) {
            return null;
        }
        return NodeForBalance;
    }

    public void show() {
        StringJoiner result = new StringJoiner(", ");
        getNodeRoot().showTree(result);
    }

    public void remove(int number) {
        Node removeNode = search(number, getNodeRoot());
        if (removeNode == null) {
            return;
        }
        size--;
        Node parent = removeNode.getParent();
        if ((Utils.isRed(removeNode)) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
            if (parent.getLeftNode() == removeNode) {
                parent.setLeftNode(null);
            } else {
                parent.setRightNode(null);
            }
            return;
        }
        if (removeNode.getLeftNode() != null && removeNode.getRightNode() != null) {
            Node maxNode = searchOfMaxNumber(removeNode.getLeftNode());
            if (maxNode == removeNode.getLeftNode()) {
                swap(maxNode, removeNode, parent);
                return;
            }
            Node parentMaxNode = maxNode.getParent();
            if (checkingNephew(maxNode) == null) {
                parentMaxNode.setRightNode(null);
                maxNode.setRightNode(removeNode.getRightNode());
                removeNode.getRightNode().setParent(maxNode);
            }
            if (parent == null) {
                nodeRoot = maxNode;
                maxNode.setLeftNode(removeNode.getLeftNode());
                getNodeRoot().setColor(Colors.BLACK);
                nodeRoot.setParent(null);
                removeNode.getLeftNode().setParent(maxNode);
                return;
            }
            if (parent.getLeftNode() == removeNode) {
                if (maxNode.getLeftNode() != null) {
                    maxNode.getLeftNode().setColor(maxNode.getColor());
                    parentMaxNode.setRightNode(maxNode.getLeftNode());
                    maxNode.getLeftNode().setParent(parentMaxNode);
                }
                Node nodePointer = parent.getLeftGrandchildOfLeftChild();
                parent.setLeftNode(maxNode);
                maxNode.setParent(parent);
                maxNode.setLeftNode(nodePointer);
                maxNode.getLeftNode().setParent(maxNode);
                maxNode.setColor(removeNode.getColor());

            } else {
                if (maxNode.getRightNode() != null) {
                    parentMaxNode.setRightNode(maxNode.getLeftNode());
                    maxNode.getLeftNode().setParent(parentMaxNode);
                }
                Node nodePointer = parent.getLeftGrandchildOfRightChild();
                parent.setRightNode(maxNode);
                maxNode.setLeftNode(nodePointer);
                maxNode.setColor(removeNode.getColor());
                parent.getRightNode().setParent(parent);
                parent.getLeftNode().setParent(parent);
            }
            if (Utils.isBlack(maxNode) && maxNode.getLeftNode() == null)
                balancingAfterRemoving(maxNode);
            return;
        }
        if (Utils.isBlack(removeNode) && (removeNode.getLeftNode() != null || removeNode.getRightNode() != null)) {
            if (removeNode.getLeftNode() != null) {
                if (parent.getLeftNode() == removeNode) {
                    parent.setLeftNode(removeNode.getLeftNode());
                    parent.getLeftNode().setColor(Colors.BLACK);
                } else {
                    parent.setRightNode(removeNode.getLeftNode());
                    parent.getRightNode().setColor(Colors.BLACK);
                }
            } else {
                if (getNodeRoot() == removeNode) {
                    nodeRoot = (removeNode.getRightNode());
                    getNodeRoot().setColor(Colors.BLACK);
                } else if (parent.getLeftNode() == removeNode) {
                    parent.setLeftNode(removeNode.getRightNode());
                    parent.getLeftNode().setColor(Colors.BLACK);
                } else {
                    parent.setRightNode(removeNode.getRightNode());
                    parent.getRightNode().setColor(Colors.BLACK);
                }
            }
        }
        if (Utils.isBlack(removeNode) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
            if (parent.getLeftNode() == removeNode) {
                parent.setLeftNode(null);
            } else {
                parent.setRightNode(null);
            }
            balancingAfterRemoving(parent);
        }
    }


    private Node search(int number, Node node) {
        Node nodePointer = node;
        if (Utils.isNull(nodePointer)) {
            return null;
        }
        if (number == node.getNumber()) {
            return nodePointer;
        } else if (number > nodePointer.getNumber()) {
            nodePointer = search(number, node.getRightNode());
        } else {
            nodePointer = search(number, node.getLeftNode());
        }
        return nodePointer;
    }

    private Node searchOfMaxNumber(Node node) {
        Node nodePointer = node;
        if (Utils.isNotNull(nodePointer.getRightNode())) {
            nodePointer = searchOfMaxNumber(node.getRightNode());
        }
        return nodePointer;
    }

    private void balancingAfterRemoving(Node node) { //parent
        Node parent = node.getParent();// grandfather
        if (Utils.isNull(parent)) {
            parent = getNodeRoot();
        }
        if (Utils.isNull(node.getLeftNode())) {
            Node nodePointer = new Node();
            node.setLeftNode(nodePointer);
            Node child = parent.getRightNode();
            if (Utils.isNull(child.getRightNode())) {
                child.setRightNode(new Node());
            }
            if (Utils.isNull(child.getLeftNode())) {
                child.setLeftNode(new Node());
            }

            if (Utils.isRed(node.getRightNode()) && Utils.isBlack(node.getLeftGrandchildOfRightChild())) {//brother is black, one left child is red, but right child is black
                rotateRight(node, parent);
                if (Utils.isNotNull(child.getLeftNode())) {
                    rotateLeft(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                }
                if (Utils.isNotNull(child.getRightNode())) {
                    rotateRight(node.getRightNode(), node);
                }
                if (Utils.isNoChildren(node)) {
                    parent = node.getParent();
                    parent.deleteRecoloring(nodeRoot);
                } else node.deleteRecoloring(nodeRoot);

            } else if (Utils.isBlack(child.getRightNode()) && Utils.isBlack(child.getLeftNode())) { // brother's black, children are black
                Node nephew = checkingNephew(node);
                if (Utils.isNotNull(nephew) && Utils.isRed(nephew)) {
                    if (node.getLeftGrandchildOfRightChild() == nephew) {
                        rotateLeft(node.getRightNode(), node);
                        nephew.setColor(Colors.BLACK);
                        nephew.getRightNode().setColor(Colors.RED);
                        rotateLeft(node.getRightNode(), node);
                    } else {
                        rotateLeft(node.getRightNode(), node);
                    }
                    node.getParent().deleteRecoloring(nodeRoot);
                } else if (Utils.isRed(parent)) {
                    parent.setColor(Colors.BLACK);
                    parent.getRightNode().setColor(Colors.RED);
                } else if (Utils.isBlack(node.getLeftNode()) && Utils.isBlack(node.getRightNode())) {
                    pushUp(node);
                }
                if ((Utils.isNotNull(node.getRightNode()) && Utils.isRed(node.getRightNode())) || (Utils.isNotNull(node.getLeftNode()) && Utils.isRed(node.getLeftNode())))
                    if (Utils.isRed(parent.getLeftNode())) {
                        nodePointer = parent.getLeftNode();
                        if (Utils.isBlack(nodePointer.getLeftNode()) && Utils.isBlack(nodePointer.getRightNode())) {
                            Node nodePointerChecker = nodePointer.getLeftNode();
                            if (Utils.isNotNullAndRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (Utils.isNotNullAndRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                            nodePointerChecker = nodePointer.getRightNode();
                            if (Utils.isNotNullAndRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (Utils.isNotNullAndRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                        }
                    }


            } else if ((Utils.isRed(node) && Utils.isBlack(node.getLeftNode())) && Utils.isBlack(parent.getRightNode())) {
                node.setColor(Colors.BLACK);
                node.getRightNode().setColor(Colors.RED);
            } else if (Utils.isRed(parent.getRightGrandchildOfRightChild()) || Utils.isRed(parent.getLeftGrandchildOfRightChild())) {
                node.getRightNode().setColor(Colors.RED);
                rotateLeft(parent.getRightNode(), parent);
                if (parent.getParent() != getNodeRoot()) {
                    parent.getParent().reverseRecoloring();
                }
            }
            if ((Utils.isNotNull(node.getLeftNode()) && Utils.isBlack(node.getLeftNode()) && Utils.isNotNull(node.getRightNode()) && Utils.isBlack(node.getRightNode())) && Utils.isNotNull(node.getLeftGrandchildOfRightChild()) && Utils.isRed(node.getLeftGrandchildOfRightChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                node.setLeftNode(null);
                rotateLeft(node.getRightNode(), node);
                node.getParent().deleteRecoloring(nodeRoot);
            }

            if (Utils.isNotNull(child.getLeftNode()) && child.getLeftNode().getNumber() == 0)
                child.setLeftNode(null);
            if (Utils.isNotNull(child.getRightNode()) && child.getRightNode().getNumber() == 0)
                child.setRightNode(null);

        } else if (Utils.isNull(node.getRightNode())) {

            Node nodePointer = new Node();

            node.setRightNode(nodePointer);
            Node child = parent.getLeftNode();
            if (Utils.isNull(child.getRightNode())) {
                child.setRightNode(new Node());
            }
            if (Utils.isNull(child.getLeftNode())) {
                child.setLeftNode(new Node());
            }

            if (Utils.isRed(node.getLeftNode()) && Utils.isBlack(node.getRightGrandchildOfLeftChild())) {
                rotateLeft(node, parent);

                if (node.getRightGrandchildOfLeftChild() != null) {
                    rotateLeft(node.getRightGrandchildOfLeftChild(), node.getLeftNode());
                }
                if (node.getLeftGrandchildOfLeftChild() != null) {
                    rotateRight(node.getLeftNode(), node);
                }

                if (node.getLeftNode() == null && node.getRightNode() == null) {
                    parent = node.getParent();
                    parent.deleteRecoloring(nodeRoot);
                } else node.deleteRecoloring(nodeRoot);


            } else if (Utils.isBlack(child.getLeftNode()) && Utils.isBlack(child.getRightNode())) {
                if (parent.getColor() == Colors.RED) {
                    parent.setColor(Colors.BLACK);
                    child.setColor(Colors.RED);
                    node.getRightNode().setColor(Colors.RED);
                    node.getLeftNode().setColor(Colors.RED);

                } else {
                    pushUp(node);
                }

            } else if (Utils.isRed(parent.getRightGrandchildOfRightChild()) || Utils.isRed(parent.getLeftGrandchildOfRightChild())) {
                node.getLeftNode().setColor(Colors.RED);
                rotateRight(parent.getLeftNode(), parent);
                parent.getParent().reverseRecoloring();
            }
            if ((node.getLeftNode() != null && Utils.isBlack(node.getLeftNode()) && node.getRightNode() != null && Utils.isBlack(node.getRightNode())) && node.getLeftGrandchildOfRightChild() != null && Utils.isRed(node.getLeftGrandchildOfRightChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                node.setLeftNode(null);
                rotateLeft(node.getRightNode(), node);
                node.getParent().deleteRecoloring(nodeRoot);
            }

            if (Utils.isNotNull(child.getLeftNode()) && child.getLeftNode().getNumber() == 0)
                child.setLeftNode(null);
            if (Utils.isNotNull(child.getRightNode()) && child.getRightNode().getNumber() == 0)
                child.setRightNode(null);


        } else {
            if (Utils.isRed(parent.getRightGrandchildOfLeftChild()) && Utils.isRed(parent.getLeftGrandchildOfLeftChild())) {
                node.getLeftNode().setColor(Colors.BLACK);
            } else if (Utils.isRed(parent.getRightGrandchildOfLeftChild()) || Utils.isRed(parent.getLeftGrandchildOfLeftChild())) {
                rotateRight(parent.getRightNode(), parent);
                parent.getParent().reverseRecoloring();
            }


        }
        if (Utils.isNotNull(node.getLeftNode()) && node.getLeftNode().getNumber() == 0) {
            node.setLeftNode(null);
        }
        if (Utils.isNotNull(node.getRightNode()) && node.getRightNode().getNumber() == 0) {
            node.setRightNode(null);
        }
    }

    private void swap(Node maxNode, Node removeNode, Node parent) {
        if (parent.getLeftNode() == removeNode) {
            parent.setLeftNode(maxNode);
            removeNode.getLeftNode().setColor(maxNode.getColor());
            maxNode.setRightNode(removeNode.getRightNode());
        } else {
            parent.setRightNode(maxNode);
            removeNode.getRightNode().setColor(maxNode.getColor());
            maxNode.setRightNode(removeNode.getRightNode());
        }
        if (Utils.isNull(maxNode.getLeftNode()) || Utils.isNull(maxNode.getRightNode())) {
            maxNode.setColor(removeNode.getColor());
        }
        if (Utils.isNotNull(maxNode.getLeftNode())) {
            maxNode.getLeftNode().setParent(maxNode);
        }
        if (Utils.isNotNull(maxNode.getRightNode())) {
            maxNode.getRightNode().setParent(maxNode);
        }
        balancingAfterRemoving(maxNode);

    }

    private Node checkingNephew(Node node) {
        Node nodePointer = node.getRightNode();
        if (Utils.isNull(nodePointer)) {
            return null;
        }
        if (Utils.isNotNull(nodePointer.getRightNode())) {
            return nodePointer.getRightNode();
        } else if (Utils.isNull(nodePointer.getLeftNode())) {
            return nodePointer.getLeftNode();
        } else {
            nodePointer = node.getLeftNode();
            if (nodePointer.getRightNode() != null) {
                return nodePointer.getRightNode();
            } else if (nodePointer.getLeftNode() != null) {
                return nodePointer.getLeftNode();
            }
        }
        return null;
    }

    private void pushUp(Node node) {
        node.setColor(Colors.BLACK);
        node.getRightNode().setColor(Colors.RED);
        node.getLeftNode().setColor(Colors.RED);
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public Node getNodeRoot() {
        return nodeRoot;
    }

    private Node searchPlaceToPut(Node addNode, Node checkNode) {
        if (Utils.isMore(addNode, checkNode)) {
            addNode = searchPlaceToPut(addNode, checkNode.getLeftNode());
        } else if (Utils.isLess(addNode, checkNode)) {
            addNode = searchPlaceToPut(addNode, checkNode.getRightNode());
        }
        if (Utils.nullOrEquals(addNode, checkNode)) {
            return null;
        }
        addNode.setParent(checkNode);
        if (checkNode.getNumber() > addNode.getNumber()) {
            checkNode.setLeftNode(addNode);
        } else {
            checkNode.setRightNode(addNode);
        }
        size++;
        checkForRebalance();
        return null;
    }

    private void checkForRebalance() {
        Node NodeForBalance = checkRight(nodeRoot);
        if (NodeForBalance != null) {
            rebalance(NodeForBalance);
        }
        NodeForBalance = checkLeft(nodeRoot);
        if (NodeForBalance != null) {
            rebalance(NodeForBalance);
        }
    }
}















