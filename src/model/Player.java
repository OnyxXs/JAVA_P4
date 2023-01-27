package model;

/**
 * Cette classe représente un joueur. Elle possède un nom, une couleur et un
 * symbole.
 */
public class Player {
    private String name;
    private String color;
    private char symbol;

    /**
     * Constructeur de la classe Player.
     * 
     * @param name   Le nom du joueur.
     * @param color  La couleur du joueur.
     * @param symbol Le symbole du joueur.
     */
    public Player(String name, String color, char symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    /**
     * Constructeur de la classe Player.
     * 
     * @param name Le nom du joueur.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Constructeur de la classe Player.
     */
    public Player() {
    }

    /**
     * Retourne le nom du joueur.
     * 
     * @return Le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la couleur du joueur.
     * 
     * @return La couleur du joueur.
     */
    public String getColor() {
        return color;
    }

    /**
     * Retourne le symbole du joueur.
     * 
     * @return Le symbole du joueur.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Défini le nom du joueur.
     * 
     * @param name Le nouveau nom du joueur.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Défini la couleur du joueur.
     * 
     * @param color La nouvelle couleur du joueur.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Défini le symbole du joueur.
     * 
     * @param symbol Le nouveau symbole du joueur.
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
