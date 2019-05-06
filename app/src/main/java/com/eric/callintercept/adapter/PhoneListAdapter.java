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
import com.eric.callintercept.activity.Allow_list;
import com.eric.callintercept.activity.Black_list;
import com.eric.callintercept.dao.object.PhoneNumberDO;
import com.eric.callintercept.dao.operater.PhoneNumberOperater;

import java.util.List;

public class PhoneListAdapter extends BaseAdapter {
    //数据列
    private List<PhoneNumberDO> data;
    //自定义布局
    private LayoutInflater layoutInflater;
    //域
    private Context context;

    public PhoneListAdapter(List<PhoneNumberDO> data, LayoutInflater layoutInflater, Context context) {
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
        final PhoneNumberDO recordDO = data.get(position);
        //控件
        TextView phoneNumber = viewPhone.findViewById(R.id.phone_number);
        ImageView deleteIcon = viewPhone.findViewById(R.id.button_delete);
        //数据填充
        phoneNumber.setText(recordDO.getPhoneNumber());
        deleteIcon.setImageResource(R.drawable.delete);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("删除").setMessage("您确定删除吗?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PhoneNumberOperater.delete(context, recordDO.getId());
                                data.remove(position);
                                if (PhoneNumberOperater.ALLOW_LIST.equals(recordDO.getType())){
                                    Allow_list i = (Allow_list)context;
                                    i.showList();
                                }else{
                                    Black_list i = (Black_list)context;
                                    i.showList();
                                }

                            }
                        }).create();
                alertDialog.show();
            }
        });
        return viewPhone;
    }
}
