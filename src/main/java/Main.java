import controller.AI;
import controller.ExpectiMax;
import action.DownSwipe;
import action.LeftSwipe;
import action.RightSwipe;
import action.UpSwipe;
import controller.HelpAIonestep;
import controller.MCTS;
import controller.MCTS2;
import controller.MiniMaxi;
import controller.Random;
import heuristic.HighestNumber;
import heuristic.ScoreHeuristic;
import model.State;
import action.Action;
import heuristic.Cocktail;
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
    private static final double[] frequencies = new double[8];
    private static double maxScore = Integer.MIN_VALUE;
    private static double minScore = Integer.MAX_VALUE;
    private static final int iterations = 100;
    private static double sum = 0;
    private static int temp = 128;

    public static void main(String[] args) throws InterruptedException {
        GUI gui = new GUI();

        for (int i = 1; i <= iterations; i++) {
            Tracker stats = new Tracker();
            State state = new State(setupBoard());
            AI ai = new ExpectiMax(3);
            //AI ai = new MCTS(100);
            //AI ai = new MCTS2(100, new HelpAIonestep());
            //AI ai = new MiniMaxi(2);
            //AI ai = new Random();
            ai.setHeuristics(outcome -> new Cocktail().getValue(outcome));

            double gameScore = 0;
            while (!state.getActions().isEmpty()) {
                gui.show(state);
                Action action = ai.getAction(state);
                stats.track(action);
                //Thread.sleep(1000);
                gameScore = new ScoreHeuristic().getValue(state);
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
            sum += gameScore;
            minScore = Math.min(minScore, gameScore);
            maxScore = Math.max(maxScore, gameScore);
            bookkeeping(new HighestNumber().getValue(state));
            System.out.println(String.format("%d\t=\t%f", i, gameScore));
        }
        printStatistics();
    }

    public static int[][] setupBoard() {
        int[][] board = new int[4][4];
        for (int[] row : board)
            Arrays.fill(row, -1);
        int startLocation = (int) (Math.random() * 16);
        board[startLocation / 4][startLocation % 4] = 2;
        return board;
    }

    private static void printStatistics() {
        System.out.println(String.format("n = %d", iterations));
        for (double d : frequencies) {
            System.out.println(String.format("%d:\t%3.2f%%", temp, (d / iterations) * 100));
            temp <<= 1;
        }
        System.out.println(String.format("max\t\t= %f\naverage\t= %f\nmin\t\t= %f", maxScore, sum / iterations, minScore));
    }

    private static void bookkeeping(double value) {
        int v = (int) value;
        if (v >= 128) frequencies[0]++;
        if (v >= 256) frequencies[1]++;
        if (v >= 512) frequencies[2]++;
        if (v >= 1024) frequencies[3]++;
        if (v >= 2048) frequencies[4]++;
        if (v >= 4096) frequencies[5]++;
        if (v >= 8192) frequencies[6]++;
        if (v >= 16384) frequencies[7]++;
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
