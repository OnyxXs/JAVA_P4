package model;

/**
* Classe publique représentant le joueur
*/
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Player {
    private String name;
    private String color;
    private char symbol;
    private int number;

    /**
    * name : le nom du joeur
    * color : la couleur du joueur
    * symbol : l'icone en jeu du joueur
    */
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