package com.vrm.invercasa;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.vrm.invercasa.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{
    private EditText txtEmail, txtPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnLogin, btnSignup;
    private TextView lblForgot;
    private CheckBox chkRemember;
    private String email, password;
    private List<UserEntity> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ui();
    }

    private void ui() {
        users = ((InvercasaApplication)getApplication()).getUserRepository().list();
        txtEmail = (EditText)findViewById(R.id.editTextEmail);
        txtPassword = (EditText)findViewById(R.id.editTextPassword);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.inputLayoutEmail);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.inputLayoutPassword);
        chkRemember = (CheckBox)findViewById(R.id.checkboxRemember);
        btnLogin = (Button)findViewById(R.id.buttonSignIn);
        btnSignup = (Button)findViewById(R.id.buttonSignUp);
        lblForgot = (TextView)findViewById(R.id.textViewForgotPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
                    login();
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
        this.password = txtPassword.getText().toString().trim();
        if (!email.equals(this.email)) {
            inputLayoutEmail.setError(getString(R.string.error_incorrect_email));
            txtEmail.requestFocus();
            return false;
        }
        if (this.password.equals("")) {
            txtPassword.requestFocus();
            return false;
        }
        if (!password.equals(this.password)) {
            inputLayoutPassword.setError(getString(R.string.error_incorrect_password));
            txtPassword.setText("");
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void login() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        for (Object user : users) {
            UserEntity temp = (UserEntity)user;
            email = txtEmail.getText().toString().trim();
            if (validateUser(temp.getEmail(), temp.getPassword())) {
                intent.putExtra("fullname", temp.getFullName());
                intent.putExtra("email", email);
                startActivity(intent);
                txtEmail.setText("");
                txtPassword.setText("");
            }
            if (temp.getEmail().equals(email))
                break;
        }
    }

    private void clear() {
        inputLayoutEmail.setError(null);
        inputLayoutPassword.setError(null);
    }
}

