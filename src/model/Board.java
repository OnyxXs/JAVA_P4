package model;

import java.util.ArrayList;

/**
 * Cette classe représente un plateau de jeu. Elle contient une liste de pions
 * sur le plateau.
 */
public class Board {
    private int width;
    private int height;
    private ArrayList<ArrayList<Pawn>> board = new ArrayList<ArrayList<Pawn>>();

    /**
     * Constructeur de la classe Board.
     * 
     * @param width  La largeur du plateau.
     * @param height La hauteur du plateau.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initialise le plateau de jeu en en le remplissant de pions n'appartenant à
     * aucun joueur.
     */
    public void initBoard() {
        board.clear();
        for (int row = 0; row < height; row++) {
            board.add(new ArrayList<Pawn>());
            for (int col = 0; col < width; col++) {
                board.get(row).add(new Pawn());
            }
        }
    }

    /**
     * Retourne la position verticale de la case vide la plus basse d'une colonne.
     * 
     * @param col La colonne à tester.
     * @return La position verticale de la case vide la plus basse.
     */
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

    /**
     * Vérifie si le plateau est rempli.
     * 
     * @return true si le plateau est rempli, false sinon.
     */
    public boolean isFull() {
        return getFullColumns().size() >= width;
    }

    /**
     * Vérifie si une colonne est pleine.
     * 
     * @param col La colonne à tester.
     * @return true si la colonne est pleine, false sinon.
     */
    public boolean isColumnFull(int col) {
        return board.get(0).get(col).getPlayer() != null;
    }

    /**
     * Retourne la liste des colonnes pleines.
     * 
     * @return La liste des colonnes pleines.
     */
    public ArrayList<Integer> getFullColumns() {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (int col = 0; col < width; col++) {
            if (isColumnFull(col)) {
                result.add(col);
            }
        }
        return result;
    }

    /**
     * Retourne le nombre de colonnes non pleines.
     * 
     * @return Le nombre de colonnes non pleines.
     */
    public int getFreeColumnNumber() {
        return width - getFullColumns().size();
    }

    /**
     * Retourne la largeur du plateau.
     * 
     * @return La largeur du plateau.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur du plateau.
     * 
     * @return La hauteur du plateau.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la représentation textuelle d'une case.
     * 
     * @param col La colonne de la case.
     * @param row La ligne de la case.
     * @return La représentation textuelle de la case.
     */
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

    /**
     * Retourne la représentation textuelle d'une ligne.
     * 
     * @param row La ligne à représenter.
     * @return La représentation textuelle de la ligne.
     */
    public String getRowToString(int row) {
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < width; col++) {
            result.append(getCellToString(col, row));
        }
        result.append("|");
        return result.toString();
    }

    /**
     * Retourne le pion qui compose une case.
     * 
     * @param col La colonne de la case.
     * @param row La ligne de la case.
     * @return La représentation textuelle du plateau.
     */
    public Pawn getCell(int col, int row) {
        return board.get(row).get(col);
    }

    /**
     * Modifie le joueur lié à un pion d'une case.
     * 
     * @param col    La colonne de la case.
     * @param row    La ligne de la case.
     * @param player Le joueur à lier au pion.
     */
    public void setCell(int col, int row, Player player) {
        board.get(row).get(col).setPlayer(player);
    }

    /**
     * Retourne la représentation textuelle du plateau.
     * 
     * @return La représentation textuelle du plateau.
     */
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
