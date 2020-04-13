package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.State;
import action.Action;
import heuristic.Heuristic;
import heuristic.ScoreHeuristic;
import util.Utils;

public class MCTS2 implements AI {

	private Action lastAction;
	private int iteration;
	private Node root;
	private static final double infinity = Double.MAX_VALUE;
	private final Set<Action> allActions = Utils.getAllActions();
	AI simBot;

	public MCTS2(int iteration, AI simBot) {
		this.iteration = iteration;
		this.simBot = simBot;
	}

	class Node {
		Action action_from_parent;
		State state; // the state can be changing
		Set<Action> allLegalActions  ;
		double score = 0;
		double UCB ; 
		int n = 0;

		// ArrayList<Node> parents = new ArrayList<Node > () ;
		Node parent;
		ArrayList<Node> children = new ArrayList<Node>();

		Node(Action action) {
			this.action_from_parent = action;
		}
	}

	public void a() {
		return ;
	}
	
	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		Set<Action> legalActions = new HashSet<Action > () ; 
		for(Action action : allActions)
			if(Utils.getEmptySquares(action.getResult(state)).size() != 0)
				legalActions.add(action) ; 

		if (legalActions.isEmpty())
			throw new IllegalStateException("a state having no further action ") ;

		
		if (root == null) {
			root = new Node(null);
			root.state = state; 
			root.allLegalActions = state.getActions()  ;
			root.action_from_parent = null ; 
			root.parent = null ;
		}
		else
			root = trimTree(state);
		
		for (int i = 0; i < iteration; ++i) {
			//System.out.println(i + " th selection iteration " ) ;
			Node n = selection(root);
			double score = 0;
			if (n.allLegalActions.isEmpty()) {
				score = new ScoreHeuristic().getValue(n.state);
				backpropagate(n, score);
			} else {
				Node nodeToSimulate = n ; 
				if(n.n > 0 || n == root) {
					expand(n);
					Action random = n.state.getRandomAction();
					for (Node child : n.children)
						if (child.action_from_parent.equals(random)) {
							nodeToSimulate = child ; 
						}
				}
				score = simulate(nodeToSimulate);
				backpropagate(nodeToSimulate, score);
			}
		}
		lastAction = getBestAct(root);
		return lastAction;
	}

	private double simulate(Node node) {
		// TODO Auto-generated method stub
		node.state = node.action_from_parent.getResult(node.parent.state);
		Utils.spawn(node.state);
		State state = node.state;
		double average = 0 ; 
		int run = 20 ; 
		for(int i = 0 ; i < run ; i++ ) {
			double value = 0;
			while (!state.getActions().isEmpty()) {
				Action action_ = simBot.getAction(state);
				state = action_.getResult(state);
				Utils.spawn(state);
			}
			value = new ScoreHeuristic().getValue(state);
			average += value ; 
		}
		average /= run ; 

		return average ;
	}

	private Action getBestAct(Node root) {
		// TODO Auto-generated method
		double maxUCB = 0;
		Action result = null;
		for (Action action : root.allLegalActions)
			for (Node child : root.children) {
				if (child.UCB > maxUCB && child.action_from_parent.equals(action)) {
					maxUCB = child.UCB;
					result = child.action_from_parent;
				}
			}
		if(result == null)
			throw new IllegalArgumentException("cannot find best action ") ; 
		return result;
	}

	double UCB(Node node) {
		if (node.n == 0)
			return infinity;
		return node.score / node.n + Math.sqrt(2 * Math.log(root.n)) / node.n;
	}

	private void expand(Node node) {
		// TODO Auto-generated method stub
		if (!node.children.isEmpty())
			throw new IllegalArgumentException("illegal expansion");
		//if(node != root )
			//System.out.println("Expand : " + node.action_from_parent.toString() + UCB(node)) ; 
		for (Action action : allActions) {
			Node actionChild = new Node(action);
			actionChild.parent = node;
			node.children.add(actionChild);
		}
	}

	void backpropagate(Node node, double score) {
		// System.out.println(node.parents.size() + " " +
		// node.probability_from_parent.size());

		node.score += score;
		node.n++;
		if (node.parent != null)
			backpropagate(node.parent, score);
	}

	private Node selection(Node node) {
		// TODO Auto-generated method stub
		if(node.action_from_parent == null && node != root )
			throw new IllegalStateException("there is no action from parent ")  ;
 		
		if (node.children.isEmpty() || (node.allLegalActions.isEmpty()))// terminate condition
			return node;
		Node result = null;
		double maxUCB = 0;
		for (Action action : node.allLegalActions)
			for (Node child : node.children) {
				if(child.action_from_parent.equals(action)) {
					child.UCB = UCB(child) ; 
					//System.out.println(action.toString() + " UCB : " + child.UCB) ; 
					if (child.UCB > maxUCB ) {
						result = child;
						maxUCB = child.UCB  ;
						if(Utils.getEmptySquares(result.action_from_parent.getResult(node.state)).size() == 0)
							throw new IllegalStateException("Illegal Action") ;
					}
				}
			}
		if(result == null & node == root )
			throw new IllegalStateException("cannot select because trim tree problem " ) ; 
		result.state = result.action_from_parent.getResult(node.state);
		if(Utils.getEmptySquares(result.state).size() == 0)
			throw new IllegalStateException("Illegal Action") ; 
		Utils.spawn(result.state);
		result.allLegalActions = result.state.getActions();
		//System.out.println("choose : " + result.action_from_parent.toString() + " UCB : " + UCB(result) + "\n"); 
		return selection(result);
	}

	private Node trimTree(State state ) {
		Node newRoot = null ; 
		for (Node child : root.children) {
			if (child.action_from_parent == lastAction)
				newRoot = child ; 
		}
		if(newRoot == null )
			throw new IllegalArgumentException("cannot find action ");
		
		newRoot.state = state ;
		newRoot.allLegalActions = new HashSet<Action >  ();
		for(Action action : allActions)
			if(Utils.getEmptySquares(action.getResult(state)).size() != 0)
				newRoot.allLegalActions.add(action) ; 
		newRoot.action_from_parent = null ; 
		newRoot.parent = null ;
		
		
		if (newRoot.allLegalActions.isEmpty())
			throw new IllegalStateException("a state having no further action ") ;

		
		for(Action action : newRoot.allLegalActions ) {
			if(Utils.getEmptySquares(action.getResult(state)).size() == 0)
				throw new IllegalStateException("wrong calc of legal action") ;
		}
		
		
		
		ArrayList<Action> remove = new ArrayList<Action > () ; 
		ArrayList<Action> can = new ArrayList<Action > () ; 
		ArrayList<Node> removeNodes = new ArrayList<Node> () ; 
		can.addAll(newRoot.allLegalActions); 
		for(Action action : allActions)
			if(!can.contains(action))
				remove.add(action ) ; 
		for(Node node : newRoot.children)
			if(remove.contains(node.action_from_parent))
				removeNodes.add(node) ; 
		newRoot.children.removeAll(removeNodes) ; 
		return newRoot; 
	}

	
	
	@Override
	public void setHeuristics(Heuristic heuristic) {
		// TODO Auto-generated method stub
		simBot.setHeuristics(heuristic);
	}

}
