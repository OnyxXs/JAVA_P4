package model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Player {
    private static final String NAME_PATTERN = "^[a-zA-Z]+(([\\'\\,\\.\\-][a-zA-Z])?[a-zA-Z]*)*$";

    private String name;
    private String color;
    private char symbol;
    private int number;

    public static Map<String, String> colorList = new HashMap<>() {
        {
            put("Rouge", Style.RED);
            put("Bleu", Style.BLUE);
            put("Jaune", Style.YELLOW);
            put("Vert", Style.GREEN);
        }
    };

    public static ArrayList<Map.Entry<String, String>> colorListIndex = new ArrayList<>(colorList.entrySet());

    public Player(String name, String color, char symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public Player() {
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

    public int getNumber() {
        return number;
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

    public void setNumber(int number) {
        this.number = number;
    }
}
