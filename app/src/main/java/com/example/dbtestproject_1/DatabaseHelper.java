package com.example.dbtestproject_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 변수 값 선언
    public static String dbName = "username";
    public  static int version = 1;

    public void println(String data) {
        Log.d("DatabaseHelper", data);
    }

    public DatabaseHelper(Context context) {
        super(context, dbName, null, version); // null --> cursorFactory object
    }

    public void onCreate(SQLiteDatabase db) {
        println("Oncreate called.");

        String sql = "create table if not exists emp("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + "mobileNum text)";

        db.execSQL(sql); // onCreate 메서드 안에서 sql 실행.
    }

    public void onOpen(SQLiteDatabase db) {
        println("onOpen called.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        println("onUpgrade called. : " + oldVersion + newVersion);
        if(newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

}
