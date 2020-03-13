package util;

import model.State;
import java.util.ArrayList;

public class Utils {
    public static int[][] copyBoard(State state){
        int[][] board = state.getBoard();
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }

    public static ArrayList<Pair<Integer, Integer>> getEmptySquares(State state){
        ArrayList<Pair<Integer, Integer>> emptySquares = new ArrayList<>();
        int[][] board = state.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == -1){
                    emptySquares.add(new Pair<>(i, j));
                }
            }
        }
        return emptySquares;
    }

    public static void spawn(State state) {
        ArrayList<Pair<Integer, Integer>> emptySquares = Utils.getEmptySquares(state);
        int emptyCount = emptySquares.size();
        int index = (int)(Math.random() * emptyCount);
        Pair<Integer, Integer> coordinates = emptySquares.get(index);
        spawn(state, coordinates);
    }

    public static void spawn(State state, Pair<Integer, Integer> coordinates){
        spawn(state, coordinates, Math.random()>0.9 ? 4 : 2);
    }

    public static void spawn(State state, Pair<Integer, Integer> coordinates, int val){
        state.getBoard()[coordinates.getFirst()][coordinates.getSecond()] = val;
    }
}
