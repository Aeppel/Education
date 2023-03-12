package com.pervishkond.array;

public class Array {
    public static void main(String[] args) {
        int length = 10;
        int legionerNumber = 0;
        int step = 2;
        int killed = 0;
        int[] soldiers = new int[length];
        for (int s = 0; s < soldiers.length; s++) {
            soldiers[s] = s + 1;
        }
        for (int j = 0; j < soldiers.length; j++) {
            legionerNumber = step - 1;
            soldiers[legionerNumber] = 0;
            step++;
            if (step >= length) {
                step = step - length;
                step++;
            }
            killed = legionerNumber;
            while (killed < length - 1) {
                int order = soldiers[killed];
                soldiers[killed] = soldiers[killed + 1];
                soldiers[killed + 1] = order;
                killed++;
            }
            length--;
            if (length == 2) {
                break;
            }


        }
        for (int x : soldiers) {
            if (x != 0) {
                System.out.println(x);
            }
        }

    }
}
