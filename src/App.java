import model.Style;
import model.Game;
import model.Player;
import model.Score;
import model.IA;
import model.Board;
import model.Menu;

import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Score.csvToLeaderboard();

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
                    // initSingleplayerGame(1);
                    initSingleplayerGame();
                    break;
                case "2":
                    initMultiplayerGame();
                    break;
                case "3":
                    Score.displayLeaderboard();
                    break;
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
        Player player1 = new Player("1");

        int AIdiff = selectAIDifficulty();
        Player ai = new IA(AIdiff);

        Game game = new Game(player1, ai);

        // Generate 0 or 1
        int firstPlayerIndex = (int) Math.round(Math.random());
        if (firstPlayerIndex == 0) {
            game.setFirstPlayer(player1);
            game.setCurrentPlayer(player1);
        } else {
            game.setFirstPlayer(ai);
            game.setCurrentPlayer(ai);
        }

        selectPlayerNameOption(player1);
        selectColorOption(player1);
        selectSymbolOption(player1, ai);

        ai.setName("IA Niveau " + AIdiff);
        selectColorOption(ai);

        playGame(game);
    }

    public static void initMultiplayerGame() {

        Player player1 = new Player("1");
        Player player2 = new Player("2");

        Game game = new Game(player1, player2);
        game.setFirstPlayer(player1);
        game.setCurrentPlayer(player1);

        selectPlayerNameOption(player1);
        selectColorOption(player1);
        selectSymbolOption(player1, player2);

        selectPlayerNameOption(player2);
        selectColorOption(player2);

        playGame(game);
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
        String selectedColor = Game.colorListIndex.get(colorIndex).getValue();
        Game.colorListIndex.remove(colorIndex);

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
                    game.setQuitStatus(true);
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
        while (game.isPlaying()) {
            if (game.getCurrentPlayer() instanceof IA) {
                ((IA) game.getCurrentPlayer()).play(game);
            } else {
                selectPlayerColumnOption(game);
            }
        }

        if (!game.wasQuit()) {
            endGame(game);
            askForRestart(game);
        }
    }

    public static void endGame(Game game) {
        if (game.getWinner() != null) {
            System.out.println("Le gagnant est " + game.getWinner().getName() + " !");
            if (!(game.getWinner() instanceof IA)) {
                int worstLbScore = Score.getWorstLeaderboardScore();
                int score = game.getTurn();

                if (Score.leaderboard.size() < 10 || score < worstLbScore) {
                    insertScoreInLeaderboard(game, score);
                }
            }

        } else {
            System.out.println("Match nul !");
        }
    }

    public static void insertScoreInLeaderboard(Game game, int score) {
        Score highscore = new Score();
        highscore.setName(game.getWinner().getName());
        highscore.setScore(score);
        highscore.setOpponentName(game.getOpponent().getName());

        highscore.saveToLeaderboard();
        Score.leaderboard.sort(null);
        if (Score.leaderboard.size() > 10) {
            Score.leaderboard.remove(10);
        }
        int position = Score.leaderboard.indexOf(highscore) + 1;

        String positionSuffix = "ème";
        if (position == 1) {
            positionSuffix = "ère";
        }
        System.out.println("Vous êtes entrés dans le top 10 ! Vous êtes à la " + position
                + positionSuffix + " place du classement !");
    }

    public static void askForRestart(Game game) {
        while (true) {
            Menu.printColoredText("Voulez-vous rejouer ?", Style.GREEN);

            String input = getUserInput();
            switch (input) {
                case "1":
                    game.reset();
                    playGame(game);
                    return;
                case "2":
                    Menu.printError("Retour au menu principal...");
                    return;
                default:
                    Menu.printError("Option invalide !");
                    break;
            }
        }
    }
}
