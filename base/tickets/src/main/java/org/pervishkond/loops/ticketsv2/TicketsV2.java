package org.pervishkond.loops.ticketsv2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TicketsV2 {
    public TicketsV2() {
        System.out.println(calculate());
    }

    private int leftNumbers1 = 0;
    private int leftNumbers2 = 0;
    private int leftNumbers3 = 0;
    private int rightNumbers1 = 0;
    private int rightNumbers2 = 0;
    private int rightNumbers3 = 1;
    private int luckyTickets = 0;


    private int calculate() {
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

