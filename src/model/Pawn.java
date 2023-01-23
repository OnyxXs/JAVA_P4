package model;

public class Pawn {
    private int x;
    private int y;
    private String color;
    private char symbol;

    public Pawn(int x, int y, String color, char symbol) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.symbol = symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
