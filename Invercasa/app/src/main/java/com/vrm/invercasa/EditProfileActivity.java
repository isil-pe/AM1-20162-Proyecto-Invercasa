package com.vrm.invercasa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vrm.invercasa.model.UserEntity;

public class EditProfileActivity extends AppCompatActivity {
    private UserEntity user;
    private Toolbar toolbar;
    private TextView txtCaps;
    private EditText txtName, txtLastName, txtAddress, txtPhone;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user = (UserEntity) getIntent().getExtras().getSerializable("USER");
        ui();
    }

    protected void ui() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtCaps = (TextView) findViewById(R.id.textViewCaps);
        txtName = (EditText) findViewById(R.id.editTextName);
        txtLastName = (EditText) findViewById(R.id.editTextLastName);
        txtAddress = (EditText) findViewById(R.id.editTextAddress);
        txtPhone = (EditText) findViewById(R.id.editTextPhone);
        btnSave = (Button) findViewById(R.id.buttonSave);
        txtName.setText(user.getName());
        txtLastName.setText(user.getLastName());
        txtAddress.setText(user.getAddress());
        txtPhone.setText(user.getPhone());
        updateCaps();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    user.setName(txtName.getText().toString().trim());
                    user.setLastName(txtLastName.getText().toString().trim());
                    user.setAddress(txtAddress.getText().toString().trim());
                    user.setPhone(txtPhone.getText().toString().trim());
                    InvercasaApplication application = (InvercasaApplication) getApplication();
                    application.getUserRepository().update(user);
                    finish();
                }
            }
        });
        View.OnFocusChangeListener foco = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    updateCaps();
            }
        };
        txtName.setOnFocusChangeListener(foco);
        txtLastName.setOnFocusChangeListener(foco);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validate() {
        boolean rpta = true;
        if (txtLastName.getText().toString().trim().equals("")) {
            txtLastName.setError(getString(R.string.error_field_required));
            txtLastName.requestFocus();
            rpta = false;
        }
        if (txtName.getText().toString().trim().equals("")) {
            txtName.setError(getString(R.string.error_field_required));
            txtName.requestFocus();
            rpta = false;
        }
        return rpta;
    }

    private void updateCaps() {
        txtCaps.setText("" + txtName.getText().charAt(0) + txtLastName.getText().charAt(0));
    }
}
