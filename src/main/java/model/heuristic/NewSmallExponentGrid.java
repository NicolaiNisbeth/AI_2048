package model.heuristic;

import model.State;
import model.heuristic.Heuristic;

public class NewSmallExponentGrid implements Heuristic {

    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        if(board.length != 4) throw new IllegalArgumentException("Heuristic only supports 4x4 board");
        int valueTopRight = topRight(board);
        int valueTopLeft = topLeft(board);
        int valueBottomLeft = bottomLeft(board);
        int valueBottomRight = bottomRight(board);
        return Math.max(valueBottomLeft, Math.max(valueBottomRight, Math.max(valueTopLeft, valueTopRight)));
    }

    private int bottomRight(int[][] board) {
        int value = 0;
        //value += Math.pow(board[3][0] == -1 ? 0 : board[3][0], 1);
        value += Math.pow(board[3][1] == -1 ? 0 : board[3][1], 2);
        value += Math.pow(board[3][2] == -1 ? 0 : board[3][2], 3);
        value += Math.pow(board[3][3] == -1 ? 0 : board[3][3], 4);

        //value += Math.pow(board[2][1] == -1 ? 0 : board[2][1], 1);
        value += Math.pow(board[2][2] == -1 ? 0 : board[2][2], 2);
        value += Math.pow(board[2][3] == -1 ? 0 : board[2][3], 3);

        value += Math.pow(board[1][2] == -1 ? 0 : board[1][2], 2);
        //value += Math.pow(board[1][3] == -1 ? 0 : board[1][3], 1);

        //value += Math.pow(board[0][3] == -1 ? 0 : board[0][3], 1);
        return value;
    }

    private int bottomLeft(int[][] board) {
        int value = 0;
        value += Math.pow(board[3][0] == -1 ? 0 : board[3][0], 4);
        value += Math.pow(board[3][1] == -1 ? 0 : board[3][1], 3);
        value += Math.pow(board[3][2] == -1 ? 0 : board[3][2], 2);
        //value += Math.pow(board[3][3] == -1 ? 0 : board[3][3], 1);

        //value += Math.pow(board[2][2] == -1 ? 0 : board[2][2], 1);
        value += Math.pow(board[2][1] == -1 ? 0 : board[2][1], 2);
        value += Math.pow(board[2][0] == -1 ? 0 : board[2][0], 3);

        value += Math.pow(board[1][0] == -1 ? 0 : board[1][0], 2);
        //value += Math.pow(board[1][1] == -1 ? 0 : board[1][1], 1);

        //value += Math.pow(board[0][1] == -1 ? 0 : board[0][1], 1);
        return value;
    }

    private int topLeft(int[][] board) {
        int value = 0;
        value += Math.pow(board[0][0] == -1 ? 0 : board[0][0], 4);
        value += Math.pow(board[0][1] == -1 ? 0 : board[0][1], 3);
        value += Math.pow(board[0][2] == -1 ? 0 : board[0][2], 2);
        //value += Math.pow(board[0][3] == -1 ? 0 : board[0][3], 1);

        //value += Math.pow(board[1][2] == -1 ? 0 : board[1][2], 1);
        value += Math.pow(board[1][1] == -1 ? 0 : board[1][1], 2);
        value += Math.pow(board[1][0] == -1 ? 0 : board[1][0], 3);

        value += Math.pow(board[2][0] == -1 ? 0 : board[2][0], 2);
        //value += Math.pow(board[2][1] == -1 ? 0 : board[2][1], 1);

        //value += Math.pow(board[2][1] == -1 ? 0 : board[3][0], 1);
        return value;
    }

    private int topRight(int[][] board) {
        int value = 0;
        //value += Math.pow(board[0][0] == -1 ? 0 : board[0][0], 1);
        value += Math.pow(board[0][1] == -1 ? 0 : board[0][1], 2);
        value += Math.pow(board[0][2] == -1 ? 0 : board[0][2], 3);
        value += Math.pow(board[0][3] == -1 ? 0 : board[0][3], 4);

        //value += Math.pow(board[1][1] == -1 ? 0 : board[1][1], 1);
        value += Math.pow(board[1][2] == -1 ? 0 : board[1][2], 2);
        value += Math.pow(board[1][3] == -1 ? 0 : board[1][3], 3);

        value += Math.pow(board[2][2] == -1 ? 0 : board[2][2], 2);
        //value += Math.pow(board[2][3] == -1 ? 0 : board[2][3], 1);

        //value += Math.pow(board[2][3] == -1 ? 0 : board[2][3], 1);
        return value;
    }
}
