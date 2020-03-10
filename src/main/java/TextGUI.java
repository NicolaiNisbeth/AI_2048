import java.util.Scanner;

public class TextGUI implements GUI {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void show(State state) {
        int[][] board = state.getBoard();
        for(int[] row : board){
            StringBuilder stringBuilder = new StringBuilder();
            for(int value : row)
                 stringBuilder.append(value).append(" ");
            System.out.println(stringBuilder.toString());
        }
    }

    @Override
    public Action getAction(State state) {
        return null;
    }
}
