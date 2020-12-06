package com.erwintobing15.siakup.insert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.database.DBManager;
import com.erwintobing15.siakup.ui.KrsActivity;

public class InsertKrsActivity extends Activity implements View.OnClickListener {

    private EditText mkEditText;
    private EditText sksEditText;
    private EditText kelasEditText;

    private DBManager dbManager;
    private Button addKrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_krs);

        initViews();
        initListeners();
        initDB();
    }

    private void initViews() {
        mkEditText = (EditText) findViewById(R.id.mk_edittext);
        sksEditText = (EditText) findViewById(R.id.sks_edittext);
        kelasEditText = (EditText) findViewById(R.id.kelas_edittext);
        addKrs = (Button) findViewById(R.id.btn_add_krs);
    }

    private void initListeners() {
        addKrs.setOnClickListener(this);
    }

    private void initDB() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_krs:
                insertKrs();
                break;
        }
    }

    private void insertKrs() {
        final String mk = mkEditText.getText().toString();
        final String sks = sksEditText.getText().toString();
        final String kelas = kelasEditText.getText().toString();

        if (mk.isEmpty()) {
            Toast.makeText(getBaseContext(), "Isikan mata kuliah", Toast.LENGTH_SHORT).show();
        } else if (sks.isEmpty()) {
            Toast.makeText(getBaseContext(), "Isikan sks", Toast.LENGTH_SHORT).show();
        } else if (kelas.isEmpty()) {
            Toast.makeText(getBaseContext(), "Isikan kelas", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insertKrs(mk, sks, kelas);
            Intent main = new Intent(InsertKrsActivity.this, KrsActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
    }
}