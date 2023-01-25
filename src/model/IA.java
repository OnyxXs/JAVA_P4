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
        ArrayList<int[]> winPositions = game.getWinningPositions(game.getCurrentPlayer());
        ArrayList<int[]> playerWinPositions = game.getWinningPositions(game.getPlayer1());

        int columnToPlay = 1;

        switch (difficulty) {
            case 1:
                columnToPlay = playRandomly(game, false);
                break;
            case 2:
                columnToPlay = playDefensively(game, false);
                break;
            case 3:
                columnToPlay = playIntelligently(game);
                break;
            case 4:
                columnToPlay = playOffensively(game);
                break;
        }

        game.play(columnToPlay);
    }

    public int playRandomly(Game game, boolean playIntelligently) {

        while (true) {
            int columnToPlay = (int) (Math.random() * game.getBoard().getWidth());
            if (!game.getBoard().isColumnFull(columnToPlay)) {
                // if (!playIntelligently) {
                // return columnToPlay;
                // }

                // ArrayList<int[]> playerWinPositions =
                // game.getWinningPositions(game.getPlayer1());
                // for (int[] pWinPos : playerWinPositions) {
                // int winX = pWinPos[0];
                // int winY = pWinPos[1];

                // int lowestEmpty = game.getBoard().getLowestEmptyCell(winX);

                // int freeColNumber = game.getBoard().getWidth() -
                // game.getBoard().getFullColumns().length;

                // ArrayList<Integer> losingCols = new ArrayList<Integer>();
                // if (winY + 1 == game.getBoard().getLowestEmptyCell(winX)) {
                // losingCols.add(winX);
                // }

                // for (int losCol : losingCols) {
                // if (losCol == columnToPlay) {
                // return playRandomly(game, false);
                // }
                // }
                // return columnToPlay;
                // }
                return columnToPlay;
            }
        }
    }

    public int playOffensively(Game game) {
        ArrayList<int[]> winningPositions = game.getWinningPositions(game.getCurrentPlayer());
        for (int[] winningPosition : winningPositions) {
            int winX = winningPosition[0];
            int winY = winningPosition[1];

            int lowestEmpty = game.getBoard().getLowestEmptyCell(winX);

            if (lowestEmpty == winY) {
                return winX;
            }
        }

        // return playIntelligently(game);
        return playIntelligently(game);
    }

    public int playIntelligently(Game game) {
        return playDefensively(game, true);
    }

    public int playDefensively(Game game, boolean playIntelligently) {
        ArrayList<int[]> playerWinPositions = game.getWinningPositions(game.getPlayer1());
        for (int[] pWinPos : playerWinPositions) {
            int winX = pWinPos[0];
            int winY = pWinPos[1];

            int lowestEmpty = game.getBoard().getLowestEmptyCell(winX);

            if (lowestEmpty == winY) {
                return winX;
            }
        }

        return playRandomly(game, playIntelligently);
    }
}
