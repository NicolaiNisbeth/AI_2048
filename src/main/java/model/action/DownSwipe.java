package model.action;

import model.State;
import util.Utils;

public class DownSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board[0].length; j++) {
            int head = board.length-1;
            for (int i = board.length-2; i >= 0; i--) {
                int value = board[i][j];
                if(value == -1)
                    continue;
                if(board[head][j] == -1){
                    board[head][j] = value;
                    board[i][j] = -1;
                } else if (board[head][j] == value) {
                    board[head--][j] += value;
                    board[i][j] = -1;
                } else {
                    if(i != --head){
                        board[i][j] = -1;
                    }
                    board[head][j] = value;
                }
            }
        }
        return new State(board);
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof DownSwipe);
    }
}
