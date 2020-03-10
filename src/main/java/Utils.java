public class Utils {
    static int[][] copyBoard(State state){
        int[][] board = state.getBoard();
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }
}
