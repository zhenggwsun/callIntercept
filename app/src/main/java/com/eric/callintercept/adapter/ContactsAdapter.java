package com.eric.callintercept.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eric.callintercept.R;
import com.eric.callintercept.dao.object.CallLogModel;
import com.eric.callintercept.dao.object.ContactModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static android.provider.CallLog.Calls.INCOMING_TYPE;
import static android.provider.CallLog.Calls.OUTGOING_TYPE;

public class ContactsAdapter extends BaseAdapter {
    //操作checkBox选中列
    public static final HashMap<Integer, String> addMap = new HashMap<Integer, String>();

    //数据列
    private List<ContactModel> dataList;
    //自定义布局
    private LayoutInflater layoutInflater;

    public ContactsAdapter(List<ContactModel> dataList, LayoutInflater layoutInflater) {
        this.dataList = dataList;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewPhone = layoutInflater.inflate(R.layout.contacts_style, null);
        //控件
        ImageView contactPhoto = viewPhone.findViewById(R.id.contact_photo);
        TextView phoneNumber = viewPhone.findViewById(R.id.phone_number);
        TextView displayName = viewPhone.findViewById(R.id.display_name);
        CheckBox checkBox = viewPhone.findViewById(R.id.checkbox);
        //数据填充
        final ContactModel contactModel = dataList.get(position);
        contactPhoto.setImageResource(R.drawable.icon_contact_null);
        phoneNumber.setText(contactModel.getNumber());
        displayName.setText(contactModel.getName());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (addMap.get(position) == null){
                        addMap.put(position, contactModel.getNumber());
                    }
                }else{
                    if (addMap.get(position) != null){
                        addMap.remove(position, contactModel.getNumber());
                    }
                }
            }
        });
        return viewPhone;
    }
}
