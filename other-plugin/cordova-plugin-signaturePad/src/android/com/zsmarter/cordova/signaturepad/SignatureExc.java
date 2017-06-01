package com.zsmarter.cordova.signaturepad;

import android.content.Intent;
import android.util.Log;
import org.apache.cordova.BuildConfig;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SignatureExc extends CordovaPlugin{

  CallbackContext callbackContext;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.callbackContext = callbackContext;
    if(BuildConfig.DEBUG){
      Log.e("plugin..","comming..");
    }
    if ("showSignature".equalsIgnoreCase(action)) {
      Intent intent = new Intent(cordova.getActivity(),SignatureActivity.class);
      JSONObject jsonObject = args.getJSONObject(0);
      int width = jsonObject.optInt("width");
      int height = jsonObject.optInt("height");
      int minWidth = jsonObject.optInt("minWidth");
      int maxWidth = jsonObject.optInt("maxWidth");
      String penColor = jsonObject.optString("penColor");
      if(penColor==null||penColor==""){
        penColor = "#000000";
      }
      intent.putExtra("width",width);
      intent.putExtra("height",height);
      intent.putExtra("minWidth",minWidth);
      intent.putExtra("maxWidth",maxWidth);
      intent.putExtra("penColor",penColor);
      cordova.startActivityForResult(this,intent,00);
    }
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if(resultCode==200){
      String url = intent.getStringExtra("imageUrl");
      JSONObject jsonObject = new JSONObject();
      try {
        jsonObject.put("imageUrl",url);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      this.callbackContext.success(jsonObject);
    }
  }
}
