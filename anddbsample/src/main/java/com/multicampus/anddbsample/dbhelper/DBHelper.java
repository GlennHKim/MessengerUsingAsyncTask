package com.multicampus.anddbsample.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.multicampus.anddbsample.vo.Contact;

/**
 * Created by student on 2016-06-28.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACT = "CREATE TABLE " + Contact.TABLE  + "("
                + Contact.KEY_ID + " INTEGAR PRIMARY KEY AUTOINCREMENT ,"
                + Contact.KEY_NAME + " TEXT, "
                + Contact.KEY_TELNUM + " TEXT, "
                + Contact.KEY_DESC + " DESC)";

        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE);

        onCreate(db);
    }
}
