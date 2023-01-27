package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class Score implements Comparable<Score> {
    private static final String csvPath = "leaderboard.csv";
    public static final String SEPARATEUR = ";";

    private String name;
    private int score;
    private String opponentName;

    public static ArrayList<Score> leaderboard = new ArrayList<Score>();

    public Score(String name, int score, String opponentName) {
        this.name = name;
        this.score = score;
        this.opponentName = opponentName;
    }

    public Score() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public static int getWorstLeaderboardScore() {
        int worstScore = -1;
        for (Score score : leaderboard) {
            if (score.getScore() > worstScore) {
                worstScore = score.getScore();
            }
        }
        return worstScore;
    }

    public void saveToLeaderboard() {
        leaderboard.add(this);

        leaderboardToCsv();
    }

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

    public static void displayLeaderboard() {
        leaderboard.sort(null);

        System.out.println("Classement des joueurs :");
        for (Score score : leaderboard) {
            StringBuilder builder = new StringBuilder();
            builder.append(Menu.coloredText(score.getName(), Style.GREEN));
            builder.append(" : ");
            builder.append(Menu.coloredText(score.getScore() + " tours", Style.CYAN));
            builder.append(" contre ");
            builder.append(Menu.coloredText(score.getOpponentName(), Style.RED));
            System.out.println(builder.toString());
        }
    }

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

    @Override
    public int compareTo(Score score2) {
        if (this.score == score2.getScore()) {
            return 1;
        } else {
            return this.score - score2.getScore();
        }
    }
}
