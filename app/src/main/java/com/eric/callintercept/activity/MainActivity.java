package com.eric.callintercept.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eric.callintercept.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //权限申请
        ActivityCompat.requestPermissions(this, permissions, 1);
        init();
    }

    public void init(){
        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item2).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);
        findViewById(R.id.item4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            default:
                return;

            case R.id.item1:
                startActivity(new Intent(this, Intercept_list.class));
                return;

            case R.id.item2:
                startActivity(new Intent(this, Black_list.class));
                return;

            case R.id.item3:
                startActivity(new Intent(this, Allow_list.class));
                return;

            case R.id.item4:
                startActivity(new Intent(this, Intercept_settings.class));
                break;
        }
    }
}
