## Cordova插件开发规范
[TOC]

>插件开发简介:插件的开发大体上分为查询类插件,操作类插件,请求类插件.每种插件一般需要不同的处理及返回方式.大部分插件的开发基本上都是在android源码的基础上继承CordovaPlugin类,使用CallBack对象返回结果。

### Android环境下
#### 1、准备工作
在android原生的基础上进行cordova插件开发，可以在android上代码实现相应的功能后把代码集成进cordova工程，也可以直接在cordova工程中开发相应的代码。		

>以在cordova工程中为例:具体操作步骤如下    [@cordova环境配置](http://cordova.apache.org/docs/en/latest/guide/cli/index.html)    
====cordova环境配置的基础是JDK环境变量和SDK环境变量都已经配置完成====

1.配置cordova环境,安装nodejs和git      
2.创建cordova工程

	cordova create hello com.example.hello HelloWorld
3.添加平台,首先要进入到工程的根目录下(如果添加android平台如下)

	cordova platform add android
4.运行app,运行到手机上或者模拟器上
注:app编译或者运行的时候会下载gradle环境,需要网络通畅(尽量能够翻墙);

	cordova run android
#### 2.插件开发
cordova的环境准备和工程创建完成后就可以进行下一步了,现在需要配置工程中的开发插件的环境.

>插件的环境配置在开发阶段是xml和js文件的定义. [@cordova插件配置](http://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html#building-a-plugin)    
>例如要开发一个前端js传参让android弹出一个Toast的插件

##### 1.定义插件对于前端js的调用方法
   在主工程中找到Project\platforms\android\assets\www\plugins目录(插件的实现方法定义区),在目录下新建cordova-plugin-toast/www目录(二级目录),目录中新建toast.js文件,js中的代码如下:
	
	cordova.define("cordova-plugin-toast.toast", function(require, exports, module) {
		var exec = require('cordova/exec');
		exports.showToast= function(success, error,args) {
	    exec(success, error, "Plugin_Toast", "showToast", [args]);
			};
		});
1.在这段js中,define括号后面的cordova-plugin-toast是插件的id,在"."后的toast是插件的name,exports后跟的是插件对前端js所暴		漏的方法,方法里的三个参数分别是插件调用成功后的处理,失败后的处理,以及传递的参数.

2.方法里面的exec方法是cordova环境下调用原生android的入口,其中第二个参数是要调用的原生android类的feature的name,第三个参数在原生的代码中定义用来弹出toast的动作,这个在插件开发下一步中会用到.

##### 2.定义前端js的调用方式
   在主工程中找到Project\platforms\android\assets\www目录下,找到cordova_plugins.js文件,在module.exports = [.....]的数组中添加我们自定义插件的内容,代码如下

	 {
                "id": "cordova-plugin-toast.toast",
                "file": "plugins/cordova-plugin-toast/www/toast.js",
                "pluginId": "cordova-plugin-toast",
                "clobbers": [
                    "cordova.toast"
                ]
            }
上述代码中:
"id":就是第一步中我们定义的插件的id和name的组合	    			
"file":是对应的第一步中的toast.js所在的相对路径	    	
"pluginId":插件的id		    
"clobbers":把插件封装进cordova.toast对象,方便js调用 

注意:每个插件的定义以逗号隔开,否则工程运行时会提示插件初始化错误.

##### 3.开发具体的android功能

1.在android的src下新建com.cordova.toast目录,新建Plugin_Toast类.并且继承CordovaPlugin类,代码如下

    	package com.cordova.toast;

    import android.widget.Toast;

    import org.apache.cordova.CallbackContext;
    import org.apache.cordova.CordovaInterface;
    import org.apache.cordova.CordovaPlugin;
    import org.apache.cordova.CordovaWebView;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    public class Plugin_Toast extends CordovaPlugin {

     @Override
     public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

      switch (action){
       case "showToast":
            JSONObject params = args.getJSONObject(0);
           Toast.makeText(cordova.getActivity(),params.getString("context"),Toast.LENGTH_LONG).show();
           JSONObject jsonObject = new JSONObject();
          jsonObject.put("reslut","I am back");
          callbackContext.success(jsonObject);
          break;
     }
      return true;
     }
    }

参数简介    
1.action:第一步中js传递的调用动作       
2.callbackContext对象用来对结果进行返回,成功时使用success返回,失败使用error返回.    

##### 4.连接android原生与cordova环境

在主工程中找到Project\platforms\android\res\xml目录下,找到config.xml文件,在widget标签下添加feature标签,如下		

	  <feature name="Plugin_Toast">
	    <param name="android-package" value="com.cordova.toast.Plugin_Toast" />
	  </feature>

参数介绍:			
name: 第一步中使用的feature的name,相当于把android类暴露给cordova环境	
value: android类所在的相对位置	

>到现在为止,一个完成的插件开发就完成了,现在只需要在前端的页面中调用就行了.  

##### 调用插件

   在主工程中找到Project\platforms\android\assets\www目录下,找到index.html,使用如下方法便可以调用插件并且拿到返回结果

          var showToast = function(){
            cordova.toast.showToast(
            function(res){ console.info(JSON.stringify(res));},
            function(err){console.info(err);},
            {"context":"I am a Toast!"} );
            }
