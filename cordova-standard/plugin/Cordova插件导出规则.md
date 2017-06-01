# Cordova插件导出规则

>模块说明:cordova插件开发完成后需要进行拆分成能够被前端人员实用的cordova标准插件,拆分过程大体分为如下几部:
>1)拆分原生代码,例如android(ios)下的代码
>2)拆分www下的js代码
>3)添加libs下的动态库或者jar
>4)最后一步是配置plugin.xml文件



### cordova插件添加以及使用过程
>.我们封装完成后的插件交给前端人员后,会通过 cordova plugin add * 命令进行插件安装,add的过程相当于把我们在plugin.xml中配置的文件全部拷贝到使用插件的主工程中.              
>.使用插件是通过我们定义的clobbers对象进行调用.



### cordova插件的文件结构
![image](https://github.com/saberforgit/Resource/blob/master/drawable/cordova_plugin_dir.png?raw=true)		

>1)红色箭头指向的是插件的名称,也是插件add时需要的名字,一般与插件中的pluginid相同.		
>2)绿色箭头指向的是需要添加的lib(包含jar和动态库文件)
>3)蓝色箭头指向的是原生代码所在的位置,包含多个平台的代码(例如android,ios)
>4)黄色箭头指向的是js调用文件,对应的是asset下对应的js文件
>5)黑色箭头指向的是整个插件的配置文件,包含所有与插件相关的东西

### 拆分原生代码
>首先第一步是拆分原生代码,具体操作是把android工程中的代码迁移到插件中来.

把android工程中与插件有关的所有代码按照原路径拷贝到src/android路径下:			
例如:原生的工程中com.zsmarter.card.nj下的代码都是与插件相关的代码,我们就要把com.zsmarter.card.nj下的代码都拷贝到插件中的src/android/目录下.			
如果插件中用到了res下的文件,我们要根据文件路径复制到相应的src/android/res下面,与原生代码的拷贝是一样的.


### 拆分www下的js代码

在插件开发的过程中我们会在assets/www/plugins/cordova-plugin-card/www/card.js中定义域插件的参数以及回调,相应的我们要把card.js文件拷贝到插件的www目录下.
>拷贝的时候需要修改js中的内容,一般的card.js文件定义如下:
			
	cordova.define("cordova-plugin-card.cordova.plugins.card", function(require, exports, module) {
	var exec = require('cordova/exec');
	exports.startCardListener = function( success, error) {
    exec(success, error, "CardReader", "startCardListener", []);
	};
	exports.closeCardListener = function( success, error) {
    exec(success, error, "CardReader", "closeCardListener", []);
	};
	});
我们需要把最外层的插件定义层去掉,因为在插件add的时候cordova帮我们添加了这层定义,修改后的文件如下:
		
	var exec = require('cordova/exec');
	exports.startCardListener = function( success, error) {
    exec(success, error, "CardReader", "startCardListener", []);
	};
	exports.closeCardListener = function( success, error) {
    exec(success, error, "CardReader", "closeCardListener", []);
	};
把修改后的js文件拷贝到www目录下就可以了.

### 添加libs

插件开发中可能会用到第三方的jar或者so文件,相应的我们要把用到的lib都拷贝到插件的libs下面.

### 配置plugin.xml文件
>最后一步就是进行xml文件的配置.一个完整的plugin.xml文件如下:
![image](https://github.com/saberforgit/Resource/blob/master/drawable/cordova_plugin_xml.png?raw=true)		
>第一行的id就是我们工程中给插件定义的pluginId.      
>第二行的name是插件定义的name
>js-module中的src是对应的我们的插件中的platforms/android/assets/www/plugins/cordova-plugin-card/www/下的js文  件,clobbers是对应的platforms/android/assets/www/cordova_plugins.js中的clobbers中的定义.               
>plarform标签是对应的多平台配置,有android,ios,等等
>platform中的config-file标签是xml复制命令,targer是需要修改的xml所在的位置,parent是需要修改的具体标签.
> 如图:权限是在AndroidManifest.xml中的/manifest标签下添加config-file标签中的所有权限.
> source-file标签是文件复制命令,我们的代码拷贝和libs拷贝都要用到.
> src是文件的来源地,target-dir是拷贝的目的地..


plugin.xml文件配置完成后,我们的插件导出就基本完成了.在接下来就是进行插件测试了,建议本地再新建一个cordova工程,专门用来测试已经导出的插件.