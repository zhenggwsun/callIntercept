package com.eric.callintercept.dao.operater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eric.callintercept.dao.db.OpenHelper;
import com.eric.callintercept.dao.object.CallInterceptRecordDO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CellInterceptOperater {

    private static final String DB_NAME = "cell_intercept_records";

    private static OpenHelper openHelper;

    private static CellInterceptOperater operater;

    private CellInterceptOperater(Context context) {
        openHelper = new OpenHelper(context, DB_NAME);
    }

    /**
     * 单个写入
     * @param context
     * @param recordDO
     */
    public  static void write(Context context, CallInterceptRecordDO recordDO){
        if (operater == null){
            operater = new CellInterceptOperater(context);
        }
        SQLiteDatabase database = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number",recordDO.getPhoneNumber());
        contentValues.put("gmt_create",recordDO.getGmtCreate().getTime());
        database.insert(DB_NAME, null, contentValues);
        database.close();
    }

    /**
     * 读取数据库列表
     * @param context
     * @return
     */
    public static List<CallInterceptRecordDO> read(Context context){

        ArrayList<CallInterceptRecordDO> recordDOS = new ArrayList<CallInterceptRecordDO>();

        if (operater == null){
            operater = new CellInterceptOperater(context);
        }

        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(DB_NAME, null, null, null, null, null, "id desc");
        while (cursor.moveToNext()){
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
            long gmt_create = cursor.getLong(cursor.getColumnIndex("gmt_create"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            CallInterceptRecordDO recordDO = new CallInterceptRecordDO(id, phoneNumber, new Date(gmt_create));
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
            operater = new CellInterceptOperater(context);
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
            operater = new CellInterceptOperater(context);
        }

        String[] strings = new String[1];
        strings[0] = id.toString();
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DB_NAME, "id=?", strings);
        database.close();
    }
}
