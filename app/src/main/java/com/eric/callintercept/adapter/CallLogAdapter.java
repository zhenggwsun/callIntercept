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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static android.provider.CallLog.Calls.INCOMING_TYPE;
import static android.provider.CallLog.Calls.OUTGOING_TYPE;

public class CallLogAdapter extends BaseAdapter {
    //操作checkBox选中列
    public static final HashMap<Integer, String> addMap = new HashMap<Integer, String>();

    //数据列
    private List<CallLogModel> dataList;
    //自定义布局
    private LayoutInflater layoutInflater;

    public CallLogAdapter(List<CallLogModel> dataList, LayoutInflater layoutInflater) {
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
        View viewPhone = layoutInflater.inflate(R.layout.call_logs_style, null);
        //控件
        ImageView callLogo = viewPhone.findViewById(R.id.call_type);
        TextView phoneNumber = viewPhone.findViewById(R.id.phone_number);
        TextView timeStr = viewPhone.findViewById(R.id.time_str);
        CheckBox checkBox = viewPhone.findViewById(R.id.checkbox);
        //数据填充
        final CallLogModel callLogModel = dataList.get(position);
        if (callLogModel.getType() == INCOMING_TYPE){
            callLogo.setImageResource(R.drawable.icon_call_incmoing);
        }else if (callLogModel.getType() == OUTGOING_TYPE){
            callLogo.setImageResource(R.drawable.icon_call_outgoing);
        }else {
            callLogo.setImageResource(R.drawable.icon_call_miss);
        }
        phoneNumber.setText(callLogModel.getNumber());
        timeStr.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(callLogModel.getDate()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (addMap.get(position) == null){
                        addMap.put(position, callLogModel.getNumber());
                    }
                }else{
                    if (addMap.get(position) != null){
                        addMap.remove(position, callLogModel.getNumber());
                    }
                }
            }
        });
        return viewPhone;
    }
}
