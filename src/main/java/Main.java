import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[][] board = setupBoard();
        State state = new State(board);
        GUI gui = new TextGUI();
        gui.show(state);
        AI ai = new JD_Random();
        ai.setHeuristics(new ScoreHeuristic());
        for (int i = 0; i < 500; i++) {
            Action action = ai.getAction(state);
            System.out.println();
            System.out.println(action);
            if(action == null){
                System.out.println(new ScoreHeuristic().getValue(state));
                break;
            }
            state = action.getResult(state);
            state.spawn();
            System.out.println();
            gui.show(state);
            Thread.sleep(100);
        }
    }

    public static int[][] setupBoard(){
        int[][] board = new int[4][4];
        for(int[] row : board)
            Arrays.fill(row, -1);
        int startLocation = (int) (Math.random() * 16);
        board[startLocation/4][startLocation%4] = 2;
        return board;
    }
}
