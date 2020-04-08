package model.heuristic;

import model.State;

public interface Heuristic {
    double getValue(State state);
}
