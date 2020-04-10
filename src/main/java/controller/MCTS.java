package controller;

import javafx.scene.transform.Shear;
import model.State;
import model.action.Action;
import model.heuristic.Heuristic;
import model.heuristic.HighestNumber;
import util.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class MCTS implements AI {
    private static final Double MIN_VALUE = -Double.MAX_VALUE;
    private static final double MAX_VALUE = Double.MAX_VALUE;

    private Heuristic heuristic;
    private Node root;
    private int iterations;

    public MCTS(int iterations){
        this.iterations = iterations;
    }

    public static class Node {
        State state;
        Node parent;
        int visited = 0;
        double score = 0;
        double probability = 1;
        Action action;
        List<Node> children = new ArrayList<>();
        boolean white = true;

        public Node(State state, Node parent) {
            this.state = state;
            this.parent = parent;
        }
    }

    @Override
    public Action getAction(State state) {
        root = findRoot(state);

        for (int i = 0; i < iterations; i++) {
            Node node = selectFrom(root);
            expansion(node);
        }

        Node maxNode = null;
        int maxVisited = Integer.MIN_VALUE;
        for (Node child : root.children){
            int visited = child.visited;
            if (visited > maxVisited){
                maxVisited = visited;
                maxNode = child;
            }
        }
        if(maxNode == null) return null;

        System.out.println("Evaluation: " + maxNode.score / maxNode.visited);
        return maxNode.action;
    }

    private Node findRoot(State state) {
        if (root == null)
            return new Node(state, null);

        for (Node child : root.children)
            for(Node grandchild : child.children) if (grandchild.state.equals(state)){
                grandchild.parent = null;
                return grandchild;
            }

        throw new IllegalStateException("Root not found");
    }

    private void expansion(Node node) {
        State state = node.state;
        Set<Action> actions = state.getActions();
        actions.forEach(action -> {
            State result = action.getResult(state);
            Node actionChild = new Node(result, node);
            actionChild.white = false;
            actionChild.action = action;
            Utils.getPossibleSpawns(result).forEach(spawn -> {
                Node resultChild = new Node(spawn.getState(), actionChild);
                resultChild.probability = spawn.getProbability();
                resultChild.action = action;
                actionChild.children.add(resultChild);
                double outcome = simulate(resultChild);
                backpropagate(resultChild, outcome);
            });
            node.children.add(actionChild);
        });
    }

    private void backpropagate(Node node, double outcome) {
        while (node != null){
            node.score += outcome;
            node.visited++;
            node = node.parent;
        }
    }

    private double simulate(Node node) {
        State state = node.state;
        Action action;

        while ((action = state.getRandomAction()) != null){
            state = action.getResult(state);
            Utils.spawn(state);
        }

        return heuristic.getValue(state);
    }

    private Node selectFrom(Node node) {

        if (node.children.isEmpty())
            return node;

        double maxValue = MIN_VALUE;
        Node maxNode = null;

        for (Node child : node.children){
            double value = getValue(child);
            if (value > maxValue){
                maxValue = value;
                maxNode = child;
            }
        }

        if (maxNode == null) throw new IllegalStateException();

        return selectFrom(maxNode);
    }

    private double getValue(Node node) {
        double exploitation = node.score / node.visited;
        double exploration = Math.sqrt(2 * Math.log(root.visited)) / node.visited;
        double interest = exploitation + exploration;
        return interest * node.probability;
    }

    @Override
    public void setHeuristics(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
