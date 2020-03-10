package controller.jd;

import controller.AI;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static util.Utils.copyBoard;

public class Dumbo implements AI {

    private Queue<State> frontier = new LinkedList<>();

    @Override
    public Action getAction(State state) {
        frontier.add(state);
        int size = frontier.size();
        int maxOdds = Integer.MIN_VALUE;
        Action maxAction = null;
        for (int j = 0; j < size; j++) {
            State s = frontier.poll();
            assert s != null;
            for(Action action : s.getActions()){
                State result = action.getResult(s);
                ArrayList<State> variations = findAllVariations(result);
                variations.removeIf(state2 -> state2.getActions().size() == 0);
                if(variations.size() > maxOdds){
                    maxOdds = variations.size();
                    maxAction = action;
                }
            }
        }
        return maxAction;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {

    }

    private ArrayList<State> findAllVariations(State result) {
        ArrayList<State> states = new ArrayList<>();
        int[][] board = copyBoard(result);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == -1){
                    board[i][j] = 2;
                    frontier.add(new State(board));
                    board[i][j] = 4;
                    frontier.add(new State(board));
                    board[i][j] = -1;
                }
            }
        }
        return states;
    }

}
