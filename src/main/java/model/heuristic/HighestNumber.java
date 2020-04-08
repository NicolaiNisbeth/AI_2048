package model.heuristic;

import model.State;

public class HighestNumber implements Heuristic{
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        int max = 0;
        for(int[] row : board){
            for( int value : row){
                if (value > max)
                    max = value;
            }
        }
        return max;
    }
}
