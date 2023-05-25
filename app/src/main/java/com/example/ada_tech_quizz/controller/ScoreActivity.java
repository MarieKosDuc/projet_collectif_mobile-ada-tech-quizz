package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {

    private TextView mCongratsTextView, mPlayerScoreTextView,mPlayerBestScoreTextView,mPlayerTotalScoreTextView;

    private Button mPlayAgainButton;

    public int mTotalPoints, mTotalQuestions;

    public Player mPlayer;


    // variables for Volley library
    private RequestQueue mRequestQueue;

    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mCongratsTextView = findViewById(R.id.score_textview_congrats);
        mPlayerScoreTextView = findViewById(R.id.score_textview_playerScore);
        mPlayerBestScoreTextView = findViewById(R.id.score_textview_playerBestScore);
        mPlayerTotalScoreTextView = findViewById(R.id.score_textview_playerTotalScore);
        mPlayAgainButton = findViewById(R.id.score_button_playAgain);

        // getting the player's name and score from the previous activity (which launched the intent)
        Intent intent = getIntent();
        Player mPlayer = new Player(intent.getIntExtra("id_key",0),intent.getStringExtra("name_key"), intent.getIntExtra("bestScore_key",0), intent.getIntExtra("totalQuestions_key",0),intent.getIntExtra("totalPoints_key",0), intent.getIntExtra("score_key",0));

        //setting the player's score for this session
        mPlayer.setScore(intent.getIntExtra("score_key", 0));

        // Setting the best score: if the current score is superior to previous best score, replace it
        if(intent.getIntExtra("score_key",0) > mPlayer.getBestScore()){
            mPlayer.setBestScore(intent.getIntExtra("score_key",0));
        }

        // Setting the total points for the player
        mPlayer.setTotalPoints(mPlayer.getTotalPoints() + intent.getIntExtra("score_key",0));

        // Setting the total questions played for the player
        mPlayer.setTotalQuestions(mPlayer.getTotalQuestions() + intent.getIntExtra("totalQuestionsSeries_key",0));

        // Displays the player's name and score
        mCongratsTextView.setText("BRAVO " + mPlayer.getName());
        mPlayerScoreTextView.setText(Integer.toString(intent.getIntExtra("score_key",0)) + "/" + intent.getIntExtra("totalQuestionsSeries_key", 0));
        mPlayerBestScoreTextView.setText(Integer.toString(mPlayer.getBestScore()) + "/" + intent.getIntExtra("totalQuestionsSeries_key", 0));
        mPlayerTotalScoreTextView.setText(Integer.toString(mPlayer.getTotalPoints())+ "/" + Integer.toString(mPlayer.getTotalQuestions()) );


        // On click on "play again": restart the game (from GameActivity.class)
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    sendData();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
                // Start new gameActivity with actualized data
                Intent gameActivityIntent = new Intent(ScoreActivity.this, GameActivity.class);

                gameActivityIntent.putExtra("name_key", mPlayer.getName());
                gameActivityIntent.putExtra("bestScore_key", mPlayer.getBestScore());
                gameActivityIntent.putExtra("totalQuestions_key", mPlayer.getTotalQuestions());
                gameActivityIntent.putExtra("totalPoints_key", mPlayer.getTotalPoints());
                gameActivityIntent.putExtra("ID_key", mPlayer.getId());
                startActivity(gameActivityIntent);
            }
        });

    }


    private void sendData() throws JSONException {

        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        url = "http://192.168.6.29:8085/users/" + Integer.toString(mPlayer.getId());

        System.out.print(url);

        // Creating the JSON object that will be sent to the API
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("bestScore", mPlayerScoreTextView.getText().toString());
        jsonBody.put("totalQuestions", mTotalQuestions);
        jsonBody.put("totalPoints", mTotalPoints);

        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

//                        // Start new gameActivity with actualized data
//                        Intent gameActivityIntent = new Intent(ScoreActivity.this, GameActivity.class);
//
//                        gameActivityIntent.putExtra("name_key", mPlayer.getName());
//                        gameActivityIntent.putExtra("bestScore_key", mPlayer.getBestScore());
//                        gameActivityIntent.putExtra("totalQuestions_key", mPlayer.getTotalQuestions());
//                        gameActivityIntent.putExtra("totalPoints_key", mPlayer.getTotalPoints());
//                        gameActivityIntent.putExtra("ID_key", mPlayer.getId());
//                        startActivity(gameActivityIntent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("VOLLEY", error.toString());
                    }
                });

        // Here we should use a singleton, but we deemed it too complicated for this simple app
        // https://stackoverflow.com/questions/41120064/what-is-the-use-of-singleton-class-in-volley
        // https://google.github.io/volley/request.html
        mRequestQueue.add(mJsonObjectRequest);
    }
}