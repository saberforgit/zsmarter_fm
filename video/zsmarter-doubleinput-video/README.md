# zsmarter-doubleinput-video

@(插件简介)[王晓峰|QQ:245902324]



**zsmarter-doubleinput-video**
适用于通过系统MediaRecorder进行视频录制，视频导入，视频播放，视频加密，以及获取手机支持的分辨率的cordova插件：
 
- **视频录制** ：支持通过传递录制参数进行视屏录制，进而控制视频的大小以及清晰度；
- **视频导入** ：支持视频导入到指定的地址，并且更换指定的名字；
- **视频播放** ：支持视频播放功能；
- **视频加密** ：支持对视频进行加密；


-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-doubleinput-video  添加插件

##使用方法
###视频录制(recordVideo)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoName  | string |  zxc   |
| videoPath     |   string |  doubleInput/video/  |
| videoEncodingBitRate     |   int |  1536  |
| audioEncodingBitRate     |   int |  1536  |
| videoMaxTime     |   int |  60  |
| cameraPos     |   int |  1  |
| cameraOri     |   int |  0  |
| mHeight     |   int |  480  |
| mWidth     |   int |  800  |
 >**参数介绍：**
@videoName  生成的视频的名字        
@ videoPath   生成的视频在手机上的位置      
@ videoEncodingBitRate    生成的视频的视频码率      
@ audioEncodingBitRate    生成的视频的音频码率      
@ videoMaxTime   限制视频录制的时长     
@ cameraPos   摄像头位置(1:前摄像头,0:后摄像头)     
@ cameraOri   录制视频的方向(0,90,180...)   
@ mHeight     分辨率高度
@ mWidth    分辨率宽度
#### 调用示例
    var recordVideo = function(){
             zsmarter.doubleinput.video.recordVideo(
                function(res){
                 alert(JSON.stringify(res));
                 },
	            function(err){
	            alert(JSON.stringify(res));
	            },
	             {
              videoName: "zxc",
              videoPath: "doubleInput/video/",
              mHeight: 240,
              mWidth: 320,
              cameraPos: 1,
              videoEncodingBitRate: 1536,
              audioEncodingBitRate: 1536,
              videoMaxTime: 60
            });
            }
#### 返回结果
| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoPath  | string |   doubleInput/video/***.mp4  |
| videoHeader     |   string |  doubleInput/video/***.jpg  |
 > 
 @videoName  导入的视频的路径
@ videoPath   导入的视频的预览图的地址


###视频导入(importVideo)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoName  | string |  zxc   |
| videoPath     |   string |  doubleInput/video/  |
 > **参数介绍：**
 @videoName  导入的视频的名字；
@ videoPath   导入的视频在手机上的位置；

#### 调用示例
    var importVideo = function(){
            zsmarter.doubleinput.video.importVideo(
            function(res){ alert(JSON.stringify(res));},
            function(err){alert(err);},
            {
                videoName: "123",
              videoPath: "doubleInput/video/",
              }
            );
          }
#### 返回结果
| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoPath  | string |   doubleInput/video/***.mp4  |
| videoHeader     |   string |  doubleInput/video/***.jpg  |
 > **参数介绍：**
  @videoName  导入的视频的路径
@ videoPath   导入的视频的预览图的地址


###视频播放(playVideo)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| videoPath     |   string |  doubleInput/video/***.pm4  |
 > **参数介绍：**
@ videoPath   播放的视频在手机上的位置；

#### 调用示例

    var playVideo = function(){
             zsmarter.doubleinput.video.playVideo(
                function(res){ alert(JSON.stringify(res));},
            function(err){alert(err);},
             {
              videoPath: "/storage/emulated/0/doubleInput/video/123.mp4",
            });
            }
#### 返回结果 NO

 > **参数介绍：**NO


###获取摄像头参数(getVideoParams)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| cameraPs     |   int |  1 |
 > **参数介绍：**
@ cameraPs   前后摄像头，前:0,后:1；

#### 调用示例

    var getVideoParams = function(){
             zsmarter.doubleinput.video.getVideoParams(
                function(res){ alert(JSON.stringify(res));},
            function(err){alert(err);},
             {
              cameraPs:1,
            });
            }
#### 返回结果 
JSONArray :包含分辨率的数组，格式如  [800x480,320x240,...]

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