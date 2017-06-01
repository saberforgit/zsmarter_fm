package com.zsmarter.doubleinput.videoplay;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoPlay extends CordovaPlugin {

    private Activity mContext;
    protected final static String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.e(cordova.getClass().getName(), "init..");
        this.mContext = cordova.getActivity();
        PermissionHelper.requestPermissions(this, 0, permissions);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(cordova.getClass().getName(), "onStart..");

    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        Log.e(cordova.getClass().getName(), "onResume..");
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        Log.e(cordova.getClass().getName(), "onPause..");

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e(cordova.getClass().getName(), "onPlugining..");
        JSONObject jsonObject1 = args.getJSONObject(0);
        String _videoPath = jsonObject1.optString("videoPath");
        playVideo(_videoPath);
        return true;
    }

    private void playVideo(String path) {
        Intent intent = new Intent(mContext,VideoViewDemo.class);
        intent.putExtra("videoPath", path);
        mContext.startActivity(intent);
    }

}
