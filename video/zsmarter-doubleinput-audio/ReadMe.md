# zsmarter-doubleinput-audio

@(插件简介)[何成斌|QQ:472966681]



**zsmarter-doubleinput-audio**
适用于通过系统MediaRecorder进行音频录制，音频导入，音频播放，音频加密的cordova插件：
 
- **音频录制** ：进行音频录制；
- **音频导入** ：支持音频导入到指定的地址，并且更换指定的名字；
- **音频播放** ：支持音频播放功能；
- **音频加密** ：支持对音频进行加密；


-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-doubleinput-audio  添加插件

##使用方法
###开始音频录制(startVideo)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| aduioName  | string |  zxc   |
| path     |   string |  doubleInput/video/  |
| playPath     |   string |  doubleInput/video/111.mp3  |

 >**参数介绍：**
@audioName  生成的音频的名字(默认格式mp3)
@ path   生成的音频在手机上的位置

#### 调用示例
var startAudio = function(){
          zsmarter.audio.startAudio(
            function(res){
            console.info(res);
            },
            function(err){
             console.info(err);
            },{
            "audioName":"11111",
            "path":"/zsmater/test/audio/"
            }
            );}

 > 
 @aduioName  生成的音频的名字
path   生成的音频在手机上的位置

###结束音频录制(stopVideo)


#### 调用示例
    var stopAudio = function(){
    zsmarter.audio.stopAudio(
        function(res){
        path = res.audioPath;
        console.info(res);
        },
        function(err){
            console.info(err);
    }
    );}

#### 返回结果
> audioPath 音频文件所在的绝对路径

###音频播放(playAudio)
#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| playPath     |   string |  doubleInput/video/111.mp3  |
 > **参数介绍：**
@ playPath   播放的音频在手机上的位置；

#### 调用示例

var playAudio = function(){
          zsmarter.audio.playAudio(
            function(res){
            console.info(res);
            },
            function(err){
             console.info(err);
            },{
            "playPath":path
            }
            );}
#### 返回结果 NO
>@playPath 播放的音频在手机上的位置





-------------------
> **注意：**       
1.添加插件前需要在:project/platforms/android下的build.gradle中的dependencies标签中的最下面添加compile 'com.android.support:appcompat-v7:25.0.1' ；compile ’com.android.support:support-v4:24.1.1+‘，如果已经添加其他兼容性包，可以忽略此信息。     

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

