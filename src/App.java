import model.Style;
import model.Game;
import model.IA;
import model.Player;
import model.IA;
import model.Board;
import model.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
* classe publique représentant les fonctions de l'application
*/
public class App {

    /**
    * Crée un scanner 
    */
    private static Scanner scanner = new Scanner(System.in);

    /**
    * Appel la fonction pour sélectionner l'option du menu principal
    */
    public static void main(String[] args) throws Exception {
    SelectMainMenuOption();
    }

    /**
    * recupere l'entrée de l'utilisateur via scanner
    */
    public static String getUserInput() {
        // Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static boolean VSIA = false;

    /**
    * affiche le main menu
    * permet a l'utilisateur de faire des choix et appele la fonction associée au choix 
    * la boucle while continuera de tourner tant que l'option 4 n'est pas selectionnée
    * un message d'erreur s'affiche si l'utilisateur entre une touche non valide
    */
    public static void SelectMainMenuOption() throws IOException {
        
        while (true) {
            Menu.displayMainMenu();

            String input = getUserInput();
            switch (input) {
                case "1":
                    System.out.println("1");
                    return;
                case "2":
                    initMultiplayerGame();
                    break;
                case "3":
                    afficherscores();
                    return;
                case "4":
                    Menu.printError("Fermeture du programme...");
                    return;
                default:
                    Menu.printError("Option invalide !");
                    break;
            }
        }
    }

    public static void initSingleplayerGame() {
        Player player1 = new Player();
        player1.setNumber(1);

        int AIdiff = selectAIDifficulty();

        Player player2 = new IA(AIdiff);
        player2.setNumber(2);

        selectPlayerNameOption(player1);
        selectColorOption(player1);
        selectSymbolOption(player1, player2);

        player2.setName("IA");
        selectColorOption(player2);

        Game game = new Game(player1, player2);

        game.setBoard(new Board(7, 6));
        game.getBoard().initBoard();
        game.setPlayingStatus(true);

        playGame(game);
    }

    /**
    * Initialise une nouvelle partie multijoueur en créant deux joueurs (couleur, pseudo, symbole)
    * crée un nouveau tableau et commence la boucle de jeu
    */
    public static void initMultiplayerGame() {
        Player player1 = new Player();
        player1.setNumber(1);

        Player player2 = new Player();
        player2.setNumber(2);

        selectPlayerNameOption(player1);
        selectColorOption(player1);
        selectSymbolOption(player1, player2);

        selectPlayerNameOption(player2);
        selectColorOption(player2);

        Game game = new Game(player1, player2);

        game.setBoard(new Board(7, 6));
        game.getBoard().initBoard();
        game.setPlayingStatus(true);

        playGame(game);
    }

    /**
    * méthode qui se repete tant que le joueur n'a pas choisi de pseudo
    * un message d'erreur s'affiche si le nom n'est pas valide
    */
    public static void selectPlayerNameOption(Player player) {
        while (true) {
            Menu.displayPlayerNameMenu(player);

            String input = getUserInput();
            if (input.length() > 0) {
                player.setName(input);
                return;
            } else {
                Menu.printError("Nom invalide !");
            }
        }
    }

    /**
    * le joueur tape un chiffre qui défini sa couleur
    * Un message d'erreur s'affiche si le chiffre ne correspond pas a une couleur
    */
    public static void selectColorOption(Player player) {
        while (true) {
            Menu.displayColorMenu(player);

            String input = getUserInput();

            try {
                int colorIndex = Integer.parseInt(input);
                colorIndex--;

                String selectedColor = selectColor(colorIndex);
                player.setColor(selectedColor);
                return;

            } catch (Exception e) {

            }
        }
    }

    public static String selectColor(int colorIndex) {
        String selectedColor = Player.colorListIndex.get(colorIndex).getValue();
        Player.colorListIndex.remove(colorIndex);

        return selectedColor;
    }

    /**
    * Permet à un joueur de sélectionner un symbole pour jouer
    */
    public static void selectSymbolOption(Player player1, Player player2) {
        while (true) {
            Menu.displaySymbolMenu(player1);

            String input = getUserInput();
            switch (input) {
                case "1":
                    player1.setSymbol('@');
                    player2.setSymbol('=');
                    return;
                case "2":
                    player1.setSymbol('=');
                    player2.setSymbol('@');
                    return;
                default:
                    Menu.printError("Option invalide !");
                    break;
            }
        }
    }
     
    /**
    * le joeur choisi dans quelle colonne mettre son pion
    * cette fonction tourne tant qu'une colonne valide n'est pas saisie
    */
    public static void selectPlayerColumnOption(Game game) {
        while (true) {
            game.display();
            Menu.displayPlayMenu();

            String input = getUserInput();
            try {
                int column = Integer.parseInt(input);
                column--;

                if (column >= 0 && column <= game.getBoard().getWidth() - 1) {
                    if (game.getBoard().isColumnFull(column)) {
                        Menu.printError("La colonne est pleine !");
                    } else {
                        game.play(column);
                        return;
                    }
                } else {
                    Exception e = new Exception("invalid column");
                    throw e;
                }
            } catch (Exception e) {
                if (input.equals("q")) {
                    Menu.printError("Retour au menu principal...");
                    game.setPlayingStatus(false);
                    return;

                } else {
                    Menu.printError("Colonne invalide !");
                }
            }
        }
    }

    public static int selectAIDifficulty() {
        while (true) {
            Menu.displayAIDifficultyMenu();

            String input = getUserInput();
            try {
                int difficulty = Integer.parseInt(input);
                if (difficulty >= 1 && difficulty <= 4) {
                    return difficulty;
                } else {
                    Exception e = new Exception("invalid difficulty");
                    throw e;
                }
            } catch (Exception e) {
                Menu.printError("Difficulté invalide !");
            }
        }
    }

    public static void playGame(Game game) {
        selectPlayerColumnOption(game);
    }
}
