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
        placePawn(col);

        switchPlayer();
    }

    public void placePawn(int col) {
        int LowestEmptyCell = board.getLowestEmptyCell(col);

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

    public void reset() {
        board.initBoard();
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
