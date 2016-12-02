package com.vrm.invercasa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView txtVersion = (TextView) findViewById(R.id.textViewVersion);
        txtVersion.setText(getString(R.string.version) + BuildConfig.VERSION_NAME);
    }
}
