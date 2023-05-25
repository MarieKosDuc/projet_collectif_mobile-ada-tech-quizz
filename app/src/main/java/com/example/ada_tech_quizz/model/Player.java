package com.example.ada_tech_quizz.model;

public class Player {

    private int id;
    private String name;
    private int bestScore;
    private int totalQuestions;
    private int totalPoints;

    private int score = 0;

    public Player(int id, String name, int bestScore, int totalQuestions, int totalPoints, int score) {
        this.id = id;
        this.name = name;
        this.bestScore = bestScore;
        this.totalQuestions = totalQuestions;
        this.totalPoints = totalPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
