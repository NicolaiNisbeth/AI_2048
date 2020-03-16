package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class NnHeauristic implements Heuristic {
    @Override
    public int getValue(State state) {
        int[][] board = state.getBoard();
        int sum = 0;

        int[][] leftUp = {
                {7,6,5,4},
                {6,5,4,3},
                {5,4,3,2},
                {4,3,2,1}
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != -1){
                    sum += board[i][j] * leftUp[i][j];
                }
                else {
                    // credit states with more free tiles
                    sum += 10;
                }
            }
        }

        return sum;
    }



}
