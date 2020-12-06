package com.erwintobing15.siakup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.erwintobing15.siakup.R;
import com.erwintobing15.siakup.database.DBManager;
import com.erwintobing15.siakup.database.DatabaseHelper;
import com.erwintobing15.siakup.insert.InsertKrsActivity;
import com.erwintobing15.siakup.update.UpdateKrsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KrsActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_add_krs;
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    private TextView mkTextView;
    private TextView sksTextView;
    private TextView kelasTextView;

    final String[] from = new String[] { DatabaseHelper.ID_KRS, DatabaseHelper.MK,
            DatabaseHelper.SKS, DatabaseHelper.KELAS};

    final int[] to = new int[] {R.id.id_krs_body, R.id.tv_mk_body, R.id.tv_sks_body, R.id.tv_kelas_body};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs);

        initViews();
        initListeners();
        initDB();
        initAdapter();
    }

    private void initViews() {
        fab_add_krs = (FloatingActionButton) findViewById(R.id.fab_add_krs);
        listView = (ListView) findViewById(R.id.list_view_krs);
        mkTextView = (TextView) findViewById(R.id.tv_mk_header);
        sksTextView = (TextView) findViewById(R.id.tv_sks_header);
        kelasTextView = (TextView) findViewById(R.id.tv_kelas_header);
    }

    private void initListeners() {
        fab_add_krs.setOnClickListener((View.OnClickListener) this);
    }

    private void initDB() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    private void initAdapter() {
        listView.setEmptyView(findViewById(R.id.empty_krs));

        // hide unhide header
        int krs = dbManager.countKrsData();

        if (krs == 0) {
            mkTextView.setVisibility(View.GONE);
            sksTextView.setVisibility(View.GONE);
            kelasTextView.setVisibility(View.GONE);
        }
        else {
            mkTextView.setVisibility(View.VISIBLE);
            sksTextView.setVisibility(View.VISIBLE);
            kelasTextView.setVisibility(View.VISIBLE);
        }

        Cursor cursor = dbManager.allKrs();
        adapter = new SimpleCursorAdapter(this, R.layout.krs_body, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        // update and delete listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTextView = (TextView) view.findViewById(R.id.id_krs_body);
                TextView mkTextView = (TextView) view.findViewById(R.id.tv_mk_body);
                TextView sksTextView = (TextView) view.findViewById(R.id.tv_sks_body);
                TextView kelasTextView = (TextView) view.findViewById(R.id.tv_kelas_body);

                String _id = idTextView.getText().toString();
                String mk = mkTextView.getText().toString();
                String sks = sksTextView.getText().toString();
                String kelas = kelasTextView.getText().toString();

                Intent update_intent = new Intent(getApplicationContext(), UpdateKrsActivity.class);
                update_intent.putExtra("id", _id);
                update_intent.putExtra("mk", mk);
                update_intent.putExtra("sks", sks);
                update_intent.putExtra("kelas", kelas);
                startActivity(update_intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_krs:
                Intent addKrs = new Intent(KrsActivity.this, InsertKrsActivity.class);
                startActivity(addKrs);
                break;
        }
    }
}