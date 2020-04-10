import controller.AI;
import controller.MCTS;
import controller.jd.ExpectiMax;
import controller.jd.MiniMaxi;
import controller.nn.Alphabeta;
import controller.nn.Minimax;
import model.heuristic.Corners;
import model.heuristic.Heuristic;
import model.heuristic.HighestNumber;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.Cocktail;
import model.heuristic.nn.EmptySquares;
import model.heuristic.nn.Smoothness;
import model.heuristic.youmadethis.SmallExponentGrid;
import model.heuristic.youmadethis.SmallSnake;
import model.heuristic.youmadethis.SnakeHeuristic;
import util.Utils;
import view.TextGUI;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TextGUI textGUI = new TextGUI();
        double score = 0;
        int iterations = 10;
        double sum = 0;
        for (int i = 1; i <= iterations; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new ExpectiMax(2);
            ai.setHeuristics(outcome ->
                new Cocktail().getValue(outcome)
            );
            double value = 0;
            while(!state.getActions().isEmpty()) {

                //textGUI.show(state);
                Action action = ai.getAction(state);
                //System.out.println(action);

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
