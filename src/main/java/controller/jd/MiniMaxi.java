package controller.jd;

import controller.AI;
import model.Result;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import util.Utils;

import java.util.Set;

public class MiniMaxi implements AI {

    private int maxDepth;
    private Heuristic heuristic;

    public MiniMaxi(int depth){
        this.maxDepth = depth;
    }

    @Override
    public Action getAction(State state) {
        Set<Action> actions = state.getActions();
        double max = Integer.MIN_VALUE;
        Action maxAction = null;
        for(Action action : actions){
            double value = minimizer(action, state, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            if(value >= max){
                max = value;
                maxAction = action;
            }
        }
        return maxAction;
    }

    private double minimizer(Action action, State parent, double alpha, double beta, int depth) {
        State state = action.getResult(parent);
        Set<Result> results = Utils.getPossibleSpawns(state);
        double min = Integer.MAX_VALUE;
        for(Result result : results){
            State resultState = result.getState();
            double value = maximizer(resultState, depth, alpha, beta);
            if(value < min){
                min = value;
            }
            beta = Math.min(beta, min);
            if (value <= alpha) break;
        }
        return min;
    }

    private double maximizer(State state, int depth, double alpha, double beta){
        Set<Action> actions = state.getActions();
        boolean leaf = actions.size() == 0 || depth >= maxDepth;
        if(leaf){
            return heuristic.getValue(state);
        }

        double max = Integer.MIN_VALUE;
        for(Action action : actions){
            double value = minimizer(action, state, alpha, beta, depth+1);
            if(value > max){
                max = value;
            }
            alpha = Math.max(alpha, value);
            if (value >= beta) break;
        }
        return max;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
