package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Question;
import com.example.ada_tech_quizz.model.QuestionBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    // initialization of the member variables
    private TextView mQuestionText;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Question question1 = new Question("Click on 3",
            Arrays.asList(
                    "0",
                    "1",
                    "toto",
                    "3"
            ), 3);

    private Question question2 = new Question("Click on 1",
            Arrays.asList(
                    "0",
                    "1",
                    "titi",
                    "3"
            ), 1);

    private QuestionBank mQuestionBank = initializeQuestionBank();

    public QuestionBank initializeQuestionBank(){
        List<Question> questions = new ArrayList<Question>();
        return new QuestionBank(questions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // plugging the variables into the layout components
        mQuestionText = findViewById(R.id.game_activity_textview_question);
        mButton1 = findViewById(R.id.game_activity_button_1);
        mButton2 = findViewById(R.id.game_activity_button_2);
        mButton3 = findViewById(R.id.game_activity_button_3);
        mButton4 = findViewById(R.id.game_activity_button_4);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name_key");

        displayQuestion(question1);

    }

    private void displayQuestion(final Question question){
        mQuestionText.setText(question.getQuestion());
        String[] mQuestionList = question.getChoiceList().toArray(new String[0]);
        mButton1.setText(mQuestionList[0]);
        mButton2.setText(mQuestionList[1]);
        mButton3.setText(mQuestionList[2]);
        mButton4.setText(mQuestionList[3]);
    }

    @Override
    public void onClick(View v) {
        int index;
        if(v == mButton1){
            index = 0;
        } else if(v == mButton2){
            index = 1;
        } else if(v == mButton3){
            index = 2;
        }else if(v == mButton4){
            index = 3;
        } else{
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        if(index == question1.getAnswerIndex()){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}