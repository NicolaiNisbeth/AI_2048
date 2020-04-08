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

    private int depthLimit;
    private Heuristic heuristic;

    public Alphabeta(int depth){
        this.depthLimit = depth;
    }

    @Override
    public Action getAction(State state) {
        double maxValue = Integer.MIN_VALUE;
        Action maxAction = null;

        for (Action action : state.getActions()){
            State child = action.getResult(state);
            double value = alphabeta(child, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            //System.out.println(value + " " + action);
            if(value > maxValue){
                maxValue = value;
                maxAction = action;
            }
        }

        return maxAction;
    }

    private double alphabeta(State parent, int depth, double alpha, double beta, boolean maximizer) {
        if (depth == 0 || parent.getActions().isEmpty())
            return heuristic.getValue(parent);

        if (maximizer){
            double value = Integer.MIN_VALUE;
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

            double value = Integer.MAX_VALUE;
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
        this.heuristic = heuristic;
    }
}
