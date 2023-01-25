package model;

import java.util.ArrayList;

public class IA extends Player {
    private int difficulty;

    public IA(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void play(Game game) {

        int columnToPlay = 1;

        if (difficulty == 1) {
            columnToPlay = playRandomly(game);
        } else if (difficulty == 2) {
            // columnToPlay = playDefensively(game);
        } else if (difficulty == 3) {
            // columnToPlay = playIntelligently(game);
        } else if (difficulty == 4) {
            columnToPlay = playOffensively(game);
        }

        game.play(columnToPlay);
    }

    public int playRandomly(Game game) {
        while (true) {
            int columnToPlay = (int) (Math.random() * game.getBoard().getWidth());
            if (!game.getBoard().isColumnFull(columnToPlay)) {
                return columnToPlay;
            }
        }
    }

    public int playOffensively(Game game) {
        ArrayList<int[]> winningPositions = game.getWinningPositions(game.getCurrentPlayer());
        for (int[] winningPosition : winningPositions) {
            int winX = winningPosition[0];
            int winY = winningPosition[1];
            int lowestEmpty = game.getBoard().getLowestEmptyCell(winningPosition[0]);

            if (game.checkVictory(winX, lowestEmpty, game.getCurrentPlayer())) {
                return winX;
            }
        }

        return playRandomly(game);
    }

}
