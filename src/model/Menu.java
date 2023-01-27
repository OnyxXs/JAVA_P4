package model;

public class Menu {
    public static void displayMainMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Puissance 4 - Menu principal\n"));
        menu.append(menuOptions("1. Jouer seul\n"));
        menu.append(menuOptions("2. Jouer à deux\n"));
        menu.append(menuOptions("3. Afficher le classement\n"));
        menu.append(menuOptions("4. Quitter"));
        System.out.println(menu.toString());
    }

    public static void displayPlayerNameMenu(Player player) {
        System.out.println(inputQuery("Entrez le nom du joueur " + player.getName() + " :"));
    }

    public static void displayColorMenu(Player player) {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix de la couleur de " + player.getName() + "\n"));

        for (int colorNumber = 0; colorNumber < Game.colorListIndex.size(); colorNumber++) {
            String color = Game.colorListIndex.get(colorNumber).getValue();
            String colorName = Game.colorListIndex.get(colorNumber).getKey();

            menu.append((colorNumber + 1) + ". ");
            menu.append(color);
            menu.append(colorName + "\n");
            menu.append(Style.RESET);
        }
        menu.deleteCharAt(menu.length() - 1);
        System.out.println(menu.toString());
    }

    public static void displaySymbolMenu(Player player) {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix du symbole\n"));
        menu.append(menuOptions("1. @\n"));
        menu.append(menuOptions("2. ="));
        System.out.println(menu.toString());
    }

    public static void displayAIDifficultyMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Choix de la difficulté\n"));
        menu.append(menuOptions("1. Facile\n"));
        menu.append(menuOptions("2. Moyen\n"));
        menu.append(menuOptions("3. Difficile\n"));
        menu.append(menuOptions("4. Pro"));
        System.out.println(menu.toString());
    }

    public static void displayRestartMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append(MenuTitle("Voulez-vous rejouer ?\n"));
        menu.append(menuOptions("1. Oui\n"));
        menu.append(menuOptions("2. Non"));
        System.out.println(menu.toString());
    }

    public static void displayPlayMenu() {
        // printInputQuery("Entrez un numéro de colonne, ou q pour quitter :");
        System.out.println(inputQuery("Entrez un numéro de colonne, ou q pour quitter :"));
    }

    public static String MenuTitle(String title) {
        return coloredText(title, Style.CYAN);
    }

    public static String menuOptions(String options) {
        return coloredText(options, Style.GREEN);
    }

    public static String coloredText(String message, String color) {
        StringBuilder coloredText = new StringBuilder();
        coloredText.append(color);
        coloredText.append(message);
        coloredText.append(Style.RESET);
        return coloredText.toString();
    }

    public static String inputQuery(String message) {
        return coloredText(message, Style.YELLOW);
    }

    public static String info(String message) {
        return coloredText(message, Style.CYAN);
    }

    public static String success(String message) {
        return coloredText(message, Style.GREEN);
    }

    public static String error(String message) {
        return coloredText(message, Style.RED);
    }
}
