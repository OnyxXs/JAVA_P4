package model;

/**
 * Class publique Pawn (Pion)
 */
public class Pawn {

    /**
     * Joueur associé au pion
     */
    private Player player;

    /**
     * Crée un nouveau pion pour le joueur 'player'
     */
    public Pawn(Player player) {
        this.player = player;
    }

    /**
     * Crée un nouveau pion sans propriétaire
     */
    public Pawn() {
    }

    /**
     * récupère le joueur associé au pion
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Associe un joueur au pion
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
