package model.heuristic.youmadethis;

import model.State;
import model.heuristic.Heuristic;

public class SnakeHeuristic implements Heuristic {

    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        if(board.length != 4) throw new IllegalArgumentException("Heuristic only supports 4x4 board");
        double value = 0;
        value += Math.pow(board[0][0], 16);
        value += Math.pow(board[0][1], 15);
        value += Math.pow(board[0][2], 14);
        value += Math.pow(board[0][3], 13);
        value += Math.pow(board[1][3], 12);
        value += Math.pow(board[1][2], 11);
        value += Math.pow(board[1][1], 10);
        value += Math.pow(board[1][0], 9);
        value += Math.pow(board[2][0], 8);
        value += Math.pow(board[2][1], 7);
        value += Math.pow(board[2][2], 6);
        value += Math.pow(board[2][3], 5);
        value += Math.pow(board[3][3], 4);
        value += Math.pow(board[3][2], 3);
        value += Math.pow(board[3][1], 2);
        value += Math.pow(board[3][0], 1);
        if(value < 0) System.out.println("Big yikes");
        return value;
    }
}
