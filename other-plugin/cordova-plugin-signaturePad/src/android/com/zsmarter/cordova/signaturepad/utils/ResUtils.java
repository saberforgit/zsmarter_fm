package com.zsmarter.cordova.signaturepad.utils;

import android.content.Context;

/**
 * Created by wangxf on 2016/9/5.
 */

public class ResUtils {
    public static int getResAttrByString(Context context, String type,String source){
        return context.getResources().getIdentifier(source, type, context.getPackageName());
    }
}
