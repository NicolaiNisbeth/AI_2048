package model.heuristic;

import model.State;

public class Corners implements Heuristic {
    @Override
    public double getValue(State state) {
        double value = 0;
        int[][] board = state.getBoard();
        return Math.max(board[0][0], Math.max(board[0][3], Math.max(board[3][0], board[3][3])));
    }
}
