package com.zsmarter.qn;

import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;

import org.apache.cordova.BuildConfig;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangxf on 2017/4/14.
 */

public class Plugin_QN_Upload extends CordovaPlugin {

    //取消上传控制位
    private volatile boolean isCancelled = false;
    //    private CallbackContext mCallBack;
//    private Message msg;
//    //回调统一处理handler
//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message message) {
//            try {
//                switch (message.what) {
//                    case QiniuLabConfig.ERROR_WHAT:
//                        mCallBack.error(new JSONObject().put("error", String.valueOf(message
// .obj)));
//                        break;
//                    case QiniuLabConfig.SUCCESS_WHAT:
//                        mCallBack.success(new JSONObject().put("response", String.valueOf(message
//                                .obj)));
//                        break;
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//    });

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws
            JSONException {
//        mCallBack = callbackContext;
        try {
            switch (action) {
                case "upload":
                    JSONObject params = args.getJSONObject(0);
                    ZS_QN_UploadOption zs_qn_uploadOption = initUploadParams(params,callbackContext);
                        upload(zs_qn_uploadOption, callbackContext);
                    break;
                case "cancel":
                    cancel();
                    break;
            }
        } catch (Exception e) {
//            sendErrorMessage(e.getMessage());
            callbackContext.error(new JSONObject().put("error", e.getMessage()));
        }


        return true;
    }


//    //错误回调
//    private void sendErrorMessage(String message) {
//        msg = new Message();
//        msg.what = QiniuLabConfig.ERROR_WHAT;
//        msg.obj = message;
//        handler.sendMessage(msg);
//    }
//
//    //成功回调
//    private void sendSuccessMessage(String message) {
//        msg = new Message();
//        msg.what = QiniuLabConfig.SUCCESS_WHAT;
//        msg.obj = message;
//        handler.sendMessage(msg);
//    }

    /**
     * JsonObject转Map
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private Map<String, String> fromJSONObject(JSONObject jsonObject) throws JSONException {
        Iterator it = jsonObject.keys();
        Map<String, String> params = new HashMap<String, String>();
        while (it.hasNext()) {
            String key = (String) it.next();
            params.put("x:" + key, jsonObject.optString(key));
        }
        return params;
    }

    /**
     * 初始化js传递的参数,设置默认值
     *
     * @param params
     * @return
     */
    private ZS_QN_UploadOption initUploadParams(JSONObject params, CallbackContext
            callbackContext) throws JSONException {
        ZS_QN_UploadOption zs_qn_uploadOption = ZS_QN_UploadOption.getInstance();
        try {
            zs_qn_uploadOption.setGetTokenServePath(params.optString("getTokenServePath") == null
                    || params.optString("getTokenServePath") == "" ? QiniuLabConfig
                    .REMOTE_SERVICE_SERVER : params.optString("getTokenServePath"));
            zs_qn_uploadOption.setGetTokenReqPath(params.optString("getTokenReqPath") == null ||
                    params.optString("getTokenReqPath") == "" ? QiniuLabConfig
                    .SIMPLE_UPLOAD_WITHOUT_KEY_PATH : params.optString("getTokenReqPath"));
            zs_qn_uploadOption.setUploadCacheDir(params.optString("uploadCacheDir") == null ||
                    params.optString("uploadCacheDir") == "" ? QiniuLabConfig
                    .UPLOAD_LOCAL_CACHE_PATH : params.optString("uploadCacheDir"));
            zs_qn_uploadOption.setUploadBlockSize(params.optInt("uploadBlockSize") == 0 ?
                    QiniuLabConfig.UPLOAD_LOCAL_CACHE_SIZE : params.optInt("uploadBlockSize"));
            zs_qn_uploadOption.setParams(fromJSONObject(params.getJSONObject("params")));
            zs_qn_uploadOption.setUploadFilePath(params.optString("uploadFilePath"));
        } catch (JSONException e) {
//            sendErrorMessage(e.getMessage());
            callbackContext.error(new JSONObject().put("error", e.getMessage()));
        }
        return zs_qn_uploadOption;
    }

    /**
     * 上传
     *
     * @param zs_qn_uploadOption
     */
    private void upload(final ZS_QN_UploadOption zs_qn_uploadOption, final CallbackContext
            callbackContext) throws JSONException {
        final int uploadBlockSize = zs_qn_uploadOption.getUploadBlockSize();
        final String uploadCacheDir = zs_qn_uploadOption.getUploadCacheDir();
        final String uploadFilePath = zs_qn_uploadOption.getUploadFilePath();
        //从业务服务器获取上传凭证
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient httpClient = new OkHttpClient();
                    Request req = new Request.Builder().url(QiniuLabConfig.makeUrl
                            (zs_qn_uploadOption.getGetTokenServePath(), zs_qn_uploadOption
                                    .getGetTokenReqPath())).method("GET", null).build();
                    Response resp = null;
                    try {
                        resp = httpClient.newCall(req).execute();
                        JSONObject jsonObject = new JSONObject(resp.body().string());
                        String uploadToken = jsonObject.optString("uptoken");
                        if (BuildConfig.DEBUG) {
                            Log.d("uptoken", uploadToken);
                        }
                        //TODO upload
                        uploadWithToken(uploadToken, uploadFilePath, uploadCacheDir,
                                uploadBlockSize, zs_qn_uploadOption.getParams(), callbackContext);
                    } catch (IOException e) {
                        Log.e("error", "get token failed:" + e.getMessage());
//                        sendErrorMessage("Get token failed," + e.getMessage());
                        callbackContext.error(new JSONObject().put("error", e.getMessage()));
                    } catch (JSONException e) {
                    } finally {
                        if (resp != null) {
                            resp.body().close();
                        }
                    }
                } catch (Exception e) {
//                    sendErrorMessage(e.getMessage());
                    try {
                        callbackContext.error(new JSONObject().put("error", e.getMessage()));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();

    }

    // 初始化、执行上传

    private void uploadWithToken(String uploadToken, String uploadFilePath, String
            uploadCacheDir, int uploadBlockSize, Map<String, String> params, final
    CallbackContext callbackContext) throws JSONException {
        try {
            UploadOptions uploadOptions = new UploadOptions(params, null, false, new
                    UpProgressHandler() {
                public void progress(String key, double percent) {
                    Log.i("qiniu", String.valueOf(percent));
                }
            }, new UpCancellationSignal() {
                public boolean isCancelled() {
                    return isCancelled;
                }
            });

            makeDirectory(uploadCacheDir);

            Recorder recorder = null;
            try {
                recorder = new FileRecorder(uploadCacheDir);
            } catch (Exception e) {
            }
            //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
            //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
            KeyGenerator keyGen = new KeyGenerator() {
                public String gen(String key, File file) {
                    // 不必使用url_safe_base64转换，uploadManager内部会处理
                    // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                    return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                }
            };

            Configuration config = new Configuration.Builder().chunkSize(uploadBlockSize)
                    //分片上传时，每片的大小。 默认256K
                    .recorder(recorder, keyGen) //
                    .connectTimeout(10) // 链接超时。默认10秒
                    .responseTimeout(60) // 服务器响应超时。默认60秒
                    .zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                    .build();
            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
            UploadManager uploadManager = new UploadManager(config);
//    uploadToken = "I3Do1z6q68LrKUdMH_ffIjdIsL3iXuingZJ9NZWk:gVZp4yWc2-X9SDBXYpATBOT8P2Y
// =:eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9kaS56c21hcnRlci5jb20vZGkvd3MvdXBsb2FkL2NhbGxiYWNrIiwic2NvcGUiOiJ3aXJ0aCIsImNhbGxiYWNrQm9keVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIiwicmV0dXJuQm9keSI6IntcImtleVwiOlwiJChrZXkpXCIsXCJoYXNoXCI6XCIkKGV0YWcpXCIsXCJidWNrZXRcIjpcIiQoYnVja2V0KVwiLFwiZnNpemVcIjokKGZzaXplKX0iLCJjYWxsYmFja0JvZHkiOiJ7XCJoYXNoXCI6XCIkKGV0YWcpXCIsXCJidWNrZXRcIjpcIiQoYnVja2V0KVwiLFwiZnNpemVcIjpcIiQoZnNpemUpXCIsXCJwb2xpY3lOb1wiOlwiJCh4OnBvbGljeU5vKVwiLFwibWltZVR5cGVcIjpcIiQobWltZVR5cGUpXCIsXCJmbmFtZVwiOlwiJChmbmFtZSlcIixcImtleVwiOlwiJChrZXkpXCJ9IiwiZGVhZGxpbmUiOjE0OTI0NTEwOTd9";
            File data = new File(uploadFilePath);
            uploadManager.put(data, null, uploadToken, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject res) {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK()) {
                        Log.i("qiniu", "Upload Success");
//                        sendSuccessMessage(String.valueOf(res));
                        try {
                            callbackContext.success(new JSONObject().put("response", res));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("qiniu", "Upload Fail");
//                        sendErrorMessage(info.error);
                        try {
                            callbackContext.error(new JSONObject().put("error", (info.error)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }, uploadOptions);
        } catch (Exception e) {
//            sendErrorMessage(e.getMessage());
            callbackContext.error(new JSONObject().put("error", e.getMessage()));
        }
    }


    // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
    private void cancel() {
        isCancelled = !isCancelled;
    }


    /**
     * 生成文件夹
     *
     * @param filePath
     */
    public void makeDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
//            sendErrorMessage(e.getMessage());
            e.printStackTrace();
        }
    }
}
