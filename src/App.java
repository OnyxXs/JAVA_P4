import model.Style;
import model.Game;
import model.IA;
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

        while (game.isPlaying()) {
            playGame(game);
        }
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

    /**
    * Cette méthode permet d'entrer les scores dans "scores.csv"
    * Si le mode de jeu est contre l'IA, seul le nom du joueur et le score sont enregistrés ainsi que le niveau de difficulté de l'IA
    * Sinon, les noms des deux joueurs et le score final sont enregistrés.
    */
    public static void entrerscores(Player player1, Player player2) throws IOException{
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("src/scores.csv", true)));
        if(VSIA==true){
            pw.println(player1.getName()+" ; "+Game.getScore()+" ; IA niveau "+IA.getDifficulty());
        }
        else{
            pw.println(player1.getName()+" ; "+player2.getName()+" ; "+Game.getScore());
        }
        pw.close();
    }

    /**
    * lis le fichier "scores.csv" qui contiens les scores de la partie précedente
    * trie les scores par les troisiemes elements de la ligne qui représente le score
    * affiche le score dans l'ordre trié
    */
    public static void afficherscores() throws IOException{
        List<String> scores = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("src/scores.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            scores.add(line);
        }
        br.close();
        Collections.sort(scores, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1.split(" ; ")[2]) - Integer.parseInt(o2.split(" ; ")[2]);
            }
        });
        scores.sort(Comparator.comparingInt(o -> Integer.parseInt(o.split(" ; ")[2])));
        for (String score : scores) {
            System.out.println(score);
        }
    }
}
