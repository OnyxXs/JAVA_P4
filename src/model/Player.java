package model;

public class Player {
    private Pawn pawn;

    public Player(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}
