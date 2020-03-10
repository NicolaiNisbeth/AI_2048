public class LeftSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int i = 0; i < board.length; i++) {
            int head = 0;
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != -1){
                    if(head == 0){
                        board[head][j] = board[i][j];
                        if(i != head){
                            board[i][j] = -1;
                        }
                        head++;
                    } else {
                        if(board[head-1][j] == board[i][j]){
                            board[head-1][j] += board[i][j];
                            board[i][j] = -1;
                        } else {
                            board[head][j] = board[i][j];
                            if(i != head){
                                board[i][j] = -1;
                            }
                            head++;
                        }
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
