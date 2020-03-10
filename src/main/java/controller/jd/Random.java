package controller.jd;

import controller.AI;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;

import java.util.Iterator;
import java.util.Set;

public class Random implements AI {

    @Override
    public Action getAction(State state) {
        Set<Action> actions = state.getActions();
        if(actions.size() == 0)
            return null;

        Action action = null;
        Iterator<Action> iterator = actions.iterator();
        int random = (int)(Math.random() * actions.size());
        for (int i = 0; i <= random; i++) {
            action = iterator.next();
        }
        return action;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {

    }

}
