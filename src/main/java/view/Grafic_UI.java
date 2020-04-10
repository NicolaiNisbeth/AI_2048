package view;

import model.State;
import model.action.Action;
import view.GUIs.Board;
import view.GUIs.Tile;
import view.GUIs.Value;

import javax.swing.*;
import java.awt.*;

public class Grafic_UI extends JFrame implements GUI  {
    Grafic_UI game;
    Board gameBoard;
    private static final long serialVersionUID = 1L;

    public static JLabel statusBar;

    private static final String TITLE = "2048 in Java";

    public static final String WIN_MSG = "Ai won... AI iz happy";

    public static final String LOSE_MSG = "This makes me a saaaaad AI";

    State startstate = new State(new int[4][4]);
    public Grafic_UI(){
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 400);
        setResizable(false);

        statusBar = new JLabel("");
        add(statusBar, BorderLayout.SOUTH);

        game = this;
        gameBoard = new Board(game);

        //Board.GOAL = Value.of(Integer.parseInt(args[0]));

        //KeySetting kb = KeySetting.getkeySetting(board);
        //board.addKeyListener(kb);
        game.add(gameBoard);

        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

    @Override
    public void show(State state) {
        gameBoard.tiles = state_to_board(state);
        gameBoard.repaint();
    }

    @Override
    public Action getAction(State state) {
        return null;
    }

    public void win(){
        statusBar.setText(WIN_MSG);
    }

    public void lose(){
        statusBar.setText(LOSE_MSG);
    }

    public Tile[] state_to_board(State state){
        Tile[] tiles = new Tile[4*4];
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {
                if(state.getBoard()[i][j] == -1){
                    tiles[j+i*4] = new Tile(Value._0);
                }
                else{
                    tiles[j+i*4] = new Tile(Value.of(state.getBoard()[i][j]));
                }
                if(tiles[j+i*4] == null){
                    System.out.println("nooooo");
                }
            }
        }
        return tiles;
    }
}
