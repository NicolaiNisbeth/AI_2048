package controller.nn;

import controller.AI;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import model.heuristic.nn.NnHeauristic;
import util.Pair;

import java.util.ArrayList;

import static util.Utils.copyBoard;
import static util.Utils.getEmptySquares;
import static util.Utils.spawn;

public class Alphabeta implements AI {

    @Override
    public Action getAction(State state) {
        int maxValue = Integer.MIN_VALUE;
        Action maxAction = null;

        for (Action action : state.getActions()){
            State child = action.getResult(state);
            int value = alphabeta(child, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

            if(value > maxValue){
                maxValue = value;
                maxAction = action;
            }
        }

        return maxAction;
    }

    private int alphabeta(State parent, int depth, int alpha, int beta, boolean maximizer) {
        if (depth == 0 || parent.getActions().isEmpty())
            return new NnHeauristic().getValue(parent);

        if (maximizer){
            int value = Integer.MIN_VALUE;
            for (Action action : parent.getActions()){
                State child = action.getResult(parent);
                value = Math.max(value, alphabeta(child, depth-1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) break;
            }
            return value;
        }
        else {
            // Compute random tile states
            ArrayList<State> children = new ArrayList<>();
            State temp = new State(copyBoard(parent));
            for (Pair<Integer, Integer> coordinates : getEmptySquares(parent)){
                spawn(temp, coordinates, 2);
                children.add(new State(copyBoard(temp)));
                spawn(temp, coordinates, 4);
                children.add(new State(copyBoard(temp)));
                spawn(temp, coordinates, -1);
            }

            int value = Integer.MAX_VALUE;
            for (State child : children){
                value = Math.min(value, alphabeta(child, depth-1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (alpha >= beta) break;
            }
            return value;
        }
    }


    @Override
    public void setHeuristics(Heuristic heuristic) {

    }
}
