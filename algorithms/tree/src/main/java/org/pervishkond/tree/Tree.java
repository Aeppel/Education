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
            Node parentNode = searchPlaceToPut(new Node(number), getNodeRoot());
            if (Utils.isNull(parentNode)) {
                return;
            }
            size++;
            Node toPut = new Node(number);
            toPut.setParent(parentNode);
            Utils.put(toPut);
            checkToRebalance();
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public Node getNodeRoot() {
        return nodeRoot;
    }

    private void setNodeRoot(Node node) {
        nodeRoot = node;
        nodeRoot.setParent(null);
    }

    private void rebalance(Node node) {
        if (node == getNodeRoot()) {
            getNodeRoot().recoloring();
        } else {
            Node parent = node.getParent();
            if (parent.getLeftNode() == node) {
                if (Utils.isRightBrotherRed(parent)) {
                    parent.recoloring();
                } else {
                    Node nodePointer = node.rotateRight(parent);
                    if (Utils.isNotNull(nodePointer)) {
                        setNodeRoot(nodePointer);
                    }

                }
            } else if (parent.getRightNode() == node) {
                if (Utils.isLeftBrotherRed(parent)) {
                    parent.recoloring();
                } else {
                    Node nodePointer = node.rotateLeft(parent);
                    if (Utils.isNotNull(nodePointer)) {
                        setNodeRoot(nodePointer);
                    }
                }
            } else {
                parent.recoloring();
            }
            checkToRebalance();
        }
    }

    public void show() {
        StringJoiner result = new StringJoiner(", ");
        nodeRoot.showTree(result);
        System.out.println(result);
    }

    public void remove(int number) {
        Node removeNode = search(number, getNodeRoot());
        if (Utils.isNull(removeNode)) {
            return;
        }
        size--;
        if ((Utils.isRed(removeNode)) && (Utils.isNoChildren(removeNode))) {
            removeRedNodeWithNoChildren(removeNode);
            return;
        }
        if (Utils.IsChildren(removeNode)) {
            removeNodeWithChildren(removeNode);
            return;
        }
        if (Utils.isBlack(removeNode) && (Utils.hasChild(removeNode))) {
            removeBlackNodeWithChild(removeNode);
        }
        if (Utils.isBlack(removeNode) && (Utils.isNoChildren(removeNode))) {
            removeBlackNodeWithNoChildren(removeNode);
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
        Node grandParent = node.getParent();
        if (Utils.isNull(grandParent)) {
            grandParent = getNodeRoot();
        }
        if (Utils.isNull(node.getLeftNode())) {
            Node nodePointer = new Node();
            node.setLeftNode(nodePointer);
            Node child = grandParent.getRightNode();
            createNullLeaf(child);
            if (Utils.isRed(node.getRightNode()) && Utils.isBlack(node.getLeftGrandchildOfRightChild())) {//brother is black, one left child is red
                removingBalanceRightNodeWithBlackBrotherAndRedGrandChild(node, grandParent, child);

            } else if (Utils.isBlack(child.getRightNode()) && Utils.isBlack(child.getLeftNode())) { // brother's black, children are black
                removingBalanceRightNodeWithBlackBrotherAndBlackChildren(node, grandParent);

            } else if ((Utils.isRed(node) && Utils.isBlack(node.getLeftNode())) && Utils.isBlack(grandParent.getRightNode())) {
                node.setColor(Colors.BLACK);
                node.getRightNode().setColor(Colors.RED);
            } else if (Utils.isRed(grandParent.getRightGrandchildOfRightChild()) || Utils.isRed(grandParent.getLeftGrandchildOfRightChild())) {
                removingBalanceRightNodeWithRedChildren(node, grandParent);
            }
            if (Utils.checkForRedNephewAndBlackLeftChild(node)) {// Проверка на красного племянника при удалении красной вершины
                removingBalanceWithRedNephewAndBlackLeftChild(node);
            }
            deleteNullLeaf(child);
        } else if (Utils.isNull(node.getRightNode())) {
            Node nodePointer = new Node();
            node.setRightNode(nodePointer);
            Node child = grandParent.getLeftNode();
            createNullLeaf(child);
            if (Utils.isRed(node.getLeftNode()) && Utils.isBlack(node.getRightGrandchildOfLeftChild())) {
                removingBalanceLeftNodeWithRedChildAndBlackGrandChild(node, grandParent);

            } else if (Utils.isBlack(child.getLeftNode()) && Utils.isBlack(child.getRightNode())) {
                removingBalanceLeftNodeWithBlackChildren(node, grandParent, child);
            } else if (Utils.isRed(grandParent.getRightGrandchildOfRightChild()) || Utils.isRed(grandParent.getLeftGrandchildOfRightChild())) {
                removingBalanceLeftNodeWithRedChildren(node, grandParent);
            }
            if (Utils.checkForRedNephewAndBlackLeftChild(node)) {// Проверка на красного племянника при удалении красной вершины
                removingBalanceWithRedNephewAndBlackLeftChild(node);
            }
            deleteNullLeaf(child);
        } else {
            if (Utils.isRed(grandParent.getRightGrandchildOfLeftChild()) && Utils.isRed(grandParent.getLeftGrandchildOfLeftChild())) {
                node.getLeftNode().setColor(Colors.BLACK);
            } else if (Utils.isRed(grandParent.getRightGrandchildOfLeftChild()) || Utils.isRed(grandParent.getLeftGrandchildOfLeftChild())) {
                Node nodePointer = grandParent.getRightNode().rotateRight(grandParent);
                if (Utils.isNotNull(nodePointer)) {
                    setNodeRoot(nodePointer);
                }

                grandParent.getParent().reverseRecoloring();
            }
        }
        deleteNullLeaf(node);
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
            if (Utils.isNotNull(nodePointer.getRightNode())) {
                return nodePointer.getRightNode();
            } else if (Utils.isNotNull(nodePointer.getLeftNode())) {
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

    private Node searchPlaceToPut(Node toPut, Node node) {
        if (Utils.isMore(toPut, node)) {
            node = searchPlaceToPut(toPut, node.getLeftNode());
        } else if (Utils.isLess(toPut, node)) {
            node = searchPlaceToPut(toPut, node.getRightNode());
        }
        if (Utils.isEquals(node, toPut)) {
            return null;
        }
        return node;
    }


    private void checkToRebalance() {
        Node toBalance = new Node();
        toBalance = toBalance.checkRight(nodeRoot);
        if (Utils.isNotNull(toBalance)) {
            rebalance(toBalance);
        }
        toBalance = new Node();
        toBalance = toBalance.checkLeft(nodeRoot);
        if (Utils.isNotNull(toBalance)) {
            rebalance(toBalance);
        }
    }

    private void removeRedNodeWithNoChildren(Node removeNode) {
        if (removeNode.getParent().getLeftNode() == removeNode) {
            removeNode.getParent().setLeftNode(null);
        } else {
            removeNode.getParent().setRightNode(null);
        }
    }

    private void removeNodeWithChildren(Node removeNode) {
        Node maxNode = searchOfMaxNumber(removeNode.getLeftNode());
        if (maxNode == removeNode.getLeftNode()) {
            swap(maxNode, removeNode, removeNode.getParent());
            return;
        }
        if (Utils.isNull(checkingNephew(maxNode))) {
            maxNode.getParent().setRightNode(null);
            maxNode.setRightNode(removeNode.getRightNode());
            removeNode.getRightNode().setParent(maxNode);
        }
        if (Utils.isNull(removeNode.getParent())) {
            nodeRoot = maxNode;
            maxNode.setLeftNode(removeNode.getLeftNode());
            getNodeRoot().setColor(Colors.BLACK);
            nodeRoot.setParent(null);
            removeNode.getLeftNode().setParent(maxNode);
            return;
        }
        if (removeNode.getParent().getLeftNode() == removeNode) {
            if (Utils.isNotNull(maxNode.getLeftNode())) {
                maxNode.getLeftNode().setColor(maxNode.getColor());
                maxNode.getParent().setRightNode(maxNode.getLeftNode());
                maxNode.getLeftNode().setParent(maxNode.getParent());
            }
            Node nodePointer = removeNode.getParent().getLeftGrandchildOfLeftChild();
            removeNode.getParent().setLeftNode(maxNode);
            maxNode.setParent(removeNode.getParent());
            maxNode.setLeftNode(nodePointer);
            maxNode.getLeftNode().setParent(maxNode);
            maxNode.setColor(removeNode.getColor());

        } else {
            if (Utils.isNotNull(maxNode.getRightNode())) {
                maxNode.getParent().setRightNode(maxNode.getLeftNode());
                maxNode.getLeftNode().setParent(maxNode.getParent());
            }
            Node nodePointer = removeNode.getParent().getLeftGrandchildOfRightChild();
            removeNode.getParent().setRightNode(maxNode);
            maxNode.setLeftNode(nodePointer);
            maxNode.setColor(removeNode.getColor());
            removeNode.getParent().getRightNode().setParent(removeNode.getParent());
            removeNode.getParent().getLeftNode().setParent(removeNode.getParent());
        }
        if (Utils.isBlack(maxNode) && Utils.isNull(maxNode.getLeftNode())) {
            balancingAfterRemoving(maxNode);
        }
    }

    private void removeBlackNodeWithChild(Node removeNode) {
        if (Utils.isNotNull(removeNode.getLeftNode())) {
            if (removeNode.getParent().getLeftNode() == removeNode) {
                removeNode.getParent().setLeftNode(removeNode.getLeftNode());
                removeNode.getParent().getLeftNode().setColor(Colors.BLACK);
            } else {
                removeNode.getParent().setRightNode(removeNode.getLeftNode());
                removeNode.getParent().getRightNode().setColor(Colors.BLACK);
            }
        } else {
            if (getNodeRoot() == removeNode) {
                nodeRoot = (removeNode.getRightNode());
                getNodeRoot().setColor(Colors.BLACK);
            } else if (removeNode.getParent().getLeftNode() == removeNode) {
                removeNode.getParent().setLeftNode(removeNode.getRightNode());
                removeNode.getParent().getLeftNode().setColor(Colors.BLACK);
            } else {
                removeNode.getParent().setRightNode(removeNode.getRightNode());
                removeNode.getParent().getRightNode().setColor(Colors.BLACK);
            }
        }
    }

    private void removeBlackNodeWithNoChildren(Node removeNode) {
        if (removeNode.getParent().getLeftNode() == removeNode) {
            removeNode.getParent().setLeftNode(null);
        } else {
            removeNode.getParent().setRightNode(null);
        }
        balancingAfterRemoving(removeNode.getParent());
    }

    private void createNullLeaf(Node child) {
        if (Utils.isNull(child.getRightNode())) {
            child.setRightNode(new Node());
        }
        if (Utils.isNull(child.getLeftNode())) {
            child.setLeftNode(new Node());
        }
    }

    private void deleteNullLeaf(Node child) {
        if (Utils.isNotNull(child.getLeftNode()) && child.getLeftNode().getNumber() == 0)
            child.setLeftNode(null);
        if (Utils.isNotNull(child.getRightNode()) && child.getRightNode().getNumber() == 0)
            child.setRightNode(null);
    }

    private void removingBalanceRightNodeWithBlackBrotherAndRedGrandChild(Node node, Node grandParent, Node child) {
        Node nodePointer = node.rotateRight(grandParent);
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        if (Utils.isNotNull(child.getLeftNode())) {
            nodePointer = node.getLeftGrandchildOfRightChild().rotateLeft(node.getRightNode());
            if (Utils.isNotNull(nodePointer)) {
                setNodeRoot(nodePointer);
            }
        }
        if (Utils.isNotNull(child.getRightNode())) {
            nodePointer = node.getRightNode().rotateRight(node);
            if (Utils.isNotNull(nodePointer)) {
                setNodeRoot(nodePointer);
            }
            if (Utils.isNoChildren(node)) {
                grandParent = node.getParent();
                grandParent.RecoloringAfterRemove();
            } else node.RecoloringAfterRemove();
        }
    }

    private void removingBalanceRightNodeWithBlackBrotherAndBlackChildren(Node node, Node grandParent) {
        Node nephew = checkingNephew(node);
        if (Utils.isNotNull(nephew) && Utils.isRed(nephew)) {
            if (node.getLeftGrandchildOfRightChild() == nephew) {
                Node nodePointer = node.getRightNode().rotateLeft(node);
                if (Utils.isNotNull(nodePointer)) {
                    setNodeRoot(nodePointer);
                }
                nephew.setColor(Colors.BLACK);
                nephew.getRightNode().setColor(Colors.RED);
                nodePointer = node.getRightNode().rotateLeft(node);
                if (Utils.isNotNull(nodePointer)) {
                    setNodeRoot(nodePointer);
                }
            } else {
                Node nodePointer = node.getRightNode().rotateLeft(node);
                if (Utils.isNotNull(nodePointer)) {
                    setNodeRoot(nodePointer);
                }
            }
            node.getParent().RecoloringAfterRemove();
        } else if (Utils.isRed(grandParent)) {
            grandParent.setColor(Colors.BLACK);
            grandParent.getRightNode().setColor(Colors.RED);
        } else if (Utils.isBlack(node.getLeftNode()) && Utils.isBlack(node.getRightNode())) {
            pushUp(node);
        }
        if ((Utils.isNotNull(node.getRightNode()) && Utils.isRed(node.getRightNode())) || (Utils.isNotNull(node.getLeftNode()) && Utils.isRed(node.getLeftNode())))
            if (Utils.isRed(grandParent.getLeftNode())) {
                Node nodePointer = grandParent.getLeftNode();
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
    }

    private void removingBalanceRightNodeWithRedChildren(Node node, Node grandParent) {
        node.getRightNode().setColor(Colors.RED);
        Node nodePointer = grandParent.getRightNode().rotateLeft(grandParent);
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        if (grandParent.getParent() != getNodeRoot()) {
            grandParent.getParent().reverseRecoloring();
        }
    }

    private void removingBalanceWithRedNephewAndBlackLeftChild(Node node) {
        Node nodePointer = node.getLeftGrandchildOfRightChild().rotateRight(node.getRightNode());
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        node.setLeftNode(null);
        nodePointer = node.getRightNode().rotateLeft(node);
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        node.getParent().RecoloringAfterRemove();
    }

    private void removingBalanceLeftNodeWithRedChildAndBlackGrandChild(Node node, Node grandParent) {
        Node nodePointer = node.rotateLeft(grandParent);
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        if (Utils.isNotNull(node.getRightGrandchildOfLeftChild())) {
            nodePointer = node.getRightGrandchildOfLeftChild().rotateLeft(node.getLeftNode());
            if (Utils.isNotNull(nodePointer)) {
                setNodeRoot(nodePointer);
            }
        }
        if (Utils.isNotNull(node.getLeftGrandchildOfLeftChild())) {
            nodePointer = node.getLeftNode().rotateRight(node);
            if (Utils.isNotNull(nodePointer)) {
                setNodeRoot(nodePointer);
            }
        }
        if (Utils.isNoChildren(node)) {
            grandParent = node.getParent();
            grandParent.RecoloringAfterRemove();
        } else node.RecoloringAfterRemove();
    }

    private void removingBalanceLeftNodeWithBlackChildren(Node node, Node grandParent, Node child) {
        if (grandParent.getColor() == Colors.RED) {
            grandParent.setColor(Colors.BLACK);
            child.setColor(Colors.RED);
            node.getRightNode().setColor(Colors.RED);
            node.getLeftNode().setColor(Colors.RED);
        } else {
            pushUp(node);
        }
    }

    private void removingBalanceLeftNodeWithRedChildren(Node node, Node grandParent) {
        node.getLeftNode().setColor(Colors.RED);
        Node nodePointer = grandParent.getLeftNode().rotateRight(grandParent);
        if (Utils.isNotNull(nodePointer)) {
            setNodeRoot(nodePointer);
        }
        grandParent.getParent().reverseRecoloring();
    }


}















