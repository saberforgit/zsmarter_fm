package com.zsmarter.softencode;

/**
 * Created by wangxf on 2017/4/27.
 */

public class VideoRecorderOptions {

    private int videoMaxTime;
    private int mWidth;
    private int mHeight;
    private int frame;
    private int videoEncodingBitRate;
    private int audioEncodingBitRate;
    private int audioSampleRate;
    private int waterPaddingWidth;
    private int waterPaddingHeight;
    private int frameRate;
    private int distinguishability;
    private boolean isHaveWaterMark;
    private String videoPath;

    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(int audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }

    public int getDistinguishability() {
        return distinguishability;
    }

    public void setDistinguishability(int distinguishability) {
        this.distinguishability = distinguishability;
    }

    public boolean isHaveWaterMark() {
        return isHaveWaterMark;
    }

    public void setHaveWaterMark(boolean haveWaterMark) {
        isHaveWaterMark = haveWaterMark;
    }

    private static VideoRecorderOptions videoRecorderOptions;

    private VideoRecorderOptions() {
    }

    public static VideoRecorderOptions getInstance() {
        if (videoRecorderOptions == null) {
            videoRecorderOptions = new VideoRecorderOptions();
        }
        return videoRecorderOptions;
    }

    public int getWaterPaddingWidth() {
        return waterPaddingWidth;
    }

    public void setWaterPaddingWidth(int waterPaddingWidth) {
        this.waterPaddingWidth = waterPaddingWidth;
    }

    public int getWaterPaddingHeight() {
        return waterPaddingHeight;
    }

    public void setWaterPaddingHeight(int waterPaddingHeight) {
        this.waterPaddingHeight = waterPaddingHeight;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getVideoMaxTime() {
        return videoMaxTime;
    }

    public void setVideoMaxTime(int videoMaxTime) {
        this.videoMaxTime = videoMaxTime;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getVideoEncodingBitRate() {
        return videoEncodingBitRate;
    }

    public void setVideoEncodingBitRate(int videoEncodingBitRate) {
        this.videoEncodingBitRate = videoEncodingBitRate;
    }

    public int getAudioEncodingBitRate() {
        return audioEncodingBitRate;
    }

    public void setAudioEncodingBitRate(int audioEncodingBitRate) {
        this.audioEncodingBitRate = audioEncodingBitRate;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
