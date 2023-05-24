package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Player;

public class MainActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private Button mPlayButton, mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mSignUpButton = findViewById(R.id.main_button_sign_up);

        mPlayButton.setEnabled(false);

        // use Enter button to click on button
        mNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mPlayButton.isEnabled()) {
                        mPlayButton.performClick();
                        return true;
                    }
                }
                return false;
            }
        });


        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());

            }
        });

        Player mPlayer = new Player();

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.setFirstName(mNameEditText.getText().toString());
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                String name =  "toto";
                String email = "toto@mail.com";
                int bestScore = 0;
                int totalQuestions = 0;
                int totalPoints = 0;
                gameActivityIntent.putExtra("name_key", name);
                gameActivityIntent.putExtra("email_key", email);
                gameActivityIntent.putExtra("bestScore_key", bestScore);
                gameActivityIntent.putExtra("totalQuestions_key", totalQuestions);
                gameActivityIntent.putExtra("totalPoints_key", totalPoints);
                startActivity(gameActivityIntent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivityIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpActivityIntent);
            }
        });
    }

}