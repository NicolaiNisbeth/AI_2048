package model;

import action.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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
                if(j > 0 && (board[i][j] != -1 && (board[i][j-1] == -1 || board[i][j-1] == board[i][j])))
                    directions[LEFT] = true;
                if(j < board[i].length-1 && (board[i][j] != -1 && (board[i][j+1] == -1 || board[i][j+1] == board[i][j])))
                    directions[RIGHT] = true;
                if(i > 0 && (board[i][j] != -1 && (board[i-1][j] == -1 || board[i-1][j] == board[i][j])))
                    directions[UP] = true;
                if(i < board.length-1 && (board[i][j] != -1 && (board[i+1][j] == -1 || board[i+1][j] == board[i][j])))
                    directions[DOWN] = true;
            }
        }

        return directions;
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


    public Action getRandomAction() {
        Set<Action> actions = getActions();

        if (actions.size() == 0) return null;

        int random = (int)(Math.random() * actions.size());
        Iterator<Action> iterator = actions.iterator();
        for (int i = 0; i < random; i++) {
            iterator.next();
        }
        return iterator.next();
    }

}
