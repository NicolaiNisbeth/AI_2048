import controller.AI;
import controller.jd.Dumbo;
import controller.jd.Random;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import view.GUI;
import view.TextGUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int score = 0;
        for (int i = 0; i < 1000; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new Random();
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
