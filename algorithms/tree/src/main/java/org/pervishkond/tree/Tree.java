package org.pervishkond.tree;

public class Tree extends Node {
    private int size = 0;


    public Tree() {
    }

    public boolean add(int number) {
        Node checkingBadNode;
        if (size == 0) {
            nodeRoot = new Node(number);
            nodeRoot.color = 0;
        } else {
            Node nodePointer = nodeRoot;
            Node addNode = new Node(number);
            while (size > 0) {
                if (nodePointer.number > addNode.number && nodePointer.leftNode != null) {
                    nodePointer = nodePointer.leftNode;
                } else if (nodePointer.number < addNode.number && nodePointer.rightNode != null) {
                    nodePointer = nodePointer.rightNode;
                } else if (nodePointer.number == addNode.number) {
                    return false;
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
        size++;
        return true;

    }

    private void optionsToBalance(Node node) {
        if (node == nodeRoot) {
            recoloring(nodeRoot);
        } else {
            Node parent = whoIsParent(node, nodeRoot);
            if (parent.leftNode == node) {
                if (parent.rightNode != null && parent.rightNode.color == 1) {
                    recoloring(parent);
                } else {
                    rotateRight(node, parent);
                }
            } else if (parent.rightNode == node) {
                if (parent.leftNode != null && parent.leftNode.color == 1) {
                    recoloring(parent);
                } else {
                    rotateLeft(node, parent);
                }

            } else {
                recoloring(parent);
            }

            Node checkingBadNode = checkLeft(nodeRoot);
            if (checkingBadNode != null) {
                optionsToBalance(checkingBadNode);
            }
            checkingBadNode = checkRight(nodeRoot);
            if (checkingBadNode != null) {
                optionsToBalance(checkingBadNode);
            }
        }
    }

    private void recoloring(Node parent) {
        parent.color = 1;
        if (parent.leftNode != null) {
            parent.leftNode.color = 0;
        }
        if (parent.rightNode != null) {
            parent.rightNode.color = 0;
        }
        nodeRoot.color = 0;

    }


    void rotateLeft(Node node, Node parent) {
        if (node == null || parent == null) {
            return;
        }
        if (checkForChild(node, parent)) {
            return;
        }
        Node n = node.leftNode;
        if (node.leftNode == null || node.leftNode.color == 0) {
            node.color = 0;
            parent.color = 1;
            node.leftNode = parent;
            parent.rightNode = n;
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
        } else if (node.rightNode == null || node.rightNode.color == 0) { /// TODO: 21.11.2022 разобраться с малым поворотом на левую сторону
            Node nodePointer = node.leftNode.rightNode;
            node.leftNode.rightNode = node;
            parent.rightNode = node.leftNode;
            node.leftNode = nodePointer;


        } else if (parent.leftNode == null || parent.leftNode.number == 0) {
            Node nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.rightNode = node.leftNode;
                nodeRoot.leftNode = nodePointer;
                nodeRoot.rightNode.color = 0;
                nodeRoot.color = 0;
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
                recoloring(parent);
            }
        }

    }


    protected void rotateRight(Node node, Node parent) {
        if (node == null || parent == null) {
            return;
        }

        Node nodePointer = parent, n = node.rightNode;
        if (node.rightNode == null || node.rightNode.color == 0) {
            node.color = 0;
            parent.color = 1;
            node.rightNode = parent;
            parent.leftNode = n;
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
        } else if (node.leftNode == null || node.leftNode.color == 0) {
            nodePointer = node.rightNode.leftNode;
            node.rightNode.leftNode = node;
            parent.leftNode = node.rightNode;
            node.rightNode = nodePointer;

        } else if (parent.rightNode == null) {
            nodePointer = nodeRoot;
            if (parent == nodeRoot) {
                nodeRoot = node;
                nodePointer.leftNode = node.rightNode;
                nodeRoot.rightNode = nodePointer;
                nodeRoot.leftNode.color = 0;
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

                recoloring(parent);

            }
        }


    }

    protected Node checkRight(Node node) {
        Node badNode = null;
        if ((node.leftNode != null) && (node.color != node.leftNode.color)) {
            badNode = checkRight(node.leftNode);
        } else if ((node.leftNode != null) && (node.leftNode.color == 0)) {
            badNode = checkRight(node.leftNode);
        }
        if (badNode == null) {
            badNode = node;

            if ((node.rightNode != null) && (node.color != node.rightNode.color)) {
                badNode = checkRight(node.rightNode);
            } else if ((node.rightNode != null) && (node.rightNode.color == 0)) {
                badNode = checkRight(node.rightNode);
            }
        }
        if (badNode == null) {
            return null;
        }
        if ((node.leftNode == null) && (node.rightNode == null)) {
            return null;
        } else if ((node.rightNode == null) && (badNode.color != node.leftNode.color)) {
            return null;
        } else if ((node.leftNode == null) && (badNode.color != node.rightNode.color)) {
            return null;
        }
        return badNode;
    }

    protected Node checkLeft(Node node) {
        Node badNode = null;
        if ((node.rightNode != null) && (node.color != node.rightNode.color)) {
            badNode = checkLeft(node.rightNode);
        } else if ((node.rightNode != null) && (node.rightNode.color == 0)) {
            badNode = checkLeft(node.rightNode);
        }
        if (badNode == null) {
            badNode = node;

            if ((node.leftNode != null) && (node.color != node.leftNode.color)) {
                badNode = checkLeft(node.leftNode);
            } else if ((node.leftNode != null) && (node.leftNode.color == 0)) {
                badNode = checkLeft(node.leftNode);
            }
        }
        if (badNode == null) {
            return null;
        }
        if ((node.leftNode == null) && (node.rightNode == null)) {
            return null;
        } else if ((node.rightNode == null) && (badNode.color != node.leftNode.color)) {
            return null;
        } else if ((node.leftNode == null) && (badNode.color != node.rightNode.color)) {
            return null;
        }
        return badNode;
    }

    protected Node whoIsParent(Node node, Node nodeRoot) {
        Node parentNode = nodeRoot;
        if (node == nodeRoot) {
            return null;
        }
        if ((parentNode.leftNode == node) || (parentNode.rightNode == node)) {
            return parentNode;
        }
        if (node.number < parentNode.number) {
            if (parentNode.leftNode != null) {
                parentNode = whoIsParent(node, parentNode.leftNode);
            }
        } else {
            if (parentNode.rightNode != null) {
                parentNode = whoIsParent(node, parentNode.rightNode);
            }
        }
        return parentNode;

    }

    public String show() {
        return show1(nodeRoot);
    }

    public void remove(int number) {
        Node removeNode = search(number, nodeRoot);
        if (removeNode == null) {
            return;
        }
        size--;

        Node parent = whoIsParent(removeNode, nodeRoot);
        if ((removeNode.color == 1) && (removeNode.leftNode == null && removeNode.rightNode == null)) {
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
                nodeRoot.color = 0;
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
                maxNode.color = removeNode.color;
            }
            if (maxNode.color == 0 && maxNode.leftNode == null) balancingAfterRemoving(maxNode);
            return;
        }
        if (removeNode.color == 0 && (removeNode.leftNode != null || removeNode.rightNode != null)) {
            if (removeNode.leftNode != null) {
                if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.leftNode;
                    parent.leftNode.color = 0;
                } else {
                    parent.rightNode = removeNode.leftNode;
                    parent.rightNode.color = 0;
                }

            } else {
                if (nodeRoot == removeNode) {
                    nodeRoot = removeNode.rightNode;
                    nodeRoot.color = 0;
                } else if (parent.leftNode == removeNode) {
                    parent.leftNode = removeNode.rightNode;
                    parent.leftNode.color = 0;
                } else {
                    parent.rightNode = removeNode.rightNode;
                    parent.rightNode.color = 0;

                }
            }
        }
        if (removeNode.color == 0 && (removeNode.leftNode == null && removeNode.rightNode == null)) {
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
        if (nodePointer == null) {
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
        if (nodePointer.rightNode != null) {
            nodePointer = searchOfMaxNumber(node.rightNode);
        }
        return nodePointer;

    }

    private void balancingAfterRemoving(Node node) { //parent
        Node parent = whoIsParent(node, nodeRoot);// grandfather
        if (parent == null) {
            parent = nodeRoot;
        }
        if (node.leftNode == null) {
            Node nodePointer = new Node();
            nodePointer.color = 0;
            node.leftNode = nodePointer;
            if (parent.rightNode.rightNode == null) {
                parent.rightNode.rightNode = new Node();
                parent.rightNode.rightNode.color = 0;
            }
            if (parent.rightNode.leftNode == null) {
                parent.rightNode.leftNode = new Node();
                parent.rightNode.leftNode.color = 0;
            }

            if (node.rightNode.color == 1 && node.rightNode.leftNode.color == 0) {//brother is black, one left child is red, but right child is black
                rotateRight(node, parent);
                if (node.rightNode.leftNode != null) rotateLeft(node.rightNode.leftNode, node.rightNode);
                if (node.rightNode.rightNode != null) rotateRight(node.rightNode, node);
                if (node.leftNode == null && node.rightNode == null) {
                    parent = whoIsParent(node, nodeRoot);
                    recoloringAfterRemoving(parent);
                } else recoloringAfterRemoving(node);

            } else if (parent.rightNode.rightNode.color == 0 && parent.rightNode.leftNode.color == 0) { // brother's black, children are black
                Node nephew = checkingNephew(node);
                if (nephew != null && nephew.color == 1) {
                    if (node.rightNode.leftNode == nephew) { // TODO: 05.02.2023 указать, что если племянник левый то надо сначала повернуть направо и поменять цвет
                        rotateLeft(node.rightNode, node);
                        nephew.color = 0;
                        nephew.rightNode.color = 1;
                        rotateLeft(node.rightNode, node);
                    } else {
                        rotateLeft(node.rightNode, node);
                    }
                    recoloringAfterRemoving(whoIsParent(node, nodeRoot));
                } else if (parent.color == 1) {
                    parent.color = 0;
                    parent.rightNode.color = 1;
                } else if (node.leftNode.color == 0 && node.rightNode.color == 0) {
                    pushUp(node);
                }
                if ((node.rightNode != null && node.rightNode.color == 1) || (node.leftNode != null && node.leftNode.color == 1))
                    if (parent.leftNode.color == 0) {
                        nodePointer = parent.leftNode;
                        if (nodePointer.leftNode.color == 0 && nodePointer.rightNode.color == 0) { // TODO: 06.02.2023  вывести эту хрень в отдельный метод, параметром сделать nodePointer также сделать это и для другой стороны 
                            if (nodePointer.leftNode.leftNode != null && nodePointer.leftNode.leftNode.color == 1)
                                nodePointer.color = 1;
                            if (nodePointer.leftNode.rightNode != null && nodePointer.leftNode.rightNode.color == 1)
                                nodePointer.color = 1;

                            if (nodePointer.rightNode.leftNode != null && nodePointer.rightNode.leftNode.color == 1)
                                nodePointer.color = 1;
                            if (nodePointer.rightNode.rightNode != null && nodePointer.rightNode.rightNode.color == 1)
                                nodePointer.color = 1;
                        }
                    }


            } else if ((node.color == 1 && node.leftNode.color == 0) && parent.rightNode.color == 0) {
                node.color = 0;
                node.rightNode.color = 1;
            } else if (parent.rightNode.rightNode.color == 1 || parent.rightNode.leftNode.color == 1) {// brother's black, one of his child is red
                node.rightNode.color = 1;
                rotateLeft(parent.rightNode, parent);
                if (whoIsParent(parent, nodeRoot) != nodeRoot) {
                    reverseRecoloring(whoIsParent(parent, nodeRoot));
                }
            }
            if ((node.leftNode != null && node.leftNode.color == 0 && node.rightNode != null && node.rightNode.color == 0) && node.rightNode.leftNode != null && node.rightNode.leftNode.color == 1) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.rightNode.leftNode, node.rightNode);
                node.leftNode = null;
                rotateLeft(node.rightNode, node);
                recoloringAfterRemoving(whoIsParent(node, nodeRoot));
            }

            if (parent.rightNode.leftNode != null && parent.rightNode.leftNode.number == 0)
                parent.rightNode.leftNode = null;
            if (parent.rightNode.rightNode != null && parent.rightNode.rightNode.number == 0)
                parent.rightNode.rightNode = null;

        } else if (node.rightNode == null) {

            Node nodePointer = new Node();
            nodePointer.color = 0;
            node.rightNode = nodePointer;
            if (parent.leftNode.rightNode == null) {
                parent.leftNode.rightNode = new Node();
                parent.leftNode.rightNode.color = 0;
            }
            if (parent.leftNode.leftNode == null) {
                parent.leftNode.leftNode = new Node();
                parent.leftNode.leftNode.color = 0;
            }

            if (node.leftNode.color == 1 && node.leftNode.rightNode.color == 0) {//brother is black, one left child is red, but right child is black
                rotateLeft(node, parent);

                if (node.leftNode.rightNode != null) rotateLeft(node.leftNode.rightNode, node.leftNode);
                if (node.leftNode.leftNode != null) rotateRight(node.leftNode, node);

                if (node.leftNode == null && node.rightNode == null) {
                    parent = whoIsParent(node, nodeRoot);
                    recoloringAfterRemoving(parent);
                } else recoloringAfterRemoving(node);


            } else if (parent.leftNode.leftNode.color == 0 && parent.leftNode.rightNode.color == 0) { // brother's black, children are black
                if (parent.color == 1) {
                    parent.color = 0;
                    parent.leftNode.color = 1;
                    node.rightNode.color = 1;
                    node.leftNode.color = 1;

                } else {
                    pushUp(node);


                }
            } else if (parent.rightNode.rightNode.color == 1 || parent.rightNode.leftNode.color == 1) {// brother's black, one of his child is red
                node.leftNode.color = 1;
                rotateRight(parent.leftNode, parent);
                reverseRecoloring(whoIsParent(parent, nodeRoot));
            }
            if ((node.leftNode != null && node.leftNode.color == 0 && node.rightNode != null && node.rightNode.color == 0) && node.rightNode.leftNode != null && node.rightNode.leftNode.color == 1) {// Проверка на красного племянника при удалении красной вершины
                rotateRight(node.rightNode.leftNode, node.rightNode);
                node.leftNode = null;
                rotateLeft(node.rightNode, node);
                recoloringAfterRemoving(whoIsParent(node, nodeRoot));
            }

            if (parent.leftNode.leftNode != null && parent.leftNode.leftNode.number == 0)
                parent.leftNode.leftNode = null;
            if (parent.leftNode.rightNode != null && parent.leftNode.rightNode.number == 0)
                parent.leftNode.rightNode = null;


        } else {
            if (parent.leftNode.rightNode.color == 1 && parent.leftNode.leftNode.color == 1) {
                node.leftNode.color = 0;
            } else if (parent.leftNode.rightNode.color == 1 || parent.leftNode.leftNode.color == 1) {
                rotateRight(parent.rightNode, parent);
                reverseRecoloring(whoIsParent(parent, nodeRoot));
            }


        }
        if (node.leftNode != null && node.leftNode.number == 0) node.leftNode = null;
        if (node.rightNode != null && node.rightNode.number == 0) node.rightNode = null;


    }


    private void recoloringAfterRemoving(Node node) {
        Node parent = whoIsParent(node, nodeRoot);
        Node grandparent = whoIsParent(parent, nodeRoot);
        if (grandparent != null) {
            if (grandparent.leftNode == parent) {
                parent.color = grandparent.rightNode.color;
            } else parent.color = grandparent.leftNode.color;
        }
        if (parent.leftNode == node) {
            node.color = parent.rightNode.color;
        } else node.color = parent.leftNode.color;
        if (node.leftNode != null && node.rightNode != null) {
            if (parent.color == 1) {
                node.color = 0;
            } else {
                node.color = 1;
            }
            if (parent.leftNode.color == 0) node.color = 0;
            node.rightNode.color = 0;
            node.leftNode.color = 0;
        } else {
            if (node.leftNode != null) node.leftNode.color = 1;
            else node.rightNode.color = 1;
        }
    }

    private void reverseRecoloring(Node parent) {
        parent.color = 1;
        if (parent.leftNode != null) {
            parent.leftNode.color = 0;
        }
        if (parent.rightNode != null) {
            parent.rightNode.color = 0;
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
        if (maxNode.leftNode == null || maxNode.rightNode == null) maxNode.color = removeNode.color;
        balancingAfterRemoving(maxNode);

    }

    private Node checkingNephew(Node node) {
        Node nodePointer = node.rightNode;
        if (nodePointer == null) return null;
        if (nodePointer.rightNode != null) {
            return nodePointer.rightNode;
        } else if (nodePointer.leftNode != null) {
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
        node.color = 0;
        node.rightNode.color = 1;
        node.leftNode.color = 1;

    }

    private Boolean checkForChild(Node node, Node parent) {
        if (parent.leftNode == node) {
            return false;
        } else if (parent.rightNode == node) {
            return false;
        } else return true;
    }

}










