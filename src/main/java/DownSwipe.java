import java.util.ArrayList;

public class DownSwipe implements Action {

    private ArrayList<Pair<Integer, Integer>> emptySquares = new ArrayList<>();

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board.length; j++) {
            int emptySquareCount = 0;
            int head = board[j].length-1;
            for (int i = board[j].length-1; i >= 0; i--) {
                if(board[i][j] != -1){
                    if(head == board[j].length-1){
                        board[head--][j] = board[i][j];
                        board[i][j] = -1;
                    } else {
                        if(board[head+1][j] == board[i][j]){
                            board[head+1][j] += board[i][j];
                            emptySquareCount++;
                        } else {
                            board[head--][j] = board[i][j];
                            board[i][j] = -1;
                        }
                    }
                } else {
                    emptySquareCount++;
                }
                boolean lastIteration = i == board.length -1;
                if(lastIteration){
                    for (int k = 0; k < emptySquareCount; k++) {
                        emptySquares.add(new Pair<>(k,j));
                    }
                }
            }
        }
        spawn(board);
        return new State(board);
    }

    private void spawn(int[][] board) {
        if(emptySquares.size() == 0)
            return;

        int count = emptySquares.size();
        int random = (int) (Math.random() * count);
        Pair<Integer, Integer> coordinates = emptySquares.get(random);
        int valueToSpawn = (Math.random() > 0.9) ? 4 : 2;
        board[coordinates.getFirst()][coordinates.getSecond()] = valueToSpawn;
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof DownSwipe);
    }
}
