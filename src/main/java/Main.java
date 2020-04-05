import controller.AI;
import controller.nn.Alphabeta;
import controller.nn.Minimax;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import util.Utils;
import view.TextGUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TextGUI textGUI = new TextGUI();
        int score = 0;
        int iterations = 100;
        int sum = 0;
        for (int i = 1; i <= iterations; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new Alphabeta();
            int value = 0;
            while(!state.getActions().isEmpty()) {
                Action action = ai.getAction(state);
                /*
                Thread.sleep(250);
                textGUI.show(state);
                System.out.println(action);


                 */
                value = new ScoreHeuristic().getValue(state);
                if(value > score)
                    score = value;


                state = action.getResult(state);
                Utils.spawn(state);
            }
            sum += value;
            System.out.println(String.format("%d\t=\t%d", i, value));
        }
        System.out.println(String.format("max = %d\naverage = %d", score, sum/iterations));
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
