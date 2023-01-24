import model.Style;
import model.Game;
import model.Player;
import model.Board;
import model.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        SelectMainMenuOption();
    }

    public static String getUserInput() {
        // Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }
    public static boolean VSIA = false;

    public static void SelectMainMenuOption() {
        
        while (true) {
            Menu.displayMainMenu();

            String input = getUserInput();
            switch (input) {
                case "1":
                    System.out.println("1");
                    VSIA=true;
                    return;
                case "2":
                    initMultiplayerGame();
                    return;
                case "3":
                    System.out.println("3");
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
        game.display();
    }

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

    public static void selectColorOption(Player player) {
        while (true) {
            Menu.displayColorMenu(player);

            String input = getUserInput();
            switch (input) {
                case "1":
                    player.setColor(Style.RED);
                    return;
                case "2":
                    player.setColor(Style.BLUE);
                    return;
                case "3":
                    player.setColor(Style.YELLOW);
                    return;
                case "4":
                    player.setColor(Style.GREEN);
                    return;
                default:
                    Menu.printError("Option invalide !");
                    break;
            }
        }
    }

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
    public static void entrerscores(Player player1, Player player2) throws IOException{
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("src/scores.csv", true)));
        if(VSIA==true){
            pw.println(player1.getName()+" ; "+player1.getScore()+" ; IA niveau "+player2.getDifficulty());
        }
        else{
            pw.println(player1.getName()+" ; "+player2.getName()+" ; "+player1.getScore());
        }
        pw.close();
    }

    public static void afficherscores() throws IOException{
        
    }
}
