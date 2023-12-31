package com.example.dbtestproject_1;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 변수 선언
    EditText editText1, editText2;
    Button button1, button2, button3;
    TextView textView;

    SQLiteDatabase database;
    String tableName;

    DatabaseHelper dbHelper; // DatabaseHelper 클래스 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 변수 값 선언
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

//        button1.setOnClickListener(new View.OnClickListener() { 람다식 사용 전의 기존 방식
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        // Button setOnclickListener
        button1.setOnClickListener(view -> {
            String databaseName = editText1.getText().toString();
            createDatabase(databaseName);
        });

        button2.setOnClickListener(view -> {
            tableName = editText2.getText().toString();
            createTable(tableName);
            insertRecord();
        });

        button3.setOnClickListener(view -> {
            executeQuery();
        });

    }

//    private void createDatabase(String dbname) { // DB 생성 메소드 1 -> 별도 클래스 생성 없이, 바로 DB 생성하는 메소드
//        println("createDatabase 호출됨!");
//        database = openOrCreateDatabase(dbname, MODE_PRIVATE, null);
//        println("DB 생성함 : " + dbname);
//    }

    private void createDatabase(String name) { // DB 생성 메소드 2 -> DatabaseHelper 클래스를 생성해 사용하여 DB 생성하는 메소드
        println("createDatabase called.");
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    private void createTable(String name) { // 테이블 생성 메소드
        println("createTable called.");
        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }
        database.execSQL("create table if not exists " + name + "("
                // createTalbe 메서드에서 호출한 execSQL 메서드는 SQL 문을 파라미터 형식으로 전달받아 원하는 기능의 SQL을 정의해 사용할 수 있다.
                // 만들 테이블 : 직원 테이블 -> 직원 id, 이름, 나이, 폰 번호 등을 칼럼으로 정의함.
                // id 앞에 _를 붙여서 _id 로 하는 것 -> 안드로이드에서 권장하는 칼럼 정의 방법.
                + " _id integer PRIMARY KEY autoincrement, " // PRIMARY KEY autoincrement 의미 => 자동으로 1씩 증가하는(autoincrement) + 키 값(PRIMARY KEY)
                + " name text, "
                + " age integer, "
                + " mobileNum text)"  );

        println("테이블 생성함 : " + name);

    }

    private void insertRecord() { // 레코드 추가 메소드
        println("insertRecord called.");
        if(database == null) {
            println("DB를 먼저 생성해주세요.");
            return;
        }
        if(tableName == null) {
            println("Table 생성을 하셔야 합니다.");
            return;
        }

        database.execSQL("insert into " + tableName
                + "(name, age, mobileNum) "
                + " values "
                + "('Dawon', 18, '010-1234-5678')");

        println("레코드 추가함");

    }

    private void executeQuery() {
        println("executeQuery called.");

        // cursor 객체 : 테이블에 들어있는 각각의 레코드를 순서대로 접근할 수 있도록 해줌.
        Cursor cursor = database.rawQuery("select _id, name, age, mobileNum from emp", null); // sql select문 실행해서 rawQuery 메소드를 사용해 cursor 객체 반환받음.
        int recordCount = cursor.getCount(); // 레코드 개수 세기.
        println("레코드 개수 : " + recordCount);

        for(int i = 0;i < recordCount;i++) {
            cursor.moveToNext();
            // 순서대로 접근하여 변수 값을 지정함.
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobileNum = cursor.getString(3);
            // 해당 레코드 값 출력.
            println("레코드 [" + i + "] : " + id + "," + name + "," + age + "," + mobileNum);
        }
        cursor.close();
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

}
