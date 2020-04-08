package model.heuristic.youmadethis;

import model.State;
import model.heuristic.Heuristic;

public class SmallSnake implements Heuristic {

    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        if(board.length != 4) throw new IllegalArgumentException("Heuristic only supports 4x4 board");
        double value = 0;
        value += Math.pow(board[0][0] == -1 ? 0 : board[0][0], 8);
        value += Math.pow(board[0][1] == -1 ? 0 : board[0][1], 7);
        value += Math.pow(board[0][2] == -1 ? 0 : board[0][2], 6);
        value += Math.pow(board[0][3] == -1 ? 0 : board[0][3], 5);
        value += Math.pow(board[1][3] == -1 ? 0 : board[1][3], 4);
        value += Math.pow(board[1][2] == -1 ? 0 : board[1][2], 3);
        value += Math.pow(board[1][1] == -1 ? 0 : board[1][1], 2);
        value += Math.pow(board[1][0] == -1 ? 0 : board[1][0], 1);
        if(value < 0) System.out.println("Big yikes");
        return value;
    }
}
