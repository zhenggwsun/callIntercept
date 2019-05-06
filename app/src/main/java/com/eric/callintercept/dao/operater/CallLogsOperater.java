package com.eric.callintercept.dao.operater;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.eric.callintercept.dao.object.CallLogModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogsOperater {

    private static final String[] columns = {
            CallLog.Calls.CACHED_NAME,  //联系人
            CallLog.Calls.NUMBER,   //号码
            CallLog.Calls.DATE,     //时间
            CallLog.Calls.DURATION, //通讯时长
            CallLog.Calls.TYPE      //类型
    };

    public static List<CallLogModel> read(Context context){
        List<CallLogModel> callLogModels = new ArrayList<CallLogModel>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, columns, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            Long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            Integer duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            Integer type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

            CallLogModel callLogModel = new CallLogModel(name, number, new Date(date), duration, type);
            callLogModels.add(callLogModel);
        }
        return callLogModels;
    }
}
