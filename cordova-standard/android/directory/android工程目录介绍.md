# Android的目录结构
1.工程项目结构解析：
我们开发大部分时间都花在下面这个部分上：    
![image](https://github.com/saberforgit/Resource/blob/master/drawable/31508278.jpg?raw=true)       
接下来我们对关键部分进行讲解：  
>java：我们写Java代码的地方，业务功能都在这里实现    
>res：存放我们各种资源文件的地方，有图片，字符串，动画，音频等，还有各种形式的XML文件 

### 1.res资源文件夹介绍：
>PS：说到这个res目录，另外还有提下这个assets目录，虽然这里没有，但是我们可以自己创建，两者的区别在于是否前者下所有的资源文件都会在R.java文件下生成对应的资源id，而后者并不会；前者我们可以直接通过资源id访问到对应的资源；而后者则需要我们通过AssetManager以二进制流的形式来读取！对了，这个R文件可以理解为字典，res下每个资源都都会在这里生成一个唯一的id！    

##### 接着说下res这个资源目录下的相关目录：  
>》PS:下述mipmap的目录，在Eclipse并不存在这个，Eclipse中都是drawable开头的，其实区别不大，只是使用mipmap会在图片缩放在提供一定的性能优化，分辨率不同系统会根据屏幕分辨率来选择hdpi，mdpi，xmdpi，xxhdpi下的对应图片，所以你解压别人的apk可以看到上述目录同一名称的图片，在四个文件夹下都有，只是大小和像素不一样而已！    
》当然,这也不是绝对的,比如我们把所有的图片都丢在了drawable-hdpi下的话,即使手机 本该加载ldpi文件夹下的图片资源,但是ldpi下没有,那么加载的还会是hdpi下的图片!     
》另外,还有一种情况:比如是hdpi,mdpi目录下有,ldpi下没有,那么会加载mdpi中的资源! 原则是使用最接近的密度级别!另外如果你想禁止Android不跟随屏幕密度加载不同文件夹的资源,只需在AndroidManifest.xml文件中添加android:anyDensity="false"字段即可!    
###### 1.先说下图片资源：    
>drawable：存放各种位图文件，(.png，.jpg，.9png，.gif等)除此之外可能是一些其他的drawable类型的XML文件   
mipmap-hdpi：高分辨率，一般我们把图片丢这里 
mipmap-mdpi：中等分辨率，很少，除非兼容的的手机很旧     
mipmap-xhdpi：超高分辨率，手机屏幕材质越来越好，以后估计会慢慢往这里过渡    
mipmap-xxhdpi：超超高分辨率，这个在高端机上有所体现     

###### 2.接着说下布局资源：
>layout：该目录下存放的就是我们的布局文件，另外在一些特定的机型上，我们做屏幕适配，比如480*320这样的手机，我们会另外创建一套布局，就行：layout-480x320这样的文件夹！      

###### 3.接下来说下菜单资源：   
>menu：在以前有物理菜单按钮，即menu键的手机上，用的较多，现在用的并不多，菜单项相关的资源xml可在这里编写，不知道谷歌会不会出新的东西来替代菜单了    

###### 4.接下来说下values目录：     
>demens.xml：定义尺寸资源    
string.xml：定义字符串资源  
styles.xml：定义样式资源    
colors.xml：定义颜色资源    
arrays.xml：定义数组资源    
attrs.xml：自定义控件时用的较多，自定义控件的属性！     
theme主题文件，和styles很相似，但是会对整个应用中的Actvitiy或指定Activity起作用，一般是改变窗口外观的！可在Java代码中通过setTheme使用，或者在Androidmanifest.xml中为<application...>添加theme的属性!     
PS:你可能看到过这样的values目录：values-w820dp，values-v11等，前者w代表平板设备，820dp代表屏幕宽度；而v11这样代表在API(11)，即android 3.0后才会用到的！

###### 5.在接着说下这个raw目录：        

>用于存放各种原生资源(音频，视频，一些XML文件等)，我们可以通过openRawResource(int id)来获得资源的二进制流！其实和Assets差不多，不过这里面的资源会在R文件那里生成一个资源id而已    

###### 6.最后还有个动画的，动画有两种：属性动画和补间动画：     

>animator：存放属性动画的XML文件    
>anim：存放补间动画的XML文件    

#### AndroidManifest.xml配置文件:
代码如下：      

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
     package="jay.com.example.firstapp" >

        <application
          android:allowBackup="true"
          android:icon="@mipmap/ic_launcher"
          android:label="@string/app_name"
          android:theme="@style/AppTheme" >
             <activity
             android:name=".MainActivity"
             android:label="@string/app_name" >
             <intent-filter>
                 <action android:name="android.intent.action.MAIN" />

                 <category android:name="android.intent.category.LAUNCHER" />
              </intent-filter>
          </activity>
     </application>

    </manifest>
    
    
代码分析：      
![image](https://github.com/saberforgit/Resource/blob/master/drawable/13063914.jpg?raw=true)        

###### 除了上述内容外：     

>①如果app包含其他组件的话,都要使用类型说明语法在该文件中进行声明 Server:元素 BroadcastReceiver元素 ContentProvider元素 IntentFilter<intent-filter>元素     
②权限的声明: 在该文件中显式地声明程序需要的权限,防止app错误地使用服务, 不恰当地访问 资源,最终提高android app的健壮性 android.permission.SEND_SMS 有这句话表示app需要使用发送信息的权限,安装的时候就会提示用户, 相关权限可以在sdk参考手册查找！


## Android 的配置文件详解   
![image](https://github.com/saberforgit/Resource/blob/master/drawable/20150708104746_354.png?raw=true)      

>app/build/ app模块build编译输出的目录  
app/build.gradle app模块的gradle编译文件    
app/app.iml app模块的配置文件   
app/proguard-rules.pro app模块proguard文件  
build.gradle 项目的gradle编译文件   
settings.gradle 定义项目包含哪些模块    
gradlew 编译脚本，可以在命令行执行打包  
local.properties 配置SDK/NDK(cordova工程中会在这更改sdk的引用位置)    
MyApplication.iml 项目的配置文件    
External Libraries 项目依赖的Lib, 编译时自动下载的  
