import java.util.HashSet;
import java.util.Set;

public class State {

    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    private boolean[] directionLegality;
    private final Set<Action> actions = new HashSet<>();
    private final int[][] board;

    public State(int[][] board){
        this.board = board;
    }

    public Set<Action> getActions(){
        if(directionLegality != null)
            return actions;

        directionLegality = calculateDirectionLegality();

        if(directionLegality[UP])
            actions.add(new UpSwipe());
        if(directionLegality[DOWN])
            actions.add(new DownSwipe());
        if(directionLegality[LEFT])
            actions.add(new LeftSwipe());
        if(directionLegality[RIGHT])
            actions.add(new RightSwipe());

        return actions;
    }

    private boolean[] calculateDirectionLegality() {
        boolean[] directions = new boolean[4];

        // Enjoy :)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(j > 0 && (board[i][j] != -1 && board[i][j-1] == -1))
                    directions[LEFT] = true;
                if(j < board[i].length-1 && (board[i][j] != -1 && board[i][j+1] == -1))
                    directions[RIGHT] = true;
                if(i > 0 && (board[i][j] != -1 && board[i-1][j] == -1))
                    directions[UP] = true;
                if(i < board.length-1 && (board[i][j] != -1 && board[i+1][j] == -1))
                    directions[DOWN] = true;
            }
        }

        return directions;
    }

    public int[][] getBoard() {
        return board;
    }


}
