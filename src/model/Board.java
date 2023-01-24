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

    public void displayBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (board.get(i).get(j).getPlayer() == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board.get(i).get(j).getPlayer().getSymbol());
                }
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
