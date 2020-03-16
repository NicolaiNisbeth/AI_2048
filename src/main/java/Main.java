import controller.AI;
import controller.nn.Pseudo;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import util.Utils;
import view.GUI;
import view.TextGUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TextGUI textGUI = new TextGUI();
        int score = 0;
        for (int i = 0; i < 1000; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new Pseudo();
            while(!state.getActions().isEmpty()) {
                Action action = ai.getAction(state);
                /*
                Thread.sleep(250);
                textGUI.show(state);
                System.out.println(action);

                 */
                int value = new ScoreHeuristic().getValue(state);
                if(value > score)
                    score = value;

                state = action.getResult(state);
                Utils.spawn(state);
            }
        }
        System.out.println(score);
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
