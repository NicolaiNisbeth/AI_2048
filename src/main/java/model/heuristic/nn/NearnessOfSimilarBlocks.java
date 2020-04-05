package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class NearnessOfSimilarBlocks implements Heuristic {
    @Override
    public int getValue(State state) {
        int merges = 0;
        int[][] board = state.getBoard();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] == -1) continue;
                if (j != board.length-1 && board[i][j] == board[i][j+1]) merges++;
                if (i != board.length-1 && board[i][j] == board[i+1][j]) merges++;
            }
        }
        return merges;
    }
}