package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;
import util.Utils;

public class EmptySquares implements Heuristic {
    @Override
    public double getValue(State state) {
        //int maxEmptySquares = 16;
        return Utils.getEmptySquares(state).size();
    }
}
