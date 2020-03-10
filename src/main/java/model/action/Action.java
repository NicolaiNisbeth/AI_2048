package model.action;

import model.State;

public interface Action {
    State getResult(State state);
}
