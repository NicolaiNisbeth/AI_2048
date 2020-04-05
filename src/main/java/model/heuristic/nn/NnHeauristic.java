package model.heuristic.nn;

import model.State;
import model.heuristic.Heuristic;
import model.heuristic.ScoreHeuristic;

public class NnHeauristic implements Heuristic {
    @Override
    public int getValue(State state) {

        Heuristic locationOfHighValueBlocks = new LocationOfHighValueBlocks();
        Heuristic moreMerges = new NearnessOfSimilarBlocks();
        Heuristic moreEmptySquares = new MoreEmptySquares();

        double a = 47 * locationOfHighValueBlocks.getValue(state);
        double b = 2 * moreMerges.getValue(state);
        double c = 10 * moreEmptySquares.getValue(state);

        return (int)(b+c);
    }



}
