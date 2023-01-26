package model;

public class Menu {
    public static void displayMainMenu() {
        System.out.println(Style.BACKGROUND_CYAN + Style.GREEN + "Menu principal" + Style.BACKGROUND_RESET);
        StringBuilder options = new StringBuilder();
        options.append("1. Jouer seul\n");
        options.append("2. Jouer à deux\n");
        options.append("3. Afficher le classement\n");
        options.append("4. Quitter");
        printMenuOptions(options.toString());
    }

    public static void displayPlayerNameMenu(Player player) {
        printInputQuery("Entrez le nom du joueur " + player.getNumber() + " :");
    }

    public static void displayColorMenu(Player player) {
        printMenuTitle("Choix de la couleur");
        StringBuilder options = new StringBuilder();

        for (int colorNumber = 0; colorNumber < Player.colorListIndex.size(); colorNumber++) {
            String color = Player.colorListIndex.get(colorNumber).getValue();
            String colorName = Player.colorListIndex.get(colorNumber).getKey();

            options.append((colorNumber + 1) + ". ");
            options.append(color);
            options.append(colorName + "\n");
            options.append(Style.RESET);
        }
        options.deleteCharAt(options.length() - 1);
        printMenuOptions(options.toString());
    }

    public static void displaySymbolMenu(Player player) {
        printMenuTitle("Choix du symbole");
        StringBuilder options = new StringBuilder();
        options.append("1. @\n");
        options.append("2. =");
        printMenuOptions(options.toString());
    }

    public static void displayAIDifficultyMenu() {
        printMenuTitle("Choix de la difficulté");
        StringBuilder options = new StringBuilder();
        options.append("1. Facile\n");
        options.append("2. Moyen\n");
        options.append("3. Difficile\n");
        options.append("4. Pro");
        printMenuOptions(options.toString());
    }

    public static void displayPlayMenu() {
        printInputQuery("Entrez le numéro de la colonne sur laquelle jouer :");
    }

    public static void printMenuTitle(String title) {
        printStylizedText(title, new String[] { Style.BACKGROUND_CYAN });
    }

    public static void printMenuOptions(String options) {
        printStylizedText(options, new String[] { Style.GREEN });
    }

    public static void printStylizedText(String message, String[] style) {
        StringBuilder result = new StringBuilder();
        for (String s : style) {
            result.append(s);
        }
        result.append(message);
        result.append(Style.RESET);
        System.out.println(result.toString());
    }

    public static void printColoredText(String message, String color) {
        printStylizedText(message, new String[] { color });
    }

    public static void printInputQuery(String message) {
        printColoredText(message, Style.YELLOW);
    }

    public static void printInfo(String message) {
        printColoredText(message, Style.CYAN);
    }

    public static void printSuccess(String message) {
        printColoredText(message, Style.YELLOW);
    }

    public static void printError(String message) {
        printColoredText(message, Style.RED);
    }
}
