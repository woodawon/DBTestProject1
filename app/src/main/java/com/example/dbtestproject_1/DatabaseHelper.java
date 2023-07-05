package com.example.dbtestproject_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 변수 값 선언
    public static String dbName = "username.db";
    public  static int version = 1;

    public void println(String data) {
        Log.d("DatabaseHelper", data);
    }

    public DatabaseHelper(Context context) {
        super(context, dbName, null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        println("Oncreate called.");


    }

    public void onOpen(SQLiteDatabase db) {

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
