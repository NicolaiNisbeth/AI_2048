package model;

public class Result {

    private State state;
    private double probability;

    public Result(State state, double probability){
        this.state = state;
        this.probability = probability;
    }

    public State getState() {
        return state;
    }

    public double getProbability() {
        return probability;
    }
}
