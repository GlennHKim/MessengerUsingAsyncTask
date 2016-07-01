package com.multicampus.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler(Context content){
        this.helper = new DBHelper(content);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context context){
        DBHandler handler = new DBHandler(context);
        return handler;
    }

    public void close(){
        helper.close();
    }

    public Cursor selectAll(){
        ArrayList<TodoItem> list = new ArrayList<>();
        String query = "SELECT * FROM todo ORDER BY _id DESC";
        // query 실행(query, query에 ?가 포함될 경우 바인딩할 값들을 담은 String[])
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void insert(TodoItem item){
        Log.d(this.getClass().getSimpleName(), item.toString());
        String sql = "INSERT INTO todo(title, content, complete, important) VALUES(?, ?, ?, ?)";
        // DML 실행
        db.execSQL(sql, new String[]{
                item.getTitle(),
                item.getContent(),
                (item.isComplete()?"1":"0"),
                (item.isImportant()?"1":"0")
        });
    }

    public void updateComplete(int todoId, boolean complete){
        Log.d(this.getClass().getSimpleName(), todoId + ", " + complete);
        String sql = "UPDATE todo SET complete=? WHERE _id=?";
        db.execSQL(sql, new String[]{
                (complete?"1":"0"),
                todoId+""
        });
    }

    public void delete(int todoId){
        Log.d(this.getClass().getSimpleName(), todoId + "");
        String sql = "DELETE FROM todo WHERE _id=" + todoId;
        db.execSQL(sql);
    }

    public void update(TodoItem item){
        Log.d(this.getClass().getSimpleName(), item.toString());
        String sql = "UPDATE todo SET content=?, important=? WHERE _id=?";
        db.execSQL(sql, new String[]{
                item.getContent(),
                (item.isImportant()?"1":"0"),
                item.getTodoId()+""
        });
    }

}
