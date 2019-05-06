package com.eric.callintercept.util;

import android.content.Context;
import android.os.IBinder;


import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class PhoneUtils {

    /**
     * 挂断电话
     */
    public static void endCall() {
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
