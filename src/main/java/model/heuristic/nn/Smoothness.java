package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class Smoothness implements Heuristic {
    @Override
    public int getValue(State state) {
        int[][] board = state.getBoard();
        int monotonicityLeft = 0;
        int monotonicityRight = 0;

        for (int i = 0; i < board.length; i++){
            for (int j = 1; j < board.length; j++){
                if (board[i][j-1] == -1) continue;
                if (board[i][j-1] > board[i][j]){
                    monotonicityLeft += Math.pow(board[i][j-1], 4) - Math.pow(board[i][j], 4);
                } else {
                    monotonicityRight += Math.pow(board[i][j], 4) - Math.pow(board[i][j-1], 4);
                }
            }
        }
        return Math.min(monotonicityLeft, monotonicityRight);
    }
}
