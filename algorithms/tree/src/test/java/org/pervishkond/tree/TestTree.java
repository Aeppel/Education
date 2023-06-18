package org.pervishkond.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestTree extends Tree {

    Tree testTree = new Tree();

    @BeforeEach
    public void init() {
        List<Integer> integers = List.of(
                50, 40, 30, 60, 70);

        for (int i = 0; i < integers.size(); i++) {
            testTree.add(integers.get(i));
        }
    }

    @Test
    public void checkAddPart() {
        checkAddingNodeRoot();
        checkSettingAndColorLeftChild();
        checkSettingAndColorRightChild();
        checkSettingAndColorGrandLeftChildOfRightChild();
        checkSettingAndColorGrandRightChildOfRightChild();
    }

    @Test
    public void checkRemovePart() {
        testTree.remove(30);
        checkNodeRootAfterRemoving();
        checkLeftChildAfterRemoving();
        checkRightChildAfterRemoving();
        checkGrandRightChildOfLeftChild();
    }

    private void checkAddingNodeRoot() {
        assertEquals(testTree.getNodeRoot().getNumber(), 40);
        assertEquals(testTree.getNodeRoot().getColor(), Colors.BLACK);
    }

    private void checkSettingAndColorLeftChild() {
        assertEquals(testTree.getNodeRoot().getLeftNode().getNumber(), 30);
        assertEquals(testTree.getNodeRoot().getLeftNode().getColor(), Colors.BLACK);
    }

    private void checkSettingAndColorRightChild() {
        assertEquals(testTree.getNodeRoot().getRightNode().getNumber(), 60);
        assertEquals(testTree.getNodeRoot().getRightNode().getColor(), Colors.BLACK);
    }

    private void checkSettingAndColorGrandLeftChildOfRightChild() {
        assertEquals(testTree.getNodeRoot().getRightNode().getLeftNode().getNumber(), 50);
        assertEquals(testTree.getNodeRoot().getRightNode().getLeftNode().getColor(), Colors.RED);
    }

    private void checkSettingAndColorGrandRightChildOfRightChild() {
        assertEquals(testTree.getNodeRoot().getRightNode().getRightNode().getNumber(), 70);
        assertEquals(testTree.getNodeRoot().getRightNode().getRightNode().getColor(), Colors.RED);

    }


    private void checkNodeRootAfterRemoving() {
        assertEquals(testTree.getNodeRoot().getNumber(), 60);
        assertEquals(testTree.getNodeRoot().getColor(), Colors.BLACK);
    }

    private void checkLeftChildAfterRemoving() {
        assertEquals(testTree.getNodeRoot().getLeftNode().getNumber(), 40);
        assertEquals(testTree.getNodeRoot().getLeftNode().getColor(), Colors.BLACK);
    }

    private void checkRightChildAfterRemoving() {
        assertEquals(testTree.getNodeRoot().getRightNode().getNumber(), 70);
        assertEquals(testTree.getNodeRoot().getRightNode().getColor(), Colors.BLACK);
    }

    private void checkGrandRightChildOfLeftChild() {
        assertEquals(testTree.getNodeRoot().getLeftNode().getRightNode().getNumber(), 50);
        assertEquals(testTree.getNodeRoot().getLeftNode().getRightNode().getColor(), Colors.RED);
    }
}
