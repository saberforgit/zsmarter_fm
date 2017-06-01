package com.zsmarter.doubleinput.videorecord;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zsmarter.doubleinput.utils.XFResourcesIDFinder;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * 视频播放控件
 */
public class MovieRecorderView extends LinearLayout implements OnErrorListener {

  private SurfaceView mSurfaceView;
  private SurfaceHolder mSurfaceHolder;
  private ProgressBar mProgressBar;

  private MediaRecorder mMediaRecorder;
  private Camera mCamera;
  private Timer mTimer;// 计时器
  private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

  public int mWidth;// 视频分辨率宽度
  public int mHeight;// 视频分辨率高度
  private boolean isOpenCamera;// 是否一开始就打开摄像头
  public int mRecordMaxTime;// 一次拍摄最长时间
  private int mTimeCount;// 时间计数
  private File mRecordFile = null;// 文件
  public int videoEncodingBitRate;
  public int audioEncodingBitRate;
  public int cameraPos;
  public int cameraOri;
  public String videoName;
  public String videoPath;

  public MovieRecorderView(Context context) {
    this(context, null);
  }

  public MovieRecorderView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    // 初始化各项组件
    TypedArray a = context.obtainStyledAttributes(attrs, XFResourcesIDFinder.getStyleableArray
      (context, "MovieRecorderView"), defStyle, 0);
    SharedPreferences preferences = context.getSharedPreferences("video", MODE_PRIVATE);
    int videoMaxTime = preferences.getInt("videoMaxTime", 60);
    isOpenCamera = a.getBoolean(XFResourcesIDFinder.getResStyleableID
      ("MovieRecorderView_is_open_camera"), true);// 默认打开
    mRecordMaxTime = a.getInteger(XFResourcesIDFinder.getResStyleableID
      ("MovieRecorderView_record_max_time"), videoMaxTime);

    LayoutInflater.from(context).inflate(XFResourcesIDFinder.getResLayoutID
      ("movie_recorder_view"), this);
    mSurfaceView = (SurfaceView) findViewById(XFResourcesIDFinder.getResIdID("surfaceview"));
    mProgressBar = (ProgressBar) findViewById(XFResourcesIDFinder.getResIdID("progressBar"));
    mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量

    mSurfaceHolder = mSurfaceView.getHolder();
    mSurfaceHolder.addCallback(new CustomCallBack());
    mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    a.recycle();
  }

  private class CustomCallBack implements Callback {

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      if (!isOpenCamera) return;
      try {
        initCamera();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      if (!isOpenCamera) return;
      freeCameraResource();
    }

  }

  /**
   * 初始化摄像头
   *
   * @throws IOException
   */
  public void initCamera() throws IOException {
    if (mCamera != null) {
      freeCameraResource();
    }
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
          Camera.CameraInfo info = new Camera.CameraInfo();
          Camera.getCameraInfo(i, info);
          if (info.facing == cameraPos) {
            mCamera = Camera.open(i);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      freeCameraResource();
    }
    if (mCamera == null) return;

    // setCameraParams();
    // mCamera.setDisplayOrientation(cameraOri);
    mCamera.setPreviewDisplay(mSurfaceHolder);
    mCamera.startPreview();
    mCamera.unlock();
  }

  /**
   * 设置摄像头为竖屏
   *
   */
    /*private void setCameraParams() {
        if (mCamera != null) {
            Parameters params = mCamera.getParameters();
            params.set("orientation", "portrait");
            mCamera.setParameters(params);
        }
    }*/

  /**
   * 释放摄像头资源
   */
  public void freeCameraResource() {
    if (mCamera != null) {
      mCamera.setPreviewCallback(null);
      mCamera.stopPreview();
      mCamera.lock();
      mCamera.release();
      mCamera = null;
    }
  }

  private void createRecordDir() {
    File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator +
      videoPath);
    if (!sampleDir.exists()) {
      sampleDir.mkdirs();
    }
    File vecordDir = sampleDir;
    // 创建文件
    try {
      mRecordFile = new File(vecordDir + File.separator + videoName + ".mp4"); //mp4格式
      mRecordFile.createNewFile();
      Log.i("TAG", mRecordFile.getAbsolutePath());
    } catch (IOException e) {
    }
  }

  /**
   * 初始化
   *
   * @throws IOException
   */
  private void initRecord() throws Exception {
    mMediaRecorder = new MediaRecorder();
    mMediaRecorder.reset();
    if (mCamera != null) mMediaRecorder.setCamera(mCamera);
    mMediaRecorder.setOnErrorListener(this);
    mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
    mMediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
    mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
    mMediaRecorder.setOutputFormat(OutputFormat.MPEG_4);// 视频输出格式
    mMediaRecorder.setAudioEncoder(AudioEncoder.AAC);// 音频格式
    mMediaRecorder.setVideoEncoder(VideoEncoder.H264);// 视频录制格式
    mMediaRecorder.setVideoEncodingBitRate(videoEncodingBitRate * 1024);// 设置视屏码率
    mMediaRecorder.setAudioEncodingBitRate(audioEncodingBitRate * 1024);// 设置音频码率
    mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率
    mMediaRecorder.setOrientationHint(cameraOri);// 输出旋转90度，保持竖屏录制
    mMediaRecorder.setOutputFile(mRecordFile.getAbsolutePath());
    mMediaRecorder.prepare();
    mMediaRecorder.start();
  }

  /**
   * 开始录制视频
   *
   * @param
   * @param onRecordFinishListener 达到指定时间之后回调接口
   */
  public void record(final OnRecordFinishListener onRecordFinishListener) throws Exception {
    this.mOnRecordFinishListener = onRecordFinishListener;
    createRecordDir();
    if (!isOpenCamera)// 如果未打开摄像头，则打开
      initCamera();
    initRecord();
    mTimeCount = 0;// 时间计数器重新赋值
    mTimer = new Timer();
    mTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        // TODO Auto-generated method stub
        mTimeCount++;
        mProgressBar.setProgress(mTimeCount);// 设置进度条
        if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
          stop();
          if (mOnRecordFinishListener != null) mOnRecordFinishListener.onRecordFinish();
        }
      }
    }, 0, 1000);
  }

  /**
   * 停止拍摄
   */
  public void stop() {
    try {
      stopRecord();
      releaseRecord();
      freeCameraResource();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

  }

  /**
   * 停止录制
   */
  public void stopRecord() {
    mProgressBar.setProgress(0);
    if (mTimer != null) mTimer.cancel();
    if (mMediaRecorder != null) {
      // 设置后不会崩
      mMediaRecorder.setOnErrorListener(null);
      try {
        mMediaRecorder.stop();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (RuntimeException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
      mMediaRecorder.setPreviewDisplay(null);
    }
  }

  /**
   * 释放资源
   */
  private void releaseRecord() {
    if (mMediaRecorder != null) {
      mMediaRecorder.setOnErrorListener(null);
      try {
        mMediaRecorder.release();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    mMediaRecorder = null;
  }

  public int getTimeCount() {
    return mTimeCount;
  }

  /**
   * @return the mVecordFile
   */
  public File getmRecordFile() {
    return mRecordFile;
  }

  /**
   * 录制完成回调接口
   */
  public interface OnRecordFinishListener {
    public void onRecordFinish();
  }

  @Override
  public void onError(MediaRecorder mr, int what, int extra) {
    try {
      if (mr != null) mr.reset();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
