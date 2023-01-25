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
                    break;
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
            game.display();
            Menu.displayPlayMenu();

            String input = getUserInput();
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
                Menu.printError("Colonne invalide !");
            }
        }
    }

    public static void playGame(Game game) {
        selectPlayerColumnOption(game);
    }
}
