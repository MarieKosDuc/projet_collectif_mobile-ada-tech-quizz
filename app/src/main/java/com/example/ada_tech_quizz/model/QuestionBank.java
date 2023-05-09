package com.example.ada_tech_quizz.model;

import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
    }



    public Question getNextQuestion(){
        //Loop over the questions and return a new one at each call
        return null;
    }


}
