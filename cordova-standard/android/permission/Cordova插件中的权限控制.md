#Cordova插件中的权限控制




[TOC]

## Android权限简介

> 在android23以下的版本中，所有的权限统一配置在manifest文件中
> 在android23以上的版本，敏感的权限不仅要在manifest中添加，还需要在代码中动态申请。
> [@谷歌文档](https://developer.android.com/guide/topics/security/permissions.html)(翻墙)
> [@CSDN文档](http://blog.csdn.net/xuezhe__/article/details/51541553)



## cordova插件中的权限控制
 cordova插件中的权限请求分为两种，一种是前端调用api时即时申请权限，另一种是在应用启动时主动申请需要用到的所有权限。     
 

### 1.即时调用时
 》权限申请的代码是在插件中完成的，把需要的权限定义在插件中，只有使用插件时才会进行权限请求(cordova的大部分插件都是即时申请，但是涉及到需要调用硬件的权限时，权限请求页面跟硬件启动会同时运行，低配置的手机会使应用卡住一段时间，影响应用的使用体验)。
> 例如：前端调用cordova插件的api时，插件中检查所需要的权限是否已经允许，所有权限允许的情况下再进行api的调用，检查到有权限被拒绝或者没有允许时，要先请求权限(具体操作代码中自主实现).

### 2.应用启动时
》所有的权限请求都在app启动时完成，每个插件在添加的时候只是把需要请求的权限注册进config.xml中
>注:权限的请求放在插件的initialize方法中可以实现，但是主工程插件多了之后，可能出现权限请求不完整的现象，与前端开发人员的本地环境和工程环境有关，为了减少出问题的几率，我们建议把权限的请求控制放在可控的代码里面。

#### 需要cordova的主工程中做如下准备(只是针对本模块中的PermissionUtils的实现方式)。

1.首先主工程中要存在PermissionUtils类。(PermissionUtils类在模块的根目录下)

2.主工程中的values下面添加permissions.xml。(permissions.xml模板在模块的根目录下，可以直接拷贝进主工程)

3.其次主工程的MainActivity中要做如下的代码实现:具体操作是通过ActivityCompat进行权限请求，重写
onRequestPermissionsResult方法，实现用户操作权限后的具体逻辑(默认提示信息后关闭应用)


	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras();
    if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
      moveTaskToBack(true);
    }
    //主要是通过support-v4的ActivityCompat进行权限的请求
    ActivityCompat.requestPermissions(MainActivity.this, PermissionsUtils.getNativePermissions(this), PermissionsUtils.PERMISSION_REQUEST);
    loadUrl(launchUrl);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	    switch (requestCode) {
	      case PermissionsUtils.PERMISSION_REQUEST:
	        if(!PermissionsUtils.checkAllPermissions(this,permissions)){
	          //TODO 权限不允许的情况下,app端的处理逻辑。
	          //默认弹出提示信息后关闭应用
	          Toast.makeText(this,"您拒绝的权限会影响到双录应用的使用,请重新进入应用允许后使用!",Toast.LENGTH_LONG).show();
          finish();
	        };
	        break;
		    }
	  }
#### 需要开发的插件中做如下准备(只是针对本模块中的PermissionUtils的实现方式)。 

1).在插件的config.xml的platform标签下添加如下代码

		   <config-file parent="/resources/string-array/" target="res/values/permissions.xml">
				<item>android.permission.INTERNET</item >
				<item>android.permission.ACCESS_NETWORK_STATE</item >
				<item>android.permission.ACCESS_WIFI_STATE</item>
	       </config-file>
>item标签下的权限是本插件需要动态申请的权限（动态申请的权限在manifest中依然需要配置）

#### 备注
>1.permissionUtils类中包含R文件的引用，不同的工程中需要重新引用。    
>2.MainActivity中的ActivityCompat类需要引入support包，具体在project/platforms/android下的build.gradle中的dependencies标签中的最下面添加compile "com.android.support:support-v4:24.1.1+"；    
>3.开发插件时遇到权限问题，以上代码仅作为基础的权限控制进行使用，复杂的权限控制需要开发人员自主实现代码逻辑，实际的权限请求场景上述代码不能满足时也需要开发人员自行开发，并不是指定代码。


