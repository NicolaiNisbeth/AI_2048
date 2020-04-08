package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class Smoothness implements Heuristic {
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        int horizontal = 0;
        int vertical = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 1; j < board.length; j++){
                if (board[i][j-1] == -1) continue;
                if (board[i][j-1] > board[i][j]){
                    horizontal += board[i][j-1] - board[i][j];
                }
                if (board[j-1][i] > board[j][i]){
                    vertical += board[j-1][i] - board[j][i];
                }
            }
        }
        return horizontal + vertical;
    }
}
