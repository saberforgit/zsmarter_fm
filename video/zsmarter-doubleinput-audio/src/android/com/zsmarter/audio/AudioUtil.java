package com.zsmarter.audio;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by hechengbin on 17/3/14.
 */

public class AudioUtil {
    private MediaUtils mediaUtils;
    private Activity activity;
    private String mp3Path;
    
    public AudioUtil(Activity activity) {
        this.activity = activity;
        mediaUtils = new MediaUtils(activity);
        
        //        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }
    
    
    public void startAudio(String path, String name) {
        
        FileUtil fileUtil = new FileUtil();
        File file =  fileUtil.makeRootDirectoryReturn(path);
        Log.i("test", file.getAbsolutePath());
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        //        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        mediaUtils.setTargetDir(file);
        mediaUtils.setTargetName(name + ".mp3");
        mediaUtils.record();
        mp3Path = path + name + ".mp3";
        Log.i("test","startAudrio");
        Log.i("test","mp3Path"+mp3Path);
    }

    public void starAudioParam(String path, String name,int encodingBitRate,int samplingRate,String format){

        FileUtil fileUtil = new FileUtil();
        File file =  fileUtil.makeRootDirectoryReturn(path);
        Log.i("test", file.getAbsolutePath());
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        mediaUtils.setTargetDir(file);
        mediaUtils.setTargetName(name + "." + format);
        mediaUtils.setAudioSamplingRate(samplingRate);
        mediaUtils.setAudioEncodingBitRate(encodingBitRate);
        mediaUtils.record();
        mp3Path = path + name + "." + format;
        Log.i("test","starAudioParam");
        Log.i("test","mp3Path"+mp3Path);
    }
    
    public void stopAudio() {
        //        mediaUtils.stopNow();
        mediaUtils.setRecording(true);
        mediaUtils.stopRecordSave();
        Log.i("test","stopAudrio");
        //        Toast.makeText(activity, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
        
    }
    
    
    public void playAudio(String path){
        Log.i("test","playAudio");
        Log.i("test","path"+path);
        
        
        File file = new File(path);
        Intent it = new Intent(Intent.ACTION_VIEW);
        if (android.os.Build.VERSION.SDK_INT<24){
            
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            it.setDataAndType(Uri.parse("file://" + path), "audio/MP3");
            
            
        }else {
            //7.0以上版本自定义播放器播放
            Uri uri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getPackageName() + ".provider", file);
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            
            Log.i("test","uri "+uri);
            it.setDataAndType(uri,"audio/*");
            
            List<PackageInfo> resInfoList = activity.getApplicationContext().getPackageManager().getInstalledPackages(0);
            for (PackageInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.packageName;
                activity.getApplicationContext().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            //            Intent intent = new Intent(activity,AudioPlayActivity.class);
            //            intent.putExtra(AudioPlayActivity.PLAY_PATH,path);
            //            activity.startActivity(intent);
        }
        activity.startActivity(it);
    }
    
    public String getPath() {
        return mp3Path;
    }
    
    
    
}
