package com.eric.callintercept.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eric.callintercept.R;
import com.eric.callintercept.adapter.CellInterceptListAdapter;
import com.eric.callintercept.dao.object.CallInterceptRecordDO;
import com.eric.callintercept.dao.operater.CellInterceptOperater;

import java.util.List;

public class Intercept_list extends AppCompatActivity {

//    private RadioGroup radioGroup;

    private ListView interceptList;

    private TextView textView;

    private Button deleteAll;

    private View viewLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept_list);
        init();
    }

    public void init() {
//        radioGroup = findViewById(R.id.radio_group_change);
        textView = findViewById(R.id.text_view_intercept_list_message);
        interceptList = findViewById(R.id.list_view_intercept_list);
        deleteAll = findViewById(R.id.button_delete_all_intercept_cell_records);
        viewLine = findViewById(R.id.intercept_list_view_line);

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                showList(checkedId);
//            }
//        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Intercept_list.this)
                        .setTitle("清空") .setMessage("您确定要清空吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                CellInterceptOperater.delete(Intercept_list.this);
                                /*switch(radioGroup.getCheckedRadioButtonId()){
                                    case R.id.radio_button_cell:
                                        CellInterceptOperater.delete(Intercept_list.this);
                                        break;
                                    case R.id.radio_button_message:
                                        break;
                                }*/
//                                showList(radioGroup.getCheckedRadioButtonId());
                                showCellRecords();
                            } })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            } }).create();
                alertDialog.show();
            }
        });

        //默认值：展示电话拦截记录
//        showList(radioGroup.getCheckedRadioButtonId());
        showCellRecords();
    }

    /**
     * 列表展示
     * @param checkedId
     */
    /*public void showList(int checkedId){
        switch(checkedId){
            case R.id.radio_button_cell:
                showCellRecords();
                break;
            case R.id.radio_button_message:
                showMessageRecords();
                break;
        }
    }*/

    /**
     * 列表展示
     */
    private void showCellRecords() {
        List<CallInterceptRecordDO> recordDOS = CellInterceptOperater.read(this);
        //文字展示设置
        if (recordDOS.size() == 0){
            textView.setVisibility(View.VISIBLE);
            deleteAll.setEnabled(false);
            viewLine.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.GONE);
            deleteAll.setEnabled(true);
            viewLine.setVisibility(View.VISIBLE);
        }
        //自定义adapter
        LayoutInflater inflater = getLayoutInflater();
        CellInterceptListAdapter cellInterceptListAdapter = new CellInterceptListAdapter(recordDOS, inflater, this);
        interceptList.setAdapter(cellInterceptListAdapter);
    }

    /**
     * 列表展示
     */
    /*private void showMessageRecords() {
        List<MessageInterceptRecordDO> recordDOS = MessageInterceptOperater.read(this);
        //文字展示设置
        if (recordDOS.size() == 0){
            textView.setVisibility(View.VISIBLE);
            deleteAll.setEnabled(false);
            viewLine.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.GONE);
            deleteAll.setEnabled(true);
            viewLine.setVisibility(View.VISIBLE);
        }
        ArrayList<String> dataList = new ArrayList<>();
        Iterator<MessageInterceptRecordDO> iterator = recordDOS.iterator();
        while(iterator.hasNext()){
            MessageInterceptRecordDO recordDO = iterator.next();
            dataList.add(recordDO.getPhoneNumber());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, dataList);
        interceptList.setAdapter(adapter);
    }*/

}
