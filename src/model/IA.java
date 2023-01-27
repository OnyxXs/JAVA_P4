package model;

import java.util.ArrayList;

/**
 * Cette classe représente un joueur contrôlé par l'IA. Elle possède
 * une difficulté, et adapte son comportement en fonction de celle-ci.
 */
public class IA extends Player {
    private int difficulty;

    /**
     * Constructeur de la classe IA.
     * 
     * @param difficulty La difficulté de l'IA.
     */
    public IA(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Retourne la difficulté de l'IA.
     * 
     * @return La difficulté de l'IA.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Défini la difficulté de l'IA.
     * 
     * @param difficulty La nouvelle difficulté de l'IA.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Joue un coup pour l'IA.
     * 
     * @param game Le jeu en cours.
     */
    public void play(Game game) {
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

    /**
     * Joue un coup aléatoire pour l'IA.
     * 
     * @param game              Le jeu en cours.
     * @param playIntelligently Si l'IA doit jouer intelligemment.
     * @return La colonne où l'IA a joué.
     */
    public int playRandomly(Game game, boolean playIntelligently) {
        while (true) {
            int randCol = (int) (Math.random() * game.getBoard().getWidth());
            if (!game.getBoard().isColumnFull(randCol)) {
                // Si le mode intelligent est désactivé, on retourne la colonne aléatoire
                if (!playIntelligently) {
                    return randCol;
                }

                // Vérifie si il est reste des colonnes non perdantes libre pour l'IA
                ArrayList<Integer> AIlosCols = getAILosingColumns(game);
                if (AIlosCols.size() >= game.getBoard().getFreeColumnNumber()) {
                    return playRandomly(game, false);
                }

                // Vérifie si la colonne aléatoire n'est pas une colonne perdante pour l'IA
                if (!AIlosCols.contains(randCol)) {
                    return randCol;
                }
            }
        }
    }

    /**
     * Joue un coup offensif pour l'IA.
     * 
     * @param game Le jeu en cours.
     * @return La colonne où l'IA a joué.
     */
    public int playOffensively(Game game) {
        ArrayList<Integer> winPoses = game.getPlayerWinningColumns(game.getCurrentPlayer());
        if (winPoses.size() > 0) {
            return winPoses.get(0);
        }

        return playIntelligently(game);
    }

    /**
     * Joue un coup intelligent pour l'IA.
     * 
     * @param game Le jeu en cours.
     * @return La colonne où l'IA a joué.
     */
    public int playIntelligently(Game game) {
        return playDefensively(game, true);
    }

    /**
     * Joue un coup défensif pour l'IA.
     * 
     * @param game              Le jeu en cours.
     * @param playIntelligently Si l'IA doit jouer intelligemment.
     * @return La colonne où l'IA a joué.
     */
    public int playDefensively(Game game, boolean playIntelligently) {
        ArrayList<Integer> oppWinCols = game.getPlayerWinningColumns(game.getOpponent());
        if (oppWinCols.size() > 0) {
            return oppWinCols.get(0);
        }

        return playRandomly(game, playIntelligently);
    }

    /**
     * Retourne les colonnes perdantes pour l'IA.
     * 
     * @param game Le jeu en cours.
     * @return Les colonnes perdantes pour l'IA.
     */
    public ArrayList<Integer> getAILosingColumns(Game game) {
        ArrayList<int[]> oppWinPoses = game.getPlayerWinningPositions(game.getOpponent());
        ArrayList<Integer> losCols = new ArrayList<Integer>();

        for (int[] oppWinPos : oppWinPoses) {
            int winX = oppWinPos[0];
            int winY = oppWinPos[1];
            int lowestEmpty = game.getBoard().getLowestEmptyCell(winX);

            if (lowestEmpty == winY + 1) {
                losCols.add(winX);
            }
        }

        return losCols;
    }
}
