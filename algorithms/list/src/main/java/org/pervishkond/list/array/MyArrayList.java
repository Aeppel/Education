package org.pervishkond.list.array;

public class MyArrayList {
    private int[] peopleAmount;
    private int killed;
    private int dead;
    private int deadPeople;
    private int[] newArray;
    private int length;

    public MyArrayList(int length) {
        this.length = length;
        peopleAmount = new int[length];
        for (int i = 1; i <= peopleAmount.length; i++) {
            peopleAmount[i - 1] = i;

        }
    }


    public void delete(int number) {
        peopleAmount[number - 1] = 0;
        killed = number;
        while (killed < peopleAmount.length) {
            dead = peopleAmount[killed];
            peopleAmount[killed] = peopleAmount[killed - 1];
            peopleAmount[killed - 1] = dead;
            killed++;
        }
        length = peopleAmount.length - 1;
        array(length);
    }

    private int array(int length) {
        newArray = new int[length];
        for (int j = 0; j < newArray.length; j++) {
            newArray[j] = peopleAmount[j];

        }
        peopleAmount = newArray.clone();
        deadPeople++;
        return deadPeople;
    }

    public void show() {
        for (int j = 0; j < peopleAmount.length; j++) {
            System.out.print(peopleAmount[j] + " ");
        }
    }

    public void add(int length) {
        newArray = new int[peopleAmount.length + length];
        for (int j = 0; j < peopleAmount.length; j++) {
            newArray[j] = peopleAmount[j];
        }
        for (int i = peopleAmount.length; i < newArray.length; i++) {
            newArray[i] = (i + 1) + deadPeople;
        }
        peopleAmount = newArray.clone();

    }

    public int size() {
        return length;
    }
}

