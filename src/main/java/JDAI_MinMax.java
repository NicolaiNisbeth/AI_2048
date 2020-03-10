import java.util.ArrayList;
import java.util.Set;

public class JDAI_MinMax implements AI {

    private Heuristics heuristics;

    @Override
    public Action getAction(State state) {
        Set<Action> actions = state.getActions();
        if(actions.size() == 0)
            return null;

        int max = Integer.MIN_VALUE;
        Action maxAction = null;
        for(Action action : actions){
            State result = action.getResult(state);
            result.spawn();
            int min = Integer.MAX_VALUE;
            Action minAction = null;
            for(Action action2 : result.getActions()){
                State result2 = action2.getResult(result);
                int value = heuristics.getValue(result2);
                if(value < min){
                    min = value;
                    minAction = action2;
                }
            }
            if(min > max){
                max = min;
                maxAction = minAction;
            }
        }
        return maxAction;
    }

    @Override
    public void setHeuristics(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

}
