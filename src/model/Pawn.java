package model;

public class Pawn {
    private Player player;

    public Pawn(Player player) {
        this.player = player;
    }

    public Pawn() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
