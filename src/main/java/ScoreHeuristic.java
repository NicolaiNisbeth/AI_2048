public class ScoreHeuristic implements Heuristics {
    @Override
    public int getValue(State state) {
        int[][] board = state.getBoard();
        int sum = 0;
        for(int[] row : board)
            for(int value : row)
                if(value != -1)
                    sum += value;
        return sum;
    }
}
