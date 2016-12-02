package com.vrm.invercasa;

import android.app.Application;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vrm.invercasa.model.UserEntity;
import com.vrm.invercasa.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{
    private EditText txtEmail, txtPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnLogin, btnSignup;
    private TextView lblForgot;
    private PrefManager prefManager;
    private UserEntity user;
    InvercasaApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        application = (InvercasaApplication) getApplication();
        prefManager = new PrefManager(this);
        user = application.getUserRepository().getUserById(prefManager.getLoggedUser());
        ui();
        if (user != null)
            login();
    }

    private void ui() {
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        btnLogin = (Button) findViewById(R.id.buttonSignIn);
        btnSignup = (Button) findViewById(R.id.buttonSignUp);
        lblForgot = (TextView) findViewById(R.id.textViewForgotPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
                    validateLogin();
                return false;
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        lblForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private boolean validateUser(String email, String password) {
        clear();
        user = application.getUserRepository().getUserByEmail(email);
        if (user == null) {
            inputLayoutEmail.setError(getString(R.string.error_incorrect_email));
            txtEmail.requestFocus();
            return false;
        }
        if (password.equals("")) {
            txtPassword.requestFocus();
            return false;
        }
        if (!password.equals(user.getPassword())) {
            inputLayoutPassword.setError(getString(R.string.error_incorrect_password));
            txtPassword.setText("");
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void login() {
        prefManager.setLoggedUser(user.getId());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", user);;
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void validateLogin() {
        if (validateUser(txtEmail.getText().toString(), txtPassword.getText().toString()))
            login();
    }

    private void clear() {
        inputLayoutEmail.setError(null);
        inputLayoutPassword.setError(null);
    }
}

