package model;

import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
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
            System.out.println("Victoire de " + currentPlayer.getName());
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
                    // temporarily place a pawn at this position
                    board.setCell(col, row, player);

                    // check if this move results in a win
                    if (checkVictory(col, row, player)) {
                        int[] position = { col, row };
                        winningPositions.add(position);
                    }

                    // remove the temporarily placed pawn
                    board.setCell(col, row, null);
                }
            }
        }

        return winningPositions;
    }

    public boolean checkVictory(int col, int row, Player player) {
        // check for horizontal win
        int count = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            if (board.getCell(i, row) != null && board.getCell(i, row).getPlayer() == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // check for vertical win
        count = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            if (board.getCell(col, i) != null && board.getCell(col, i).getPlayer() == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;

            }
        }

        // check for diagonal win (from top left to bottom right)
        count = 0;
        int i = row;
        int j = col;
        while (i > 0 && j > 0) {
            i--;
            j--;
        }
        while (i < board.getHeight() && j < board.getWidth()) {
            if (board.getCell(j, i) != null && board.getCell(j, i).getPlayer() == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            i++;
            j++;
        }
        // check for diagonal win (from top right to bottom left)
        count = 0;
        i = row;
        j = col;
        while (i > 0 && j < board.getWidth() - 1) {
            i--;
            j++;
        }
        while (i < board.getHeight() && j >= 0) {
            if (board.getCell(j, i) != null && board.getCell(j, i).getPlayer() == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            i++;
            j--;
        }
        // no win
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
