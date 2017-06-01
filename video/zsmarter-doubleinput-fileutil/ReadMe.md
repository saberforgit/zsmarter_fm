# zsmarter-doubleinput-fileutil

@(插件简介)[何成斌|QQ:472966681]



**zsmarter-doubleinput-fileutil**
创建目录，删除文件，拷贝文件，重命名文件的cordova插件：

- **创建目录** ：创建一级或多级目录；
- **删除文件** ：删除指定文件或删除目录下全部文件；
- **拷贝文件** ：拷贝文件到指定目录；
- **重命名文件** ：重命名文件；
- **获取文件对象** ：获取文件对象；
- **获取全部文件** ：获取某路径下全部文件；

-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-doubleinput-fileutil  添加插件

##使用方法
###拷贝文件(copyFile)
#### 参数
| Params      |   type |    value    |
| :---------- | -----: | :---------: |
| fileOldPath | string |   /Music/   |
| fileOldName | string |  11111.m4a  |
| fileNewPath | string | /Test/1111/ |
| fileNewName | string |  11111.m4a  |

>**参数介绍：**
>@fileOldPath  要拷贝文件的原路径
>@fileOldName  要拷贝文件的原名称
>@fileNewPath  要拷贝文件的目标
>@fileNewName  要拷贝文件的新名称

#### 调用示例
var copy = function(){
zsmarter.fileutil.copyFile(
function(res){
console.info(res);
var success = res.copySuccess;
},
function(err){
console.info(err);

},
{
"fileOldPath":"/Music/",
"fileOldName":"11111.m4a",
"fileNewPath":"/Test/1111/",
"fileNewName":"11111.m4a"
}
);}

#### 返回结果
>boolean  success 创建是否成功



###删除文件(deleteFile)
#### 参数
| Params      | type | value |
| :---------- | ---: | :---: |
| deleteArray |   [] |       |


#### 调用示例
#####删除多个文件
```
var delete1 = "/storage/emulated/0/zsmater/test/audio/";

var delete2 = "/storage/emulated/0/zsmater/test/Video/";


var deleteArray = [delete1,delete2];

var delereFile = function(){

zsmarter.fileutil.deleteFile(

function(res){

console.info(res);

},

function(err){
alert(err);
},{

"deleteArray":deleteArray

}

);}
```


#### 返回结果
> boolean  success 创建是否成功

###文件重命名(renameFile)
#### 参数
| Params         |   type |   value   |
| :------------- | -----: | :-------: |
| fileRenamePath | string |  /Music/  |
| fileOldName    | string | 11111.m4a |
| fileNewName    | string | 11111.m4a |
> **参数介绍：**
> @fileRenamePath  要重命名文件的路径
> @fileOldName  要重命名文件的原名称
> @fileNewName  要重命名文件的新名称

#### 调用示例

var rename = function(){
zsmarter.fileutil.renameFile(
function(res){
console.info(res);
var success = res.renameSuccess;
},
function(err){
console.info(err);

},{
"fileRenamePath":"/Music/",
"fileOldName":"11111.m4a",
"fileNewName":"22222.m4a"
}
);}
#### 返回结果
>boolean  success 创建是否成功


###获取文件对象(getFile)
#### 参数
| Params |   type |  value  |
| :----- | -----: | :-----: |
| path   | string | /Music/ |

> **参数介绍：**
> @path  文件的路径

#### 调用示例

var getFile = function(){
zsmarter.fileutil.getFile(
function(res){

var file = res.file;
alert(name);
},
function(err){
console.info(err);

},
{
"path":"/Music/"

}
);}
#### 返回结果
>obj  file 文件对象
>String file.fileName 文件名称
>>String file.fileLength 文件大小


###获取文件名称(getFileName)
#### 参数
| Params |   type |  value  |
| :----- | -----: | :-----: |
| path   | string | /Music/ |

> **参数介绍：**
> @path  文件的路径

#### 调用示例

var getFileName = function(){
zsmarter.fileutil.getFileName(
function(res){

var name = res.name;
alert(name);
},
function(err){
console.info(err);

},
{
"path":"/Music/"

}
);}
#### 返回结果
>string  name 文件名称

###获取文件大小(getFileLength)
#### 参数
| Params |   type |  value  |
| :----- | -----: | :-----: |
| path   | string | /Music/ |

> **参数介绍：**
> @path  文件的路径

#### 调用示例

var getFileLength = function(){
zsmarter.fileutil.getFileLength(
function(res){

var length = res.length;
alert(length);
},
function(err){
console.info(err);

},
{
"path":"/Music/"
}
);}
#### 返回结果
>string  length 文件大小


###获取全部文件(getAllFile)
#### 参数
| Params |   type |  value  |
| :----- | -----: | :-----: |
| path   | string | /Music/ |

> **参数介绍：**
> @path  文件的路径

#### 调用示例

	var getAllFile = function(){
	zsmarter.fileutil.getAllFile(
	            function(res){
	
	                var fileList = res.fileList;
	                alert(JSON.stringify(fileList));
	        },
	            function(err){
	                console.info(err);
	
	        },
	        {
	            "path":"/storage/emulated/0/zsmater/"
	        }
	        );}
#### 返回结果
>jasonArray  fileList 文件绝对路径数组
>key  absolutePath 文件绝对路径

-------------------
> **注意：**
> 添加工程前需要在:project/platforms/android下的build.gradle中的:
> compileOptions {
> sourceCompatibility JavaVersion.VERSION_1_7
> targetCompatibility JavaVersion.VERSION_1_7
> }

>   修改java版本为1_7  (修改示例如上 )





