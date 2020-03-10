public class DownSwipe implements Action {
    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        int head = 0;
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board[j].length; i++) {
                if(board[i][j] != -1){
                    if(head == 0){
                        board[head++][j] = board[i][j];
                    } else {
                        if(board[head-1][j] == board[i][j]){
                            board[head-1][j] += board[i][j];
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
