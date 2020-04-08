import controller.AI;
import controller.jd.ExpectiMax;
import controller.nn.Alphabeta;
import model.heuristic.Denmark;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.EmptySquares;
import model.heuristic.youmadethis.SmallExponentGrid;
import model.heuristic.youmadethis.SmallSnake;
import util.Utils;
import view.TextGUI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TextGUI textGUI = new TextGUI();
        double score = 0;
        int iterations = 100;
        double sum = 0;
        for (int i = 1; i <= iterations; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new Alphabeta(4);
            ai.setHeuristics(new Denmark());
            double value = 0;
            while(!state.getActions().isEmpty()) {

                //textGUI.show(state);
                Action action = ai.getAction(state);
                //System.out.println(action);
                //Thread.sleep(1500);




                value = new ScoreHeuristic().getValue(state);
                if(value > score)
                    score = value;


                state = action.getResult(state);
                Utils.spawn(state);
            }
            sum += value;
            System.out.println(String.format("%d\t=\t%f", i, value));
        }
        System.out.println(String.format("max = %f\naverage = %f", score, sum/iterations));
    }

    public static int[][] setupBoard(){
        int[][] board = new int[4][4];
        for(int[] row : board)
            Arrays.fill(row, -1);
        //int startLocation = (int) (Math.random() * 16);
        //board[startLocation/4][startLocation%4] = 2;
        board[0][2] = 2;
        return board;
    }
}
