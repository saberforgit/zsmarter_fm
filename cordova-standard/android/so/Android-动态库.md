# Android-动态库(*.so)使用		

简介:动态库的使用大部分是方便app与底层硬件或者第三方外设打交道，部分动态库也应用在算法的优化，以及核心代码的保护防止反编译而封装成了.so文件。	

### 动态库文件简介		
对于android工程而言，so的使用非常普遍，当我们拿到一个动态库文件时，都是类似(libwltdecode.so)这样
			
			libnative-lib.so

> lib(native-lib前面的lib) : 动态库的固定前缀,动态库的命名都是(libwrite.so,libread.so,libvideo.so)这样,都带有lib前缀    
> native-lib : 动态库的名称     
> .so : 动态库的后缀    

### 动态库文件的使用		    

#### 1. 加载动态库 		    
Android加载动态库的方式有很多种，基于android工程的目录可以分为两种:	    		
@1) 标准的android-studio工程,主工程目录如下		    

		Project/app/src/main/...    
		
首先切换工程模式为Project,然后在src/main下 创建jniLibs文件夹,放入.so文件,gradle编译完成后会自动在android模式下的工程中生成jniLibs文件夹,其中的动态库也可以直接引用.			
	    
		app/jniLibs

@2)非标准的android工程,类似Cordova下的android目录,为了不影响cordova工程的编译运行,我们可以通过gradle的配置来加载动态库.找到工程的gradle配置文件,在其中的android标签下的	sourceSets/main中添加 jniLibs.srcDirs = ['libs']一行.如果没有sourceSets标签,可以自己添加,添加后的代码如下		

		android {
			...
		    sourceSets {
		    main {		 
		        jniLibs.srcDirs = ['libs']//加载.so文件
		        }
		    }
		}
#### 2. 使用动态库
动态库的加载完成了,剩下的就是使用了.对于第三方提供的so文件,大部分都有配套的jar配合使用. 因为动态库的调用需要调用的java类的包名与动态库中指定的路径相同.	    		
>演示示例是一个通过动态库打印log并返回的简单调用.   

1.通过示例介绍so文件的使用过程，so文件的内容如下    

		#include <jni.h>
		#include <string>
		#include "LogUtils.h"
		extern "C"
		JNIEXPORT jstring JNICALL
		Java_com_zsmarter_ndkdemo_NDKHelper_getAppKey(JNIEnv *env, jclass type) {
		    //测试代码, 没有任何意义
		    char* app_key = "5465465416948";
		    //生成 Java 中的字符串对象
		    LOGE("appkey = %s", app_key);
		    LOGW("this is a warning");
	    return env ->NewStringUTF(app_key);
}

@动态库中方法名的定义是要符合一定规范的，否则java调用动态库会提示找不到so文件,一下就Java_com_zsmarter_ndkdemo_NDKHelper_getAppKey进行分析   
@ Java : 固定前缀 （供java调用）    
@ com_zsmarter_ndkdemo : (供给java调用的类所在的包名)   
@ NDKHelper : （供给java调用的类名）    
@ getAppKey : (java类中调用的方法)  


2.相对应的android动态库的load代码如下（动态库加载类）   

		package com.zsmarter.ndkdemo;

		public class NDKHelper {
		    static {
	        System.loadLibrary("native-lib");
		    }
	    public static native String getAppKey();
		}

调用so文件的android类的定义的包名必须跟动态库中定义的包名一样，类名也必须跟so文件中指定的类名一样。		    
在Android的类中通过System.loadLibrary("native-lib")连接动态库,native-lib是动态库的名字,注意load动态库的时候不要带上固定前缀lib.     


3.真正在java代码中调用代码  

		Log.d(NDKHelper.getAppKey());   

>到上面这段代码运行后，结果是会打印三条log，两条上jni中打印的，一条是android拿到jni的返回后打印的。通过上述例子我们能够看出，so的使用分为如下几步，首先工程要引入so文件，然后再定义java类通过so名称加载so文件并且定义静态方法，使用时直接调用java类中的方法就行。                         

>so文件的使用我们都了解了，但真正so的使用场景并不会这么复杂，一个对外提供的so一般会自带jar进行so的加载，否则就要提供包路径我们自己创建加载类。                    

#### Spad中的动态库
>Spad中用到的动态库都是第三方的，以SDK的形式集成到spad中，so的调用基本都已经封装好，唯一要注意的一点是身份证读取的so文件的使用，现在spad可能会用到多个厂商的sdk，由于读取身份证的so统一由公安部提供的，定义的加载类也都是com.ivsign.android.IDCReader.IDCReaderSDK，多个sdk同时在一个工程中使用时会出现类重复冲突，冲突的解决方案是让厂家把sdk中的com.ivsign.android.IDCReader.IDCReaderSDK这个类不要放在jar中，暴露给我们自行处理so加载类。
