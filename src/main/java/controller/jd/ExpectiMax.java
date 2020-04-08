package controller.jd;

import controller.AI;
import model.Result;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import util.Utils;

import java.util.List;
import java.util.Set;

public class ExpectiMax implements AI {

    private int maxDepth;
    private Heuristic heuristic;

    public ExpectiMax(int depth){
        this.maxDepth = depth;
    }

    @Override
    public Action getAction(State state) {
        Set<Action> actions = state.getActions();
        double max = Integer.MIN_VALUE;
        Action maxAction = null;
        for(Action action : actions){
            double value = getValue(action, state, 1);
            //System.out.println(value + " " + action);
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
        double sum = 0;
        for(Result result : results){
            State resultState = result.getState();
            double probability = result.getProbability();
            double resultValue = probability * getValue(resultState, depth);
            sum += resultValue;
        }
        return 2 * sum / results.size();
    }

    private double getValue(State state, int depth){
        Set<Action> actions = state.getActions();
        boolean leaf = actions.size() == 0 || depth >= maxDepth;
        if(leaf){
            return heuristic.getValue(state);
        }

        double max = 0;
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
