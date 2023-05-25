package com.example.ada_tech_quizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText, mPasswordEditText;
    private Button mPlayButton, mSignUpButton;

    private Player mPlayer;


    public TextView mMessageTextView;

    // variables for Volley library
    private RequestQueue mRequestQueue;
    private String url = "http://192.168.6.29:8085/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = findViewById(R.id.main_edittext_email);
        mPasswordEditText = findViewById(R.id.main_edittext_password);
        mPlayButton = findViewById(R.id.main_button_play);
        mSignUpButton = findViewById(R.id.main_button_sign_up);
        mMessageTextView = findViewById(R.id.main_textview_response);

        //mMessageTextView.setText("toto");

        // use Enter button to click on button
        mPlayButton.setOnKeyListener(new View.OnKeyListener() {
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

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered()){
                    try {
                        sendData();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                };
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

    public boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean checkDataEntered(){
        boolean allFieldsOK = false;
        if (isEmpty(mEmailEditText) || isEmpty(mPasswordEditText)) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
        } else {
            allFieldsOK = true;
        }
        return allFieldsOK;
    }

    private void sendData() throws JSONException {

        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // Creating the JSON object that will be sent to the API
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", mEmailEditText.getText().toString());
        jsonBody.put("password", mPasswordEditText.getText().toString());

        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mMessageTextView.setText("Response: " + response.toString());
                        // RECUPERER LA REPONSE POUR CREER LES INTENT
                        Gson gson = new GsonBuilder().create();
                        final Player mPlayer = gson.fromJson(String.valueOf(response),Player.class);


                        Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                        //String name =  "JEREMY";
                        //String email = "toto@mail.com";
                        //int bestScore = 2;
                        //int totalQuestions = 20;
                        //int totalPoints = 15;
                        gameActivityIntent.putExtra("name_key", mPlayer.getName());
                        gameActivityIntent.putExtra("bestScore_key", mPlayer.getBestScore());
                        gameActivityIntent.putExtra("totalQuestions_key", mPlayer.getTotalQuestions());
                        gameActivityIntent.putExtra("totalPoints_key", mPlayer.getTotalPoints());
                        gameActivityIntent.putExtra("ID_key", mPlayer.getId());
                        startActivity(gameActivityIntent);
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