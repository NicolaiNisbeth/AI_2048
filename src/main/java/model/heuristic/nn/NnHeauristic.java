package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;

public class NnHeauristic implements Heuristic {
    @Override
    public int getValue(State state) {

        Heuristic locationOfHighValueBlocks = new LocationOfHighValueBlocks();
        Heuristic numOfMerges = new Merges();
        Heuristic numOfEmptySquares = new EmptySquares();

        double a = 47 * locationOfHighValueBlocks.getValue(state);
        double b = 2 * numOfMerges.getValue(state);
        double c = 10 * numOfEmptySquares.getValue(state);

        return (int)(b+c);
    }



}
