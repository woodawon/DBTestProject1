package com.example.dbtestproject_1;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    Button button1;
    Button button2;
    TextView textView;

    SQLiteDatabase database;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String databaseName = editText1.getText().toString();
                createDatabase(databaseName);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName = editText2.getText().toString();
                createTable(tableName);
            }
        });

    }

    private void createDatabase(String name) { // DB 생성 메소드
        println("createDatabase 호출됨!");
        database = openOrCreateDatabase(name, MODE_PRIVATE, null);
        println("DB 생성함 : " + name);
    }

    private void createTable(String name) { // 테이블 생성 메소드
        println("createTable 호출됨!");
        if(database != null) {
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
        println("insertRecord 호출됨!");
        if(database == null) {
            println("DB를 먼저 생성해주세요.");
            return;
        }
        if(tableName == null) {
            println("Table을 생성하셔야 합니다.");
            return;
        }

        database.execSQL("insert into" + tableName
                + "(name, age, mobile) "
                + "values "
                + "('Dawon', 18, '010-1234-5678')");

        println("레코드 추가함");

    }

}
