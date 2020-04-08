package model.heuristic;

import controller.AI;
import model.State;

public class CenterBad implements Heuristic {
    @Override
    public double getValue(State state) {
        double value = 0;
        int[][] board = state.getBoard();
        value -= board[1][1];
        value -= board[1][2];
        value -= board[2][1];
        value -= board[2][2];
        return value;
    }
}
