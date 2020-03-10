package model.action;

import model.State;
import util.Utils;

public class LeftSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int[] row : board) {
            int head = 0;
            for (int i = 1; i < row.length; i++) {
                int value = row[i];
                if (value == -1)
                    continue;
                if (row[head] == -1) {
                    row[head] = value;
                    row[i] = -1;
                } else if (row[head] == value) {
                    row[head++] += value;
                    row[i] = -1;
                } else {
                    if (i != ++head) {
                        row[i] = -1;
                    }
                    row[head] = value;
                }
            }
        }
        return new State(board);
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof LeftSwipe);
    }
}
