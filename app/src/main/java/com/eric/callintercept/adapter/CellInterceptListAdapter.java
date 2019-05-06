package com.eric.callintercept.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eric.callintercept.R;
import com.eric.callintercept.activity.Intercept_list;
import com.eric.callintercept.dao.object.CallInterceptRecordDO;
import com.eric.callintercept.dao.operater.CellInterceptOperater;

import java.text.SimpleDateFormat;
import java.util.List;

public class CellInterceptListAdapter extends BaseAdapter {
    //数据列
    private List<CallInterceptRecordDO> data;
    //自定义布局
    private LayoutInflater layoutInflater;
    //域
    private Context context;

    public CellInterceptListAdapter(List<CallInterceptRecordDO> data, LayoutInflater layoutInflater, Context context) {
        this.data = data;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewPhone = layoutInflater.inflate(R.layout.cell_intercept_list_style, null);
        final CallInterceptRecordDO recordDO = data.get(position);
        //控件
        ImageView contactPhoto = viewPhone.findViewById(R.id.contact_photo);
        TextView phoneNumber = viewPhone.findViewById(R.id.phone_number);
        ImageView deleteIcon = viewPhone.findViewById(R.id.button_delete);
        TextView timeStr = viewPhone.findViewById(R.id.time_str);
        //数据填充
        contactPhoto.setImageResource(R.drawable.contact);
        phoneNumber.setText(recordDO.getPhoneNumber());
        deleteIcon.setImageResource(R.drawable.delete);
        timeStr.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordDO.getGmtCreate()));
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("删除").setMessage("您确定删除吗?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CellInterceptOperater.delete(context, recordDO.getId());
                                data.remove(position);
                                Intercept_list i = (Intercept_list)context;
                                i.init();
                            }
                        }).create();
                alertDialog.show();
            }
        });
        return viewPhone;
    }
}
