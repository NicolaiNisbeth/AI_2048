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
        Action action;
        do {
            action = readAction();
        } while(!state.getActions().contains(action));
        return action;
    }

    private Action readAction() {
        Action action = null;
        System.out.println("Enter direction");
        String line = scanner.nextLine();
        char move = line.toUpperCase().charAt(0);
        switch(move){
            case 'U': action = new UpSwipe();
                break;
            case 'D': action = new DownSwipe();
                break;
            case 'L': action = new LeftSwipe();
                break;
            case 'R': action = new RightSwipe();
                break;
            default:
                break;
        }
        return action;
    }
}
