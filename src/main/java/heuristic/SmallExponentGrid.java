package heuristic;

import model.State;

public class SmallExponentGrid implements Heuristic {
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        if(board.length != 4) throw new IllegalArgumentException("Heuristic only supports 4x4 board");
        return topLeft(board);
    }

    private double topLeft(int[][] board) {
        long value = 0;
        value += Math.pow(board[0][0] == -1 ? 0 : board[0][0], 4);
        value += Math.pow(board[0][1] == -1 ? 0 : board[0][1], 3);
        value += Math.pow(board[0][2] == -1 ? 0 : board[0][2], 2);
        value += Math.pow(board[0][3] == -1 ? 0 : board[0][3], 1);

        value += Math.pow(board[1][2] == -1 ? 0 : board[1][2], 1);
        value += Math.pow(board[1][1] == -1 ? 0 : board[1][1], 2);
        value += Math.pow(board[1][0] == -1 ? 0 : board[1][0], 3);

        value += Math.pow(board[2][0] == -1 ? 0 : board[2][0], 2);
        value += Math.pow(board[2][1] == -1 ? 0 : board[2][1], 1);

        return value;
    }
}
