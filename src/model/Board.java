package model;

import java.util.ArrayList;

public class Board {
    private int width;
    private int height;
    private ArrayList<ArrayList<Pawn>> board = new ArrayList<ArrayList<Pawn>>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Board() {

    }

    public void initBoard() {
        board.clear();
        for (int i = 0; i < width; i++) {
            board.add(new ArrayList<Pawn>());
            for (int j = 0; j < height; j++) {
                board.get(i).add(new Pawn());
            }
        }
    }

    public int getColumnHeight(int col) {
        int height = 0;
        for (int i = 0; i < this.height; i++) {
            if (board.get(col).get(i).getPlayer() != null) {
                height++;
            }
        }
        return height;
    }

    public boolean isFull() {
        for (int i = 0; i < width; i++) {
            if (getColumnHeight(i) < height) {
                return false;
            }
        }
        return true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getCellToString(int x, int y) {
        StringBuilder result = new StringBuilder();
        result.append("| ");
        if (board.get(x).get(y).getPlayer() == null) {
            result.append(" ");
        } else {
            result.append(board.get(x).get(y).getPlayer().getSymbol());
        }
        result.append(" ");
        return result.toString();
    }

    public String getRowToString(int y) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < width; i++) {
            result.append(getCellToString(i, y));
        }
        result.append("|");
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("  1   2   3   4   5   6   7\n");
        for (int i = 0; i < height; i++) {
            String row = getRowToString(i);
            result.append(row);
            result.append("\n");
        }

        result.append("\\===========================/");

        return result.toString();
    }
}
