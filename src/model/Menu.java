package model;

public class Menu {
    public static void displayMainMenu() {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Menu principal" + Style.BACKGROUND_RESET);
        System.out.println("1. Jouer seul");
        System.out.println("2. Jouer à deux");
        System.out.println("3. Afficher le classement");
        System.out.println("4. Quitter" + Style.RESET);
    }

    public static void displayPlayerNameMenu(Player player) {
        System.out.println("Entrez le nom du joueur " + player.getNumber() + " :" + Style.RESET);
    }

    public static void displayColorMenu(Player player) {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Choix de la couleur" + Style.BACKGROUND_RESET);
        System.out.println("1. Rouge");
        System.out.println("2. Bleu");
        System.out.println("3. Jaune");
        System.out.println("4. Vert" + Style.RESET);
    }

    public static void displaySymbolMenu(Player player) {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Choix du symbole" + Style.BACKGROUND_RESET);
        System.out.println("1. @");
        System.out.println("2. =" + Style.RESET);
    }

    public static void displayError(String message) {
        System.out.println(Style.RED + message + Style.RESET);
    }

    public static void displayBoard(Game game) {
        String board = game.getBoard().toString();
        System.out.println(board);
    }
}
