package com.eric.callintercept.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.eric.callintercept.activity.Intercept_settings;
import com.eric.callintercept.service.PhoneService;

public class TelReceiver extends BroadcastReceiver {

    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences(Intercept_settings.NAME, Context.MODE_PRIVATE);
        //当拦截开关打开时，启动服务
        if (sharedPreferences.getBoolean(Intercept_settings.SETTING_SWITCH, false)){
            //来电拦截
            context.startService(new Intent(context, PhoneService.class));
        }

    }

}
