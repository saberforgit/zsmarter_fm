#zsmarter-qn-upload

>插件简介:本插件用于图片,音频,视频的上传,支持分片上传,断点续传,分片上传,上传进度,上传取消.(插件只适应于青牛云的上传)

### upload()
简介:上传操作
使用方法:   cordova add plugin zsmarter-qn-upload
调用方式:

		zsmarter.upload.upload(
            function(res){ console.info(JSON.stringify(res));},
            function(err){console.info(JSON.stringify(err));},
            {
	          "getTokenServePath":"http://192.168.6.160:8080",
              "getTokenReqPath":"/mobilebank/ws/upload/token",
              "uploadCacheDir":"/storage/emulated/0/doubleinput/cache/",
              "uploadFilePath":"/storage/emulated/0/123.jpg",
              "uploadBlockSize":"254",
              "params":{
              "plocy":"123123",
              "thhh":"afsdafsdf",
              "asada":"879iyuioyuio"
              }
              });

#### 参数
| Params      |    type | value  |
| :-------- | --------:| :--: |
| getTokenServePath| string |  http://192.168.6.160:8080   |
| getTokenReqPath|   string |  /mobilebank/ws/upload/token  |
| uploadCacheDir|   string |  /storage/emulated/0/doubleinput/cache/|
| uploadFilePath|   string |  /storage/emulated/0/123.jpg|
| uploadBlockSize |   int |  256*1024|
| params|   object|  {...}|
 >**参数介绍：**
@ getTokenServePath  获取token的ip       
@ getTokenReqPath 获取token的服务器路径  
@ uploadCacheDir  本地缓存的路径(用于断点续传)   
@ uploadFilePath 要上传的文件路径(必须)
@ uploadBlockSize 分片上传时每片的大小  
@ params 自定义的参数 

### cabcel()
简介:取消上传(上传过程中调用取消,取消后调用继续上传)
使用方法:

		var cancel = function(){
            zsmarter.upload.cancel(
            function(res){ console.info(JSON.stringify(res));},
            function(err){console.info(err);} );
            }



### 注意       
  1.添加插件前需要在:project/platforms/android下的build.gradle中的dependencies标签中的最下面添加
   compile 'com.qiniu:qiniu-android-sdk:7.2.+'
   (添加插件后首次编译需要联网下载上面的jar包,请务必保持网络通畅)