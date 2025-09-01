package com.example.nodepos.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.nodepos.model.absenModel;
import com.example.nodepos.model.loginModel;
import com.example.nodepos.model.userModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nodepos";
    private static final int DATABASE_VERSION = 3;

    // Nama tabel
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_USER = "user";
    private static final String TABLE_ABSEN = "absen";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Buat tabel saat pertama kali
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabel Login
        db.execSQL("CREATE TABLE " + TABLE_LOGIN + " (" +
                "status TEXT," +
                "reseponseMessage TEXT," +
                "reseponseCode TEXT" +
                ")");

        // Tabel User
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                "data TEXT," +
                "uuid TEXT PRIMARY KEY," +
                "nik TEXT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "fullName TEXT," +
                "email TEXT," +
                "password TEXT," +
                "position TEXT," +
                "role TEXT," +
                "isActive TEXT" +
                ")");

        // Tabel Absen
        db.execSQL("CREATE TABLE " + TABLE_ABSEN + " (" +
                "idAbsen TEXT," +
                "jenisAbsen TEXT," +
                "tanggalAbsen TEXT," +
                "waktuAbsen TEXT" +
                ")");
    }

    // Upgrade jika versi DB berubah
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ABSEN);
        onCreate(db);
    }

    // Simpan loginModel
    public void insertLogin(loginModel login) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", login.getStatus());
        values.put("reseponseMessage", login.getReseponseMessage());
        values.put("reseponseCode", login.getReseponseCode());
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    // Simpan userModel
    public void insertUser(userModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data", user.getData());
        values.put("uuid", user.getUuid());
        values.put("nik", user.getNik());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("fullName", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("position", user.getPosition());
        values.put("role", user.getRole());
        values.put("isActive", user.getIsActive());
        db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Ambil user berdasarkan UUID
    public userModel getUserByUuid(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "uuid = ?", new String[]{uuid},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userModel user = new userModel(
                    cursor.getString(cursor.getColumnIndexOrThrow("data")),
                    cursor.getString(cursor.getColumnIndexOrThrow("uuid")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nik")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fullName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("position")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role")),
                    cursor.getString(cursor.getColumnIndexOrThrow("isActive"))
            );
            cursor.close();
            return user;
        }

        return null;
    }

    public void insertAbsen(String jenis,String tanggal, String waktu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("jenisAbsen", jenis);
        values.put("tanggalAbsen", tanggal);
        values.put("waktuAbsen", waktu);
        db.insert(TABLE_ABSEN, null, values);
        db.close();
    }

    public List<absenModel> getAllLogs() {
        List<absenModel> logs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ABSEN, null, null, null, null, null, "idAbsen" + " DESC");
        if (cursor.moveToFirst()) {
            do {
                String jenis = cursor.getString(cursor.getColumnIndexOrThrow("jenisAbsen"));
                String tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggalAbsen"));
                String waktu = cursor.getString(cursor.getColumnIndexOrThrow("waktuAbsen"));
                logs.add(new absenModel(jenis, tanggal,waktu));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return logs;
    }

    // Hapus data login dan user (jika logout)
    public void clearAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_USER, null, null);
        db.close();
    }


}

