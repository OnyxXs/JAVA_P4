package model;

/**
* classe publique représentant l'IA
*/
public class IA {

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