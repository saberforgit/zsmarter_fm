1.��ű�ģ��ʹ�÷���
@����һ��showSignature ����ʾ��дģ��

���÷�ʽ��
   1��������var param = {"width":1300,"height":700,"minWidth":1,"maxWidth":7,"penColor":"#000FFF"};
   width��ģ����  height��ģ��߶�  minWidth����С�ʻ����  maxWidth:���ʻ����  penColor���ʻ���ɫ
   2�����ã� cordova.plugins.signaturePad.showSignature(param,function (res) {
                                                        alert(JSON.stringify(res));
                                                        document.getElementById("image").src=res.imageUrl;
                                                        }, function (error) {
                                                        })


