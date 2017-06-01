package com.zsmarter.phone;

import android.app.Activity;
import android.content.IntentFilter;
import com.android.internal.telephony.PhoneCallReceiver;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangxf on 2017/4/21.
 */

public class Plugin_ZS_Phone extends CordovaPlugin{


  //定义一个广播监听器；
  private PhoneCallReceiver mPhoneCallReceiver;
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Activity mContext = cordova.getActivity();
    switch (action){
      case "startPhoneCallListener":
        try {
          startPhoneCallListener(mContext);
          callbackContext.success(new JSONObject().put("response","start listener success"));
        } catch (Exception e) {
          callbackContext.error(new JSONObject().put("response","start listener fail:"+e.getMessage()));
          e.printStackTrace();
        }
        break;
      case "stopPhoneCallListener":
        try {
          stopPhoneCallListener(mContext);
          callbackContext.success(new JSONObject().put("response","stop listener success"));
        } catch (Exception e) {
          callbackContext.error(new JSONObject().put("response","start listener fail:"+e.getMessage()));
          e.printStackTrace();
        }
        break;
    }
    return true;
  }


  private void startPhoneCallListener(Activity mContext) throws Exception{
    //定义一个过滤器；
    IntentFilter intentFilter;
    //实例化过滤器；
    intentFilter = new IntentFilter();
    //添加过滤的Action值；
    intentFilter.addAction("android.intent.action.PHONE_STATE");

    //实例化广播监听器；
    mPhoneCallReceiver = new PhoneCallReceiver();

    //将广播监听器和过滤器注册在一起；
    mContext.registerReceiver(mPhoneCallReceiver, intentFilter);
  }

  private void stopPhoneCallListener(Activity mContext) throws Exception{
    if(mPhoneCallReceiver!=null){
      //销毁Activity时取消注册广播监听器；
      mContext.unregisterReceiver(mPhoneCallReceiver);
    }
  }
}
