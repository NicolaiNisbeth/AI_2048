package heuristic;

import model.State;

public class ScoreHeuristic implements Heuristic {
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        int sum = 0;
        for(int[] row : board)
            for(int value : row)
                if(value != -1)
                    sum += value;
        return sum;
    }
}
