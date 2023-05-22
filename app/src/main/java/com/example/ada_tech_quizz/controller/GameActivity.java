package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.DataModel;
import com.example.ada_tech_quizz.model.Player;
import com.example.ada_tech_quizz.model.Question;
import com.example.ada_tech_quizz.model.QuestionBank;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    // initialization of the member variables
    public TextView mQuestionText;
    private Button mButton1, mButton2, mButton3, mButton4;
    private QuestionBank mQuestionBank = initializeQuestionBank();

    public  List<Question> newQuestionList;

    private int mScore = 0, mQuestionNumber = 5;

    private Player mPlayer = new Player();

    int correctAnswer;


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

        // gets the player's name from the intent that launched GameActivity.class
        Intent intent = getIntent();
        mPlayer.setFirstName(intent.getStringExtra("name_key"));


        //Retrofit Builder

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.139:8085/") // mettre son adresse IP ----ICI-----
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // instance for interface
        MyAPICal myAPIcal = retrofit.create(MyAPICal.class);
        Call<DataModel> call = myAPIcal.getData();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {

                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.fromJson(response.body().toString(), JsonElement.class);

// Vérifiez si l'élément JSON est un tableau (dans votre cas, une liste de questions)
                    if (jsonElement.isJsonArray()) {
                        JsonArray jsonArray = jsonElement.getAsJsonArray();

                        // Parcourez les éléments du tableau (questions)
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                            // Extrayez les valeurs des clés nécessaires
                            String question = jsonObject.get("question").getAsString();
                            String answer1 = jsonObject.get("answer1").getAsString();
                            String answer2 = jsonObject.get("answer2").getAsString();
                            String answer3 = jsonObject.get("answer3").getAsString();
                            String answer4 = jsonObject.get("answer4").getAsString();
                            int correctAnswerIndex = jsonObject.get("correctAnswer").getAsInt();

                            // Créez l'objet Question correspondant et ajoutez-le à votre liste de questions
                            Question newQuestion = new Question(question,
                                    Arrays.asList(answer1, answer2, answer3, answer4),
                                    correctAnswerIndex);
                            //newQuestionList.add(newQuestion);
                            getAPIQuestions(newQuestion, newQuestionList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }

        });
        // displays the first question
        displayQuestion(mQuestionBank.getCurrentQuestion());

    }


            private List<Question> getAPIQuestions (Question question, List<Question> qlist){
        qlist.add(question);
        return qlist;
    } ;

            // method to generate a new questionBank
            private QuestionBank initializeQuestionBank(){


                return new QuestionBank(newQuestionList);
            };


    // method to display a question
    private void displayQuestion(final Question question){
        // get text and answers for the question
        mQuestionText.setText(question.getQuestion());
        String[] mQuestionList = question.getChoiceList().toArray(new String[0]);

        // display text and answers, initialize buttons color and make them clickable
        // A REFACTORER !!
        mButton1.setText(mQuestionList[0]);
        mButton1.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton1.setEnabled(true);
        mButton2.setText(mQuestionList[1]);
        mButton2.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton2.setEnabled(true);
        mButton3.setText(mQuestionList[2]);
        mButton3.setBackgroundColor(getResources().getColor(R.color.pink));
        mButton3.setEnabled(true);
        mButton4.setText(mQuestionList[3]);
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
        buttonsMap.get(mQuestionBank.getCurrentQuestion().getAnswerIndex()).setBackgroundColor(Color.rgb(107, 195 , 109));

        // if answer is right: +1 score
        if(index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
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

            // after 4 seconds, display next question
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayQuestion(mQuestionBank.getCurrentQuestion());
                }
            }, 4000);

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