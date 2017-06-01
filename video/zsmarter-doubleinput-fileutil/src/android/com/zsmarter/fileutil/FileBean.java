package com.zsmarter.fileutil;

/**
 * Created by hechengbin on 2017/3/21.
 */

public class FileBean {

    private String fileName;
    private String fileLength;


    public FileBean(String fileName, String fileLength) {
        this.fileName = fileName;
        this.fileLength = fileLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }
}
