import controller.AI;
import controller.MCTS;
import controller.MCTS2;
import controller.HelpAIonestep;
import controller.jd.ExpectiMax;
import controller.jd.MiniMaxi;
import model.action.DownSwipe;
import model.action.LeftSwipe;
import model.action.RightSwipe;
import model.action.UpSwipe;
import model.heuristic.HighestNumber;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.Cocktail;

import model.heuristic.nn.EmptySquares;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import util.Utils;
import view.GUI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[] frequencies = new double[8];
        int temp = 128;
        GUI gui = new GUI();
        double maxScore = Integer.MIN_VALUE;
        double minScore = Integer.MAX_VALUE;
        int iterations = 100;
        double sum = 0;
        for (int i = 1; i <= iterations; i++) {
            Tracker stats = new Tracker();
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new ExpectiMax(3);
            ai.setHeuristics(outcome ->
                    //new ScoreHeuristic().getValue(outcome)
                    new Cocktail().getValue(outcome)
            );
            double value = 0;
            while (!state.getActions().isEmpty()) {

                gui.show(state);
                Action action = ai.getAction(state);
                stats.track(action);

                value = new ScoreHeuristic().getValue(state);
                state = action.getResult(state);
                Utils.spawn(state);
            }
            stats.show();

            if (new HighestNumber().getValue(state) >= 2048) {
                //gui.win();
                //GUI.playSound("/win.wav");
            } else {
                //gui.lose();
                //GUI.playSound("/loss.wav");
            }
            sum += value;
            minScore = Math.min(minScore, value);
            maxScore = Math.max(maxScore, value);
            System.out.println(String.format("%d\t=\t%f", i, value));
            bookkeeping(frequencies, new HighestNumber().getValue(state));
        }
        System.out.println(String.format("n = %d", iterations));
        for (double d : frequencies) {
            System.out.println(String.format("%d:\t%3.2f%%", temp, (d / iterations) * 100));
            temp <<= 1;
        }
        System.out.println(String.format("max\t\t= %f\naverage\t= %f\nmin\t\t= %f", maxScore, sum / iterations, minScore));
    }

    public static int[][] setupBoard() {
        int[][] board = new int[4][4];
        for (int[] row : board)
            Arrays.fill(row, -1);
        int startLocation = (int) (Math.random() * 16);
        board[startLocation / 4][startLocation % 4] = 2;
        return board;
    }

    private static void bookkeeping(double[] stats, double value) {
        int v = (int) value;
        if (v >= 128) stats[0]++;
        if (v >= 256) stats[1]++;
        if (v >= 512) stats[2]++;
        if (v >= 1024) stats[3]++;
        if (v >= 2048) stats[4]++;
        if (v >= 4096) stats[5]++;
        if (v >= 8192) stats[6]++;
        if (v >= 16384) stats[7]++;
    }

    private static class Tracker {

        private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
        int[] actions = new int[4];

        public void show() {
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

        public void track(Action action) {
            if (action instanceof UpSwipe)
                actions[UP]++;
            if (action instanceof DownSwipe)
                actions[DOWN]++;
            if (action instanceof LeftSwipe)
                actions[LEFT]++;
            if (action instanceof RightSwipe)
                actions[RIGHT]++;
        }

    }
}
