package com.example.ada_tech_quizz.model;

public class DataModel {
    // This class will be as a template for the data that we are going to parse
    private  int  id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswer;

    //Getter

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }


    public int getCorrectAnswerIndex() {
        return correctAnswer;
    }
}
