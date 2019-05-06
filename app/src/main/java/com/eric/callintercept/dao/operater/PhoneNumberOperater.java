package com.eric.callintercept.dao.operater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eric.callintercept.dao.db.OpenHelper;
import com.eric.callintercept.dao.object.PhoneNumberDO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhoneNumberOperater {
    private static final String DB_NAME = "phone_number";
    public static final String BLACK_LIST = "BLACK_LIST";
    public static final String ALLOW_LIST = "ALLOW_LIST";

    private static OpenHelper openHelper;

    private static PhoneNumberOperater operater;

    private PhoneNumberOperater(Context context) {
        openHelper = new OpenHelper(context, DB_NAME);
    }

    /**
     * 单个写入
     * @param context
     * @param phoneNumberDO
     */
    public  static void write(Context context, PhoneNumberDO phoneNumberDO){
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }
        SQLiteDatabase database = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number",phoneNumberDO.getPhoneNumber());
        contentValues.put("type",phoneNumberDO.getType());
        database.insert(DB_NAME, null, contentValues);
        database.close();
    }

    /**
     * 批量写入
     * @param context
     * @param phoneNumberDOS
     */
    public static void write(Context context, List<PhoneNumberDO> phoneNumberDOS){
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }
        SQLiteDatabase database = openHelper.getWritableDatabase();

        Iterator<PhoneNumberDO> iterator = phoneNumberDOS.iterator();
        while(iterator.hasNext()){
            PhoneNumberDO phoneNumberDO = iterator.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone_number",phoneNumberDO.getPhoneNumber());
            contentValues.put("type",phoneNumberDO.getType());
            database.insert(DB_NAME, null, contentValues);
        }

        database.close();
    }

    /**
     * 读取数据库列表
     * @param context
     * @return
     */
    public static List<PhoneNumberDO> read(Context context, String type){

        ArrayList<PhoneNumberDO> phoneNumberDOS = new ArrayList<>();
        if (!BLACK_LIST.equals(type) && !ALLOW_LIST.equals(type)){
            type = BLACK_LIST;
        }
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }
        String[] strings = new String[1];
        strings[0] = type;
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(DB_NAME, null, "type=?", strings, null, null, "id");
        while (cursor.moveToNext()){
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
            String numberType = cursor.getString(cursor.getColumnIndex("type"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            PhoneNumberDO phoneNumberDO = new PhoneNumberDO(id, phoneNumber, numberType);
            phoneNumberDOS.add(phoneNumberDO);
        }
        database.close();
        return phoneNumberDOS;
    }

    /**
     * 根据phone_number 查询
     * @param context
     * @param phoneNumber
     * @return
     */
    public static PhoneNumberDO read(Context context, String phoneNumber, String type){
        PhoneNumberDO phoneNumberDO= null;
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }
        if (!BLACK_LIST.equals(type) && !ALLOW_LIST.equals(type)){
            type = BLACK_LIST;
        }
        String[] strings = new String[2];
        strings[0] = phoneNumber;
        strings[1] = type;
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(DB_NAME, null, "phone_number=? and type=?", strings, null, null, null);
        while (cursor.moveToNext()){
            String number = cursor.getString(cursor.getColumnIndex("phone_number"));
            String numberType = cursor.getString(cursor.getColumnIndex("type"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            phoneNumberDO = new PhoneNumberDO(id,number, numberType);
        }
        database.close();
        return phoneNumberDO;
    }

    /**
     * 删除数据
     * @param context
     * @param type
     */
    public static void delete(Context context, String type){
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }
        if (!BLACK_LIST.equals(type) && !ALLOW_LIST.equals(type)){
            type = BLACK_LIST;
        }
        String[] strings = new String[1];
        strings[0] = type;
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DB_NAME, "type=?", strings);
        database.close();
    }

    /**
     * 删除数据
     * @param context
     * @param id
     */
    public static void delete(Context context, Integer id){
        if (operater == null){
            operater = new PhoneNumberOperater(context);
        }

        String[] strings = new String[1];
        strings[0] = id.toString();
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DB_NAME, "id=?", strings);
        database.close();
    }
}
