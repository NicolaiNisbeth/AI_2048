import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] board = new int[4][4];
        for(int[] row : board)
            Arrays.fill(row, -1);
        int startLocation = (int) (Math.random() * 16);
        board[startLocation/4][startLocation%4] = 2;
        State initialState = new State(board);
    }
}
