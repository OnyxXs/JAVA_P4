import model.Style;
import model.Game;
import model.Player;
import model.Board;
import model.Menu;

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

    public static void SelectMainMenuOption() {
        while (true) {
            Menu.displayMainMenu();

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
        game.setPlayingStatus(true);

        while (game.isPlaying()) {
            playGame(game);
        }
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

    public static void selectPlayerColumnOption(Game game) {
        while (true) {
            Menu.displayPlayMenu();

            String input = getUserInput();
            int column = Integer.parseInt(input);
            column--;
            System.out.println("Vous avez choisi la colonne " + (column + 1));

            if (column >= 0 && column <= game.getBoard().getWidth() - 1) {
                if (game.getBoard().isColumnFull(column)) {
                    Menu.printError("La colonne est pleine !");
                } else {
                    game.play(column);
                    return;
                }
            } else {
                Menu.printError("Colonne invalide !");
            }
        }
    }

    public static void playGame(Game game) {
        // selectPlayerColumnOption(game);

        // // if (game.getBoard().isWinning(game.getCurrentPlayer().getSymbol())) {
        // // Menu.displayWinningMenu(game);
        // // game.setPlayingStatus(false);
        // // } else if (game.getBoard().isFull()) {
        // // Menu.displayDrawMenu();
        // // game.setPlayingStatus(false);
        // // } else {
        // // game.switchPlayer();
        // // }
        // game.switchPlayer();
        game.display();
        selectPlayerColumnOption(game);
    }
}
