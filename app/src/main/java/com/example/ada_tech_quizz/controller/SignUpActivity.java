package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ada_tech_quizz.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameEditText, mEmailEditText, mPasswordEditText1, mPasswordEditText2;
    private TextView mErrorPaswword;
    private Button mOKButton;

    private int mFieldsCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameEditText = findViewById(R.id.signup_edittext_name);
        mEmailEditText = findViewById(R.id.signup_edittext_email);
        mPasswordEditText1 = findViewById(R.id.signup_edittext_password1);
        mPasswordEditText2 = findViewById(R.id.signup_edittext_password2);
        mErrorPaswword = findViewById(R.id.signup_textview_password_error);


// A l'initialisation, le bouton OK est grisé
        mOKButton.setEnabled(false);

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


    // OK button actif seulement si tous les champs ont été remplis et si les deux MdP coïncident

        // vérification que le joueur a saisi son nom
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mFieldsCounter ++;

            }
        });

        // vérification que le joueur a saisi son email
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mFieldsCounter ++;

            }
        });

        // vérification que le joueur a saisi son mdp
        mPasswordEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mFieldsCounter ++;

            }
        });

        // vérification que le joueur a saisi une deuxième fois son MdP et qu'ils coïncident
        mPasswordEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Vérification de la concordance des deux MdP
                String password1 = mPasswordEditText1.getText().toString();
                String password2 = mPasswordEditText2.getText().toString();

                mFieldsCounter ++;

                // si tous les champs sont remplis et que les MdP coïncident, OK button actif
                if(mFieldsCounter == 4 && password1.equals(password2)){
                    mOKButton.setEnabled(true);
                }

            }
        });






    }
}