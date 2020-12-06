package com.erwintobing15.siakup.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.erwintobing15.siakup.R;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener{

    CardView cv_bluetooth;
    CardView cv_wifi;
    CardView cv_kamera;
    CardView cv_gps;
    CardView cv_contact;
    CardView cv_soundrec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        initViews();
        initListener();
    }

    private void initViews() {
        cv_bluetooth = findViewById(R.id.cv_bluetooth);
        cv_wifi = findViewById(R.id.cv_wifi);
        cv_kamera = findViewById(R.id.cv_kamera);
        cv_gps = findViewById(R.id.cv_gps);
        cv_contact = findViewById(R.id.cv_contact);
        cv_soundrec = findViewById(R.id.cv_soundrec);
    }

    private void initListener() {
        cv_bluetooth.setOnClickListener(this);
        cv_wifi.setOnClickListener(this);
        cv_kamera.setOnClickListener(this);
        cv_gps.setOnClickListener(this);
        cv_contact.setOnClickListener(this);
        cv_soundrec.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cv_bluetooth:

                break;

            case R.id.cv_wifi:

                break;

            case R.id.cv_kamera:

                break;

            case R.id.cv_gps:

                break;

            case R.id.cv_contact:

                break;

            case R.id.cv_soundrec:

                break;

        }
    }
}