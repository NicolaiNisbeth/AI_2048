import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[][] board = new int[4][4];
        for(int[] row : board)
            Arrays.fill(row, -1);
        int startLocation = (int) (Math.random() * 16);
        board[startLocation/4][startLocation%4] = 2;
        State state = new State(board);
        GUI gui = new TextGUI();
        gui.show(state);
        AI ai = new JDAI_MinMax();
        ai.setHeuristics(new ScoreHeuristic());
        for (int i = 0; i < 500; i++) {
            Action action = ai.getAction(state);
            state = action.getResult(state);
            state.spawn();
            gui.show(state);
            Thread.sleep(1000);
        }
    }
}
