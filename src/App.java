import model.Game;
import model.Player;
import model.Score;
import model.IA;
import model.Menu;

import java.util.Scanner;

/**
 * Cette classe représente l'application. C'est le point d'entrée du programme.
 */
public class App {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Point d'entrée du programme.
     * 
     * @param args Les arguments passés au programme.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Score.csvToLeaderboard();

        SelectMainMenuOption();
    }

    /**
     * Retourne l'entrée de l'utilisateur.
     * 
     * @return L'entrée de l'utilisateur.
     */
    public static String getUserInput() {
        // Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Affiche le menu principal et permet de sélectionner une option.
     */
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
                    System.out.println(Menu.error("Fermeture du programme..."));
                    return;
                default:
                    System.out.println(Menu.error("Option invalide !"));
                    break;
            }
        }
    }

    /**
     * Initialise une partie en mode solo.
     */
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

    /**
     * Initialise une partie en mode multijoueur.
     */
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

    /**
     * Affiche le menu de sélection du nom du joueur et permet d'en rentrer un
     * 
     * @param player Le joueur dont on veut modifier le nom.
     */
    public static void selectPlayerNameOption(Player player) {
        while (true) {
            Menu.displayPlayerNameMenu(player);

            String input = getUserInput();
            if (input.length() > 0 && !input.contains(";")) {
                player.setName(input);
                return;
            } else {
                System.out.println(Menu.error("Nom invalide !"));
            }
        }
    }

    /**
     * Affiche le menu de sélection de la couleur du joueur et permet
     * d'en
     * sélectionner une via son numéro.
     * 
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
                System.out.println(Menu.error("Couleur invalide !"));
            }
        }
    }

    /**
     * Retourne la couleur correspondant au numéro donné.
     *
     * @param colorIndex L'index de la couleur sélectionnée.
     * @return Le numéro de la couleur sélectionnée.
     */
    public static String selectColor(int colorIndex) {
        String selectedColor = Game.colorListIndex.get(colorIndex).getValue();
        Game.colorListIndex.remove(colorIndex);

        return selectedColor;
    }

    /**
     * Affiche le menu de sélection du symbole du joueur et permet d'en sélectionner
     * un.
     * 
     * @param player1 Le joueur 1.
     * @param player2 Le joueur 2.
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
                    System.out.println(Menu.error("Symbole invalide !"));
                    break;
            }
        }
    }

    /**
     * Affiche le menu de sélection de la colonne sur laquelle jouer et permet d'y
     * placer un pion
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
                        System.out.println(Menu.error("La colonne est pleine !"));
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
                    System.out.println(Menu.error("Retour au menu principal..."));
                    game.setPlayingStatus(false);
                    game.setQuitStatus(true);
                    return;

                } else {
                    System.out.println(Menu.error("Colonne invalide !"));
                }
            }
        }
    }

    /**
     * Affiche le menu de sélection de la difficulté de l'IA et permet d'en
     * sélectionner
     * une.
     * 
     * @return Le numéro de la difficulté sélectionnée.
     */
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
                System.out.println(Menu.error("Difficulté invalide !"));
            }
        }
    }

    /**
     * Lance une partie
     * 
     * @param game La partie à lancer
     */
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

    /**
     * Termine une partie et affiche le gagnant
     * 
     * @param game La partie en cours
     */
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

    /**
     * Insère le score du joueur dans le classement
     * 
     * @param game  La partie en cours
     * @param score Le score du joueur
     */
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

    /**
     * Affiche le menu de redémarrage et permet de redémarrer une partie ou de
     * retourner
     * au menu principal.
     * 
     */
    public static void askForRestart(Game game) {
        while (true) {
            Menu.displayRestartMenu();

            String input = getUserInput();
            switch (input) {
                case "1":
                    game.reset();
                    playGame(game);
                    return;
                case "2":
                    System.out.println(Menu.error("Retour au menu principal..."));
                    return;
                default:
                    System.out.println(Menu.error("Option invalide !"));
                    break;
            }
        }
    }
}
