package model.heuristic;

import model.State;

public class Corners implements Heuristic {
    @Override
    public double getValue(State state) {
        double value = 0;
        int[][] board = state.getBoard();
        value += board[0][0];
        value += board[0][3];
        value += board[3][0];
        value += board[3][3];
        return value;
    }
}
