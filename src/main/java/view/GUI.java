package view;

import model.State;
import model.action.Action;

public interface GUI {
    void show(State state);
    Action getAction(State state);
}
