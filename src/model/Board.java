package model;

import java.util.ArrayList;

/**
* Classe représentant le tableau
*/
public class Board {
    /**
    * Largeur du tableau
    */
    private int width;
    /**
    * longueur du tableau
    */
    private int height;
    /**
    * Liste représentant le tableau, contenant les pions
    */
    private ArrayList<ArrayList<Pawn>> board = new ArrayList<ArrayList<Pawn>>();

    /**
    * Crée un nouveau tableau avec la longueur et la largeur données
    */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
    * Crée un nouveau tableau sans longueur et largeur donnés
    */
    public Board() {
    }

    /**
    * Initialise le tableau en créant une liste de pions a une position specifique en hauteur et largeur
    */
    public void initBoard() {
        board.clear();
        for (int i = 0; i < width; i++) {
            board.add(new ArrayList<Pawn>());
            for (int j = 0; j < height; j++) {
                board.get(i).add(new Pawn());
            }
        }
    }

    /**
    * Récupere la hauteur d'une colonne
    */
    public int getColumnHeight(int col) {
        int height = 0;
        for (int i = 0; i < this.height; i++) {
            if (board.get(col).get(i).getPlayer() != null) {
                height++;
            }
        }
        return height;
    }

    /**
    * vérifie si le tableau est plein
    */
    public boolean isFull() {
        for (int i = 0; i < width; i++) {
            if (getColumnHeight(i) < height) {
                return false;
            }
        }
        return true;
    }

    /**
    * récupere la largeur du tableau
    */
    public int getWidth() {
        return width;
    }

    /**
    * récupère la hauteur du tableau
    */
    public int getHeight() {
        return height;
    }

    /**
    * représente une chaine de charctere sur la case donnée en X/Y
    */
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

    /**
    * Récupère une représentation de la ligne aux coordonées Y données
    */
    public String getRowToString(int y) {
        StringBuilder result = new StringBuilder();
        for (int col = 0; col < width; col++) {
            y /= 2;
            result.append(getCellToString(col, y));
        }
        result.append("|");
        return result.toString();
    }

    /**
    * Affiche une représentation du tableau
    */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("  1   2   3   4   5   6   7\n");
        for (int i = 0; i < height * 2 - 1; i++) {
            if (i % 2 == 0) {
                String row = getRowToString(i);
                result.append(row);
            } else {
                result.append("|---+---+---+---+---+---+---|");
            }
            result.append("\n");
        }

        result.append("\\===========================/");

        return result.toString();
    }
}
