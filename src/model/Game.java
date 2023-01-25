package model;

import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player winner;
    private Board board;
    private int turn = 1;
    private boolean isPlaying = false;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
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
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
            turn++;
        }
    }

    public boolean isOver() {
        return board.isFull() || board.hasWinner();
    }

    public boolean isWinner(Player player) {
        return board.hasWinner(player);
    }

    public boolean isDraw() {
        return board.isFull() && !board.hasWinner();
    }

    public void play(int col) {
        int row = board.getLowestEmptyCell(col);
        placePawn(col, row);

        if (checkVictory(col, row, currentPlayer)) {
            // System.out.println("Victoire de " + currentPlayer.getName());
            winner = currentPlayer;
            isPlaying = false;
            return;
        } else if (board.isFull()) {
            System.out.println("Match nul");
            isPlaying = false;
            return;
        }

        switchPlayer();
    }

    public void placePawn(int col, int LowestEmptyCell) {

        for (int row = 0; row <= LowestEmptyCell; row++) {
            if (row != 0) {
                board.setCell(col, row - 1, null);
            }
            board.setCell(col, row, currentPlayer);

            System.out.println(board.toString());

            try {
                Thread.sleep(200); // Attends x ms avant de passer à l'itération suivante
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<int[]> getWinningPositions(Player player) {
        ArrayList<int[]> winningPositions = new ArrayList<int[]>();

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.getCell(col, row) == null) {
                    // Place un pion temporairement pour vérifier si le coup est gagnant
                    board.setCell(col, row, player);

                    // Si le coup est gagnant, on ajoute les coordonnées du coup gagnant à la liste
                    if (checkVictory(col, row, player)) {
                        int[] position = { col, row };
                        winningPositions.add(position);
                    }

                    // On remet la case à null
                    board.setCell(col, row, null);
                }
            }
        }

        return winningPositions;
    }

    public boolean checkVictory(int col, int row, Player player) {
        boolean isHorizontalWin = checkHorizontalWin(col, row, player);
        boolean isVerticalWin = checkVerticalWin(col, row, player);
        boolean isTopLeftToBotRightWin = checkTopLeftToBottomRightWin(col, row, player);
        boolean isBotLeftToTopRightWin = checkBottomLeftToTopRightWin(col, row, player);

        return isHorizontalWin || isVerticalWin || isTopLeftToBotRightWin || isBotLeftToTopRightWin;
    }

    public boolean checkHorizontalWin(int col, int row, Player player) {
        int count = 0;
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);
        for (int checkCol = colStart; checkCol <= colEnd; checkCol++) {

            Pawn pawn = board.getCell(checkCol, row);
            if (pawn != null && pawn.getPlayer() == player) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean checkVerticalWin(int col, int row, Player player) {
        int count = 0;
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);
        for (int checkRow = rowStart; checkRow <= rowEnd; checkRow++) {

            Pawn pawn = board.getCell(col, checkRow);
            if (pawn != null && pawn.getPlayer() == player) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean checkTopLeftToBottomRightWin(int col, int row, Player player) {
        int count = 0;
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);
        for (int checkCol = colStart, checkRow = rowStart; checkCol <= colEnd
                && checkRow <= rowEnd; checkCol++, checkRow++) {

            Pawn pawn = board.getCell(checkCol, checkRow);
            if (pawn != null && pawn.getPlayer() == player) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean checkBottomLeftToTopRightWin(int col, int row, Player player) {
        int count = 0;
        int colStart = Math.max(col - 3, 0);
        int colEnd = Math.min(col + 3, board.getWidth() - 1);
        int rowStart = Math.max(row - 3, 0);
        int rowEnd = Math.min(row + 3, board.getHeight() - 1);
        for (int checkCol = colStart, checkRow = rowEnd; checkCol <= colEnd
                && checkRow >= rowStart; checkCol++, checkRow--) {

            Pawn pawn = board.getCell(checkCol, checkRow);
            if (pawn != null && pawn.getPlayer() == player) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public void reset() {
        board.reset();
        currentPlayer = player1;
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
