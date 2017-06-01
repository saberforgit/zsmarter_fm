package com.zsmarter.qn;

import java.util.Map;

/**
 * Created by wangxf on 2017/4/17.
 */

public class ZS_QN_UploadOption {

    private static ZS_QN_UploadOption instance = null;
    private String uploadToken;
    private String uploadCacheDir;
    private String uploadFilePath;
    private String getTokenServePath;
    private String getTokenReqPath;
    private int uploadBlockSize;
    private Map<String, String> params;

    private ZS_QN_UploadOption() {
    }

    public static synchronized ZS_QN_UploadOption getInstance() {
        if (instance == null) {
            instance = new ZS_QN_UploadOption();
        }
        return instance;
    }

    public static void setInstance(ZS_QN_UploadOption instance) {
        ZS_QN_UploadOption.instance = instance;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    public String getUploadCacheDir() {
        return uploadCacheDir;
    }

    public void setUploadCacheDir(String uploadCacheDir) {
        this.uploadCacheDir = uploadCacheDir;
    }

    public int getUploadBlockSize() {
        return uploadBlockSize;
    }

    public void setUploadBlockSize(int uploadBlockSize) {
        this.uploadBlockSize = uploadBlockSize;
    }

    public String getGetTokenServePath() {
        return getTokenServePath;
    }

    public void setGetTokenServePath(String getTokenServePath) {
        this.getTokenServePath = getTokenServePath;
    }

    public String getGetTokenReqPath() {
        return getTokenReqPath;
    }

    public void setGetTokenReqPath(String getTokenReqPath) {
        this.getTokenReqPath = getTokenReqPath;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
