package controller;

import model.State;
import model.action.Action;
import model.heuristic.Heuristic;

public interface AI {
    Action getAction(State state);
    void setHeuristics(Heuristic heuristic);
}
