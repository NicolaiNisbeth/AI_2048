package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class LocationOfHighValueBlocks implements Heuristic {
    @Override
    public int getValue(State state) {
        int[][] board = state.getBoard();
        int sum = 0;

        int[][] weights = getWeightMatrix();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] != -1)
                    sum += weights[i][j] * board[i][j];
            }
        }


        return sum;
    }

    private int[][] getSnakeMatrix(){
        return new int[][]{
                {(int)Math.pow(4,15),(int)Math.pow(4,14),(int)Math.pow(4,13),(int)Math.pow(4,12)},
                {(int)Math.pow(4,8),(int)Math.pow(4,9),(int)Math.pow(4,10),(int)Math.pow(4,11)},
                {(int)Math.pow(4,7),(int)Math.pow(4,6),(int)Math.pow(4,5),(int)Math.pow(4,4)},
                {(int)Math.pow(4,0),(int)Math.pow(4,1),(int)Math.pow(4,2),(int)Math.pow(4,3)}
        };
    }
    private int[][] getWeightMatrix(){
        return new int[][]{
                {(int)Math.pow(4,2),(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,2)},
                {(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,0)},
                {(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,0)},
                {(int)Math.pow(4,2),(int)Math.pow(4,0),(int)Math.pow(4,0),(int)Math.pow(4,2)}
        };
    }
}
