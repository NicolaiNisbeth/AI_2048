import model.State;
import action.*;
import util.Pair;
import view.TextUI;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;

public abstract class ActionTest {

    private State initialState;
    private Action action;
    private TextUI textUI;
    protected Pair<int[], int[]> initialSums, updatedSums;

    protected abstract Action createAction();

    @org.junit.Before
    public void setUp() {
        action = createAction();
        initialState = new State(Main.setupBoard());
        textUI = new TextUI();
    }

    @org.junit.After
    public void tearDown() {
        action = null;
        initialState = null;
        textUI = null;
        initialSums = null;
        updatedSums = null;
    }

    @org.junit.Test
    public void validateAction() {
        // random data
        System.out.println("\n"+action.getClass());
        System.out.println("## RANDOM DATA ##");
        System.out.println("Initial state");
        textUI.show(initialState);

        initialSums = getRowColSums(initialState.getBoard());
        State updatedState = action.getResult(initialState);
        updatedSums = getRowColSums(updatedState.getBoard());

        System.out.println("Our state");
        textUI.show(updatedState);

        // hardcoded data
        System.out.println("\n## HARDCODED DATA ##");
        ArrayList<Pair<int[][], int[][]>> testdata = getData(action);
        for (Pair<int[][], int[][]> test : testdata){
            initialState = new State(test.getFirst());
            System.out.println("Initial state");
            textUI.show(initialState);

            updatedState = action.getResult(initialState);
            System.out.println("Our state");
            textUI.show(updatedState);

            System.out.println("Correct state");
            textUI.show(new State(test.getSecond()));

            assertTrue(Arrays.deepEquals(updatedState.getBoard(), test.getSecond()));
        }
    }

    private static Pair<int[], int[]> getRowColSums(int[][] board){
        int[] row_sums = new int[board.length];
        int[] col_sums = new int[board[0].length];

        for (int i = 0; i < board.length; i++) {
            int row_sum = 0, col_sum = 0;
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] != -1)
                    row_sum += board[i][j];
                if(board[j][i] != -1)
                    col_sum += board[j][i];
            }
            row_sums[i] = row_sum;
            col_sums[i] = col_sum;
        }
        return new Pair<>(row_sums, col_sums);
    }

    private ArrayList<Pair<int[][], int[][]>> getData(Object action) {
        ArrayList<Pair<int[][], int[][]>> data = new ArrayList<>();
        Pair<int[][], int[][]> e=null, e2=null;

        int[][] t = {{-1,-1,-1,2},{-1,-1,4,-1},{-1,8,-1,-1},{16,-1,-1,-1}};
        int[][] t2 = {{2,2,2,2},{-1,-1,-1,-1},{-1,-1,-1,-1},{4,4,2,2}};

        if (action instanceof UpSwipe){
            e = new Pair<>(t, new int[][]{{16,8,4,2},{-1,-1,-1,-1},{-1,-1,-1,-1},{-1,-1,-1,-1}});
            e2 = new Pair<>(t2, new int[][]{{2,2,4,4},{4,4,-1,-1},{-1,-1,-1,-1},{-1,-1,-1,-1}});
        }
        else if (action instanceof RightSwipe){
            e = new Pair<>(t, new int[][]{{-1,-1,-1,2},{-1,-1,-1,4},{-1,-1,-1,8},{-1,-1,-1,16}});
            e2 = new Pair<>(t2, new int[][]{{-1,-1,4,4},{-1,-1,-1,-1},{-1,-1,-1,-1},{-1,-1,8,4}});
        }
        else if (action instanceof DownSwipe){
            e = new Pair<>(t, new int[][]{{-1,-1,-1,-1},{-1,-1,-1,-1},{-1,-1,-1,-1},{16,8,4,2}});
            e2 = new Pair<>(t2, new int[][]{{-1,-1,-1,-1},{-1,-1,-1,-1},{2,2,-1,-1},{4,4,4,4}});
        }
        else if (action instanceof LeftSwipe){
            e = new Pair<>(t, new int[][]{{2,-1,-1,-1},{4,-1,-1,-1},{8,-1,-1,-1},{16,-1,-1,-1}});
            e2 = new Pair<>(t2, new int[][]{{4,4,-1,-1},{-1,-1,-1,-1},{-1,-1,-1,-1},{8,4,-1,-1}});
        }

        data.add(e);
        data.add(e2);

        return data;
    }


}