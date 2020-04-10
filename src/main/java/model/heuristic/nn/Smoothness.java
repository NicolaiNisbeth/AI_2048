package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class Smoothness implements Heuristic {
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        int delta = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                int value = board[i][j];
                if(i > 1){
                    int previous = board[i-1][j];
                    delta += Math.abs(previous - value);
                }
                if(j > 1){
                    int previous = board[i][j-1];
                    delta += Math.abs(previous - value);
                }
            }
        }
        return delta;
    }
}
