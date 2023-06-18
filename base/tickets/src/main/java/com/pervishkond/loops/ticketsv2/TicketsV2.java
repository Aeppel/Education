package com.pervishkond.loops.ticketsv2;

public class TicketsV2 {
    public TicketsV2() {
        System.out.println(calculate());
    }

    private int calculate() {
        int luckyTickets = 0;
        int rightNumbers3 = 1;
        int rightNumbers2 = 0;
        int rightNumbers1 = 0;
        int leftNumbers3 = 0;
        int leftNumbers2 = 0;
        int leftNumbers1 = 0;
        for (int i = 1; i <= 999999; i++) {
            int sumRight = rightNumbers1 + rightNumbers2 + rightNumbers3;
            int sumLeft = leftNumbers1 + leftNumbers2 + leftNumbers3;
            if (sumLeft == sumRight) {
                luckyTickets++;
            }
            rightNumbers3++;
            if (rightNumbers3 == 10) {
                rightNumbers2++;
                rightNumbers3 = 0;
            }
            if (rightNumbers2 == 10) {
                rightNumbers1++;
                rightNumbers2 = 0;
            }
            if (rightNumbers1 == 10) {
                leftNumbers3++;
                rightNumbers1 = 0;
            }
            if (leftNumbers3 == 10) {
                leftNumbers2++;
                leftNumbers3 = 0;
            }
            if (leftNumbers2 == 10) {
                leftNumbers1++;
                leftNumbers2 = 0;
            }
        }
        return luckyTickets;
    }
}

