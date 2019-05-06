package com.eric.callintercept.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.eric.callintercept.R;
import com.eric.callintercept.activity.Intercept_settings;
import com.eric.callintercept.dao.object.CallInterceptRecordDO;
import com.eric.callintercept.dao.object.PhoneNumberDO;
import com.eric.callintercept.dao.operater.CellInterceptOperater;
import com.eric.callintercept.dao.operater.PhoneNumberOperater;
import com.eric.callintercept.util.PhoneUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PhoneService extends Service {
    private TelephonyManager telephonyManager;

    private SharedPreferences sharedPreferences;

    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        PhoneListener phoneListener = new PhoneListener();
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("服务启动");
        return super.onStartCommand(intent, flags, startId);
    }

    class PhoneListener extends PhoneStateListener{

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);

            switch(state){
                case TelephonyManager
                        .CALL_STATE_RINGING:
//                    Toast.makeText(PhoneService.this, "来电号码:"+phoneNumber, Toast.LENGTH_SHORT).show();
                    //电话拦截
                    callIntercept(phoneNumber);
            }

        }
    }

    public void callIntercept(String phoneNumber){
        //拦截配置检测
        if (sharedPreferences == null){
            sharedPreferences = getSharedPreferences(Intercept_settings.NAME, Context.MODE_PRIVATE);
        }
        //开关状态判断
        if (!sharedPreferences.getBoolean(Intercept_settings.SETTING_SWITCH, false)){
            return;
        }

        switch (sharedPreferences.getInt(Intercept_settings.SETTING_MODE, R.id.radio_black_list)){
            case R.id.radio_black_list:
                blackListIntercept(phoneNumber);
                break;
            case R.id.radio_allow_list:
                allowListIntercept(phoneNumber);
                break;
        }

    }

    /**
     * 只接受白名单
     * @param phoneNumber
     */
    private void allowListIntercept(String phoneNumber) {
        ArrayList<String> phoneNumberList = new ArrayList<>();
        List<PhoneNumberDO> phoneNumberDOS = PhoneNumberOperater.read(this, PhoneNumberOperater.ALLOW_LIST);
        if (phoneNumberDOS != null){
            Iterator<PhoneNumberDO> iterator = phoneNumberDOS.iterator();
            while (iterator.hasNext()){
                PhoneNumberDO numberDO = iterator.next();
                phoneNumberList.add(numberDO.getPhoneNumber());
            }
        }
        if (phoneNumberDOS == null || !phoneNumberList.contains(phoneNumber)){
            PhoneUtils.endCall();
            CellInterceptOperater.write(this, new CallInterceptRecordDO(null,phoneNumber, new Date()));
        }
    }

    /**
     * 黑名单拦截
     * @param phoneNumber
     */
    private void blackListIntercept(String phoneNumber) {
        ArrayList<String> phoneNumberList = new ArrayList<>();
        List<PhoneNumberDO> phoneNumberDOS = PhoneNumberOperater.read(this, PhoneNumberOperater.BLACK_LIST);
        if (phoneNumberDOS != null){
            Iterator<PhoneNumberDO> iterator = phoneNumberDOS.iterator();
            while (iterator.hasNext()){
                PhoneNumberDO numberDO = iterator.next();
                phoneNumberList.add(numberDO.getPhoneNumber());
            }
        }
        if (phoneNumberDOS != null && phoneNumberList.contains(phoneNumber)){
            PhoneUtils.endCall();
            CellInterceptOperater.write(this, new CallInterceptRecordDO(null,phoneNumber, new Date()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        startService(new Intent())
        System.out.println("service on destroy");
    }
}
