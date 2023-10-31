package com.pervishkond.montecarlo;

public class MonteCarlo {
    public static void main(String[] args) {
        double circlePoints = 0;
        double countThrows = 100000;
        double pointSizeSquare = 1 / countThrows;
        double pi = 0;
        for (double y = 0.0; y <= 1; y = y + pointSizeSquare) {
            for (double x = 0; x <= 1; x = x + pointSizeSquare) {
                double coordinate = Math.sqrt((x * x) + (y * y));
                if (coordinate < 1) {
                    circlePoints++;
                }
            }
        }
        pi = ((circlePoints / countThrows) * 4) / countThrows;
        System.out.println(pi);
    }
}

