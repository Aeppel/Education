package org.pervishkond.tree;

class Tree extends Node {
    private int size = 0;

    public Tree() {
    }

    public void add(int number) {
        if (isEmpty()) {
            nodeRoot = new Node(number);
            nodeRoot.setColor(Colors.BLACK);
        } else {
            Node addNode = new Node(number);
            searchPlaceToPut(addNode, nodeRoot);
        }
        size++;

    }

    private void optionsToBalance(Node node) {
        if (node == nodeRoot) {
            nodeRoot.recoloring(nodeRoot);
        } else {
            Node parent = node.searchParentNode(nodeRoot);
            if (parent.leftNode == node) {
                if (isRightBrotherRed(parent)) {
                    parent.recoloring(nodeRoot);
                } else {
                    rotateRight(node, parent);
                }
            } else if (parent.rightNode == node) {
                if (isLeftBrotherRed(parent)) {
                    parent.recoloring(nodeRoot);
                } else {
                    rotateLeft(node, parent);
                }

            } else {
                parent.recoloring(nodeRoot);
            }

            Node checkingBadNode = checkLeft(nodeRoot);
            if (isNotNull(checkingBadNode)) {
                optionsToBalance(checkingBadNode);
            }
            checkingBadNode = checkRight(nodeRoot);
            if (isNotNull(checkingBadNode)) {
                optionsToBalance(checkingBadNode);
            }
        }
    }

    protected void rotateLeft(Node node, Node parent) {
        if (node == null || parent == null) {
            return;
        }
        if (hasChild(node, parent)) {
            return;
        }
        Node ln = node.leftNode;
        if (isLeftNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.leftNode = parent;
            parent.rightNode = ln;
            if (parent == nodeRoot) {
                nodeRoot = node;
            } else {
                Node grandparent = parent.searchParentNode(nodeRoot);
                if (grandparent.leftNode == parent) {
                    grandparent.leftNode = node;
                } else {
                    grandparent.rightNode = node;
                }
            }
        } else if (isRightNodeBlack(node)) {
            Node nodePointer = node.getLeftNodeRightChild();
            node.leftNode.rightNode = node;
            parent.rightNode = node.getLeftNode();
            node.leftNode = nodePointer;
        } else if (isNull(parent.getLeftNode()) || parent.getLeftNode().number == 0) {
            Node nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.rightNode = node.getLeftNode();
                nodeRoot.leftNode = nodePointer;
                nodeRoot.getRightNode().setColor(Colors.BLACK);
                nodeRoot.setColor(Colors.BLACK);
            } else {
                Node grandParent = parent.searchParentNode(nodeRoot);
                if (grandParent.leftNode == parent) {
                    nodePointer = parent.getLeftNode();
                    grandParent.leftNode = node;
                } else {
                    nodePointer = parent.getRightNode();
                    grandParent.rightNode = node;
                }
                node.leftNode.rightNode = nodePointer;
                parent.recoloring(nodeRoot);
            }
        }

    }


    protected void rotateRight(Node node, Node parent) {
        if (node == null || parent == null) {
            return;
        }
        Node nodePointer;
        Node rn = node.rightNode;
        if (isRightNodeBlack(node)) {
            node.setColor(Colors.BLACK);
            parent.setColor(Colors.RED);
            node.rightNode = parent;
            parent.leftNode = rn;
            if (parent == nodeRoot) {
                nodeRoot = node;
            } else {
                Node grandparent = parent.searchParentNode(nodeRoot);
                if (grandparent.leftNode == parent) {
                    grandparent.leftNode = node;
                } else {
                    grandparent.rightNode = node;
                }
            }
        } else if (isLeftNodeBlack(node)) {
            nodePointer = node.getRightNodeLeftChild();
            node.rightNode.leftNode = node;
            parent.leftNode = node.getRightNode();
            node.rightNode = nodePointer;

        } else if (isNull(parent.rightNode)) {
            nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.leftNode = node.getRightNode();
                nodeRoot.rightNode = nodePointer;
                nodeRoot.leftNode.setColor(Colors.BLACK);
            } else {
                Node grandParent = parent.searchParentNode(nodeRoot);
                if (grandParent.rightNode == parent) {
                    nodePointer = parent.getRightNode();
                    grandParent.rightNode = node;
                } else {
                    nodePointer = parent.getLeftNode();
                    grandParent.leftNode = node;
                }
                node.rightNode.leftNode = nodePointer;
                parent.recoloring(nodeRoot);
            }
        }

    }


    protected Node checkRight(Node node) {
        Node badNode = null;
        if (isGoingLeft(node)) {
            badNode = checkRight(node.getLeftNode());
        }
        if (isNull(badNode)) {
            badNode = node;
            if (isGoingRight(node)) {
                badNode = checkRight(node.getRightNode());
            }
        }
        if (isNull(badNode)) {
            return null;
        }
        if (isNoChildren(node)) {
            return null;
        } else if (isNeighborSameColor(node, badNode)) {
            return null;
        }
        return badNode;
    }

    protected Node checkLeft(Node node) {
        Node badNode = null;
        if (isGoingRight(node)) {
            badNode = checkLeft(node.getRightNode());
        }
        if (badNode == null) {
            badNode = node;

            if (isGoingLeft(node)) {
                badNode = checkLeft(node.getLeftNode());
            }
        }
        if (badNode == null) {
            return null;
        }
        if (isNoChildren(node)) {
            return null;
        } else if (isNeighborSameColor(node, badNode)) {
            return null;
        }
        return badNode;
    }


    public void show() {
        StringBuilder result = new StringBuilder();
        nodeRoot.showTree(result);
    }

    public void remove(int number) {
        Node removeNode = search(number, nodeRoot);
        if (removeNode == null) {
            return;
        }
        size--;
        Node parent = removeNode.searchParentNode(nodeRoot);
        if ((isRed(removeNode)) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
            if (parent.getLeftNode() == removeNode) {
                parent.leftNode = null;
            } else {
                parent.rightNode = null;
            }
            return;
        }
        if (removeNode.getLeftNode() != null && removeNode.getRightNode() != null) {
            Node maxNode = searchOfMaxNumber(removeNode.getLeftNode());
            if (maxNode == removeNode.getLeftNode()) {
                swap(maxNode, removeNode, parent);
                return;
            }
            Node parentMaxNode = maxNode.searchParentNode(nodeRoot);
            if (checkingNephew(maxNode) == null)
                parentMaxNode.rightNode = null;
            maxNode.rightNode = removeNode.getRightNode();
            if (parent == null) {
                nodeRoot = maxNode;
                maxNode.leftNode = removeNode.getLeftNode();
                nodeRoot.setColor(Colors.BLACK);
                return;
            }
            if (parent.getLeftNode() == removeNode) {

                if (maxNode.getLeftNode() != null) {
                    maxNode.getLeftNode().color = maxNode.color;
                    parentMaxNode.rightNode = maxNode.getLeftNode();
                }
                Node nodePointer = parent.getLeftNodeLeftChild();
                parent.leftNode = maxNode;
                maxNode.leftNode = nodePointer;
                maxNode.color = removeNode.color;

            } else {
                if (maxNode.getRightNode() != null) {
                    parentMaxNode.rightNode = maxNode.getLeftNode();
                }
                Node nodePointer = parent.getRightNodeLeftChild();
                parent.rightNode = maxNode;
                maxNode.leftNode = nodePointer;
                maxNode.setColor(removeNode.color);
            }
            if (isBlack(maxNode) && maxNode.leftNode == null)
                balancingAfterRemoving(maxNode);
            return;
        }
        if (isBlack(removeNode) && (removeNode.getLeftNode() != null || removeNode.getRightNode() != null)) {
            if (removeNode.leftNode != null) {
                if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.getLeftNode();
                    parent.leftNode.setColor(Colors.BLACK);
                } else {
                    parent.rightNode = removeNode.getLeftNode();
                    parent.rightNode.setColor(Colors.BLACK);
                }

            } else {
                if (nodeRoot == removeNode) {
                    nodeRoot = removeNode.getRightNode();
                    nodeRoot.setColor(Colors.BLACK);
                } else if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.getRightNode();
                    parent.getLeftNode().setColor(Colors.BLACK);
                } else {
                    parent.rightNode = removeNode.getRightNode();
                    parent.getRightNode().setColor(Colors.BLACK);

                }
            }
        }
        if (isBlack(removeNode) && (removeNode.getLeftNode() == null && removeNode.getRightNode() == null)) {
            if (parent.getLeftNode() == removeNode) {
                parent.leftNode = null;
            } else {
                parent.rightNode = null;
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
        Node parent = node.searchParentNode(nodeRoot);// grandfather
        if (isNull(parent)) {
            parent = nodeRoot;
        }
        if (isNull(node.getLeftNode())) {
            Node nodePointer = new Node();
            nodePointer.setColor(Colors.BLACK);
            node.leftNode = nodePointer;
            Node child = parent.getRightNode();
            if (isNull(child.getRightNode())) {
                child.rightNode = new Node();
                child.getRightNode().setColor(Colors.BLACK);
            }
            if (isNull(child.getLeftNode())) {
                child.leftNode = new Node();
                child.getLeftNode().setColor(Colors.BLACK);
            }

            if (isRed(node.getRightNode()) && isBlack(node.getRightNodeLeftChild())) {//brother is black, one left child is red, but right child is black
                rotateRight(node, parent);
                if (isNotNull(child.getLeftNode())) {
                    rotateLeft(node.getRightNodeLeftChild(), node.getRightNode());
                }
                if (isNotNull(child.getRightNode())) {
                    rotateRight(node.getRightNode(), node);
                }
                if (isNoChildren(node)) {
                    parent = node.searchParentNode(nodeRoot);
                    parent.recoloringAfterRemoving();
                } else node.recoloringAfterRemoving();

            } else if (isBlack(child.getRightNode()) && isBlack(child.getLeftNode())) { // brother's black, children are black
                Node nephew = checkingNephew(node);
                if (isNotNull(nephew) && isRed(nephew)) {
                    if (node.getRightNodeLeftChild() == nephew) {
                        rotateLeft(node.getRightNode(), node);
                        nephew.setColor(Colors.BLACK);
                        nephew.getRightNode().setColor(Colors.RED);
                        rotateLeft(node.getRightNode(), node);
                    } else {
                        rotateLeft(node.getRightNode(), node);
                    }
                    node.searchParentNode(nodeRoot).recoloringAfterRemoving();
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
                            if (isNotNull(nodePointerChecker.getLeftNode()) && isRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (isNotNull(nodePointerChecker.getRightNode()) && isRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                            nodePointerChecker = nodePointer.getRightNode();
                            if (isNotNull(nodePointerChecker.getLeftNode()) && isRed(nodePointerChecker.getLeftNode()))
                                nodePointer.setColor(Colors.RED);
                            if (isNotNull(nodePointerChecker.getRightNode()) && isRed(nodePointerChecker.getRightNode()))
                                nodePointer.setColor(Colors.RED);
                        }
                    }


            } else if ((isRed(node) && isBlack(node.getLeftNode())) && isBlack(parent.getRightNode())) {
                node.setColor(Colors.BLACK);
                node.getRightNode().setColor(Colors.RED);
            } else if (isRed(parent.getRightNodeRightChild()) || isRed(parent.getRightNodeLeftChild())) {
                node.getRightNode().setColor(Colors.RED);
                rotateLeft(parent.getRightNode(), parent);
                if (parent.searchParentNode(nodeRoot) != nodeRoot) {
                    parent.searchParentNode(nodeRoot).reverseRecoloring();
                }
            }
            if ((isNotNull(node.getLeftNode()) && isBlack(node.getLeftNode()) && isNotNull(node.getRightNode()) && isBlack(node.getRightNode())) && isNotNull(node.getRightNodeLeftChild()) && isRed(node.getRightNodeLeftChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getRightNodeLeftChild(), node.getRightNode());
                node.leftNode = null;
                rotateLeft(node.getRightNode(), node);
                node.searchParentNode(nodeRoot).recoloringAfterRemoving();
            }

            if (isNotNull(child.getLeftNode()) && child.getLeftNode().number == 0)
                child.leftNode = null;
            if (isNotNull(child.getRightNode()) && child.getRightNode().number == 0)
                child.rightNode = null;

        } else if (isNull(node.getRightNode())) {

            Node nodePointer = new Node();
            nodePointer.setColor(Colors.BLACK);
            node.rightNode = nodePointer;
            Node child = parent.getLeftNode();
            if (isNull(child.getRightNode())) {
                child.rightNode = new Node();
                child.getRightNode().setColor(Colors.BLACK);
            }
            if (isNull(child.getLeftNode())) {
                child.leftNode = new Node();
                child.getLeftNode().setColor(Colors.BLACK);
            }

            if (isRed(node.getLeftNode()) && isBlack(node.getLeftNodeRightChild())) {
                rotateLeft(node, parent);

                if (node.getLeftNodeRightChild() != null) {
                    rotateLeft(node.getLeftNodeRightChild(), node.getLeftNode());
                }
                if (node.getLeftNodeLeftChild() != null) {
                    rotateRight(node.getLeftNode(), node);
                }

                if (node.getLeftNode() == null && node.getRightNode() == null) {
                    parent = node.searchParentNode(nodeRoot);
                    parent.recoloringAfterRemoving();
                } else node.recoloringAfterRemoving();


            } else if (isBlack(child.getLeftNode()) && isBlack(child.getRightNode())) {
                if (parent.color == Colors.RED) {
                    parent.setColor(Colors.BLACK);
                    child.setColor(Colors.RED);
                    node.getRightNode().setColor(Colors.RED);
                    node.getLeftNode().setColor(Colors.RED);

                } else {
                    pushUp(node);
                }

            } else if (isRed(parent.getRightNodeRightChild()) || isRed(parent.getRightNodeLeftChild())) {
                node.getLeftNode().setColor(Colors.RED);
                rotateRight(parent.getLeftNode(), parent);
                parent.searchParentNode(nodeRoot).reverseRecoloring();
            }
            if ((node.getLeftNode() != null && isBlack(node.getLeftNode()) && node.getRightNode() != null && isBlack(node.getRightNode())) && node.getRightNodeLeftChild() != null && isRed(node.getRightNodeLeftChild())) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.getRightNodeLeftChild(), node.getRightNode());
                node.leftNode = null;
                rotateLeft(node.getRightNode(), node);
                node.searchParentNode(nodeRoot).recoloringAfterRemoving();
            }

            if (isNotNull(child.getLeftNode()) && child.getLeftNode().number == 0)
                child.leftNode = null;
            if (isNotNull(child.getRightNode()) && child.getRightNode().number == 0)
                child.rightNode = null;


        } else {
            if (isRed(parent.leftNode.rightNode) && isRed(parent.leftNode.leftNode)) {
                node.leftNode.setColor(Colors.BLACK);
            } else if (isRed(parent.getLeftNodeRightChild()) || isRed(parent.getLeftNodeLeftChild())) {
                rotateRight(parent.getRightNode(), parent);
                parent.searchParentNode(nodeRoot).reverseRecoloring();
            }


        }
        if (isNotNull(node.getLeftNode()) && node.getLeftNode().number == 0) {
            node.leftNode = null;
        }
        if (isNotNull(node.getRightNode()) && node.getRightNode().number == 0) {
            node.rightNode = null;
        }
    }

    private void swap(Node maxNode, Node removeNode, Node parent) {
        if (parent.getLeftNode() == removeNode) {
            parent.leftNode = maxNode;
            removeNode.getLeftNode().color = maxNode.color;
            maxNode.rightNode = removeNode.getRightNode();
        } else {
            parent.rightNode = maxNode;
            removeNode.getRightNode().color = maxNode.color;
            maxNode.rightNode = removeNode.getRightNode();
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

    private Node searchPlaceToPut(Node addNode, Node nodeRoot) {
        if (nodeRoot.number > addNode.number && nodeRoot.getLeftNode() != null) {
            addNode = searchPlaceToPut(addNode, nodeRoot.getLeftNode());
        } else if (nodeRoot.number < addNode.number && nodeRoot.getRightNode() != null) {
            addNode = searchPlaceToPut(addNode, nodeRoot.getRightNode());
        } else if (nodeRoot.number == addNode.number) {
            size--;
            return null;
        }
        if (addNode == null) {
            return null;
        }
        if (nodeRoot.number > addNode.number) {
            nodeRoot.leftNode = addNode;
        } else {
            nodeRoot.rightNode = addNode;
        }
        Node checkingBadNode;
        checkingBadNode = checkRight(nodeRoot);
        if (checkingBadNode != null) {
            optionsToBalance(checkingBadNode);
        }
        checkingBadNode = checkLeft(nodeRoot);
        if (checkingBadNode != null) {
            optionsToBalance(checkingBadNode);
        }
        return null;
    }
}















