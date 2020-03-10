public class DownSwipe implements Action {
    @Override
    public State getResult(State state) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof DownSwipe);
    }
}
