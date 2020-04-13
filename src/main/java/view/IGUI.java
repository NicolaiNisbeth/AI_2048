package view;

import model.State;
import action.Action;

public interface IGUI {
    void show(State state);
    Action getAction(State state);
}
