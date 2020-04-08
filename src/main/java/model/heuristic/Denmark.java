package model.heuristic;

import controller.AI;
import model.State;

public class Denmark implements Heuristic {
    @Override
    public double getValue(State state) {
        double value = 0;
        int[][] board = state.getBoard();
        value += board[0][0];
        value += board[0][3];
        value += board[3][0];
        value += board[3][3];
        value -= board[1][1];
        value -= board[1][2];
        value -= board[2][1];
        value -= board[2][2];
        return value;
    }
}
