package com.pervishkond.matrix;

public class Matrix {
    public static void main(String[] args) {
        int longestLengthX = 3;
        int longestLengthY = 4;
        int[][] arrayA = new int[longestLengthY][longestLengthY];
        int[][] arrayB = new int[longestLengthY][longestLengthX];
        int[][] arrayC = new int[longestLengthY][longestLengthX];

        arrayANumber(longestLengthY, arrayA);
        arrayBNumber(longestLengthY, longestLengthX, arrayB);
        calculateArrayC(longestLengthY, longestLengthX, arrayA, arrayB, arrayC);
        showResult(arrayC, longestLengthY, longestLengthX);

    }


    private static void arrayANumber(int longestLengthY, int[][] arrayA) {
        int i = 1;
        for (int s = 0; s < longestLengthY; s++) {
            for (int j = 0; j < longestLengthY; j++) {
                arrayA[s][j] += i;
                i++;

            }
        }
    }


    private static void arrayBNumber(int longestLengthY, int longestLengthX, int[][] arrayB) {
        int i = 1;
        for (int s = 0; s < longestLengthY; s++) {
            for (int j = 0; j < longestLengthX; j++) {
                arrayB[s][j] += i;
                i++;

            }
        }
    }

    private static void calculateArrayC(int longestLengthY, int longestLengthX, int[][] arrayA, int[][] arrayB, int[][] arrayC) {
        int sum = 0;

        for (int j = 0; j < longestLengthY; j++) { // передвигаем отсчет по столбцу
            for (int l = 0; l < longestLengthX; l++) { // передвигаем отсчет по строке
                for (int k = 0; k < longestLengthY; k++) { // рассчет  одного элемента
                    int multiplication = arrayA[j][k] * arrayB[k][l];
                    sum += multiplication;

                }

                arrayC[j][l] = sum;
                sum = 0;
            }
        }

    }

    private static void showResult(int[][] arrayC, int longestLengthY, int longestLengthX) {
        for (int s = 0; s < longestLengthY; s++) {
            for (int j = 0; j < longestLengthX; j++) {
                System.out.print(arrayC[s][j] + " ");
            }
            System.out.println("");
        }

    }


}




