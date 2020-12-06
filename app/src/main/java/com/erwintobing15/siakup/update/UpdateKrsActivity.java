package com.erwintobing15.siakup.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.database.DBManager;
import com.erwintobing15.siakup.ui.KrsActivity;

public class UpdateKrsActivity extends Activity implements View.OnClickListener{

    EditText mkEditText;
    EditText sksEditText;
    EditText kelasEditText;
    private long _id;

    private Button updateBtn;
    private Button delBtn;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Update KRS");
        setContentView(R.layout.activity_update_krs);

        initViews();
        initListeners();
        initDB();
        initExtra();
    }

    private void initViews() {
        mkEditText = (EditText) findViewById(R.id.et_update_mk);
        sksEditText = (EditText) findViewById(R.id.et_update_sks);
        kelasEditText = (EditText) findViewById(R.id.et_update_kelas);
        updateBtn = (Button) findViewById(R.id.btn_update_krs);
        delBtn = (Button) findViewById(R.id.btn_delete_krs);
    }

    private void initListeners() {
        updateBtn.setOnClickListener((View.OnClickListener) this);
        delBtn.setOnClickListener((View.OnClickListener) this);
    }

    private void initDB() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    private void initExtra() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String mk = intent.getStringExtra("mk");
        String sks = intent.getStringExtra("sks");
        String kelas = intent.getStringExtra("kelas");

        assert id != null;
        _id = Long.parseLong(id);

        mkEditText.setText(mk);
        sksEditText.setText(sks);
        kelasEditText.setText(kelas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_krs:
                String mk = mkEditText.getText().toString();
                String sks = sksEditText.getText().toString();
                String kelas = kelasEditText.getText().toString();

                if (mk.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan mata kuliah", Toast.LENGTH_SHORT).show();
                } else if (sks.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan sks", Toast.LENGTH_SHORT).show();
                } else if (kelas.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Isikan kelas", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.updateKrs(_id, mk, sks, kelas);
                    this.returnHome();
                }
                break;
            case R.id.btn_delete_krs:
                dbManager.deleteKrs(_id);
                returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), KrsActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}