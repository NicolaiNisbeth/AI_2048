package controller;

import java.util.ArrayList;

import model.State;
import model.action.Action;
import model.heuristic.Heuristic;

public class TwoBrains implements AI {

	Heuristic heuristic ; 
	ArrayList<Double> last_heuristics_diff = new ArrayList<Double> () ; 
	double average_heuristics_diff  ; 
	int round = 0 ; 
	int prerun = 10 ; 
	int diffkeep ; 
	double last_heuristic ; 
	AI main_ai ; 
	AI second_ai ; 
	AI simBot ; 
	
	
	public TwoBrains(AI ai1, int diffkeep , int prerun ) {//keep track on the heuristics change difference
		this.main_ai = ai1; 
		this.diffkeep = diffkeep ; 
		this.simBot = new HelpAIonestep() ;  
	}
	
	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		round++ ; 
		double how_good_this_state = this.heuristic.getValue(state) ; 
		double diff_with_last_time = how_good_this_state - last_heuristic ;
		System.out.println(average_heuristics_diff);
		last_heuristic = how_good_this_state ; 
		int listsize = last_heuristics_diff.size() ;  
		if(listsize== 0 )
		{
			last_heuristics_diff.add(how_good_this_state) ; 
			average_heuristics_diff = how_good_this_state ;
			System.out.println("main") ; 
			return main_ai.getAction(state);
		}
		if(listsize < diffkeep ) {
			last_heuristics_diff.add(diff_with_last_time);
			average_heuristics_diff = (average_heuristics_diff * (round - 1) + diff_with_last_time) / round ;   
			System.out.println("main") ; 
			return main_ai.getAction(state) ; 
		}
		else {
			last_heuristics_diff.remove(0 ) ; 
			last_heuristics_diff.add(diff_with_last_time) ; 
			average_heuristics_diff = (average_heuristics_diff * (round - 1) + diff_with_last_time) / round ;   
		}
		if(average(last_heuristics_diff) < average_heuristics_diff && round > prerun )
			//change AI
		{
			switchAI()  ;
			System.out.println("switch ") ; 
			return main_ai.getAction(state)  ;
		}
		else {
			System.out.println("main") ; 
			return main_ai.getAction(state) ;
		}
	}
	
	private void switchAI () {
		if(main_ai instanceof MCTS) { // switch back to EM
			main_ai = second_ai ; 
			second_ai = null ;
		}
		else {
			second_ai = main_ai ; 
			main_ai = new MCTS2(100 , simBot) ; 
			main_ai.setHeuristics(heuristic);
			second_ai.setHeuristics(heuristic);
		}
		//reset parameter for new AI 
		round = 1 ; 
		last_heuristics_diff.clear();
		average_heuristics_diff = 0 ; 
		
	}
	
	private double average(ArrayList<Double> l) {
		double average = 0 ; 
		for(Double d : l)
			average += d ; 
		return average / l.size() ; 
	}

	@Override
	public void setHeuristics(Heuristic heuristic) {
		// TODO Auto-generated method stub
		this.heuristic = heuristic ; 
		main_ai.setHeuristics(heuristic);
		simBot.setHeuristics(heuristic);
	}

}
