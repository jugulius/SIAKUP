package com.erwintobing15.siakup.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.authentication.LoginActivity;
import com.erwintobing15.siakup.database.DBManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cv_krs;
    CardView cv_khs;
    CardView cv_jadwal;
    CardView cv_kontak;
    CardView cv_permission;
    CardView cv_logout;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
        initDB();

    }

    private void initViews() {
        cv_krs = findViewById(R.id.cv_krs);
        cv_khs = findViewById(R.id.cv_khs);
        cv_jadwal = findViewById(R.id.cv_jadwal);
        cv_kontak = findViewById(R.id.cv_kontak);
        cv_permission = findViewById(R.id.cv_permission);
        cv_logout = findViewById(R.id.cv_logout);
    }

    private void initListener() {
        cv_krs.setOnClickListener(this);
        cv_khs.setOnClickListener(this);
        cv_jadwal.setOnClickListener(this);
        cv_kontak.setOnClickListener(this);
        cv_permission.setOnClickListener(this);
        cv_logout.setOnClickListener((View.OnClickListener) this);

    }

    private void initDB() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cv_krs:
                Intent cvkrs = new Intent(MainActivity.this, KrsActivity.class);
                startActivity(cvkrs);
                break;

            case R.id.cv_khs:
                Intent cvkhs = new Intent(MainActivity.this, KhsActivity.class);
                startActivity(cvkhs);
                break;

            case R.id.cv_jadwal:
                Intent cvjadwal = new Intent (MainActivity.this, JadwalActivity.class);
                startActivity(cvjadwal);
                break;

            case R.id.cv_kontak:
                Intent cvkontak = new Intent (MainActivity.this, KontakActivity.class);
                startActivity(cvkontak);
                break;

            case R.id.cv_permission:
                Intent cvinfo = new Intent (MainActivity.this, PermissionActivity.class);
                startActivity(cvinfo);
                break;

            case R.id.cv_logout:
                dbManager.logout(MainActivity.this);
                Intent cvlogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(cvlogout);
                dbManager.close();
                finish();
                break;

        }
    }
}