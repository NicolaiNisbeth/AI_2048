package util;

import model.Result;
import model.State;
import action.Action;
import action.DownSwipe;
import action.LeftSwipe;
import action.RightSwipe;
import action.UpSwipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public static Set<Result> getPossibleSpawns(State state){
        Set<Result> spawns = new HashSet<>();
        ArrayList<Pair<Integer, Integer>> coordinates = getEmptySquares(state);
        for(Pair<Integer, Integer> coordinate : coordinates){
            int[][] board2 = copyBoard(state);
            int[][] board4 = copyBoard(state);
            State state2 = new State(board2);
            State state4 = new State(board4);
            spawn(state2, coordinate, 2);
            spawn(state4, coordinate, 4);
            spawns.add(new Result(state2, 0.9));
            spawns.add(new Result(state4, 0.1));
        }
        return spawns;
    }

    public static Set<Action > getAllActions(){
    	Set<Action> allActions = new HashSet<Action>() ; 
    	allActions.add(new UpSwipe()) ; 
    	allActions.add(new DownSwipe()) ; 
    	allActions.add(new LeftSwipe()) ; 
    	allActions.add(new RightSwipe()) ; 
    	return allActions ; 
    }
}
