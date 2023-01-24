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
        int[] fullColumns = getFullColumns();
        return fullColumns.length == width;
    }

    public boolean isColumnFull(int col) {
        if (getLowestEmptyCell(col) < 0) {
            return true;
        }
        return false;
    }

    public int[] getFullColumns() {
        int[] fullColumns = new int[width];
        int index = 0;
        for (int i = 0; i < width; i++) {
            if (isColumnFull(i)) {
                fullColumns[index] = i;
                index++;
            }
        }
        return fullColumns;
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
