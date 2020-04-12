import controller.AI;
import controller.jd.ExpectiMax;
import model.heuristic.HighestNumber;
import model.heuristic.ScoreHeuristic;
import model.State;
import model.action.Action;
import model.heuristic.nn.Cocktail;

import util.Utils;
import view.Grafic_UI;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        double[] freqs = new double[8];
        int temp = 128;
        Grafic_UI GUI = new Grafic_UI();
        double maxScore = 0;
        double minScore = Integer.MAX_VALUE;
        int iterations = 100;
        double sum = 0;
        for (int i = 1; i <= iterations; i++) {
            int[][] board = setupBoard();
            State state = new State(board);
            AI ai = new ExpectiMax(2);
            ai.setHeuristics(outcome ->
                    new Cocktail().getValue(outcome)
            );
            double value = 0;
            while (!state.getActions().isEmpty()) {

                GUI.show(state);
                Action action = ai.getAction(state);

                value = new ScoreHeuristic().getValue(state);
                state = action.getResult(state);
                Utils.spawn(state);
            }
            if (new HighestNumber().getValue(state) >= 2048) {
                GUI.win();
                Grafic_UI.playSound("/win.wav");
            } else {
                GUI.lose();
                Grafic_UI.playSound("/loss.wav");
            }
            sum += value;
            minScore = Math.min(minScore, value);
            maxScore = Math.max(maxScore, value);
            System.out.println(String.format("%d\t=\t%f", i, value));
            bookkeeping(freqs, value);
        }
        System.out.println(String.format("n = %d", iterations));
        for (double d : freqs) {
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
        board[startLocation/4][startLocation%4] = 2;
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
}
