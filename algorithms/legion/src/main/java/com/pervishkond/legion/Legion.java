package com.pervishkond.legion;

import org.pervishkond.list.array.MyArrayList;
import org.pervishkond.list.linked.MyLinkedList;

public class Legion {
    public static void main(String[] args) {
        int step = 2;
        int nextStep = 1;
        long m = System.currentTimeMillis();
        MyArrayList myArrayList = new MyArrayList(10);
        while (myArrayList.size() > 2) {
            if (step > myArrayList.size()) {
                step = step - myArrayList.size();
            }
            myArrayList.delete(step);
            step += nextStep;
        }

        myArrayList.show();

        System.out.println("Time of completing code - ArrayList: " + (double) (System.currentTimeMillis() - m));


        System.out.println("");

        m = System.currentTimeMillis();
        MyLinkedList myLinkedList = new MyLinkedList(10);
        while (myLinkedList.size() > 3) {
            if (step >= myLinkedList.size()) {
                step = step - myLinkedList.size();
            }
            myLinkedList.delete(step);
            step += nextStep;
        }
        myLinkedList.show();
        System.out.println("Time of completing code - LinkedList: " + (double) (System.currentTimeMillis() - m));
    }
}


