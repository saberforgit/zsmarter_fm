package com.android.internal.telephony;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by wangxf on 2017/4/21.
 */

public class PhoneCallReceiver extends BroadcastReceiver {
    String TAG = "tag";
    TelephonyManager telMgr;
    @Override
    public void onReceive(Context context, Intent intent) {

        telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
      try {
        endCall();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    /**
     * 挂断电话
     */
    private void endCall() throws Exception
    {
        Class<TelephonyManager> c = TelephonyManager.class;
        try
        {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            Log.d(TAG, "End call.");
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr, (Object[]) null);
            iTelephony.endCall();
        }
        catch (Exception e)
        {
            Log.e(TAG, "Fail to answer ring call.", e);
        }
    }
}
