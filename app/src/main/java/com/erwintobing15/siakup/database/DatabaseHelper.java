package com.erwintobing15.siakup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // name tables
    public static final String TABLE_USER = "USER";
    public static final String TABLE_KRS = "KRS";

    // column table TABLE_USER
    public static final String _ID = "_id";
    public static final String NPM = "npm";
    public static final String PASSWORD = "password";

    // column table TABLE_KRS
    public static final String ID_KRS = "_id";
    public static final String MK = "mk";
    public static final String SKS = "sks";
    public static final String KELAS = "kelas";

    // name database
    static final String DB_NAME = "SIAKUP.DB";

    // database version
    static final int DB_VERSION = 1;

    // make query for table TABLE_USER
    private static final String CREATE_TABLE_USER = "create table " + TABLE_USER + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NPM + " TEXT, " +
            PASSWORD + " TEXT " +
            ");";

    // make query for table TABLE_KRS
    private static final String CREATE_TABLE_KRS = "create table " + TABLE_KRS + "(" +
            ID_KRS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MK + " TEXT, " +
            SKS + " TEXT, " +
            KELAS + " TEXT " +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_KRS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KRS);
        onCreate(db);
    }
}
