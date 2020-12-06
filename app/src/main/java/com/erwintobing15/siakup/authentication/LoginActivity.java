package com.erwintobing15.siakup.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.database.DBManager;
import com.erwintobing15.siakup.ui.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText npm;
    public EditText pass;
    Button btnRegister;
    Button btnLogin;

    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListener();

        dbManager = new DBManager(this);
        dbManager.open();

        if (dbManager.isLoggedIn(this)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initViews() {
        npm = (EditText) findViewById(R.id.et_npm);
        pass = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void initListener() {
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void verifyFromSQLite() {

        if (dbManager.validateUser(npm.getText().toString().trim(),
                pass.getText().toString().trim())) {

                if (dbManager.login(LoginActivity.this, "12345678")) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Session", Toast.LENGTH_LONG).show();
                }

        } else {
            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                verifyFromSQLite();
                break;
            case R.id.btn_register:
                Intent regInt = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regInt);
                break;
        }
    }
}