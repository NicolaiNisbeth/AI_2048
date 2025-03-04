package view.lib;
/* all code is taken from https://github.com/Alwayswithme/2048.java/blob/master/src/phx/Board.java
 * 10/4 2020
 * */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static java.awt.event.KeyEvent.*;

public class KeySetting extends KeyAdapter {

    private static final HashMap<Integer, Method> keyMapping = new HashMap<>();

    private static Integer[] KEYS = { VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_R };

    private static Integer[] VI_KEY = { VK_K, VK_J, VK_H, VK_L };

    private static String[] methodName = { "up", "down", "left", "right", "initTiles" };

    private static Board board;

    private static final KeySetting INSTANCE = new KeySetting();
    
    public static KeySetting getkeySetting(Board b) {
        board = b;
        return INSTANCE;
    }

    // Singleton
    private KeySetting() {
        initKey(KEYS);
        initKey(VI_KEY);
    }

    /**
     * initialize keycode in the kcs array.
     */
    void initKey(Integer[] kcs) {
        for (int i = 0; i < kcs.length; i++) {
            try {
                keyMapping.put(kcs[i], Board.class.getMethod(methodName[i]));
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Use reflect to invoke the mapping method.
     */
    @Override
    public void keyPressed(KeyEvent k) {
        super.keyPressed(k);
        Method action = keyMapping.get(k.getKeyCode());
        if (action == null) {
            System.gc();
            return;
        }
        try {
            action.invoke(board);
            board.repaint();
        } catch (InvocationTargetException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (!board.canMove()) {  // can not move, game over
            board.host.lose();
        }

    }

}
