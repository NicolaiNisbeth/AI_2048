import java.util.ArrayList;
import java.util.Arrays;
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

    //TODO: Allow swiping for merge (fx whole board is 2s)
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

    //TODO: Optimize
    public void spawn() {
        int emptyCount = 0;
        ArrayList<Pair<Integer, Integer>> emptySquares = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == -1){
                    emptyCount++;
                    emptySquares.add(new Pair<>(i, j));
                }
            }
        }
        int index = (int)(Math.random() * emptyCount);
        Pair<Integer, Integer> coordinates = emptySquares.get(index);
        board[coordinates.getFirst()][coordinates.getSecond()] = Math.random() > 0.9 ? 4 : 2;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof State){
            return Arrays.deepEquals(board, ((State) o).board);
        }
        return false;
    }


}
