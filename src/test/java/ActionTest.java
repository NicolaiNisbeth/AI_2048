import model.State;
import model.action.Action;
import util.Pair;
import view.TextGUI;

import static org.junit.Assert.assertEquals;

public abstract class ActionTest {

    private State initialState;
    private Action action;
    private TextGUI textGUI;
    protected Pair<int[], int[]> initialSums, updatedSums;

    protected abstract Action createAction();

    @org.junit.Before
    public void setUp() {
        action = createAction();
        initialState = new State(Main.setupBoard());
        textGUI = new TextGUI();
    }

    @org.junit.After
    public void tearDown() {
        action = null;
        initialState = null;
        textGUI = null;
        initialSums = null;
        updatedSums = null;
    }

    @org.junit.Test
    public void validateAction() {
        System.out.println("Initial state");
        textGUI.show(initialState);

        initialSums = getRowColSums(initialState.getBoard());
        State updatedState = action.getResult(initialState);
        updatedSums = getRowColSums(updatedState.getBoard());

        System.out.println(action.getClass());
        textGUI.show(updatedState);
    }

    public static Pair<int[], int[]> getRowColSums(int[][] board){
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


}