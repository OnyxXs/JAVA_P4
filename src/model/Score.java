package model;

import java.util.ArrayList;

public class Score {
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

}
