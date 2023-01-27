package model;

/**
 * Cette classe représente un pion. Elle est associée à un joueur.
 */
public class Pawn {
    private Player player;

    /**
     * Constructeur de la classe Pawn.
     * 
     * @param player Le joueur associé au pion.
     */
    public Pawn(Player player) {
        this.player = player;
    }

    /**
     * Constructeur de la classe Pawn.
     */
    public Pawn() {
    }

    /**
     * Retourne le joueur associé au pion.
     * 
     * @return Le joueur associé au pion.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Défini le joueur associé au pion.
     * 
     * @param player Le nouveau joueur associé au pion.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
