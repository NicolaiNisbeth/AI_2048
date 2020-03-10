import java.util.ArrayList;

public class DownSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board.length; j++) {
            int head = board[j].length-1;
            for (int i = board[j].length-1; i >= 0; i--) {
                if(board[i][j] != -1){
                    if(head == board[j].length-1){
                        board[head--][j] = board[i][j];
                        if(i != head){
                            board[i][j] = -1;
                        }
                    } else {
                        if(board[head+1][j] == board[i][j]){
                            board[head+1][j] += board[i][j];
                        } else {
                            board[head--][j] = board[i][j];
                            board[i][j] = -1;
                        }
                    }
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
