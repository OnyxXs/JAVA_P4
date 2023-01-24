package model;

/**
* classe publique représentant l'IA
*/
public class IA {

    /**
    * difficulté de l'IA
    */
    private int difficulty;

    /**
    * Crée une IA avec le niveau de difficulté donné
    */
    public IA(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
    * Récupère le niveau de difficulté d'une IA
    */
    public int getDifficulty() {
        return difficulty;
    }

    /**
    * Choisi/modifie le niveau de difficulté de l'IA
    */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}