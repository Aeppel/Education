package org.pervishkond.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestTree extends Tree {
    int attempt1 = 50;
    int attempt2 = 40;
    int attempt3 = 30;
    int attempt4 = 60;
    int attempt5 = 70;
    Tree testTree = new Tree();


    @Test
    public void setTestTree() {
        testTree.add(attempt1);
        testTree.add(attempt1);
        testTree.add(attempt2);
        testTree.add(attempt2);
        testTree.add(attempt3);
        testTree.add(attempt3);
        testTree.add(attempt4);
        testTree.add(attempt5);
        testTree.add(attempt5);
        checkAddPart();
        CheckRemovePart(attempt3);
        CheckRemovePart(attempt3);
        CheckRemovePart((int) (Math.random() * 10));
    }

    @Test
    public void checkAddPart() {
        assertAddingNodeRoot();
        assertSettingAndColorLeftChild();
        assertSettingAndColorRightChild();
        assertSettingAndColorGrandLeftChildOfRightChild();
        assertSettingAndColorGrandRightChildOfRightChild();
    }


    private void assertAddingNodeRoot() {
        Assertions.assertEquals(testTree.getNodeRoot().getNumber(), attempt2);
        Assertions.assertEquals(testTree.getNodeRoot().getColor(), Colors.BLACK);
    }

    private void assertSettingAndColorLeftChild() {
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getNumber(), attempt3);
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getColor(), Colors.BLACK);
    }

    private void assertSettingAndColorRightChild() {
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getNumber(), attempt4);
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getColor(), Colors.BLACK);
    }

    private void assertSettingAndColorGrandLeftChildOfRightChild() {
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getLeftNode().getNumber(), attempt1);
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getLeftNode().getColor(), Colors.RED);
    }

    private void assertSettingAndColorGrandRightChildOfRightChild() {
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getRightNode().getNumber(), attempt5);
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getRightNode().getColor(), Colors.RED);

    }

    @Test
    public void CheckRemovePart(int number) {
        testTree.remove(number);
        assertNodeRootAfterRemoving();
        assertLeftChildAfterRemoving();
        assertRightChildAfterRemoving();
        assertGrandRightChildOfLeftChild();
    }

    private void assertNodeRootAfterRemoving() {
        Assertions.assertEquals(testTree.getNodeRoot().getNumber(), attempt4);
        Assertions.assertEquals(testTree.getNodeRoot().getColor(), Colors.BLACK);
    }

    private void assertLeftChildAfterRemoving() {
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getNumber(), attempt2);
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getColor(), Colors.BLACK);
    }

    private void assertRightChildAfterRemoving() {
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getNumber(), attempt5);
        Assertions.assertEquals(testTree.getNodeRoot().getRightNode().getColor(), Colors.BLACK);
    }

    private void assertGrandRightChildOfLeftChild() {
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getRightNode().getNumber(), attempt1);
        Assertions.assertEquals(testTree.getNodeRoot().getLeftNode().getRightNode().getColor(), Colors.RED);
    }

//    @Test
//    public void CheckRotatePart(Node node, Node node2) {
//        testTree.rotateLeft(node, node2);
//        assertNodeRootAfterRemoving();
//        assertLeftChildAfterRemoving();
//        assertRightChildAfterRemoving();
//        assertGrandRightChildOfLeftChild();
//    }


}
