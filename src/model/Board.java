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
        for (int row = 0; row < height; row++) {
            board.add(new ArrayList<Pawn>());
            for (int col = 0; col < width; col++) {
                board.get(row).add(new Pawn());
            }
        }
    }

    public int getLowestEmptyCell(int col) {
        int row = height - 1;

        while (board.get(row).get(col).getPlayer() != null) {
            row--;
            if (row < 0) {
                return -1;
            }
        }
        return row;
    }

    public boolean isFull() {
        for (int col = 0; col < width; col++) {
            if (!isColumnFull(col)) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(int col) {
        return board.get(0).get(col).getPlayer() != null;
    }

    public int[] getFullColumns() {
        int[] result = new int[width];
        int count = 0;
        for (int col = 0; col < width; col++) {
            if (isColumnFull(col)) {
                result[count] = col;
                count++;
            }
        }
        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getCellToString(int col, int row) {
        StringBuilder result = new StringBuilder();
        result.append("| ");
        if (board.get(row).get(col).getPlayer() == null) {
            result.append(" ");
        } else {
            result.append(board.get(row).get(col).getPlayer().getColor());
            result.append(board.get(row).get(col).getPlayer().getSymbol());
            result.append(Style.RESET);
        }
        result.append(" ");
        return result.toString();
    }

    public String getRowToString(int row) {
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < width; col++) {
            result.append(getCellToString(col, row));
        }
        result.append("|");
        return result.toString();
    }

    public Pawn getCell(int col, int row) {
        return board.get(row).get(col);
    }

    public void setCell(int col, int row, Player player) {
        board.get(row).get(col).setPlayer(player);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("  1   2   3   4   5   6   7\n");
        for (int row = 0; row < height * 2 - 1; row++) {
            if (row % 2 == 0) {
                int rowNumber = row / 2;
                String rowString = getRowToString(rowNumber);
                result.append(rowString);
            } else {
                result.append("|---+---+---+---+---+---+---|");
            }
            result.append("\n");
        }

        result.append("\\===========================/");

        return result.toString();
    }
}
