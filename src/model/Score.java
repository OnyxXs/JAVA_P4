package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Score {
    public static final String SEPARATEUR = ";";

    private String name;
    private int score;
    private String opponentName;

    public static ArrayList<Score> scores = new ArrayList<Score>();

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

    public static void csvToLeaderboard() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("leaderboard.csv"));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(SEPARATEUR);
                Score score = new Score(data[0], Integer.parseInt(data[1]), data[2]);
                scores.add(score);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier leaderboard.csv");
        }
    }

    public static void leaderboardToCsv() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.csv"));
            for (Score score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'écriture du fichier leaderboard.csv");
        }
    }

    public static void displayLeaderboard() {
        csvToLeaderboard();
        System.out.println("Classement des joueurs :");
        for (Score score : scores) {
            System.out
                    .println(score.getName() + " : " + score.getScore() + " points contre " + score.getOpponentName());
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
}
