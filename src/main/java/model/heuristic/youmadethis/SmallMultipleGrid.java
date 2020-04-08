package model.heuristic.youmadethis;

import model.State;
import model.heuristic.Heuristic;

public class SmallMultipleGrid implements Heuristic {

    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        if(board.length != 4) throw new IllegalArgumentException("Heuristic only supports 4x4 board");
        long value = 0;
        value += board[0][0] == -1 ? 0 : board[0][0] * 8;
        value += board[0][1] == -1 ? 0 : board[0][1] * 6;
        value += board[0][2] == -1 ? 0 : board[0][2] * 4;
        value += board[0][3] == -1 ? 0 : board[0][3];
        value += board[1][2] == -1 ? 0 : board[1][2];
        value += board[1][1] == -1 ? 0 : board[1][1] * 4;
        value += board[1][0] == -1 ? 0 : board[1][0] * 6;
        value += board[2][0] == -1 ? 0 : board[2][0] * 4;
        value += board[2][1] == -1 ? 0 : board[2][1];
        if(value < 0)
            System.out.println("Big yikes");
        return value;
    }
}