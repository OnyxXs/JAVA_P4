package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

/**
 * Cette classe représente un score. Elle contient le nom du joueur, son score
 * et
 * le nom de son adversaire.
 */
public class Score implements Comparable<Score> {
    private static final String csvPath = "leaderboard.csv";
    public static final String SEPARATEUR = ";";

    private String name;
    private int score;
    private String opponentName;

    public static ArrayList<Score> leaderboard = new ArrayList<Score>();

    /**
     * Constructeur de la classe Score.
     * 
     * @param name         Le nom du joueur.
     * @param score        Le score du joueur.
     * @param opponentName Le nom de l'adversaire du joueur.
     */
    public Score(String name, int score, String opponentName) {
        this.name = name;
        this.score = score;
        this.opponentName = opponentName;
    }

    /**
     * Constructeur de la classe Score.
     */
    public Score() {
    }

    /**
     * Retourne le nom du joueur.
     * 
     * @return Le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Défini le nom du joueur.
     * 
     * @param name Le nouveau nom du joueur.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le score du joueur.
     * 
     * @return Le score du joueur.
     */
    public int getScore() {
        return score;
    }

    /**
     * Défini le score du joueur.
     * 
     * @param score Le score du joueur.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retourne le nom de l'adversaire du joueur.
     * 
     * @return Le nom de l'adversaire du joueur.
     */
    public String getOpponentName() {
        return opponentName;
    }

    /**
     * Défini le nom de l'adversaire du joueur.
     * 
     * @param opponentName Le nom de l'adversaire du joueur.
     */
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    /**
     * Retourne le pire score du classement.
     */
    public static int getWorstLeaderboardScore() {
        int worstScore = -1;
        for (Score score : leaderboard) {
            if (score.getScore() > worstScore) {
                worstScore = score.getScore();
            }
        }
        return worstScore;
    }

    /**
     * Enregistre le score dans le classement.
     */
    public void saveToLeaderboard() {
        leaderboard.add(this);

        leaderboardToCsv();
    }

    /**
     * Enregistre le classement dans le fichier csv.
     */
    public static void csvToLeaderboard() {
        File csvFile = new File(csvPath);

        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
            } catch (Exception e) {
                System.out.println(Menu.error("Erreur lors de la création du fichier de classement"));
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            leaderboard.clear();

            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(SEPARATEUR);
                Score score = new Score(data[0], Integer.parseInt(data[1]), data[2]);
                leaderboard.add(score);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(Menu.error("Erreur lors de la lecture du fichier de classement"));
        }
    }

    /**
     * Récupère le classement depuis le fichier csv.
     */
    public static void leaderboardToCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            for (Score score : leaderboard) {
                writer.write(score.toString());
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(Menu.error("Erreur lors de l'écriture du fichier de classement"));
        }
    }

    /**
     * Affiche le classement.
     */
    public static void displayLeaderboard() {
        if (leaderboard.size() == 0) {
            System.out.println(Menu.error("Le classement est vide"));
            return;
        }

        leaderboard.sort(null);

        System.out.println(Menu.coloredText("Top 10 des joueurs :", Style.YELLOW));
        int position = 1;
        for (Score score : leaderboard) {
            StringBuilder builder = new StringBuilder();
            builder.append(Menu.coloredText("#" + position + " - ", Style.YELLOW));
            builder.append(Menu.coloredText(score.getName(), Style.GREEN));
            builder.append(" : ");
            builder.append(Menu.coloredText(score.getScore() + " tours", Style.CYAN));
            builder.append(" contre ");
            builder.append(Menu.coloredText(score.getOpponentName(), Style.RED));
            System.out.println(builder.toString());

            position++;
        }
    }

    /**
     * Retourne une chaîne de caractères représentant le score.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(SEPARATEUR);
        builder.append(score);
        builder.append(SEPARATEUR);
        builder.append(opponentName);

        return builder.toString();
    }

    /**
     * Comparateur de la classe Score.
     */
    @Override
    public int compareTo(Score score2) {
        if (this.score == score2.getScore()) {
            return 1;
        } else {
            return this.score - score2.getScore();
        }
    }
}
