package com.example.ada_tech_quizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ada_tech_quizz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameEditText, mEmailEditText, mPasswordEditText1, mPasswordEditText2;
    private TextView mErrorText;
    private Button mOKButton;

    // variables for Volley library
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.6.29:8085/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameEditText = findViewById(R.id.signup_edittext_name);
        mEmailEditText = findViewById(R.id.signup_edittext_email);
        mPasswordEditText1 = findViewById(R.id.signup_edittext_password1);
        mPasswordEditText2 = findViewById(R.id.signup_edittext_password2);
        mErrorText = findViewById(R.id.signup_textview_password_error);

        mOKButton = findViewById(R.id.signup_button_OK);


        // use Enter key on keyboard to use OK button
        mNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mOKButton.isEnabled()) {
                        mOKButton.performClick();
                        return true;
                    }
                }
                return false;
            }
        });

        // Onclick on OK button: send data to the API
        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered()){
                    try {
                        sendData();

                        Toast.makeText(SignUpActivity.this, "Ton compte a bien été créé!", Toast.LENGTH_SHORT).show();

                        // handler to generate a delay before action
                        Handler mHandler = new Handler();

                        // After 3 seconds: go back to main activity
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(mainActivityIntent);
                            }
                        }, 3000);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

//    Method to display an error message if a field is empty, email is not an email or if passwords don't match
    private boolean checkDataEntered(){
        boolean allFieldsOK = false;
        if (isEmpty(mNameEditText) || isEmpty(mEmailEditText) || isEmpty(mPasswordEditText1) || isEmpty(mPasswordEditText2)) {
            mErrorText.setText("Tous les champs sont obligatoires");
        } else if (!isEmail(mEmailEditText)) {
            mErrorText.setText("email invalide");
        } else if (!checkPasswords(mPasswordEditText1, mPasswordEditText2)){
            mErrorText.setText("Les deux mots de passe doivent être identiques");
        } else {
            allFieldsOK = true;
        }
    return allFieldsOK;
    }

    // checking if a field is empty
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    // checking if email is a valid email
    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // checking if both passwords are the same
    boolean checkPasswords(EditText pwd1, EditText pwd2){
        CharSequence password1 = pwd1.getText().toString();
        CharSequence password2 = pwd2.getText().toString();

        return(password1.equals(password2));
    }


    // Method to send data to API and DB
    private void sendData() throws JSONException {

        try {
            // RequestQueue initialized
            mRequestQueue = Volley.newRequestQueue(this);

            // Creating the JSON object that will be sent to the API
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", mNameEditText.getText().toString());
            jsonBody.put("email", mEmailEditText.getText().toString());
            jsonBody.put("password", mPasswordEditText1.getText().toString());
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
                        // if response == 200 OK, display OK message. Else, display error
                        if(response.statusCode == 200) {
                            // TOAST NOT WORKING !!!!!!!!!!!!!!
                            Toast.makeText(SignUpActivity.this, "Ton compte a bien été créé", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Erreur...", Toast.LENGTH_SHORT).show();
                        }
                        // can get more details such as response.headers

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            mRequestQueue.add(mStringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}