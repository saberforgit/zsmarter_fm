# zsmarter-doubleinput-watermark

@(插件简介)[王晓峰|QQ:245902324]
**zsmarter-doubleinput-video**适用于通过系统相机拍照后添加水印或者直接传入图片地址后对图片进行添加水印
 
- **[文本水印][1]** ：支持通过传递参数改变水印的位置，边距，显示内容，字体大小，文本颜色，文本透明度，以及导出的图片的压缩率；
- **[图片水印][1]** ：支持通过传递参数改变水印的位置，边距，图标透明度，以及导出的图片的压缩率，底板图片和水印图片提供地址传入和base64传入两种格式；

-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-doubleinput-watermark  添加插件

##使用方法
###视频录制(createWaterMark)
#### 参数

| Params      |    type | value  |
| :-------- | --------:| :--: |
| fromType| int|  1   |
| type|   string |  string  |
| postion|   string|  leftTop|
| sourcePhotoPath|   string|  /storage/emulated/0/doubleInput/img/ID/watermark.jpg|
| markPhotoPath|   string|  /storage/emulated/0/doubleInput/img/ID/watermark.jpg|
| sourcePhotoBase64|   string|  SDFAS^&%A%SD|
| markPhotoBase64|   string|  SDFAS^&%A%SD|
| penColor|   string|  #000FFF|
| inSampleSize|   int|  9|
| stringMarkSize|   int|  9|
| alpha|   string|  0|
| leftStringPadding|   int|  15|
| rightStringPadding|   int|  15|
| bottomStringPadding|   int|  15|
| topStringPadding|   int|  15|
| leftPhotoPadding|   int|  15|
| rightPhotoPadding|   int|  15|
| bottomPhotoPadding|   int|  15|
| topPhotoPadding|   int|  15|
| imgPath|   string|  doubleInput/img/ID/|
| imgName|   string|  watermark|

>**参数介绍：**     
@ fromType:水印的来源类型》通过相机拍摄的图片直接生成水印：1，通过传递图片地址生成水印：0，默认1;       
@ type:水印的类型》文本水印：string，图片水印：photo，文本加图片水印：all，默认文本;    
@ postion: 水印的位置》rightBottom：右下角，leftBottom：左下角，rightTop：右上角，leftTop：左上角，默认右下角;      
@ stringMarkText:文本水印的内容》默认系统时间       
@ sourcePhotoPath:底版图片的地址        
@ markPhotoPath:水印图片的地址          
@ sourcePhotoBase64:底版图片的base64数据        
@ penColor:水印的颜色，默认黑色     
@ inSampleSize: 导出图片的压缩率        
@ stringMarkSize:文本水印的字号     
@ markPhotoBase64：水印图片的base64资源     
@ alpha:水印的透明度》1-255，1完全透明，255完全不透明       
@ leftStringPadding:文本水印距离左侧的边距，系统默认底版图片/35     
@ rightStringPadding:文本水印距离右侧的边距，系统默认底版图片/35    
@ bottomStringPadding:文本水印距离底侧的边距，系统默认底版图片/35   
@ topStringPadding:文本水印距离顶侧的边距，系统默认底版图片/35  
@ leftPhotoPadding：图片水印距离左侧的边距，系统默认底版图片/35     
@ rightPhotoPadding：图片水印距离左侧的边距，系统默认底版图片/35    
@ bottomPhotoPadding：图片水印距离左侧的边距，系统默认底版图片/35   
@ topPhotoPadding：图片水印距离左侧的边距，系统默认底版图片/35  
@ imgPath:生成的图片保存的位置（必填）  
@ imgName:生成的图片的名字（必填）    


 > **注意：**
 > 调用时只传入必输项imgpath，imgName时其它都是默认选项，实现是相机拍照加水印后返回地址。
 
#### 调用示例
	var waterMark = function(base64){
    var option = {
	fromType:1,
    type:"string",
    postion:"leftTop",
    stringMarkText:"12321FFSSF",
    sourcePhotoPath:"/storage/emulated/0/doubleInput/img/ID/watermark.jpg",
    markPhotoPath:"file:///android_asset/www/img/logo.png",
    sourcePhotoBase64:base64,
    markPhotoBase64:base64,
    penColor:"#000FFF",
    inSampleSize:10,
    stringMarkSize:10,
    alpha:0,
    leftStringPadding:15,
    rightStringPadding:15,
    bottomStringPadding:15,
    topStringPadding:15,
    leftPhotoPadding:15
    rightPhotoPadding:15
    bottomPhotoPadding:15
    topPhotoPadding:15
    imgPath:"doubleInput/img/ID/",
    imgName:"watermark",
        }
        zsmarter.watermark.createWaterMark(
        function(res){
                var image = document.getElementById('resImage');
                image.src = res.photoPath;
        },
        function(res){
        alert(JSON.stringify(res));
        },
        option);
        }
#### 返回结果
| Params      |    type | value  |
| :-------- | --------:| :--: |
| photoPath| string |   doubleInput/video/***.jpg  |
 > @photoPath 生成的图片的地址
 
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
 