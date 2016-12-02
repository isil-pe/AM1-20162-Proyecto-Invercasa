package com.vrm.invercasa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vrm.invercasa.model.UserEntity;
import com.vrm.invercasa.utils.StringUtils;

public class SignUpActivity extends AppCompatActivity {
    private UserEntity user;
    private Toolbar toolbar;
    private EditText txtDNI, txtName, txtLastName, txtAddress, txtPhone, txtEmail, txtPassword, txtPasswordConfirm;
    private CheckBox chkAgree;
    private TextView lblTerms;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ui();
    }

    private void ui(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtDNI = (EditText) findViewById(R.id.editTextDNI);
        txtName = (EditText) findViewById(R.id.editTextName);
        txtLastName = (EditText) findViewById(R.id.editTextLastName);
        txtAddress = (EditText) findViewById(R.id.editTextAddress);
        txtPhone = (EditText) findViewById(R.id.editTextPhone);
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        chkAgree = (CheckBox) findViewById(R.id.checkboxAgree); 
        lblTerms = (TextView) findViewById(R.id.textViewTerms);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lblTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, TermsActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    InvercasaApplication application = (InvercasaApplication)getApplication();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    if (application.getUserRepository().add(user)) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("USER", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        txtEmail.setError(getString(R.string.error_email_already_exist));
                        txtEmail.requestFocus();
                    }
                }
            }
        });
    }

    private boolean validate() {
        clear();
        boolean rpta = true;
        user = null;
        String dni = txtDNI.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String lastName = txtLastName.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String password2 = txtPasswordConfirm.getText().toString().trim();
        if (dni.length() < 8) {
            txtDNI.setError(getString(R.string.error_short_dni));
            txtDNI.requestFocus();
            rpta =  false;
        }
        if (!StringUtils.isEmailValid(email)) {
            txtEmail.setError(getString(R.string.error_invalid_email));
            txtEmail.requestFocus();
            rpta = false;
        }
        if (password.length() < 6) {
            txtPassword.setError(getString(R.string.error_short_password));
            txtPassword.requestFocus();
            rpta =  false;
        }
        if (!password.equals(password2)) {
            txtPasswordConfirm.setError(getString(R.string.error_password_match));
            txtPasswordConfirm.requestFocus();
            rpta =  false;
        }
        if (password2.equals("")) {
            txtPasswordConfirm.setError(getString(R.string.error_field_required));
            txtPasswordConfirm.requestFocus();
            rpta = false;
        }
        if (password.equals("")) {
            txtPassword.setError(getString(R.string.error_field_required));
            txtPassword.requestFocus();
            rpta = false;
        }
        if (email.equals("")) {
            txtEmail.setError(getString(R.string.error_field_required));
            txtEmail.requestFocus();
            rpta = false;
        }
        if (lastName.equals("")) {
            txtLastName.setError(getString(R.string.error_field_required));
            txtLastName.requestFocus();
            rpta = false;
        }
        if (name.equals("")) {
            txtName.setError(getString(R.string.error_field_required));
            txtName.requestFocus();
            rpta = false;
        }
        if (dni.equals("")) {
            txtDNI.setError(getString(R.string.error_field_required));
            txtDNI.requestFocus();
            rpta = false;
        }
        if (!chkAgree.isChecked() && rpta) {
            Toast.makeText(this, getString(R.string.error_terms_agree), Toast.LENGTH_SHORT).show();
            rpta = false;
        }
        if (rpta)
            user = new UserEntity(dni, name, lastName, address, phone, email, password);
        return rpta;
    }

    private void clear() {
        txtDNI.setError(null);
        txtName.setError(null);
        txtLastName.setError(null);
        txtAddress.setError(null);
        txtPhone.setError(null);
        txtEmail.setError(null);
        txtPassword.setError(null);
        txtPasswordConfirm.setError(null);
    }
}
