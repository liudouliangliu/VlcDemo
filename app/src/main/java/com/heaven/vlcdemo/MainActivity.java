package com.heaven.vlcdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView mTaxiSurf = null;
    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_CODE = 0;
    /**
     * 视频宽高
     */
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mTaxiPlayer = null;
    private LibVLC libVLCTaxi = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (!OSUtils.checkPermission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CODE);
        }else {
            initVideo();
        }

        getPackageInfo();

    }

    private void getPackageInfo() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> packageInfos = getPackageManager().queryIntentActivities(intent, 0);

        for (int i = 0; i < packageInfos.size(); i++) {
            String launcherActivityName = packageInfos.get(i).activityInfo.name;
            String packageName = packageInfos.get(i).activityInfo.packageName;
            String appName = packageInfos.get(i).activityInfo.name;
            Log.i("appappinfo", appName+"---"+packageName + " -- launcherActivityName: " + launcherActivityName);
            Log.e("appappinfo", i + " -- packageName: " + packageName);
        }

        }

    private void initVideo() {
        mTaxiSurf.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mVideoWidth = mTaxiSurf.getMeasuredWidth();
                mVideoHeight = mTaxiSurf.getMeasuredHeight();
                String mTaxiVideoUrl = Environment.getExternalStorageDirectory() + File.separator+ "video_default.wmv";
                mTaxiVideoUrl = "http://210.12.220.60:8093/live/104hd";
                Media media = new Media(libVLCTaxi, Uri.parse(mTaxiVideoUrl));
                mTaxiPlayer.setMedia(media);
                media.release();
                //宽，高  播放窗口的大小
                Log.i("info","width and height ："+mVideoWidth+"---"+mVideoHeight);
                mTaxiPlayer.getVLCVout().setWindowSize(mVideoWidth, mVideoHeight);
                //宽，高  画面大小
                mTaxiPlayer.setAspectRatio(mVideoWidth + ":" + mVideoHeight);
                mTaxiPlayer.setScale(0);
                mTaxiPlayer.play();
            }
        });

        //初始化vlc
        ArrayList<String> options = new ArrayList<>();
        options.add("-vvv");
        if (libVLCTaxi == null) {
            libVLCTaxi = new LibVLC(MainActivity.this, options);
        }
        if (mTaxiPlayer == null) {
            mTaxiPlayer = new MediaPlayer(libVLCTaxi);
            IVLCVout ivTaxilcVout = mTaxiPlayer.getVLCVout();
            ivTaxilcVout.setVideoView(mTaxiSurf);
            ivTaxilcVout.attachViews();
            mTaxiPlayer.setEventListener(new MediaPlayer.EventListener() {
                @Override
                public void onEvent( MediaPlayer.Event event ) {
                    Log.i("info","event.type ："+event.type);
                }
            });
        }

    }

    private void initView() {
        mTaxiSurf = findViewById(R.id.surface_video_left);
        findViewById(R.id.startMap).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.startMap){

        }
    }
}
