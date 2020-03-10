import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

public class DownSwipe implements Action {

    private ArrayList<Pair<Integer, Integer>> emptySquares = new ArrayList<>();

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state);
        for (int j = 0; j < board.length; j++) {
            int emptySquareCount = 0;
            int head = board[j].length-1;
            for (int i = 0; i < board[j].length; i++) {
                if(board[i][j] != -1){
                    if(head == board[j].length-1){
                        board[head--][j] = board[i][j];
                    } else {
                        if(board[head+1][j] == board[i][j]){
                            board[head+1][j] += board[i][j];
                            emptySquareCount++;
                        }
                    }
                } else {
                    emptySquareCount++;
                }
            }
            boolean lastIteration = j == board.length -1;
            if(lastIteration){
                for (int i = 0; i < emptySquareCount; i++) {
                    emptySquares.add(new Pair<>(i,j));
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
        board[coordinates.fst][coordinates.snd] = valueToSpawn;
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof DownSwipe);
    }
}
