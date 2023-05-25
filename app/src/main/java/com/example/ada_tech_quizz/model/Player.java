package com.example.ada_tech_quizz.model;

public class Player {
    private String mFirstName;
    private String mEmail;
    private String mPassword;
    private int bestScore;

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    private int mScore;
    private int mBestScore;
    private int mTotalScore;
    private int mTotalQuestions;

    private int mID;

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }


    public int getTotalQuestions() {
        return mTotalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        mTotalQuestions = totalQuestions;
    }


    public int getTotalScore() {
        return mTotalScore;
    }

    public void setTotalScore(int totalScore) {
        mTotalScore = totalScore;
    }



    public int getBestScore() {
        return mBestScore;
    }

    public void setBestScore(int bestScore) {
        mBestScore = bestScore;
    }



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
