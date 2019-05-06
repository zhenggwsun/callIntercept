package com.eric.callintercept.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eric.callintercept.dao.operater.PhoneNumberOperater;
import com.eric.callintercept.dao.object.PhoneNumberDO;
import com.eric.callintercept.R;

public class Add_manually extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNumber;

    private Button confirm;

    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);
        initData();
    }

    private void initData() {
        phoneNumber = findViewById(R.id.editText_phone_num);
        confirm = findViewById(R.id.button_confirm);
        cancel = findViewById(R.id.button_cancel);

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                confirm.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirm.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    confirm.setEnabled(false);
                }
            }
        });

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_confirm:
                saveData();
                break;
            case R.id.button_cancel:
                finish();
                break;
        }
    }

    /**
     * 数据存储
     */
    private void saveData() {
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String type = PhoneNumberOperater.BLACK_LIST;
        if (Allow_list.ALLOW_LIST.equals(from)){
            type = PhoneNumberOperater.ALLOW_LIST;
        }
        Editable phoneNumberText = phoneNumber.getText();
        //判断是否存在
        if (PhoneNumberOperater.read(this, phoneNumberText.toString(), type) != null){
            Toast.makeText(this, "添加失败！号码已经存在。", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneNumberDO phoneNumberDO = new PhoneNumberDO(null,phoneNumberText.toString(), type);
        PhoneNumberOperater.write(this, phoneNumberDO);
        finish();
    }

}
