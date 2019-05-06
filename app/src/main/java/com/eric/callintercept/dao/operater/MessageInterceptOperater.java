package com.eric.callintercept.dao.operater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eric.callintercept.dao.db.OpenHelper;
import com.eric.callintercept.dao.object.MessageInterceptRecordDO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MessageInterceptOperater {

    private static final String DB_NAME = "message_intercept_records";

    private static OpenHelper openHelper;

    private static MessageInterceptOperater operater;

    private MessageInterceptOperater(Context context) {
        openHelper = new OpenHelper(context, DB_NAME);
    }

    /**
     * 单个写入
     * @param context
     * @param recordDO
     */
    public  static void write(Context context, MessageInterceptRecordDO recordDO){
        if (operater == null){
            operater = new MessageInterceptOperater(context);
        }
        SQLiteDatabase database = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number",recordDO.getPhoneNumber());
        contentValues.put("gmt_create",recordDO.getGmtCreate().getTime());
        contentValues.put("content",recordDO.getContent());
        database.insert(DB_NAME, null, contentValues);
        database.close();
    }

    /**
     * 读取数据库列表
     * @param context
     * @return
     */
    public static List<MessageInterceptRecordDO> read(Context context){

        ArrayList<MessageInterceptRecordDO> recordDOS = new ArrayList<MessageInterceptRecordDO>();

        if (operater == null){
            operater = new MessageInterceptOperater(context);
        }

        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(DB_NAME, null, null, null, null, null, "id");
        while (cursor.moveToNext()){
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
            long gmt_create = cursor.getLong(cursor.getColumnIndex("gmt_create"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            MessageInterceptRecordDO recordDO = new MessageInterceptRecordDO(phoneNumber, new Date(gmt_create), content);
            recordDOS.add(recordDO);
        }
        database.close();
        return recordDOS;
    }

    /**
     * 删除数据（清空）
     * @param context
     */
    public static void delete(Context context){
        if (operater == null){
            operater = new MessageInterceptOperater(context);
        }

        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DB_NAME, null, null);
        database.close();
    }

    /**
     * 删除数据(根据id)
     * @param context
     * @param id
     */
    public static void delete(Context context, Integer id){
        if (operater == null){
            operater = new MessageInterceptOperater(context);
        }

        String[] strings = new String[1];
        strings[0] = id.toString();
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DB_NAME, "id=?", strings);
        database.close();
    }
}
