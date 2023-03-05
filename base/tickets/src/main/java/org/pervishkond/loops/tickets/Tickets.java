package org.pervishkond.loops.tickets;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tickets {

    public Tickets() {
        System.out.println(calculate());
    }

    int luckyTickets = 0;

    private int calculate() {

        for (int rightNumbers1 = 0; rightNumbers1 <= 9; rightNumbers1++) {
            for (int rightNumbers2 = 0; rightNumbers2 <= 9; rightNumbers2++) {
                for (int rightNumbers3 = 0; rightNumbers3 <= 9; rightNumbers3++) {
                    for (int leftNumbers1 = 0; leftNumbers1 <= 9; leftNumbers1++) {
                        for (int leftNumbers2 = 0; leftNumbers2 <= 9; leftNumbers2++) {
                            for (int leftNumbers3 = 0; leftNumbers3 <= 9; leftNumbers3++) {
                                if (leftNumbers1 + leftNumbers2 + leftNumbers3 == rightNumbers1 + rightNumbers2 + rightNumbers3) {
                                    luckyTickets++;
                                }

                            }
                        }
                    }
                }
            }
        }
        return luckyTickets;
    }


}

