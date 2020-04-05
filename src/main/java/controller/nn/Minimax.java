package controller.nn;

import controller.AI;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import model.heuristic.nn.NnHeauristic;
import util.Pair;

import java.util.ArrayList;
import java.util.Stack;

import static util.Utils.*;

public class Minimax implements AI {


    @Override
    public Action getAction(State state) {
        int maxValue = Integer.MIN_VALUE;
        Action maxAction = null;

        for (Action action : state.getActions()){
            State child = action.getResult(state);
            int value = minimax(child);

            if(value > maxValue){
                maxValue = value;
                maxAction = action;
            }
        }

        return maxAction;
    }

    /**
     *
     * @param parent
     * @return
     */
    private int minimax(State parent) {
        int alpha = Integer.MIN_VALUE;  // lower bound on what MAX can achieve
        int beta = Integer.MAX_VALUE;   // upper bound on what MIN can achieve
        int depthLimit = 2;
        return maximizer(parent, alpha, beta, depthLimit);
    }

    /**
     *
     * @param parent
     * @param alpha
     * @param beta
     * @param depth
     * @return
     */
    private int maximizer(State parent, int alpha, int beta, int depth) {
        // evaluate board at leaf node or when depth limit is reached
        if (parent.getActions().isEmpty() || depth == 0)
            return new NnHeauristic().getValue(parent);

        int value = Integer.MIN_VALUE;
        for (Action action : parent.getActions()){
            State child = action.getResult(parent);
            value = Math.max(value, minimizer(child, alpha, beta, depth-1));
            // beta-cut
            if (value >= beta)
                break;

            alpha = Math.max(value, alpha);

        }

        return value;
    }

    /**
     *
     * @param parent
     * @param alpha
     * @param beta
     * @param depthLimit
     * @return
     */
    private int minimizer(State parent, int alpha, int beta, int depthLimit) {
        // evaluate board at leaf node or depth limit is reached
        if (parent.getActions().isEmpty() || depthLimit == 0)
            return new NnHeauristic().getValue(parent);

        // Compute random tile states
        ArrayList<State> children = new ArrayList<>();
        State temp = new State(copyBoard(parent));
        for (Pair<Integer, Integer> coordinates : getEmptySquares(parent)){
            spawn(temp, coordinates, 2);
            children.add(new State(copyBoard(temp)));
            spawn(temp, coordinates, 4);
            children.add(new State(copyBoard(temp)));
            spawn(temp, coordinates, -1);
        }

        int value = Integer.MAX_VALUE;
        for (State child : children){
            value = Math.min(value, maximizer(child, alpha, beta, depthLimit-1));
            // alpha-cut
            if (value <= alpha)
                break;

            beta = Math.min(value, beta);
        }

        return value;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {

    }
}
