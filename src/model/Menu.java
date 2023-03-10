package model;

/**
 * Classe reprséentant l'ensemble des menus du jeu et des méthodes d'affichage.
 */
public class Menu {

    /**
     * Affiche le menu principal.
     */
    public static void displayMainMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Puissance 4 - Menu principal\n"));
        menu.append(menuOptions("1. Jouer seul\n"));
        menu.append(menuOptions("2. Jouer à deux\n"));
        menu.append(menuOptions("3. Afficher le classement\n"));
        menu.append(menuOptions("4. Quitter"));
        System.out.println(menu.toString());
    }

    /**
     * Affiche le menu de choix du nom du joueur.
     * 
     * @param player Le joueur dont on veut changer le nom.
     */
    public static void displayPlayerNameMenu(Player player) {
        System.out.println(inputQuery("Entrez le nom du joueur " + player.getName() + " :"));
    }

    /**
     * Affiche le menu de choix de la couleur du joueur.
     * 
     * @param player Le joueur dont on veut changer la couleur.
     */
    public static void displayColorMenu(Player player) {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix de la couleur de " + player.getName() + "\n"));

        for (int colorNumber = 0; colorNumber < Game.colorListIndex.size(); colorNumber++) {
            String color = Game.colorListIndex.get(colorNumber).getValue();
            String colorName = Game.colorListIndex.get(colorNumber).getKey();

            menu.append((colorNumber + 1) + ". ");
            menu.append(color);
            menu.append(colorName);
            menu.append(Style.RESET + "\n");
        }
        menu.delete(menu.length() - 1, menu.length());
        System.out.println(menu.toString());
    }

    /**
     * Affiche le menu de choix du symbole du joueur.
     * 
     * @param player Le joueur dont on veut changer le symbole.
     */
    public static void displaySymbolMenu(Player player) {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix du symbole\n"));
        menu.append(menuOptions("1. @\n"));
        menu.append(menuOptions("2. ="));
        System.out.println(menu.toString());
    }

    /**
     * Affiche le menu de choix de la difficulté de l'IA.
     */
    public static void displayAIDifficultyMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix de la difficulté\n"));
        menu.append(menuOptions("1. Facile\n"));
        menu.append(menuOptions("2. Moyen\n"));
        menu.append(menuOptions("3. Difficile\n"));
        menu.append(menuOptions("4. Pro"));
        System.out.println(menu.toString());
    }

    /**
     * Affiche le menu demandant si le joueur veut rejouer.
     */
    public static void displayRestartMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Voulez-vous rejouer ?\n"));
        menu.append(menuOptions("1. Oui\n"));
        menu.append(menuOptions("2. Non"));
        System.out.println(menu.toString());
    }

    /**
     * Affiche le menu demandant sur quelle colonne le joueur veut jouer.
     */
    public static void displayPlayMenu() {
        System.out.println(inputQuery("Entrez un numéro de colonne, ou q pour quitter :"));
    }

    /**
     * Retourne un message de titre de menu.
     */
    public static String MenuTitle(String title) {
        return coloredText(title, Style.CYAN + Style.BOLD + Style.UNDERLINE);
    }

    /**
     * Retourne un message d'option de menu.
     */
    public static String menuOptions(String options) {
        return coloredText(options, Style.GREEN);
    }

    /**
     * Retourne un message de couleur.
     */
    public static String coloredText(String message, String color) {
        StringBuilder coloredText = new StringBuilder();
        coloredText.append(color);
        coloredText.append(message);
        coloredText.append(Style.RESET);
        return coloredText.toString();
    }

    /**
     * Retourne un message de demande d'entrée utilisateur.
     */
    public static String inputQuery(String message) {
        return coloredText(message, Style.YELLOW);
    }

    /**
     * Retourne un message d'information.
     */
    public static String info(String message) {
        return coloredText(message, Style.CYAN);
    }

    /**
     * Retourne un message de succès.
     */
    public static String success(String message) {
        return coloredText(message, Style.GREEN);
    }

    /**
     * Retourne un message d'erreur.
     */
    public static String error(String message) {
        return coloredText(message, Style.RED);
    }
}
