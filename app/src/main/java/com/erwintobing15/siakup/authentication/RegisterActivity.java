package com.erwintobing15.siakup.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.database.DBManager;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private DBManager dbManager;
    private Button addUser;

    private EditText npmEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListener();

        dbManager = new DBManager(this);
        dbManager.open();
    }

    private void initViews() {
        npmEditText = (EditText) findViewById(R.id.et_npm);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        addUser = (Button) findViewById(R.id.register);
    }

    private void initListener() {
        addUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                final String npm = npmEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                dbManager.insertUser(npm, password);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;

        }
    }
}