package com.eric.callintercept.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_PHONE_NUMBER = "create table phone_number(" +
            "id INTEGER primary key AUTOINCREMENT," +
            "phone_number char(50)," +
            "type char(50));";

    public static final String CREATE_CALL_INTERCEPT_RECORDS = "create table cell_intercept_records(" +
            "id INTEGER primary key AUTOINCREMENT," +
            "gmt_create INTEGER,"+
            "phone_number char(50));";

    public static final String CREATE_MESSAGE_INTERCEPT_RECORDS = "create table message_intercept_records(" +
            "id INTEGER primary key AUTOINCREMENT," +
            "gmt_create INTEGER,"+
            "phone_number char(50)," +
            "content text);";

    public OpenHelper(Context context, String dbName) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PHONE_NUMBER);
        db.execSQL(CREATE_CALL_INTERCEPT_RECORDS);
        db.execSQL(CREATE_MESSAGE_INTERCEPT_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
