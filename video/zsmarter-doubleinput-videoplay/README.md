# zsmarter-doubleinput-videoplay

@(插件简介)[王晓峰|QQ:245902324]
 
- **[文本水印][1]** ：支持通过传递视频地址播放视频

-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-doubleinput-videoplay  添加插件

##使用方法
###视频播放 (playVideo)
#### 参数

| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoPath| string |  /storage/emulated/0/doubleInput/img/ID/watermark.mp4  |
 >**参数介绍：**
@ videoPath:播放视频的路径

#### 调用示例
		var playVideoSS = function(){
             zsmarter.doubleinput.videoplay.playVideo(
                function(res){ alert(JSON.stringify(res));},
            function(err){alert(err);},
             {
              videoPath: "/storage/emulated/0/doubleInput/video/zxc.mp4",
            });
            }
#### 返回结果 NO
 
 
-------------------
 > **注意：**       
 1.添加插件前需要在:project/platforms/android下的build.gradle中的dependencies标签中的最下面添加compile 'com.android.support:appcompat-v7:25.0.1' ；compile "com.android.support:support-v4:24.1.1+"，如果已经添加其他兼容性包，可以忽略此信息。     
 
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_7
        targetCompatibility JavaVersion.VERSION_1_7
    }
    
  > 2.修改java版本为1_7  (修改示例如上 )
  > 3.确保project/platforms/android/res/xml的下面有filepaths.xml文件，没有的话自定从插件的android/res/xml中拷贝       
  > 4.添加插件前需要确保在：project/platforms/android/AndroidManifest.xml中的application标签中有如下代码，没有的话自行添加，有的话可以忽略        
#### 代码示例
	      <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths" />
        </provider>
 