package com.erwintobing15.siakup.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.erwintobing15.siakup.database.DatabaseHelper.TABLE_USER;

public class DBManager {

    public static final String KEY_USER_SESSION = "key-user-session";
    public static final String USER_SESSION = "user-session";

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertUser(String npm, String password) {

        ContentValues contentValuesUser = new ContentValues();
        contentValuesUser.put(DatabaseHelper.NPM, npm);
        contentValuesUser.put(DatabaseHelper.PASSWORD, password);
        database.insert(TABLE_USER, null, contentValuesUser);
    }

    public boolean validateUser(String npm, String password) {

        String[] columns = { DatabaseHelper._ID };
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseHelper.NPM + " = ?" + " AND " + DatabaseHelper.PASSWORD + " = ?";

        String[] selectionArgs = {npm, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean login(Context context, String session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                KEY_USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_SESSION, session);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                KEY_USER_SESSION, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(USER_SESSION, null);
        if (userJson != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                KEY_USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        return true;
    }

    public Cursor allKrs() {
        String[] columns = new String[] { DatabaseHelper.ID_KRS, DatabaseHelper.MK, DatabaseHelper.SKS,
                DatabaseHelper.KELAS};
        Cursor cursor = database.query(DatabaseHelper.TABLE_KRS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertKrs(String mk, String sks, String kelas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MK, mk);
        contentValues.put(DatabaseHelper.SKS, sks);
        contentValues.put(DatabaseHelper.KELAS, kelas);
        database.insert(DatabaseHelper.TABLE_KRS, null, contentValues);
    }

    public int updateKrs(long id_krs, String mk, String sks, String kelas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MK, mk);
        contentValues.put(DatabaseHelper.SKS, sks);
        contentValues.put(DatabaseHelper.KELAS, kelas);
        int i = database.update(DatabaseHelper.TABLE_KRS, contentValues, DatabaseHelper.ID_KRS + " = " + id_krs, null);
        return i;
    }

    public void deleteKrs(long id_krs) {
        database.delete(DatabaseHelper.TABLE_KRS, DatabaseHelper.ID_KRS + "=" + id_krs, null);
    }

    public int countKrsData() {
        int total = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.ID_KRS + ") as Total FROM " + DatabaseHelper.TABLE_KRS, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

}
