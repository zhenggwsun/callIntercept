package com.eric.callintercept.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.eric.callintercept.R;
import com.eric.callintercept.service.PhoneService;

public class Intercept_settings extends AppCompatActivity {

    public static final String NAME = "SETTING";
    /**
     *  配置开关
     */
    public static final String SETTING_SWITCH = "SETTING_SWITCH";
    /**
     * 拦截模式
     */
    public static final String SETTING_MODE = "SETTING_MODE";
    public static final String SETTING_MODE_BLACK_LIST = "SETTING_MODE_BLACK_LIST";
    public static final String SETTING_MODE_ALLOW_LIST = "SETTING_MODE_ALLOW_LIST";

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Switch aSwitch;

    private RadioGroup radioGroup;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept_settings);

        init();
    }

    /**
     * 数据初始化
     */
    private void init() {
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        aSwitch = findViewById(R.id.switch_button);
        radioGroup = findViewById(R.id.radio_group_intercept_mode);

        if (intent == null) {
            intent = new Intent(Intercept_settings.this, PhoneService.class);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(SETTING_SWITCH, isChecked);
                editor.commit();

                System.out.println("is Checked : " + isChecked);
                if (isChecked){
                    if (!isServiceRunning()) {
                        startService(intent);
                        System.out.println("start Service");
                    }
                }else {
                    stopService(intent);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                editor.putInt(SETTING_MODE, checkedId);
                editor.commit();
            }
        });

        aSwitch.setChecked(sharedPreferences.getBoolean(SETTING_SWITCH, false));
        radioGroup.check(sharedPreferences.getInt(SETTING_MODE, R.id.radio_black_list));
        System.out.println(sharedPreferences.getBoolean(SETTING_SWITCH, false));
        System.out.println(sharedPreferences.getInt(SETTING_MODE, 123));
    }

    /**
     * 判断进程是否在运行
     * @return
     */
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.eric.callintercept.service.PhoneService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
