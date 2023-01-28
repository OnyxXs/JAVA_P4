package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente une partie de jeu. Elle contient un plateau de jeu.
 * Le nombre de tours écoulés.
 * 2 joueurs, dont le joueur qui joue ce tour-ci, le jouer opposé et le joueur
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

        initGame();
    }

    /**
     * Initialise la partie.
     */
    public void initGame() {
        board = new Board(7, 6); // Affecte un nouveau plateau de jeu
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

            // Ré-affiche le plateau
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
     * Affiche une représentation textuelle de la partie
     *
     */
    public void display() {
        String gameDisplay = this.toString();
        System.out.println(gameDisplay);
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
        if (currentPlayer == player1) {
            opponent = player2;
        } else {
            opponent = player1;
        }
    }

    /**
     * Défini le joueur qui a commencé la partie.
     * 
     * @param firstPlayer Le joueur qui a commencé la partie.
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
        setCurrentPlayer(firstPlayer);
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
     * Retourne le nombre de tours écoulés.
     * 
     * @return Le nombre de tours écoulés.
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
            setCurrentPlayer(player2);
        } else {
            setCurrentPlayer(player1);
        }

        // Augmente le nombre de tours de 1 si le joueur actuel est celui qui a commencé
        if (currentPlayer == firstPlayer) {
            turn++;
        }
    }

    /**
     * Récupère la liste de toutes les positions gagnantes pour le joueur spécifié.
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

                    // On re-vide la case
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
     * Vérifie si le coup est gagnant horizontalement autour de la position
     * spécifiée.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkHorizontalWin(int col, int row, Player player) {
        int colStart = col - 3;
        int colEnd = col + 3;

        return getFourLinedPawns(colStart, colEnd, row, row, 1, 0, player, false);
    }

    /**
     * Vérifie si le coup est gagnant verticalement en dessous de la position
     * spécifiée.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkVerticalWin(int col, int row, Player player) {
        int rowStart = row;
        int rowEnd = row + 3;

        return getFourLinedPawns(col, col, rowStart, rowEnd, 0, 1, player, false);
    }

    /**
     * Vérifie si le coup est gagnant diagonalement de haut gauche à bas droite
     * autour de la position spécifiée.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkTopLeftToBottomRightWin(int col, int row, Player player) {
        int colStart = col - 3;
        int colEnd = col + 3;
        int rowStart = row - 3;
        int rowEnd = row + 3;

        return getFourLinedPawns(colStart, colEnd, rowStart, rowEnd, 1, 1, player, false);
    }

    /**
     * Vérifie si le coup est gagnant diagonalement de bas gauche à haut droite
     * autour de la position spécifiée.
     * 
     * @param col    La colonne dans laquelle le pion est placé.
     * @param row    La ligne dans laquelle le pion est placé.
     * @param player Le joueur pour lequel on vérifie si le coup est gagnant.
     * @return true si le coup est gagnant, false sinon.
     */
    public boolean checkBottomLeftToTopRightWin(int col, int row, Player player) {
        int colStart = col - 3;
        int colEnd = col + 3;
        int rowStart = row + 3;
        int rowEnd = row - 3;

        return getFourLinedPawns(colStart, colEnd, rowStart, rowEnd, 1, -1, player, true);
    }

    /**
     * Vérifie si 4 pions sont alignés autour d'une position spécifiée jusqu'à une
     * autre position spécifiée.
     *
     * @param colStart         La colonne de départ de la vérification.
     * @param colEnd           La colonne de fin de la vérification.
     * @param rowStart         La ligne de départ de la vérification.
     * @param rowEnd           La ligne de fin de la vérification.
     * @param colAdd           L'incrémentation de la colonne pour passer à la
     *                         position suivante.
     * @param rowAdd           L'incrémentation de la ligne pour passer à la
     *                         position suivante.
     * @param player           Le joueur pour lequel on vérifie les pions alignés.
     * @param invertComparator Si true, les comparateurs de colonne et de ligne sont
     *                         inversés.
     * @return true si 4 pions sont alignés, false sinon.
     */
    public boolean getFourLinedPawns(int colStart, int colEnd, int rowStart, int rowEnd, int colAdd, int rowAdd,
            Player player, boolean invertComparator) {

        ArrayList<Pawn> linedPawns = new ArrayList<Pawn>(); // Liste des pions alignés
        for (int col = colStart, row = rowStart; col <= colEnd
                && (invertComparator ? row >= rowEnd : row <= rowEnd); col += colAdd, row += rowAdd) {
            Pawn pawn = null;
            try {
                pawn = board.getCell(col, row); // Récupère le pion de la case
            } catch (Exception e) {
                // Si la case n'existe pas
            }

            // Si le pion appartient au joueur sélectionné
            if (pawn != null && pawn.getPlayer() == player) {
                // Ajoute le pion à la liste des pions alignés
                linedPawns.add(pawn);

                // Si la liste des pions alignés contient 4 pions
                if (linedPawns.size() >= 4) {

                    // S'il n'y a pas déjà 4 pions gagnants
                    if (WINNING_PAWNS.size() < 4)
                        // Ajoute les pions alignés à la liste des pions gagnants
                        WINNING_PAWNS.addAll(linedPawns);

                    return true;
                }

            } else {
                // Sinon réinitialise la liste des pions alignés
                linedPawns.clear();
            }
        }
        return false;
    }

    /**
     * Réinitialise la partie.
     */
    public void reset() {
        if (firstPlayer == player1) {
            setFirstPlayer(player2);
        } else {
            setFirstPlayer(player1);
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
