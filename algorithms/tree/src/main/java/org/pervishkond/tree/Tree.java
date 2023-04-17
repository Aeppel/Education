package org.pervishkond.tree;

class Tree extends Node {
    private int size = 0;
    protected Node nodeRoot;

    public Tree() {
    }

    public void add(int number) {
        if (empty()) {
            nodeRoot = new Node(number);
            nodeRoot.setColor("Black");
        } else {
            putIntoTree(number);
        }
        size++;
    }

    private void optionsToBalance(Node node) {
        if (node == nodeRoot) {
            recoloring(nodeRoot, nodeRoot);
        } else {
            Node parent = whoIsParent(node, nodeRoot);
            if (parent.leftNode == node) {
                if (isRightBrotherRed(parent)) {
                    recoloring(parent, nodeRoot);
                } else {
                    rotateRight(node, parent);
                }
            } else if (parent.rightNode == node) {
                if (isLeftBrotherRed(parent)) {
                    recoloring(parent, nodeRoot);
                } else {
                    rotateLeft(node, parent);
                }

            } else {
                recoloring(parent, nodeRoot);
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
        if (checkForChild(node, parent)) {
            return;
        }
        Node ln = node.leftNode;
        if (isLeftNodeBlack(node)) {
            node.setColor("Black");
            parent.setColor("Red");
            node.leftNode = parent;
            parent.rightNode = ln;
            if (parent == nodeRoot) {
                nodeRoot = node;
            } else {
                Node grandparent = whoIsParent(parent, nodeRoot);
                if (grandparent.leftNode == parent) {
                    grandparent.leftNode = node;
                } else {
                    grandparent.rightNode = node;
                }
            }
        } else if (isRightNodeBlack(node)) { /// TODO: 21.11.2022 разобраться с малым поворотом на левую сторону
            Node nodePointer = node.leftNode.rightNode;
            node.leftNode.rightNode = node;
            parent.rightNode = node.leftNode;
            node.leftNode = nodePointer;
        } else if (isNull(parent.leftNode) || parent.leftNode.number == 0) {
            Node nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.rightNode = node.leftNode;
                nodeRoot.leftNode = nodePointer;
                nodeRoot.rightNode.setColor("Black");
                nodeRoot.setColor("Black");
            } else {
                Node grandParent = whoIsParent(parent, nodeRoot);
                if (grandParent.leftNode == parent) {
                    nodePointer = parent.leftNode;
                    grandParent.leftNode = node;
                } else {
                    nodePointer = parent.rightNode;
                    grandParent.rightNode = node;
                }
                node.leftNode.rightNode = nodePointer;
                recoloring(parent, nodeRoot);
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
            node.setColor("Black");
            parent.setColor("Red");
            node.rightNode = parent;
            parent.leftNode = rn;
            if (parent == nodeRoot) {
                nodeRoot = node;
            } else {
                Node grandparent = whoIsParent(parent, nodeRoot);
                if (grandparent.leftNode == parent) {
                    grandparent.leftNode = node;
                } else {
                    grandparent.rightNode = node;
                }
            }
        } else if (isLeftNodeBlack(node)) {
            nodePointer = node.rightNode.leftNode;
            node.rightNode.leftNode = node;
            parent.leftNode = node.rightNode;
            node.rightNode = nodePointer;

        } else if (isNull(parent.rightNode)) {
            nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.leftNode = node.rightNode;
                nodeRoot.rightNode = nodePointer;
                nodeRoot.leftNode.setColor("Black");
            } else {
                Node grandParent = whoIsParent(parent, nodeRoot);
                if (grandParent.rightNode == parent) {
                    nodePointer = parent.rightNode;
                    grandParent.rightNode = node;
                } else {
                    nodePointer = parent.leftNode;
                    grandParent.leftNode = node;
                }
                node.rightNode.leftNode = nodePointer;
                recoloring(parent, nodeRoot);
            }
        }

    }


    protected Node checkRight(Node node) {
        Node badNode = null;
        if (isGoingLeft(node)) {
            badNode = checkRight(node.leftNode);
        }
        if (isNull(badNode)) {
            badNode = node;
            if (isGoingRight(node)) {
                badNode = checkRight(node.rightNode);
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
            badNode = checkLeft(node.rightNode);
        }
        if (badNode == null) {
            badNode = node;

            if (isGoingLeft(node)) {
                badNode = checkLeft(node.leftNode);
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
        showInside(nodeRoot);
    }

    public void remove(int number) {
        Node removeNode = search(number, nodeRoot);
        if (removeNode == null) {
            return;
        }
        size--;
        Node parent = whoIsParent(removeNode, nodeRoot);
        if ((isRed(removeNode)) && (removeNode.leftNode == null && removeNode.rightNode == null)) {
            if (parent.leftNode == removeNode) {
                parent.leftNode = null;
            } else {
                parent.rightNode = null;
            }
            return;
        }
        if (removeNode.leftNode != null && removeNode.rightNode != null) {
            Node maxNode = searchOfMaxNumber(removeNode.leftNode);
            if (maxNode == removeNode.leftNode) {
                swap(maxNode, removeNode, parent);
                return;
            }
            Node parentMaxNode = whoIsParent(maxNode, nodeRoot);
            if (checkingNephew(maxNode) == null)
                parentMaxNode.rightNode = null;
            maxNode.rightNode = removeNode.rightNode;
            if (parent == null) {
                nodeRoot = maxNode;
                maxNode.leftNode = removeNode.leftNode;
                nodeRoot.setColor("Black");
                return;
            }
            if (parent.leftNode == removeNode) {

                if (maxNode.leftNode != null) {
                    maxNode.leftNode.color = maxNode.color;
                    parentMaxNode.rightNode = maxNode.leftNode;
                }
                Node nodePointer = parent.leftNode.leftNode;
                parent.leftNode = maxNode;
                maxNode.leftNode = nodePointer;
                maxNode.color = removeNode.color;

            } else {
                if (maxNode.rightNode != null) {
                    parentMaxNode.rightNode = maxNode.leftNode;
                }
                Node nodePointer = parent.rightNode.leftNode;
                parent.rightNode = maxNode;
                maxNode.leftNode = nodePointer;
                maxNode.setColor(removeNode.color);
            }
            if (isBlack(maxNode) && maxNode.leftNode == null)
                balancingAfterRemoving(maxNode);
            return;
        }
        if (isBlack(removeNode) && (removeNode.leftNode != null || removeNode.rightNode != null)) {
            if (removeNode.leftNode != null) {
                if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.leftNode;
                    parent.leftNode.setColor("Black");
                } else {
                    parent.rightNode = removeNode.leftNode;
                    parent.rightNode.setColor("Black");
                }

            } else {
                if (nodeRoot == removeNode) {
                    nodeRoot = removeNode.rightNode;
                    nodeRoot.setColor("Black");
                } else if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.rightNode;
                    parent.leftNode.setColor("Black");
                } else {
                    parent.rightNode = removeNode.rightNode;
                    parent.rightNode.setColor("Black");

                }
            }
        }
        if (isBlack(removeNode) && (removeNode.leftNode == null && removeNode.rightNode == null)) {
            if (parent.leftNode == removeNode) {
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
            nodePointer = search(number, node.rightNode);
        } else nodePointer = search(number, node.leftNode);

        return nodePointer;
    }

    private Node searchOfMaxNumber(Node node) {
        Node nodePointer = node;
        if (isNotNull(nodePointer.rightNode)) {
            nodePointer = searchOfMaxNumber(node.rightNode);
        }
        return nodePointer;

    }

    private void balancingAfterRemoving(Node node) { //parent
        Node parent = whoIsParent(node, nodeRoot);// grandfather
        if (isNull(parent)) {
            parent = nodeRoot;
        }
        if (isNull(node.leftNode)) {
            Node nodePointer = new Node();
            nodePointer.setColor("Black");
            node.leftNode = nodePointer;
            Node child = parent.rightNode;
            if (isNull(child.rightNode)) {
                child.rightNode = new Node();
                child.rightNode.setColor("Black");
            }
            if (isNull(child.leftNode)) {
                child.leftNode = new Node();
                child.leftNode.setColor("Black");
            }

            if (isRed(node.rightNode) && isBlack(node.rightNode.leftNode)) {//brother is black, one left child is red, but right child is black
                rotateRight(node, parent);
                if (isNotNull(child.leftNode)) {
                    rotateLeft(node.rightNode.leftNode, node.rightNode);
                }
                if (isNotNull(child.rightNode)) {
                    rotateRight(node.rightNode, node);
                }
                if (isNoChildren(node)) {
                    parent = whoIsParent(node, nodeRoot);
                    recoloringAfterRemoving(parent);
                } else recoloringAfterRemoving(node);

            } else if (isBlack(child.rightNode) && isBlack(child.leftNode)) { // brother's black, children are black
                Node nephew = checkingNephew(node);
                if (isNotNull(nephew) && isRed(nephew)) {
                    if (node.rightNode.leftNode == nephew) { // TODO: 05.02.2023 указать, что если племянник левый то надо сначала повернуть направо и поменять цвет
                        rotateLeft(node.rightNode, node);
                        nephew.setColor("Black");
                        nephew.rightNode.setColor("Red");
                        rotateLeft(node.rightNode, node);
                    } else {
                        rotateLeft(node.rightNode, node);
                    }
                    recoloringAfterRemoving(whoIsParent(node, nodeRoot));
                } else if (isRed(parent)) {
                    parent.setColor("Black");
                    parent.rightNode.setColor("Red");
                } else if (isBlack(node.leftNode) && isBlack(node.rightNode)) {
                    pushUp(node);
                }
                if ((isNotNull(node.rightNode) && isRed(node.rightNode)) || (isNotNull(node.leftNode) && isRed(node.leftNode)))
                    if (isRed(parent.leftNode)) {
                        nodePointer = parent.leftNode;
                        if (isBlack(nodePointer.leftNode) && isBlack(nodePointer.rightNode)) { // TODO: 06.02.2023  вывести эту хрень в отдельный метод, параметром сделать nodePointer также сделать это и для другой стороны
                            Node nodePointerChecker = nodePointer.leftNode;
                            if (isNotNull(nodePointerChecker.leftNode) && isRed(nodePointerChecker.leftNode))
                                nodePointer.setColor("Red");
                            if (isNotNull(nodePointerChecker.rightNode) && isRed(nodePointerChecker.rightNode))
                                nodePointer.setColor("Red");
                            nodePointerChecker = nodePointer.rightNode;
                            if (isNotNull(nodePointerChecker.leftNode) && isRed(nodePointerChecker.leftNode))
                                nodePointer.setColor("Red");
                            if (isNotNull(nodePointerChecker.rightNode) && isRed(nodePointerChecker.rightNode))
                                nodePointer.setColor("Red");
                        }
                    }


            } else if ((isRed(node) && isBlack(node.leftNode)) && isBlack(parent.rightNode)) {
                node.setColor("Black");
                node.rightNode.setColor("Red");
            } else if (isRed(parent.rightNode.rightNode) || isRed(parent.rightNode.leftNode)) {// brother's black, one of his child is red
                node.rightNode.setColor("Red");
                rotateLeft(parent.rightNode, parent);
                if (whoIsParent(parent, nodeRoot) != nodeRoot) {
                    reverseRecoloring(whoIsParent(parent, nodeRoot));
                }
            }
            if ((isNotNull(node.leftNode) && isBlack(node.leftNode) && isNotNull(node.rightNode) && isBlack(node.rightNode)) && isNotNull(node.rightNode.leftNode) && isRed(node.rightNode.leftNode)) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.rightNode.leftNode, node.rightNode);
                node.leftNode = null;
                rotateLeft(node.rightNode, node);
                recoloringAfterRemoving(whoIsParent(node, nodeRoot));
            }

            if (isNotNull(child.leftNode) && child.leftNode.number == 0)
                child.leftNode = null;
            if (isNotNull(child.rightNode) && child.rightNode.number == 0)
                child.rightNode = null;

        } else if (isNull(node.rightNode)) {

            Node nodePointer = new Node();
            nodePointer.setColor("Black");
            node.rightNode = nodePointer;
            Node child = parent.leftNode;
            if (isNull(child.rightNode)) { //TODO вынести в отдельный метод
                child.rightNode = new Node();
                child.rightNode.setColor("Black");
            }
            if (isNull(child.leftNode)) {
                child.leftNode = new Node();
                child.leftNode.setColor("Black");
            }

            if (isRed(node.leftNode) && isBlack(node.leftNode.rightNode)) {//brother is black, one left child is red, but right child is black
                rotateLeft(node, parent);

                if (node.leftNode.rightNode != null) rotateLeft(node.leftNode.rightNode, node.leftNode);
                if (node.leftNode.leftNode != null) rotateRight(node.leftNode, node);

                if (node.leftNode == null && node.rightNode == null) {
                    parent = whoIsParent(node, nodeRoot);
                    recoloringAfterRemoving(parent);
                } else recoloringAfterRemoving(node);


            } else if (isBlack(child.leftNode) && isBlack(child.rightNode)) { // brother's black, children are black
                if (parent.color.equalsIgnoreCase("Red")) {
                    parent.setColor("Black");
                    child.setColor("Red");
                    node.rightNode.setColor("Red");
                    node.leftNode.setColor("Red");

                } else {
                    pushUp(node);


                }
            } else if (isRed(parent.rightNode.rightNode) || isRed(parent.rightNode.leftNode)) {// brother's black, one of his child is red
                node.leftNode.setColor("Red");
                rotateRight(parent.leftNode, parent);
                reverseRecoloring(whoIsParent(parent, nodeRoot));
            }
            if ((node.leftNode != null && isBlack(node.leftNode) && node.rightNode != null && isBlack(node.rightNode)) && node.rightNode.leftNode != null && isRed(node.rightNode.leftNode)) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.rightNode.leftNode, node.rightNode);
                node.leftNode = null;
                rotateLeft(node.rightNode, node);
                recoloringAfterRemoving(whoIsParent(node, nodeRoot));
            }

            if (isNotNull(child.leftNode) && child.leftNode.number == 0)
                child.leftNode = null;
            if (isNotNull(child.rightNode) && child.rightNode.number == 0)
                child.rightNode = null;


        } else {
            if (isRed(parent.leftNode.rightNode) && isRed(parent.leftNode.leftNode)) {
                node.leftNode.setColor("Black");
            } else if (isRed(parent.leftNode.rightNode) || isRed(parent.leftNode.leftNode)) {
                rotateRight(parent.rightNode, parent);
                reverseRecoloring(whoIsParent(parent, nodeRoot));
            }


        }
        if (isNotNull(node.leftNode) && node.leftNode.number == 0) {
            node.leftNode = null;
        }
        if (isNotNull(node.rightNode) && node.rightNode.number == 0) {
            node.rightNode = null;
        }
    }


    private void recoloringAfterRemoving(Node node) {
        Node parent = whoIsParent(node, nodeRoot);
        Node grandparent = whoIsParent(parent, nodeRoot);
        if (isNotNull(grandparent)) {
            if (grandparent.leftNode == parent) {
                parent.color = grandparent.rightNode.color;
            } else parent.color = grandparent.leftNode.color;
        }
        if (parent.leftNode == node) {
            node.color = parent.rightNode.color;
        } else node.color = parent.leftNode.color;
        if (isNotNull(node.leftNode) && isNotNull(node.rightNode)) {
            if (isRed(parent)) {
                node.setColor("Black");
            } else {
                node.setColor("Red");
            }
            if (isBlack(parent.leftNode)) {
                node.color = "Black";
            }
            node.rightNode.setColor("Black");
            node.leftNode.setColor("Black");
        } else {
            if (node.leftNode != null) node.leftNode.setColor("Red");
            else node.rightNode.setColor("Red");
        }
    }

    private void reverseRecoloring(Node parent) {
        parent.setColor("Red");
        if (isNotNull(parent.leftNode)) {
            parent.leftNode.setColor("Black");
        }
        if (isNotNull(parent.rightNode)) {
            parent.rightNode.setColor("Black");
        }
    }

    private void swap(Node maxNode, Node removeNode, Node parent) {
        if (parent.leftNode == removeNode) {
            parent.leftNode = maxNode;
            removeNode.leftNode.color = maxNode.color;
            maxNode.rightNode = removeNode.rightNode;
        } else {
            parent.rightNode = maxNode;
            removeNode.rightNode.color = maxNode.color;
            maxNode.rightNode = removeNode.rightNode;
        }
        if (isNull(maxNode.leftNode) || isNull(maxNode.rightNode)) {
            maxNode.color = removeNode.color;
        }
        balancingAfterRemoving(maxNode);

    }

    private Node checkingNephew(Node node) {
        Node nodePointer = node.rightNode;
        if (isNull(nodePointer)) {
            return null;
        }
        if (isNotNull(nodePointer.rightNode)) {
            return nodePointer.rightNode;
        } else if (isNull(nodePointer.leftNode)) {
            return nodePointer.leftNode;
        } else {
            nodePointer = node.leftNode;
            if (nodePointer.rightNode != null) {
                return nodePointer.rightNode;
            } else if (nodePointer.leftNode != null) {
                return nodePointer.leftNode;
            }
        }
        return null;
    }


    private void pushUp(Node node) {
        node.setColor("Black");
        node.rightNode.setColor("Red");
        node.leftNode.setColor("Red");
    }

    private void putIntoTree(int number) {
        Node checkingBadNode;
        Node nodePointer = nodeRoot;
        Node addNode = new Node(number);
        while (true) {
            if (nodePointer.number > addNode.number && nodePointer.leftNode != null) {
                nodePointer = nodePointer.leftNode;
            } else if (nodePointer.number < addNode.number && nodePointer.rightNode != null) {
                nodePointer = nodePointer.rightNode;
            } else if (nodePointer.number == addNode.number) {
                size--;
                return;
            } else {
                break;
            }
        }
        if (nodePointer.number > addNode.number) {
            nodePointer.leftNode = addNode;
        } else {
            nodePointer.rightNode = addNode;
        }
        checkingBadNode = checkRight(nodeRoot);
        if (checkingBadNode != null) {
            optionsToBalance(checkingBadNode);
        }
        checkingBadNode = checkLeft(nodeRoot);
        if (checkingBadNode != null) {
            optionsToBalance(checkingBadNode);
        }
    }


    private boolean empty() {
        return size == 0;
    }

    private boolean isNull(Node node) {
        return node == null;
    }

    private boolean isNotNull(Node node) {
        return node != null;
    }

    // Блок условий optionToBalance
    private boolean isRightBrotherRed(Node parent) {
        return parent.rightNode != null && parent.rightNode.color.equalsIgnoreCase("Red");
    }

    private boolean isLeftBrotherRed(Node parent) {
        return parent.leftNode != null && parent.leftNode.color.equalsIgnoreCase("Red");
    }

    //Блок условий на RotateLeft/Right

    private boolean isLeftNodeBlack(Node node) {
        return node.leftNode == null || node.leftNode.color.equalsIgnoreCase("Black");
    }

    private boolean isRightNodeBlack(Node node) {
        return node.rightNode == null || node.rightNode.color.equalsIgnoreCase("Black");
    }

    //Блок условий CheckLeft/Right
    private boolean isGoingLeft(Node node) {
        return (node.leftNode != null) && (!node.color.equals(node.leftNode.color)) || (node.leftNode != null) && (node.leftNode.color.equalsIgnoreCase("Black"));
    }

    private boolean isGoingRight(Node node) {
        return (node.rightNode != null) && (!node.color.equals(node.rightNode.color)) || (node.rightNode != null) && (node.rightNode.color.equalsIgnoreCase("Black"));
    }

    private boolean isNoChildren(Node node) {
        return (isNull(node.leftNode) && (isNull(node.rightNode)));
    }

    private boolean isNeighborSameColor(Node node, Node badNode) {
        return (node.rightNode == null) && (!badNode.color.equals(node.leftNode.color)) || (node.leftNode == null) && (!badNode.color.equals(node.rightNode.color));
    }

    private boolean isRed(Node node) {
        return node.color.equalsIgnoreCase("Red");
    }

    private boolean isBlack(Node node) {
        return node.color.equalsIgnoreCase("Black");
    }

    public Node getNodeRoot() {
        return nodeRoot;
    }

    private Boolean checkForChild(Node node, Node parent) {
        if (parent.leftNode == node) {
            return false;
        } else if (parent.rightNode == node) {
            return false;
        } else return true;
    }

}













