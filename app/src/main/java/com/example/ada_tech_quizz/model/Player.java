package com.example.ada_tech_quizz.model;

public class Player {
    private String mFirstName;
    private String mEmail;
    private String mPassword;

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    private int mScore;

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }
}
