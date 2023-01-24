package model;

/**
 * Classe publique représentant le joueur
 */
public class Player {
    private String name;
    private String color;
    private char symbol;
    private int number;

    /**
     * @param name le nom du joeur
     * @param color la couleur du joueur
     * @param symbol l'icone en jeu du joueur
     */
    public Player(String name, String color, char symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    /**
     * Crée un nouveau joueur
     */
    public Player() {
    }

    /**
     * récupère et retourne le nom du joueur
     */
    public String getName() {
        return name;
    }

    /**
     * récupère et retourne la couleur du joueur
     */
    public String getColor() {
        return color;
    }

    /**
     * récupère et retourne l'icone du joueur
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * récupère et retourne le numéro du joueur
     */
    public int getNumber() {
        return number;
    }

    /**
     * créer un nom de joeur
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * créer une couleur de joueur
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * créer un symbole de joueur
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * créer le numéro du joueur
     */
    public void setNumber(int number) {
        this.number = number;
    }
}