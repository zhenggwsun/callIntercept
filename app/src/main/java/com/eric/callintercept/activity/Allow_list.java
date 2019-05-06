package com.eric.callintercept.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eric.callintercept.adapter.PhoneListAdapter;
import com.eric.callintercept.dao.operater.PhoneNumberOperater;
import com.eric.callintercept.dao.object.PhoneNumberDO;
import com.eric.callintercept.R;

import java.util.List;

public class Allow_list extends AppCompatActivity {

    public static final String ALLOW_LIST = "allow_list";

    private Button add;

    private Button deleteAll;

    private ListView allowList;

    private TextView message;

    private View viewLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_list);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showList();
    }

    /**
     * 列表展示
     */
    public void showList() {
        List<PhoneNumberDO> phoneNumberDOS = PhoneNumberOperater.read(this, PhoneNumberOperater.ALLOW_LIST);
        //文字展示设置
        if (phoneNumberDOS.size() == 0){
            message.setVisibility(View.VISIBLE);
            deleteAll.setEnabled(false);
            viewLine.setVisibility(View.GONE);
        }else{
            message.setVisibility(View.GONE);
            deleteAll.setEnabled(true);
            viewLine.setVisibility(View.VISIBLE);
        }

        //自定义adapter
        LayoutInflater inflater = getLayoutInflater();
        PhoneListAdapter adapter = new PhoneListAdapter(phoneNumberDOS, inflater, this);
        allowList.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void init() {
        add = findViewById(R.id.button_add);
        deleteAll = findViewById(R.id.button_delete_all);
        allowList = findViewById(R.id.list_view_allow_list);
        message = findViewById(R.id.text_view_allow_list_message);
        viewLine = findViewById(R.id.allow_list_view_line);
        add.setOnClickListener(new View.OnClickListener() {
            /**
             * 弹框，及事件设置
             * @param v
             */
            @Override
            public void onClick(View v) {
//                String[] content = {"从通话记录添加","从联系人添加","从短信收件箱添加","手动添加"};
                String[] content = {"从通话记录添加","从联系人添加","手动添加"};
                AlertDialog alertDialog = new AlertDialog.Builder(Allow_list.this) .setTitle("添加")//标题
                        .setItems(content, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent;
                                switch (which){
                                    case 0:
                                        intent = new Intent(Allow_list.this, Add_from_call_logs.class);
                                        intent.putExtra("from", ALLOW_LIST);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(Allow_list.this, Add_from_contacts.class);
                                        intent.putExtra("from", ALLOW_LIST);
                                        startActivity(intent);
                                        break;
                                    /*case 2:
                                        break;*/
                                    case 2:
                                        intent = new Intent(Allow_list.this, Add_manually.class);
                                        intent.putExtra("from", ALLOW_LIST);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(Allow_list.this)
                        .setTitle("清空") .setMessage("您确定要清空吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                PhoneNumberOperater.delete(Allow_list.this, PhoneNumberOperater.ALLOW_LIST);
                                onStart();
                            } })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            } }).create();
                alertDialog.show();


            }
        });



    }
}
