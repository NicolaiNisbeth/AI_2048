import controller.AI;
import controller.JD_Random;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import view.GUI;
import view.TextGUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int score = 0;
        for (int i = 0; i < 1_000_000; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            GUI gui = new TextGUI();
            //gui.show(state);
            AI ai = new JD_Random();
            ai.setHeuristics(new ScoreHeuristic());
            while(true) {
                Action action = ai.getAction(state);
                if(action == null){
                    int value = new ScoreHeuristic().getValue(state);
                    if(value > score)
                        score = value;
                    break;
                }
                state = action.getResult(state);
                state.spawn();
                //gui.show(state);
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
