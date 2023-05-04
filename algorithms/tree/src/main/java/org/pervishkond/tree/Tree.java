package org.pervishkond.tree;

class Tree extends Node {
    private int size = 0;

    public Tree() {
    }

    public void add(int number) {
        if (isEmpty()) {
            setNodeRoot(new Node(number));
            getNodeRoot().setColor(Colors.BLACK);
        } else {
            Node addNode = new Node(number);
            searchPlaceToPut(addNode, getNodeRoot());
        }
        size++;

    }

    private void rebalance(Node node) {
        if (node == getNodeRoot()) {
            getNodeRoot().recoloring(getNodeRoot());
        } else {
            Node parent = node.searchParentNode(getNodeRoot());
            if (parent.getLeftNode() == node) {
                if (isRightBrotherRed(parent)) {
                    parent.recoloring(getNodeRoot());
                } else {
                    rotateRight(node, parent);
                }
            } else if (parent.getRightNode() == node) {
                if (isLeftBrotherRed(parent)) {
                    parent.recoloring(getNodeRoot());
                } else {
                    rotateLeft(node, parent);
                }

            } else {
                parent.recoloring(getNodeRoot());
            }

            Node NodeForBalance = checkLeft(getNodeRoot());
            if (isNotNull(NodeForBalance)) {
                rebalance(NodeForBalance);
            }
            NodeForBalance = checkRight(getNodeRoot());
            if (isNotNull(NodeForBalance)) {
                rebalance(NodeForBalance);
            }
        }
    }

    protected void rotateLeft(Node node, Node parent) {
        Node nodePointer = node.getLeftNode();
        if (isLeftNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.setLeftNode(parent);
            parent.setRightNode(nodePointer);
            if (parent == getNodeRoot()) {
                setNodeRoot(node);
            } else {
                Node grandparent = parent.searchParentNode(getNodeRoot());
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(node);
                } else {
                    grandparent.setRightNode(node);
                }
            }
        } else if (isRightNodeBlack(node)) {
            nodePointer = node.getRightGrandchildOfLeftChild();
            node.setLeftNodeRightChild(node);
            parent.setRightNode(node.getLeftNode());
            node.setLeftNode(nodePointer);
        } else if (isNull(parent.getLeftNode()) || parent.getLeftNode().number == 0) {
            nodePointer = getNodeRoot();
            if (parent == getNodeRoot()) {
                setNodeRoot(node);
                nodePointer.setRightNode(node.getLeftNode());
                getNodeRoot().setLeftNode(nodePointer);
                getNodeRoot().getRightNode().setColor(Colors.BLACK);
                getNodeRoot().setColor(Colors.BLACK);
            } else {
                Node grandParent = parent.searchParentNode(getNodeRoot());
                if (grandParent.getLeftNode() == parent) {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(node);
                } else {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(node);
                }
                node.setLeftNodeRightChild(nodePointer);
                parent.recoloring(getNodeRoot());
            }
        }

    }


    protected void rotateRight(Node node, Node parent) {
        Node nodePointer = node.getRightNode();
        if (isRightNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.setRightNode(parent);
            parent.setLeftNode(nodePointer);
            if (parent == getNodeRoot()) {
                setNodeRoot(node);
            } else {
                Node grandparent = parent.searchParentNode(getNodeRoot());
                if (grandparent.getLeftNode() == parent) {
                    grandparent.setLeftNode(node);
                } else {
                    grandparent.setRightNode(node);
                }
            }
        } else if (isLeftNodeBlack(node)) {
            nodePointer = node.getLeftGrandchildOfRightChild();
            node.setRightNodeLeftChild(node);
            parent.setLeftNode(node.getRightNode());
            node.setRightNode(nodePointer);

        } else if (isNull(parent.getRightNode())) {
            nodePointer = getNodeRoot();
            if (parent == getNodeRoot()) {
                setNodeRoot(node);
                nodePointer.setLeftNode(node.getRightNode());
                getNodeRoot().setRightNode(nodePointer);
                getNodeRoot().getLeftNode().setColor(Colors.BLACK);
            } else {
                Node grandParent = parent.searchParentNode(getNodeRoot());
                if (grandParent.getRightNode() == parent) {
                    nodePointer = parent.getRightNode();
                    grandParent.setRightNode(node);
                } else {
                    nodePointer = parent.getLeftNode();
                    grandParent.setLeftNode(node);
                }
                node.setRightNodeLeftChild(nodePointer);
                parent.recoloring(getNodeRoot());
            }
        }

    }


    protected Node checkRight(Node node) {
        Node NodeForBalance = null;
        if (isLeftNodeBlackTooOrDifferentColor(node)) {
            NodeForBalance = checkRight(node.getLeftNode());
        }
        if (isNull(NodeForBalance)) {
            NodeForBalance = node;
            if (isRightNodeBlackTooOrDifferentColor(node)) {
                NodeForBalance = checkRight(node.getRightNode());
            }
        }
        if (isNull(NodeForBalance)) {
            return null;
        }
        if (isNoChildren(node)) {
            return null;
        } else if (isNeighborSameColor(node, NodeForBalance)) {
            return null;
        }
        return NodeForBalance;
    }

    protected Node checkLeft(Node node) {
        Node NodeForBalance = null;
        if (isRightNodeBlackTooOrDifferentColor(node)) {
            NodeForBalance = checkLeft(node.getRightNode());
        }
        if (NodeForBalance == null) {
            NodeForBalance = node;

            if (isLeftNodeBlackTooOrDifferentColor(node)) {
                NodeForBalance = checkLeft(node.getLeftNode());
            }
        }
        if (NodeForBalance == null) {
            return null;
        }
        if (isNoChildren(node)) {
            return null;
        } else if (isNeighborSameColor(node, NodeForBalance)) {
            return null;
        }
        return NodeForBalance;
    }


    public void show() {
        StringBuilder result = new StringBuilder();
        getNodeRoot().showTree(result);
    }

    public void remove(int number) {
        Node removeNode = search(number, getNodeRoot());
        if (removeNode == null) {
            return;
        }
        size--;
        Node parent = removeNode.searchParentNode(getNodeRoot());
        if ((isRed(removeNode)) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
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
            Node parentMaxNode = maxNode.searchParentNode(getNodeRoot());
            if (checkingNephew(maxNode) == null)
                parentMaxNode.setRightNode(null);
            maxNode.setRightNode(removeNode.getRightNode());
            if (parent == null) {
                setNodeRoot(maxNode);
                maxNode.setLeftNode(removeNode.getLeftNode());
                getNodeRoot().setColor(Colors.BLACK);
                return;
            }
            if (parent.getLeftNode() == removeNode) {

                if (maxNode.getLeftNode() != null) {
                    maxNode.getLeftNode().color = maxNode.color;
                    parentMaxNode.setRightNode(maxNode.getLeftNode());
                }
                Node nodePointer = parent.getLeftGrandchildOfLeftChild();
                parent.setLeftNode(maxNode);
                maxNode.setLeftNode(nodePointer);
                maxNode.color = removeNode.color;

            } else {
                if (maxNode.getRightNode() != null) {
                    parentMaxNode.setRightNode(maxNode.getLeftNode());
                }
                Node nodePointer = parent.getLeftGrandchildOfRightChild();
                parent.setRightNode(maxNode);
                maxNode.setLeftNode(nodePointer);
                maxNode.setColor(removeNode.color);
            }
            if (isBlack(maxNode) && maxNode.getLeftNode() == null)
                balancingAfterRemoving(maxNode);
            return;
        }
        if (isBlack(removeNode) && (removeNode.getLeftNode() != null || removeNode.getRightNode() != null)) {
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
                    setNodeRoot(removeNode.getRightNode());
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
        if (isBlack(removeNode) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
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
        if (isNull(nodePointer)) {
            return null;
        }
        if (number == node.number) {
            return nodePointer;
        } else if (number > nodePointer.number) {
            nodePointer = search(number, node.getRightNode());
        } else nodePointer = search(number, node.getLeftNode());

        return nodePointer;
    }

    private Node searchOfMaxNumber(Node node) {
        Node nodePointer = node;
        if (isNotNull(nodePointer.getRightNode())) {
            nodePointer = searchOfMaxNumber(node.getRightNode());
        }
        return nodePointer;

    }

    private void balancingAfterRemoving(Node node) { //parent
        Node parent = node.searchParentNode(getNodeRoot());// grandfather
        if (isNull(parent)) {
            parent = getNodeRoot();
        }
        if (isNull(node.getLeftNode())) {
            Node nodePointer = new Node();
            node.setLeftNode(nodePointer);
            Node child = parent.getRightNode();
            if (isNull(child.getRightNode())) {
                child.setRightNode(new Node());
            }
            if (isNull(child.getLeftNode())) {
                child.setLeftNode(new Node());
            }

            if (isRed(node.getRightNode()) && isBlack(node.getLeftGrandchildOfRightChild())) {//brother is black, one left child is red, but right child is black
                rotateRight(node, parent);
                if (isNotNull(child.getLeftNode())) {
                    rotateLeft(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                }
                if (isNotNull(child.getRightNode())) {
                    rotateRight(node.getRightNode(), node);
                }
                if (isNoChildren(node)) {
                    parent = node.searchParentNode(getNodeRoot());
                    parent.deleteRecoloring(nodeRoot);
                } else node.deleteRecoloring(nodeRoot);

            } else if (isBlack(child.getRightNode()) && isBlack(child.getLeftNode())) { // brother's black, children are black
                Node nephew = checkingNephew(node);
                if (isNotNull(nephew) && isRed(nephew)) {
                    if (node.getLeftGrandchildOfRightChild() == nephew) {
                        rotateLeft(node.getRightNode(), node);
                        nephew.setColor(Colors.BLACK);
                        nephew.getRightNode().setColor(Colors.RED);
                        rotateLeft(node.getRightNode(), node);
                    } else {
                        rotateLeft(node.getRightNode(), node);
                    }
                    node.searchParentNode(getNodeRoot()).deleteRecoloring(nodeRoot);
                } else if (isRed(parent)) {
                    parent.setColor(Colors.BLACK);
                    parent.getRightNode().setColor(Colors.RED);
                } else if (isBlack(node.getLeftNode()) && isBlack(node.getRightNode())) {
                    pushUp(node);
                }
                if ((isNotNull(node.getRightNode()) && isRed(node.getRightNode())) || (isNotNull(node.getLeftNode()) && isRed(node.getLeftNode())))
                    if (isRed(parent.getLeftNode())) {
                        nodePointer = parent.getLeftNode();
                        if (isBlack(nodePointer.getLeftNode()) && isBlack(nodePointer.getRightNode())) {
                            Node nodePointerChecker = nodePointer.getLeftNode();
                            if (IsNotNullAndRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (IsNotNullAndRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                            nodePointerChecker = nodePointer.getRightNode();
                            if (IsNotNullAndRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (IsNotNullAndRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                        }
                    }


            } else if ((isRed(node) && isBlack(node.getLeftNode())) && isBlack(parent.getRightNode())) {
                node.setColor(Colors.BLACK);
                node.getRightNode().setColor(Colors.RED);
            } else if (isRed(parent.getRightGrandchildOfRightChild()) || isRed(parent.getLeftGrandchildOfRightChild())) {
                node.getRightNode().setColor(Colors.RED);
                rotateLeft(parent.getRightNode(), parent);
                if (parent.searchParentNode(getNodeRoot()) != getNodeRoot()) {
                    parent.searchParentNode(getNodeRoot()).reverseRecoloring();
                }
            }
            if ((isNotNull(node.getLeftNode()) && isBlack(node.getLeftNode()) && isNotNull(node.getRightNode()) && isBlack(node.getRightNode())) && isNotNull(node.getLeftGrandchildOfRightChild()) && isRed(node.getLeftGrandchildOfRightChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                node.setLeftNode(null);
                rotateLeft(node.getRightNode(), node);
                node.searchParentNode(getNodeRoot()).deleteRecoloring(nodeRoot);
            }

            if (isNotNull(child.getLeftNode()) && child.getLeftNode().number == 0)
                child.setLeftNode(null);
            if (isNotNull(child.getRightNode()) && child.getRightNode().number == 0)
                child.setRightNode(null);

        } else if (isNull(node.getRightNode())) {

            Node nodePointer = new Node();

            node.setRightNode(nodePointer);
            Node child = parent.getLeftNode();
            if (isNull(child.getRightNode())) {
                child.setRightNode(new Node());
            }
            if (isNull(child.getLeftNode())) {
                child.setLeftNode(new Node());
            }

            if (isRed(node.getLeftNode()) && isBlack(node.getRightGrandchildOfLeftChild())) {
                rotateLeft(node, parent);

                if (node.getRightGrandchildOfLeftChild() != null) {
                    rotateLeft(node.getRightGrandchildOfLeftChild(), node.getLeftNode());
                }
                if (node.getLeftGrandchildOfLeftChild() != null) {
                    rotateRight(node.getLeftNode(), node);
                }

                if (node.getLeftNode() == null && node.getRightNode() == null) {
                    parent = node.searchParentNode(getNodeRoot());
                    parent.deleteRecoloring(nodeRoot);
                } else node.deleteRecoloring(nodeRoot);


            } else if (isBlack(child.getLeftNode()) && isBlack(child.getRightNode())) {
                if (parent.color == Colors.RED) {
                    parent.setColor(Colors.BLACK);
                    child.setColor(Colors.RED);
                    node.getRightNode().setColor(Colors.RED);
                    node.getLeftNode().setColor(Colors.RED);

                } else {
                    pushUp(node);
                }

            } else if (isRed(parent.getRightGrandchildOfRightChild()) || isRed(parent.getLeftGrandchildOfRightChild())) {
                node.getLeftNode().setColor(Colors.RED);
                rotateRight(parent.getLeftNode(), parent);
                parent.searchParentNode(getNodeRoot()).reverseRecoloring();
            }
            if ((node.getLeftNode() != null && isBlack(node.getLeftNode()) && node.getRightNode() != null && isBlack(node.getRightNode())) && node.getLeftGrandchildOfRightChild() != null && isRed(node.getLeftGrandchildOfRightChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getLeftGrandchildOfRightChild(), node.getRightNode());
                node.setLeftNode(null);
                rotateLeft(node.getRightNode(), node);
                node.searchParentNode(getNodeRoot()).deleteRecoloring(nodeRoot);
            }

            if (isNotNull(child.getLeftNode()) && child.getLeftNode().number == 0)
                child.setLeftNode(null);
            if (isNotNull(child.getRightNode()) && child.getRightNode().number == 0)
                child.setRightNode(null);


        } else {
            if (isRed(parent.getRightGrandchildOfLeftChild()) && isRed(parent.getLeftGrandchildOfLeftChild())) {
                node.getLeftNode().setColor(Colors.BLACK);
            } else if (isRed(parent.getRightGrandchildOfLeftChild()) || isRed(parent.getLeftGrandchildOfLeftChild())) {
                rotateRight(parent.getRightNode(), parent);
                parent.searchParentNode(getNodeRoot()).reverseRecoloring();
            }


        }
        if (isNotNull(node.getLeftNode()) && node.getLeftNode().number == 0) {
            node.setLeftNode(null);
        }
        if (isNotNull(node.getRightNode()) && node.getRightNode().number == 0) {
            node.setRightNode(null);
        }
    }

    private void swap(Node maxNode, Node removeNode, Node parent) {
        if (parent.getLeftNode() == removeNode) {
            parent.setLeftNode(maxNode);
            removeNode.getLeftNode().color = maxNode.color;
            maxNode.setRightNode(removeNode.getRightNode());
        } else {
            parent.setRightNode(maxNode);
            removeNode.getRightNode().color = maxNode.color;
            maxNode.setRightNode(removeNode.getRightNode());
        }
        if (isNull(maxNode.getLeftNode()) || isNull(maxNode.getRightNode())) {
            maxNode.color = removeNode.color;
        }
        balancingAfterRemoving(maxNode);

    }

    private Node checkingNephew(Node node) {
        Node nodePointer = node.getRightNode();
        if (isNull(nodePointer)) {
            return null;
        }
        if (isNotNull(nodePointer.getRightNode())) {
            return nodePointer.getRightNode();
        } else if (isNull(nodePointer.getLeftNode())) {
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

    protected void setNodeRoot(Node node) {
        nodeRoot = node;
    }

    private Node searchPlaceToPut(Node addNode, Node checkNode) {
        if (isMore(addNode, checkNode)) {
            addNode = searchPlaceToPut(addNode, checkNode.getLeftNode());
        } else if (isLess(addNode, checkNode)) {
            addNode = searchPlaceToPut(addNode, checkNode.getRightNode());
        } else if (isEqual(addNode, checkNode)) {
            size--;
            return null;
        }
        if (addNode == null) {
            return null;
        }
        if (checkNode.number > addNode.number) {
            checkNode.setLeftNode(addNode);
        } else {
            checkNode.setRightNode(addNode);
        }
        Node NodeForBalance;
        NodeForBalance = checkRight(checkNode);
        if (NodeForBalance != null) {
            rebalance(NodeForBalance);
        }
        NodeForBalance = checkLeft(checkNode);
        if (NodeForBalance != null) {
            rebalance(NodeForBalance);
        }
        return null;
    }


}















