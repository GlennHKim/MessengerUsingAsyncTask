package com.multicampus.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // 지정한 DB를 생성하거나 오픈한다.
        // context, DB 이름, 커스텀 커서(사용할 경우 지정, 표준 커서를 사용할 경우 null), 버전
        super(context, "todo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB가 생성될 경우 호출
        String createTable = "CREATE TABLE todo (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT," +
                "content TEXT," +
                "complete INTEGER DEFAULT 0," +
                "important INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전 변경시 마이그레션 기능 구현

    }
}
