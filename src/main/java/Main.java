import controller.AI;
import controller.MCTS;
import controller.jd.ExpectiMax;
import controller.nn.Alphabeta;
import model.action.DownSwipe;
import model.action.LeftSwipe;
import model.action.RightSwipe;
import model.action.UpSwipe;
import model.heuristic.HighestNumber;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.Cocktail;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import util.Utils;
import view.Grafic_UI;

import javax.sound.midi.Track;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Grafic_UI GUI = new Grafic_UI();
        double score = 0;
        int iterations = 1;
        double sum = 0;
        for (int i = 1; i <= iterations; i++) {
            Tracker stats = new Tracker();
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new MCTS(10);
            ai.setHeuristics(outcome ->
                new ScoreHeuristic().getValue(outcome)
            );
            double value = 0;
            while(!state.getActions().isEmpty()) {

                GUI.show(state);
                Action action = ai.getAction(state);
                //System.out.println(action);
                stats.track(action);

                value = new ScoreHeuristic().getValue(state);
                if(value > score)
                    score = value;


                state = action.getResult(state);
                Utils.spawn(state);
            }
            stats.show();
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

    private static class Tracker {

        private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
        int[] actions = new int[4];

        public void show(){
            DefaultPieDataset actionPie = new DefaultPieDataset();
            actionPie.setValue("Swipe Down", actions[DOWN]);
            actionPie.setValue("Swipe Right", actions[RIGHT]);
            actionPie.setValue("Swipe Up", actions[UP]);
            actionPie.setValue("Swipe Left", actions[LEFT]);
            JFreeChart actionChart = ChartFactory.createPieChart("Action Distribution", actionPie, true, false, false);
            try {
                ChartUtilities.saveChartAsPNG(new File("test.png"), actionChart, 400, 400);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void track(Action action){
            if(action instanceof UpSwipe)
                actions[UP]++;
            if(action instanceof DownSwipe)
                actions[DOWN]++;
            if(action instanceof LeftSwipe)
                actions[LEFT]++;
            if(action instanceof RightSwipe)
                actions[RIGHT]++;
        }

    }
}
