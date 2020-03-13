package controller.nn;

import controller.AI;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import model.heuristic.ScoreHeuristic;
import util.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.Stack;

import static util.Utils.*;

public class Pseudo implements AI {
    // DFS
    private Stack<State> frontier = new Stack<>();

    @Override
    public Action getAction(State state) {
        int maxValue = Integer.MIN_VALUE;
        Action maxAction = null;

        frontier.add(state);
        while(!frontier.empty()){
            State parent = frontier.pop();
            for (Action action : parent.getActions()){
                State child = action.getResult(parent);
                int value = minimax(child, true);

                if(value > maxValue){
                    maxValue = value;
                    maxAction = action;
                }
            }
        }
        return maxAction;
    }

    /**
     *
     * @param result
     * @param maxStarts
     * @return
     */
    private int minimax(State result, boolean maxStarts) {
        int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
        int depthLimit = 5;

        if (maxStarts)
            return maximize(result, alpha, beta, depthLimit);
        else
            return minimize(result, alpha, beta, depthLimit);
    }

    /**
     *
     * @param parent
     * @param alpha
     * @param beta
     * @param depthLimit
     * @return
     */
    private int maximize(State parent, int alpha, int beta, int depthLimit) {
        // evaluate board at leaf node
        if (parent.getActions().isEmpty() || depthLimit == 0)
            return new ScoreHeuristic().getValue(parent);

        int maxValue = Integer.MIN_VALUE;
        for (Action action : parent.getActions()){
            State child = action.getResult(parent);
            maxValue = Math.max(maxValue, minimize(child, alpha, beta, depthLimit-1));

            // pruning
            if (maxValue >= beta)
                break;

            alpha = Math.max(maxValue, alpha);
        }

        return maxValue;
    }

    /**
     *
     * @param parent
     * @param alpha
     * @param beta
     * @param depthLimit
     * @return
     */
    private int minimize(State parent, int alpha, int beta, int depthLimit) {
        // evaluate board at leaf node
        if (parent.getActions().isEmpty() || depthLimit == 0)
            return new ScoreHeuristic().getValue(parent);

        // Compute random tile states, 2*n, n is empty squares
        ArrayList<State> children = new ArrayList<>();
        for (Pair<Integer, Integer> coordinates : getEmptySquares(parent)){
            State state_rand2 = new State(copyBoard(parent));
            State state_rand4 = new State(copyBoard(parent));

            spawn(state_rand2, coordinates, 2);
            spawn(state_rand4, coordinates, 4);

            children.add(state_rand2);
            children.add(state_rand4);
        }

        int minValue = Integer.MAX_VALUE;
        for (State child : children){
            minValue = Math.min(minValue, maximize(child, alpha, beta, depthLimit-1));

            // pruning
            if (minValue <= alpha)
                break;

            beta = Math.min(minValue, beta);
        }

        return minValue;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {

    }
}
