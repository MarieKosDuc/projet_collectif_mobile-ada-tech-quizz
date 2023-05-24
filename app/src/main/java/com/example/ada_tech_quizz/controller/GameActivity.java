package com.example.ada_tech_quizz.controller;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.DataModel;
import com.example.ada_tech_quizz.model.Player;
import com.example.ada_tech_quizz.model.Question;
import com.example.ada_tech_quizz.model.QuestionBank;
import com.example.ada_tech_quizz.model.QuestionsFromJSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
//import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    // initialization of the member variables
    public TextView mQuestionText;
    private Button mButton1, mButton2, mButton3, mButton4;
    public List<Question> mQuestionList;
    public QuestionBank mQuestionBank;

    private int mScore = 0, mQuestionNumber = 10;

    private Player mPlayer = new Player();


    // variables for Volley library
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.4.104:8085/questions";


    // ON CREATE: method to initialize the game screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("essaidebug", "OnCreate");

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

        // gets the player's name from the intent that launched GameActivity.class
        Intent intent = getIntent();
        mPlayer.setFirstName(intent.getStringExtra("name_key"));

        getData();

        //set question bank
        //mQuestionBank = initializeQuestionBank();

        // displays the first question
        //displayQuestion(mQuestionBank.getCurrentQuestion());

        //END OF ONCREATE METHOD
    }

    private void getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

                Gson gson = new Gson();
                Type questionListType = new TypeToken<List<Question>>(){}.getType();
                List<Question> questionList = gson.fromJson(response, questionListType);

                mQuestionBank = new QuestionBank(questionList);

                mQuestionBank.getCurrentQuestion();

                displayQuestion(mQuestionBank.getCurrentQuestion());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }


    // method to generate a new questionBank
    private QuestionBank initializeQuestionBank(){
        return new QuestionBank(mQuestionList);
            };


    // method to display a question
    private void displayQuestion(final Question question){
        // get text and answers for the question
        mQuestionText.setText(question.getQuestion());

        // display text and answers, initialize buttons color and make them clickable
        // A REFACTORER !!
        mButton1.setText(question.getAnswer1());
        mButton1.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton1.setEnabled(true);
        mButton2.setText(question.getAnswer2());
        mButton2.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton2.setEnabled(true);
        mButton3.setText(question.getAnswer3());
        mButton3.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton3.setEnabled(true);
        mButton4.setText(question.getAnswer4());
        mButton4.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));
        mButton4.setEnabled(true);
    }

    // onclick to verify the player's answer and go to the next question if correct
    @Override
    public void onClick(View v) {

        // handler to generate a delay before action
        Handler mHandler = new Handler();

        // creation of a hashmap to access the buttons
        Map<Integer, Button> buttonsMap = new HashMap<>();
        buttonsMap.put(0, mButton1);
        buttonsMap.put(1, mButton2);
        buttonsMap.put(2, mButton3);
        buttonsMap.put(3, mButton4);

        int index;

        // A REFACTORER avec un switch ?
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

        // set all buttons to red and disable them
        // A REFACTORER !!
        mButton1.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton1.setEnabled(false);
        mButton2.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton2.setEnabled(false);
        mButton3.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton3.setEnabled(false);
        mButton4.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton4.setEnabled(false);

        // set the correct answer to green
        buttonsMap.get(mQuestionBank.getCurrentQuestion().getCorrectAnswer()).setBackgroundColor(Color.rgb(107, 195 , 109));

        // if answer is right: +1 score
        if(index == mQuestionBank.getCurrentQuestion().getCorrectAnswer()){
            mScore++;

            //Toast.makeText(this, "Correct! Score : " + String.valueOf(mScore) + " Questions : " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();
        }

        /*else {

            Toast.makeText(this, "Wrong! Score : " + String.valueOf(mScore) + " Questions : " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();
        }*/

        // While there are still remaining questions, display the next question
        if (mQuestionNumber > 1){

            //Toast.makeText(this, "Remaining questions : " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();

            // increment question index to get to next question
            mQuestionBank.getNextQuestion();

            // after 3 seconds, display next question
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayQuestion(mQuestionBank.getCurrentQuestion());
                }
            }, 3000);

            // each time a button is clicked, the questions counter decreases
            mQuestionNumber--;
            //Toast.makeText(this, "Remaining questions after decrease: " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();

        } else {

            // after the last question, set player's score
            mPlayer.setScore(mScore);

            // create a 4 seconds delay
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // new intent that launches ScoreActivity.class and sends the player's name and score
                    Intent scoreActivityIntent = new Intent(GameActivity.this, ScoreActivity.class);
                    String name = mPlayer.getFirstName();
                    int score = mScore;
                    scoreActivityIntent.putExtra("name_key", name);
                    scoreActivityIntent.putExtra("score_key", score);
                    startActivity(scoreActivityIntent);
                }
            }, 4000);
        }
    }
}