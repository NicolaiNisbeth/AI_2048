public class UpSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board.length; j++) {
            int head = 0;
            for (int i = 0; i < board[j].length; i++) {
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
        return(o instanceof UpSwipe);
    }
}
