package com.pervishkond.list.array;

public class MyArrayList {
    private int[] array;
    private int deleted;
    private int[] newArray;
    private int length;

    public MyArrayList(int length) {
        this.length = length;
        array = new int[length];
        for (int i = 1; i <= array.length; i++) {
            array[i - 1] = i;
        }
    }

    public void delete(int number) {
        array[number - 1] = 0;
        int remove = number;
        while (remove < array.length) {
            int dead = array[remove];
            array[remove] = array[remove - 1];
            array[remove - 1] = dead;
            remove++;
        }
        length = array.length - 1;
        array(length);
    }

    private int array(int length) {
        newArray = new int[length];
        for (int j = 0; j < newArray.length; j++) {
            newArray[j] = array[j];
        }
        array = newArray.clone();
        deleted++;
        return deleted;
    }

    public void show() {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
    }

    public void add(int length) {
        newArray = new int[array.length + length];
        for (int j = 0; j < array.length; j++) {
            newArray[j] = array[j];
        }
        for (int i = array.length; i < newArray.length; i++) {
            newArray[i] = (i + 1) + deleted;
        }
        array = newArray.clone();
    }

    public int size() {
        return length;
    }
}

