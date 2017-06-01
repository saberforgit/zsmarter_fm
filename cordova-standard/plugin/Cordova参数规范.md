#Cordova插件参数规范

cordova插件的参数传递包括入参和出参，入参是前端js传递给插件的参数，出参是插件返回给js的参数。

### 入参
入参在js都是以Object对象传递给插件的,参数必须在object对象的包裹中，并且以key:value的形式定义。

	单参数 :  { key : value }
	多参数 :  { key0 : value0 ,key1 : value1 ,key2 : value2 }
	数组 :    { key : [ value0,value1,value2,value3 ] }
	多重参数 :
			 { key0 : value0 ,  
			   key1 : {  key0 : value0 ,key1 : value1 ,key2 : value2 } ,
			   key2 : value2 }

### 出参 

出参在插件中必须用JSONObject包裹，并且以key:value的形式返回，具体场景如下

	JSONObject resultInfo = new JSONObject();
	单参数返回：resultInfo .put("key",value);
	多参数：为方便前端区分数据的所属，多参时要在外层再包裹一层。例如：
		  JSONObject IDCardInfo = new JSONObject();	 
		   IDCardInfo .put("name",name);
		   IDCardInfo .put("age",age);
		   IDCardInfo .put("no",nameno;
		   //对外层包裹，方便前端js读取
		   resultInfo .put("IDCardInfo",IDCardInfo);

### 异常处理
插件中的异常除了JSON异常都需要自己捕获，JSON异常本身被cordova的excute抛出了，以error的形式返回给了js。
我们自己捕获的异常，异常信息比较明确的我们直接
					
		对于异常信息比较明确的：
		try{   
			...	 
		}catch (Exception e) {
		  resultInfo .put("code","4001");
		  resultInfo .put("message",e.getMessage());
          e.printStackTrace();
        }
        对于异常信息不明确，不方便前端人员理解的，我们要补充说明：
        try{   
			...	 
		}catch (Exception e) {
	   	  resultInfo .put("code","4001");
		  resultInfo .put("message","参数传递错误:"+e.getMessage());
          e.printStackTrace();
        }
        
		
		