package view.GUIs;

import view.Grafic_UI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board extends JPanel {
    private static final long serialVersionUID = -1790261785521495991L;
    /* Board row and column */
    public static final int ROW = 4;
    /* this array use for convenience iterate */
    public static final int[] _0123 = { 0, 1, 2, 3 };

    final Grafic_UI host;

    public Tile[] tiles;

    public static Value GOAL = Value._2048;

    public Board(Grafic_UI f) {
        host = f;
        setFocusable(true);
        initTiles();
    }

    /**
     * initialize the game, also use to start a new game
     */
    public void initTiles() {
        tiles = new Tile[ROW * ROW];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.ZERO;
        }
        host.statusBar.setText("");

    }

    /**
     * move all the tiles to the left side.
     */
    public void left() {
        boolean needAddTile = false;
        for (int i : _0123) {
            // get i-th line
            Tile[] origin = getLine(i);
            // get the line have been moved to left
            Tile[] afterMove = moveLine(origin);
            // get the the line after
            Tile[] merged = mergeLine(afterMove);
            // set i-th line with the merged line
            setLine(i, merged);
            if (!needAddTile && !Arrays.equals(origin, merged)) {
                // if origin and merged line is different
                // need to add a new Tile in the board
                needAddTile = true;
            }
        }

        // if needAddTile is false, those line didn't change
        // then no need to add tile
        if (needAddTile) {
            addTile();
        }
    }

    private Tile tileAt(int x, int y) {
        return tiles[x + y * ROW];
    }

    /**
     * Generate a new Tile in the availableSpace.
     */
    private void addTile() {
        List<Integer> list = availableIndex();
        int idx = list.get((int) (Math.random() * list.size()));
        tiles[idx] = Tile.randomTile();
    }

    /**
     * Query the tiles Array field, and get the list of empty tile's index. aka
     * find the index is ok to add a new Tile.
     */
    private List<Integer> availableIndex() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].empty())
                list.add(i);
        }
        return list;
    }

    /**
     * return true if the board doesn't have empty tile
     */
    private boolean isFull() {
        return availableIndex().size() == 0;
    }

    /**
     * return true if didn't lose
     */
    boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int x : _0123) {
            for (int y : _0123) {
                Tile t = tileAt(x, y);
                if ((x < ROW - 1 && t.equals(tileAt(x + 1, y)))
                        || (y < ROW - 1 && t.equals(tileAt(x, y + 1)))) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * move the not empty tile line to left
     */
    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<>();
        for (int i : _0123) {
            if (!oldLine[i].empty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            // list empty, oldLine is empty line.
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i : _0123) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    /**
     * Merge the oldLine of Tiles, then return a newLine
     */
    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < ROW; i++) {
            if (i < ROW - 1 && !oldLine[i].empty()
                    && oldLine[i].equals(oldLine[i + 1])) {
                // can be merge, double the val
                Tile merged = oldLine[i].getDouble();
                i++; // skip next one!
                list.add(merged);
                if (merged.value() == GOAL) {
                    // reach goal, show message
                    host.win();
                }
            } else {
                list.add(oldLine[i]);
            }
        }
        ensureSize(list, 4);
        return list.toArray(new Tile[4]);
    }

    /**
     * Append the empty tile to the l list of tiles, ensure it's size is s.
     */
    private static void ensureSize(List<Tile> l, int s) {
        while (l.size() < s) {
            l.add(Tile.ZERO);
        }
    }

    /**
     * get the idx-th line.
     */
    private Tile[] getLine(int idx) {
        Tile[] result = new Tile[4];
        for (int i : _0123) {
            result[i] = tileAt(i, idx);
        }
        return result;
    }

    /**
     * set the idx-th line. replace by the re array.
     */
    private void setLine(int idx, Tile[] re) {
        for (int i : _0123) {
            tiles[i + idx * ROW] = re[i];
        }
    }

    /* Background color */
    private static final Color BG_COLOR = new Color(0xbbada0);

    /* Font */
    private static final Font STR_FONT = new Font(Font.SANS_SERIF,
                                                    Font.BOLD, 17);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.setFont(STR_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int y : _0123) {
            for (int x : _0123) {
                drawTile(g, tiles[x + y * ROW], x, y);
            }
        }
    }

    /* Side of the tile square */
    private static final int SIDE = 64;

    /* Margin between tiles */
    private static final int MARGIN = 16;

    /**
     * Draw a tile use specific number and color in (x, y) coords, x and y need
     * offset a bit.
     */
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        if(tile == null){
            System.out.println(x+"   "+y);
        }
        Value val = tile.value();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(val.color());
        g.fillRect(xOffset, yOffset, SIDE, SIDE);
        g.setColor(val.fontColor());
        if (val.score() != 0)
            g.drawString(tile.toString(), xOffset
                    + (SIDE >> 1) - MARGIN, yOffset + (SIDE >> 1));
    }

    private static int offsetCoors(int arg) {
        return arg * (MARGIN + SIDE) + MARGIN;
    }

}
