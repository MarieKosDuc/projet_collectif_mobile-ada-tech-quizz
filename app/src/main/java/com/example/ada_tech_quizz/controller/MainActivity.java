package com.example.ada_tech_quizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText, mPasswordEditText;
    private Button mPlayButton, mSignUpButton;

    // variables for Volley library
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.7.136:8085/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = findViewById(R.id.main_edittext_email);
        mPasswordEditText = findViewById(R.id.main_edittext_password);
        mPlayButton = findViewById(R.id.main_button_play);
        mSignUpButton = findViewById(R.id.main_button_sign_up);

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
                checkDataEntered();
            }
        });

        Player mPlayer = new Player();

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

        try {
            // RequestQueue initialized
            mRequestQueue = Volley.newRequestQueue(this);

            // Creating the JSON object that will be sent to the API
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", mEmailEditText.getText().toString());
            jsonBody.put("password", mPasswordEditText.getText().toString());
            final String requestBody = jsonBody.toString();

            // String Request initialized
            mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                        // can get more details such as response.headers
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            mRequestQueue.add(mStringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}