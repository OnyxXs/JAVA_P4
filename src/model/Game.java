package model;

import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;
    private Player winner;
    private Board board;
    private int turn = 1;
    private boolean isPlaying = false;

    private static final String WIN_COLOR = Style.PURPLE;
    private static final ArrayList<Pawn> WINNING_PAWNS = new ArrayList<Pawn>();
    // Liste des pions 4 pions alignés gagnants

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.opponent = player2;
    }

    public void display() {
        String gameDisplay = this.toString();
        System.out.println(gameDisplay);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayingStatus(boolean status) {
        isPlaying = status;
    }

    public void switchPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
            opponent = player1;
        } else {
            currentPlayer = player1;
            opponent = player2;
            turn++;
        }
    }

    public void play(int col) {
        int row = board.getLowestEmptyCell(col);
        placePawn(col, row);

        if (checkVictory(col, row, currentPlayer)) {
            winner = currentPlayer;
            isPlaying = false;

            for (Pawn pawn : WINNING_PAWNS) {
                Player winPlayer = new Player();
                winPlayer.setColor(WIN_COLOR);
                winPlayer.setSymbol(pawn.getPlayer().getSymbol());

                pawn.setPlayer(winPlayer);
            }
            System.out.println(board.toString());

            return;
        } else if (board.isFull()) {
            isPlaying = false;
            return;
        }

        switchPlayer();
    }

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

        return winCols;
    }

    public boolean checkVictory(int col, int row, Player player) {
        WINNING_PAWNS.clear();

        boolean isHorizontalWin = checkHorizontalWin(col, row, player);
        boolean isVerticalWin = checkVerticalWin(col, row, player);
        boolean isTopLeftToBotRightWin = checkTopLeftToBottomRightWin(col, row, player);
        boolean isBotLeftToTopRightWin = checkBottomLeftToTopRightWin(col, row, player);

        return isHorizontalWin || isVerticalWin || isTopLeftToBotRightWin || isBotLeftToTopRightWin;
    }

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

    public boolean checkTopLeftToBottomRightWin(int col, int row, Player player) {
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkCol = colStart, checkRow = rowStart; checkCol <= colEnd
                && checkRow <= rowEnd; checkCol++, checkRow++) {
            if (getFourLinedPawns(checkCol, checkRow, player, winPawns)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBottomLeftToTopRightWin(int col, int row, Player player) {
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);

        ArrayList<Pawn> winPawns = new ArrayList<Pawn>();
        for (int checkCol = colStart, checkRow = rowEnd; checkCol <= colEnd
                && checkRow >= rowStart; checkCol++, checkRow--) {
            if (getFourLinedPawns(checkCol, checkRow, player, winPawns)) {
                return true;
            }
        }
        return false;
    }

    public boolean getFourLinedPawns(int col, int row, Player player, ArrayList<Pawn> winPawns) {
        Pawn pawn = board.getCell(col, row);

        // Si le pion n'est pas vide et qu'il appartient au joueur sélectionné
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(board + "\n");
        result.append("Tour n°" + turn + "\n");
        result.append("Tour de " + currentPlayer.getColor() + currentPlayer.getName() + Style.RESET + "\n");

        return result.toString();
    }
}
