package controller;

import model.State;
import action.Action;
import heuristic.Heuristic;

public interface AI {
    Action getAction(State state);
    void setHeuristics(Heuristic heuristic);
}
