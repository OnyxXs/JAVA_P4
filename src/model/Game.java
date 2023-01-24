package model;

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
        int colHeight = board.getColumnHeight(col);

        for (int i = 0; i < colHeight; i++) {
            if (i != 0) {
                board.setCell(col, i - 1, null);
            }
            board.setCell(col, i, currentPlayer);

            System.out.println(board.toString() + "\r");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        switchPlayer();
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
        result.append("Tour de " + currentPlayer.getName() + "\n");

        return result.toString();
    }
}
