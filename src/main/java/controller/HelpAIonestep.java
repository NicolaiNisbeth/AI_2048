package controller;

import model.State;
import action.Action;
import heuristic.Heuristic;

public class HelpAIonestep implements AI {
	
	Heuristic heuristic ; 

	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		double max = 0 ; 
		Action bestAct = null; 
		for(Action action : state.getActions()) {
			double a = heuristic.getValue(action.getResult(state)) ; 
			if(a > max) {
				max = a ;
				bestAct = action ; 
			}
		}
		return bestAct;
	}

	@Override
	public void setHeuristics(Heuristic heuristic) {
		// TODO Auto-generated method stub
		this.heuristic = heuristic ; 
		
	}
	
}
