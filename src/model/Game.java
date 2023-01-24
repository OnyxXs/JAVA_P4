package model;

public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Board board;
    private int turn;
    private boolean isOver = false;

    public Game(Player player1, Player player2) {
        // player1 = new Player();
        // player2 = new Player();
        // currentPlayer = player1;
        // board = new Board();

        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
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

    public void setBoard(Board board) {
        this.board = board;
    }

    public void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
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

    public void play(int row, int col) {
        board.setCell(row, col, currentPlayer.getSymbol());
        switchPlayer();
    }

    public void reset() {
        board.reset();
        currentPlayer = player1;
    }

    public String toString() {
        return board.toString();
    }
}
