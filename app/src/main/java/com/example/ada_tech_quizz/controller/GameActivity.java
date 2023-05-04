package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Question;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    // initialization of the member variables
    private TextView mQuestionText;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

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

        Intent intent = getIntent();
        String name = intent.getStringExtra("name_key");

        Question question1 = new Question("Click on 3",
                Arrays.asList(
                        "0",
                        "1",
                        "toto",
                        "3"
                ), 3);

        mQuestionText.setText(question1.getQuestion());
        String[] mQuestionList = question1.getChoiceList().toArray(new String[0]);
        mButton1.setText(mQuestionList[0]);
        mButton2.setText(mQuestionList[1]);
        mButton3.setText(mQuestionList[2]);
        mButton4.setText(mQuestionList[3]);

    }
}