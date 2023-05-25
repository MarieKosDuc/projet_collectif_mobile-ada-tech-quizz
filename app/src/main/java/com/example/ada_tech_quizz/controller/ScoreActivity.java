package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Player;

public class ScoreActivity extends AppCompatActivity {

    private TextView mCongratsTextView, mPlayerScoreTextView,mPlayerBestScoreTextView,mPlayerTotalScoreTextView;

    private Button mPlayAgainButton;


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

        // Setting the total points for the player
        mPlayer.setTotalPoints(mPlayer.getTotalPoints() + intent.getIntExtra("score_key",0));

        // Setting the total questions played for the player
        mPlayer.setTotalQuestions(mPlayer.getTotalQuestions() + intent.getIntExtra("totalQuestionsSeries_key",0));

        //Toast.makeText(this, mPlayer.getFirstName() + mPlayer.getScore(), Toast.LENGTH_LONG).show() ;

        // Displays the player's name and score
        mCongratsTextView.setText("BRAVO " + mPlayer.getName());
        mPlayerScoreTextView.setText(Integer.toString(intent.getIntExtra("score_key",0)) + "/" + intent.getIntExtra("totalQuestionsSeries_key", 0));
        mPlayerBestScoreTextView.setText(Integer.toString(mPlayer.getBestScore()) + "/" + intent.getIntExtra("totalQuestionsSeries_key", 0));
        mPlayerTotalScoreTextView.setText(Integer.toString(mPlayer.getTotalPoints())+ "/" + Integer.toString(mPlayer.getTotalQuestions()) );


        // On click on "play again": restart the game (from GameActivity.class)
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(ScoreActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("name_key", mPlayer.getName());
                startActivity(gameActivityIntent);
            }
        });

    }
}