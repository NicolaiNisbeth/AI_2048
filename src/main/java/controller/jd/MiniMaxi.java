package controller.jd;

import controller.AI;
import model.Result;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import util.Utils;

import java.util.List;
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
            double value = getValue(action, state, 1);
            System.out.println(value + " " + action);
            if(value >= max){
                max = value;
                maxAction = action;
            }
        }
        return maxAction;
    }

    private double getValue(Action action, State parent, int depth) {
        State state = action.getResult(parent);
        Set<Result> results = Utils.getPossibleSpawns(state);
        double min = Integer.MAX_VALUE;
        for(Result result : results){
            State resultState = result.getState();
            double probability = result.getProbability();
            double resultValue = probability * getValue(resultState, depth);
            if(resultValue < min){
                min = resultValue;
            }
        }
        return min;
    }

    private double getValue(State state, int depth){
        Set<Action> actions = state.getActions();
        boolean leaf = actions.size() == 0 || depth >= maxDepth;
        if(leaf){
            return heuristic.getValue(state);
        }

        double max = Integer.MIN_VALUE;
        for(Action action : actions){
            double value = getValue(action, state, depth+1);
            if(value > max){
                max = value;
            }
        }
        return max;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
