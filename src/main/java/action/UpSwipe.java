package action;

import model.State;
import util.Utils;

public class UpSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board[0].length; j++) {
            int head = 0;
            for (int i = 1; i < board.length; i++) {
                int value = board[i][j];
                if(value == -1)
                    continue;
                if(board[head][j] == -1){
                    board[head][j] = value;
                    board[i][j] = -1;
                } else if (board[head][j] == value) {
                    board[head++][j] += value;
                    board[i][j] = -1;
                } else {
                    if(i != ++head){
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
        return(o instanceof UpSwipe);
    }
}
