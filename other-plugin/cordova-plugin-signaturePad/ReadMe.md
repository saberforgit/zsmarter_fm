1.电磁笔模块使用方法
@方法一：showSignature ：显示手写模块

调用方式：
   1》参数：var param = {"width":1300,"height":700,"minWidth":1,"maxWidth":7,"penColor":"#000FFF"};
   width：模块宽度  height：模块高度  minWidth：最小笔画宽度  maxWidth:最大笔画宽度  penColor：笔画颜色
   2》调用： cordova.plugins.signaturePad.showSignature(param,function (res) {
                                                        alert(JSON.stringify(res));
                                                        document.getElementById("image").src=res.imageUrl;
                                                        }, function (error) {
                                                        })


