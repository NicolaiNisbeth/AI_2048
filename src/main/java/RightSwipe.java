public class RightSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int i = 0; i < board.length; i++) {
            int head = board.length-1;
            for (int j = board.length-1; j >= 0; j--) {
                if(board[i][j] != -1){
                    if(head == board.length-1){
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
        return(o instanceof RightSwipe);
    }
}
