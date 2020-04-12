package view;
/* most code is taken from https://github.com/Alwayswithme/2048.java/blob/master/src/phx/Board.java
 * 10/4 2020
 * */
import model.State;
import model.action.Action;
import view.lib.Board;
import view.lib.Tile;
import view.lib.Value;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class GUI extends JFrame implements IGUI {
    GUI game;
    Board gameBoard;
    private static final long serialVersionUID = 1L;

    public static JLabel statusBar;

    private static final String TITLE = "2048 in Java";

    public static final String WIN_MSG = "Ai won... AI iz happy";

    public static final String LOSE_MSG = "This makes me a saaaaad AI";

    State startstate = new State(new int[4][4]);
    public GUI(){
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
                else {
                    tiles[j + i * 4] = new Tile(Value.of(state.getBoard()[i][j]));
                }
            }
        }


        return tiles;
    }
    public static void playSound(String file){
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(GUI.class.getResource(file));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
