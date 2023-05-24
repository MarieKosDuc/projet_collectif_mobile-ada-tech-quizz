package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ada_tech_quizz.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameEditText, mEmailEditText, mPasswordEditText1, mPasswordEditText2;
    private TextView mErrorText;
    private Button mOKButton;

    public String mName, mEmail, mPassword1, mPassword2;

    private int mFieldsCounter = 0;

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

        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered()){
                    Toast.makeText(getApplicationContext(), "All ok :" + checkDataEntered(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

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
}