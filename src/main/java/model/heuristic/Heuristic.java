package model.heuristic;

import model.State;

public interface Heuristic {
    int getValue(State state);
}
