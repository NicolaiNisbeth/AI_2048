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
                int value = minimax(child);

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
     * @param parent
     * @return
     */
    private int minimax(State parent) {
        int alpha = Integer.MIN_VALUE;  // lower bound on what MAX can achieve
        int beta = Integer.MAX_VALUE;   // upper bound on what MIN can achieve
        int depthLimit = 3;
        return maximize(parent, alpha, beta, depthLimit);
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
        // evaluate board at leaf node or depth limit is reached
        if (parent.getActions().isEmpty() || depthLimit == 0)
            return new NnHeauristic().getValue(parent);

        int value = Integer.MIN_VALUE;
        for (Action action : parent.getActions()){
            State child = action.getResult(parent);
            value = Math.max(value, minimize(child, alpha, beta, depthLimit-1));
            alpha = Math.max(value, alpha);

            // beta-cut
            if (value >= beta)
                break;
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
    private int minimize(State parent, int alpha, int beta, int depthLimit) {
        // evaluate board at leaf node or depth limit is reached
        if (parent.getActions().isEmpty() || depthLimit == 0)
            return new NnHeauristic().getValue(parent);

        // Compute random tile states
        ArrayList<State> children = new ArrayList<>();
        State temp = new State(copyBoard(parent));
        for (Pair<Integer, Integer> coordinates : getEmptySquares(parent)){
            spawn(temp, coordinates, 2);
            children.add(temp);
            spawn(temp, coordinates, 4);
            children.add(temp);
            spawn(temp, coordinates, -1);
        }

        int value = Integer.MAX_VALUE;
        for (State child : children){
            value = Math.min(value, maximize(child, alpha, beta, depthLimit-1));
            beta = Math.min(value, beta);

            // alpha-cut
            if (value <= alpha)
                break;
        }

        return value;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {

    }
}
