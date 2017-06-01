#zsmarter-phone			


>插件简介:本插件用于拦截电话

			
插件使用方法		
cordova add plugin zsmarter-phone			
### startPhoneCallListener()
简介:开启拦截监听
调用方式:

		   var startListener = function(){
            zsmarter.phone.startPhoneCallListener(
            function(res){ console.info(JSON.stringify(res));},
            function(err){console.info(JSON.stringify(err));} );
            }


### stopPhoneCallListener()
简介:关闭拦截监听
使用方法:

		 var stopListener = function(){
            zsmarter.phone.stopPhoneCallListener(
            function(res){ console.info(JSON.stringify(res));},
            function(err){console.info(JSON.stringify(err));} );
            }

注意:在开启监听后会阻止电话接入,关闭监听后不影响电话接入		