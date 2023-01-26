package model;

import java.util.ArrayList;

/**
* classe publique représentant l'IA
*/

public class IA extends Player {
    private int difficulty;
    private ArrayList<Integer> losCols = new ArrayList<Integer>(); // Colonnes qui ferait perdre l'IA

    /**
    * difficulté de l'IA
    */
    private static int difficulty;

    /**
    * Crée une IA avec le niveau de difficulté donné
    */
    public IA(int difficulty) {
        IA.difficulty = difficulty;
    }

    /**
    * Récupère le niveau de difficulté d'une IA
    */
    public static int getDifficulty() {
        return difficulty;
    }

    /**
    * Choisi/modifie le niveau de difficulté de l'IA
    */
    public void setDifficulty(int difficulty) {
        IA.difficulty = difficulty;
    }
}

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

    public int playRandomly(Game game, boolean playIntelligently) {
        while (true) {
            int randCol = (int) (Math.random() * game.getBoard().getWidth());
            if (!game.getBoard().isColumnFull(randCol)) {
                // Si le mode intelligent est désactivé, on retourne la colonne aléatoire
                if (!playIntelligently) {
                    return randCol;
                }

                // Si toutes les cases vides sont des colonnes qui ferait perdre l'IA, on
                // désactive le mode intelligent
                if (losCols.size() == game.getBoard().getFreeColumnNumber()) {
                    return playRandomly(game, false);
                }

                // Si la colonne aléatoire n'est pas une colonne qui ferait perdre l'IA
                boolean canPlace = true;
                if (!losCols.contains(randCol)) {
                    ArrayList<int[]> pWinPoses = game.getPlayerWinningPositions(game.getPlayer1());
                    // positions gagnantes du joueur

                    for (int[] pWinPos : pWinPoses) {
                        int winX = pWinPos[0];
                        int winY = pWinPos[1];
                        int lowestEmpty = game.getBoard().getLowestEmptyCell(winX);

                        if (winX == randCol && lowestEmpty == winY + 1) {
                            losCols.add(winX);
                            canPlace = false;
                        }
                    }
                    if (canPlace) {
                        return randCol;
                    }
                }
            }
        }
    }

    public int playOffensively(Game game) {
        ArrayList<int[]> winningPositions = game.getPlayerWinningPositions(game.getCurrentPlayer());
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
        losCols.clear();
        return playDefensively(game, true);
    }

    public int playDefensively(Game game, boolean playIntelligently) {
        ArrayList<int[]> playerWinPositions = game.getPlayerWinningPositions(game.getPlayer1());
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
