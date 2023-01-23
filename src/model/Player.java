package model;

public class Player {
    private String name;
    private String color;
    private char symbol;

    public Player(String name, String color, char symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
