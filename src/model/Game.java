package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente une partie de jeu. Elle contient un plateau de jeu.
 * Le nombre de tour écoulé.
 * 2 joueurs, dont le joueur qui joue ce tour-ci, le jouer opposé, et le joueur
 * qui a commencé la partie.
 * Une liste de couleur que les joueurs peuvent choisir pour leurs pions.
 */
public class Game {
    private static final String WIN_COLOR = Style.PURPLE;
    private static final ArrayList<Pawn> WINNING_PAWNS = new ArrayList<Pawn>();
    // Liste des pions 4 pions alignés gagnants

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;
    private Player winner;
    private Player firstPlayer;
    private Board board;
    private int turn;
    private boolean isPlaying = false;
    private boolean wasQuit = false;

    public static Map<String, String> colorList = new HashMap<>() {
        {
            put("Rouge", Style.RED);
            put("Bleu", Style.BLUE);
            put("Jaune", Style.YELLOW);
            put("Vert", Style.GREEN);
        }
    };

    public static ArrayList<Map.Entry<String, String>> colorListIndex = new ArrayList<>(colorList.entrySet());

    /**
     * Constructeur de la classe Game.
     * 
     * @param player1 Le premier joueur.
     * @param player2 Le deuxième joueur.
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.opponent = player2;

        initGame();
    }

    /**
     * Initialise la partie.
     */
    public void initGame() {
        board = new Board(7, 6);
        board.initBoard();
        isPlaying = true;
        turn = 1;
        WINNING_PAWNS.clear();

        colorList = new HashMap<>() {
            {
                put("Rouge", Style.RED);
                put("Bleu", Style.BLUE);
                put("Jaune", Style.YELLOW);
                put("Vert", Style.GREEN);
            }
        };

        colorListIndex = new ArrayList<>(colorList.entrySet());
    }

    /**
     * Affiche une représentation textuelle de la partie
     *
     */
    public void display() {
        String gameDisplay = this.toString();
        System.out.println(gameDisplay);
    }

    /**
     * Retourne le joueur numéro 1.
     * 
     * @return Le joueur numéro 1.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Retourne le joueur numéro 2.
     * 
     * @return Le joueur numéro 2.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Retourne le joueur qui joue ce tour-ci.
     * 
     * @return Le joueur qui joue ce tour-ci.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retourne le joueur qui ne joue pas ce tour-ci.
     * 
     * @return Le joueur qui ne joue pas ce tour-ci.
     */
    public Player getOpponent() {
        return opponent;
    }

    /**
     * Retourne le joueur qui a gagné la partie.
     * 
     * @return Le joueur qui a gagné la partie.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Défini le joueur qui joue ce tour-ci.
     * 
     * @param currentPlayer Le joueur qui joue ce tour-ci.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Défini le joueur qui a gagné la partie.
     * 
     * @param winner Le joueur qui a gagné la partie.
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Défini le joueur qui a commencé la partie.
     * 
     * @param firstPlayer Le joueur qui a commencé la partie.
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * Vérifie si la partie a été quittée prématurément.
     * 
     * @param status Le statut de la partie.
     */
    public void setQuitStatus(boolean status) {
        wasQuit = status;
    }

    /**
     * Retourne le statut de la partie.
     * 
     * @return Le statut de la partie.
     */
    public boolean wasQuit() {
        return wasQuit;
    }

    /**
     * Retourne le nombre de tour écoulé.
     * 
     * @return Le nombre de tour écoulé.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Retourne le plateau de jeu.
     * 
     * @return Le plateau de jeu.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retourne le statut de la partie.
     * 
     * @return Le statut de la partie.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Affect un plateau de jeu.
     * 
     * @param board Le plateau de jeu.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Défini le statut de la partie.
     * 
     * @param status Le statut de la partie.
     */
    public void setPlayingStatus(boolean status) {
        isPlaying = status;
    }

    /**
     * Inverse le joueur actuel et son adversaire.
     */
    public void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            opponent = player1;
        } else {
            currentPlayer = player1;
            opponent = player2;
        }

        // Augmente le nombre de tours de 1 si le joueur actuel est celui qui a commencé
        if (currentPlayer == firstPlayer) {
            turn++;
        }
    }

    /**
     * Fait jouer le joueur actuel dans la colonne spécifiée.
     * 
     * @param col La colonne dans laquelle le joueur actuel joue.
     */
    public void play(int col) {
        int row = board.getLowestEmptyCell(col);
        placePawn(col, row);

        if (checkVictory(col, row, currentPlayer)) {
            winner = currentPlayer;

            Player winPlayer = new Player();
            winPlayer.setColor(WIN_COLOR);
            winPlayer.setSymbol(currentPlayer.getSymbol());

            for (Pawn pawn : WINNING_PAWNS) {
                pawn.setPlayer(winPlayer);
            }
            System.out.println(board.toString());

            isPlaying = false;
            return;
        } else if (board.isFull()) {
            isPlaying = false;
            return;
        }

        switchPlayer();
    }

    /**
     * Place un pion dans la colonne spécifiée, en le descendant jusqu'à la case
     * vide la plus basse.
     * 
     * @param col             La colonne dans laquelle le pion est placé.
     * @param LowestEmptyCell La ligne la plus basse de la colonne dans laquelle le
     *                        pion est
     *                        placé.
     */
    public void placePawn(int col, int LowestEmptyCell) {

        for (int row = 0; row <= LowestEmptyCell; row++) {
            // Supprime la position du pion précédent et la replace sur la ligne en dessous
            if (row != 0) {
                board.setCell(col, row - 1, null);
            }
            board.setCell(col, row, currentPlayer);

            // Réaffiche le plateau
            System.out.println(board.toString());

            // Attends 200 ms avant de passer à l'itération suivante
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Récupère la liste de toutes les positions gainantes pour le joueur spécifié.
     * 
     * @param player Le joueur pour lequel on récupère les positions gagnantes.
     * @return La liste de toutes les positions gagnantes pour le joueur spécifié.
     */
    public ArrayList<int[]> getPlayerWinningPositions(Player player) {
        ArrayList<int[]> winningPositions = new ArrayList<int[]>();

        // Parcours toutes les cases du plateau
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {

                // Si la case est vide
                if (board.getCell(col, row).getPlayer() == null) {
                    // Place un pion temporairement pour vérifier si le coup est gagnant
                    board.setCell(col, row, player);

                    // Si le coup est gagnant, on ajoute les coordonnées du coup gagnant à la liste
                    if (checkVictory(col, row, player)) {
                        int[] position = new int[] { col, row };
                        winningPositions.add(position);
                    }

                    // On remet revide la case
                    board.setCell(col, row, null);
                }
            }
        }

        return winningPositions;
    }

    /**
     * Récupère la liste des colonnes gagnantes pour le joueur spécifié.
     * 
     * @param player Le joueur pour lequel on récupère les colonnes gagnantes.
     * @return La liste des colonnes gagnantes pour le joueur spécifié.
     */
    public ArrayList<Integer> getPlayerWinningColumns(Player player) {
        ArrayList<Integer> winCols = new ArrayList<Integer>();
        ArrayList<int[]> winPoses = getPlayerWinningPositions(player);

        for (int[] pos : winPoses) {
            int winX = pos[0];
            int winY = pos[1];

            if (!winCols.contains(winX)) {
                int LowestEmptyCell = board.getLowestEmptyCell(winX);
                if (winY == LowestEmptyCell) {
                    winCols.add(winX);
                }
            }
        }

        for (int col : winCols) {
            System.out.println("Colonne gagnante : " + col);
        }

        return winCols;
    }

    /**
     * Vérifie si placer un pion à la position spécifiée est gagnant pour le joueur
     * spécifié
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkVictory(int col, int row, Player player) {
        WINNING_PAWNS.clear();

        boolean isHorizontalWin = checkHorizontalWin(col, row, player);
        boolean isVerticalWin = checkVerticalWin(col, row, player);
        boolean isTopLeftToBotRightWin = checkTopLeftToBottomRightWin(col, row, player);
        boolean isBotLeftToTopRightWin = checkBottomLeftToTopRightWin(col, row, player);

        return isHorizontalWin || isVerticalWin || isTopLeftToBotRightWin || isBotLeftToTopRightWin;
    }

    /**
     * Vérifie si le coup est gagnant horizontalement.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkHorizontalWin(int col, int row, Player player) {
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkCol = colStart; checkCol <= colEnd; checkCol++) {
            if (getFourLinedPawns(checkCol, row, player, winPawns)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Vérifie si le coup est gagnant verticalement.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkVerticalWin(int col, int row, Player player) {
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkRow = rowStart; checkRow <= rowEnd; checkRow++) {
            if (getFourLinedPawns(col, checkRow, player, winPawns)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si le coup est gagnant diagonalement de haut gauche à bas droite.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkTopLeftToBottomRightWin(int col, int row, Player player) {
        int colStart = col - 3;
        int colEnd = col + 4;
        int rowStart = row - 3;
        int rowEnd = row + 4;

        /* -------------------------------------------------------------------------- */
        // Correction des coordonnées pour ne pas sortir du plateau
        if (colStart < 0) {
            colStart = 0;
            rowStart = row - (col - colStart);
        }

        if (colEnd > board.getWidth() - 1) {
            colEnd = board.getWidth();
            rowEnd = rowStart + (colEnd - colStart) + 1;
        }

        if (rowStart < 0) {
            rowStart = 0;
            colStart = col - (row - rowStart);
        }

        if (rowEnd > board.getHeight() - 1) {
            rowEnd = board.getHeight();
            colEnd = colStart + (rowEnd - rowStart) + 1;
        }
        /* -------------------------------------------------------------------------- */

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkCol = colStart, checkRow = rowStart; checkCol < colEnd
                && checkRow < rowEnd; checkCol++, checkRow++) {
            if (getFourLinedPawns(checkCol, checkRow, player, winPawns)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si le coup est gagnant diagonalement de bas gauche à haut droite.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkBottomLeftToTopRightWin(int col, int row, Player player) {
        int colStart = col - 3;
        int colEnd = col + 4;
        int rowStart = row + 3;
        int rowEnd = row - 4;

        /* -------------------------------------------------------------------------- */
        // Corrige les coordonnées si elles sont hors du plateau
        if (colStart < 0) {
            colStart = 0;
            rowStart = row + (col - colStart);
        }

        if (colEnd > board.getWidth() - 1) {
            colEnd = board.getWidth() + 1;
            rowEnd = rowStart - (colEnd - colStart) + 1;
        }

        if (rowStart > board.getHeight() - 1) {
            rowStart = board.getHeight() - 1;
            colStart = col - (rowStart - row);
        }

        if (rowEnd < 0) {
            rowEnd = -1;
            colEnd = colStart + (row - rowEnd) + 1;
        }
        /* -------------------------------------------------------------------------- */

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkCol = colStart, checkRow = rowStart; checkCol < colEnd
                && checkRow > rowEnd; checkCol++, checkRow--) {
            if (getFourLinedPawns(checkCol, checkRow, player, winPawns)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un pion est aligné avec 3 pions suivants une direction
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean getFourLinedPawns(int col, int row, Player player, ArrayList<Pawn> winPawns) {
        Pawn pawn = null;
        if (col >= 0 && col < board.getWidth() && row >= 0 && row < board.getHeight()) {
            pawn = board.getCell(col, row);
        }

        // Si le pion appartient au joueur sélectionné
        if (pawn != null && pawn.getPlayer() == player) {
            // Ajoute le pion à la liste des pions alignés
            winPawns.add(pawn);

            // Si la liste des pions alignés contient 4 pions
            if (winPawns.size() >= 4) {
                if (WINNING_PAWNS.size() < 4)
                    // Ajoute les pions alignés à la liste des pions gagnants
                    WINNING_PAWNS.addAll(winPawns);

                return true;
            }

        } else {
            // Sinon réinitialise la liste des pions alignés
            winPawns.clear();
        }
        return false;
    }

    /**
     * Réinitialise la partie.
     */
    public void reset() {
        if (firstPlayer == player1) {
            firstPlayer = player2;
            currentPlayer = player2;
            opponent = player1;
        } else {
            firstPlayer = player1;
            currentPlayer = player1;
            opponent = player2;
        }

        initGame();
    }

    /**
     * Retourne une chaîne de caractères représentant l'état de la partie.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(board + "\n");
        result.append("Tour n°" + turn + "\n");
        result.append("Tour de " + currentPlayer.getColor() + currentPlayer.getName() + Style.RESET + "\n");

        return result.toString();
    }
}
