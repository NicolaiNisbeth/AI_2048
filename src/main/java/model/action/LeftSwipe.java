package model.action;

import model.State;
import util.Utils;

public class LeftSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);

        for (int i = 0; i < board.length; i++) {
            int head = 0;
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j];
                if(value != -1){
                    if(head != j){
                        if(board[i][head] == value){

                        } else {
                            board[i][head++] = value;
                            board[i][j] = -1;
                        }
                    } else {
                        if(board[i][head] == value){
                            board[i][head] += value;
                            board[i][j] = -1;
                        }
                        head++;
                    }
                }
            }
        }
        return new State(board);
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof LeftSwipe);
    }
}
