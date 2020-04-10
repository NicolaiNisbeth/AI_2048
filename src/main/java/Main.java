import controller.AI;
import controller.jd.ExpectiMax;
import model.heuristic.HighestNumber;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.Cocktail;

import util.Utils;
import view.Grafic_UI;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Grafic_UI GUI = new Grafic_UI();
        double score = 0;
        int iterations = 4;
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

                GUI.show(state);
                Action action = ai.getAction(state);
                //System.out.println(action);

                value = new ScoreHeuristic().getValue(state);
                if(value > score)
                    score = value;


                state = action.getResult(state);
                Utils.spawn(state);
            }
            if(new HighestNumber().getValue(state) >= 2048){
                GUI.win();
                Grafic_UI.playSound("/win.wav");
            }
            else{
                GUI.lose();
                Grafic_UI.playSound("/loss.wav");
                break;
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
