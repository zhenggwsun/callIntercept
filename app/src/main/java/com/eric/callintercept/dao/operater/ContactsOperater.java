package com.eric.callintercept.dao.operater;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.eric.callintercept.dao.object.CallLogModel;
import com.eric.callintercept.dao.object.ContactModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsOperater {

    private static final String[] columns = {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,  //联系人
            ContactsContract.CommonDataKinds.Phone.NUMBER   //号码
    };

    public static List<ContactModel> read(Context context){
        List<ContactModel> contactModels = new ArrayList<ContactModel>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String realNumber = number.replace("-", "").replace(" ","");
            ContactModel callLogModel = new ContactModel(name, realNumber);
            contactModels.add(callLogModel);
        }
        return contactModels;
    }
}
