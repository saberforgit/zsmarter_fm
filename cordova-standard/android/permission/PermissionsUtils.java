package com.zsmarter.doubleinput.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.zsmarter.doubleinput.R;

import io.vov.vitamio.utils.Log;

/**
 * Created by wangxf on 2017/4/13.
 */

public class PermissionsUtils {

  public static final int PERMISSION_REQUEST = 4001;
  /**
   * 获取所有需要动态申请的permission
   *
   * @param context
   * @return
   */
  public static String[] getNativePermissions(Context context) {
    return context.getResources().getStringArray(R.array.permissions);
  }

  /**
   * 检查app是否允许所有权限
   *
   * @param context
   * @param permissions
   * @return
   */
  public static boolean checkAllPermissions(Context context, String[] permissions) {
    PackageManager packageManager = context.getPackageManager();
    boolean isAllowed = true;
    for (String permission : permissions) {
      isAllowed = isAllowed && (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context.getPackageName()));
    }
    return isAllowed;
  }
}
