import java.util.function.Function;

public interface AI {
    Action getAction(State state);
    void setHeuristics(Heuristics heuristics);
}
