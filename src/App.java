import model.Style;
import model.Game;
import model.Player;

import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        SelectMainMenuOption();
    }

    public static void displayMainMenu() {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Menu principal" + Style.BACKGROUND_RESET);
        System.out.println("1. Jouer seul");
        System.out.println("2. Jouer à deux");
        System.out.println("3. Afficher le classement");
        System.out.println("4. Quitter" + Style.RESET);
    }

    public static String getUserInput() {
        // Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void SelectMainMenuOption() {
        while (true) {
            displayMainMenu();

            String input = getUserInput();
            switch (input) {
                case "1":
                    System.out.println("1");
                    return;
                case "2":
                    initMultiplayerGame();
                    return;
                case "3":
                    System.out.println("3");
                    return;
                case "4":
                    displayError("Fermeture du programme...");
                    return;
                default:
                    displayError("Option invalide !");
                    break;
            }
        }
    }

    public static void displayError(String message) {
        System.out.println(Style.RED + message + Style.RESET);
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
        // System.out.println(game.getPlayer1().getColor() + game.getPlayer1().getName()
        // + game.getPlayer1().getSymbol()
        // + Style.RESET);
        // System.out.println(game.getPlayer2().getColor() + game.getPlayer2().getName()
        // + game.getPlayer2().getSymbol()
        // + Style.RESET);
    }

    public static void displayPlayerNameMenu(Player player) {
        System.out.println("Entrez le nom du joueur " + player.getNumber() + " :" + Style.RESET);
    }

    public static void selectPlayerNameOption(Player player) {
        while (true) {
            displayPlayerNameMenu(player);

            String input = getUserInput();
            if (input.length() > 0) {
                player.setName(input);
                return;
            } else {
                displayError("Nom invalide !");
            }
        }
    }

    public static void displayColorMenu(Player player) {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Choix de la couleur" + Style.BACKGROUND_RESET);
        System.out.println("1. Rouge");
        System.out.println("2. Bleu");
        System.out.println("3. Jaune");
        System.out.println("4. Vert" + Style.RESET);
    }

    public static void selectColorOption(Player player) {
        while (true) {
            displayColorMenu(player);

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
                    displayError("Option invalide !");
                    break;
            }
        }
    }

    public static void displaySymbolMenu(Player player) {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Choix du symbole" + Style.BACKGROUND_RESET);
        System.out.println("1. @");
        System.out.println("2. =" + Style.RESET);
    }

    public static void selectSymbolOption(Player player1, Player player2) {
        while (true) {
            displaySymbolMenu(player1);

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
                    displayError("Option invalide !");
                    break;
            }
        }
    }
}
