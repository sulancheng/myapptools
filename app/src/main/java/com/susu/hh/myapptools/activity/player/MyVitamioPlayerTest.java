package com.susu.hh.myapptools.activity.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.utils.MyLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MyVitamioPlayerTest extends Activity {

    public static  final String TAG = "PlayActivity";

    private VideoView mVideoView;
    private MediaController mMediaController;
    private MyMediaController myMediaController;

    String path1 = Environment.getExternalStorageDirectory() + "/Download/eva.mkv";

    private static final int TIME = 0;
    private static final int BATTERY = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            switch (msg.what) {
                case TIME:
                    String str = sdf.format(new Date());
                    myMediaController.setTime(str.toString());
                    mHandler.sendEmptyMessageDelayed(TIME,1000);
                    break;
                case BATTERY:
                    myMediaController.setBattery(msg.obj.toString());
                    break;
            }
        }
    };
    private int progress;

    @Override
    protected void onResume() {
        super.onResume();
        if(myMediaController!=null){
            myMediaController.setSeekBarChange(progress);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myMediaController!=null){
            progress = myMediaController.getProgress();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);//qianwanbiewangjile
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        toggleHideyBar();
        setContentView(R.layout.activity_vedio_test);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        initData();
        mMediaController = new MediaController(this);
        myMediaController = new MyMediaController(this, mVideoView, this);
        mMediaController.show(5000);
        mVideoView.setMediaController(myMediaController);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        mVideoView.requestFocus();
        //画面是否拉伸
//        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 16/9 );
        registerBoradcastReceiver();
        MyLog.i("myMediaController",myMediaController.getProgress()+"jja");
//        //开始播放
//        mVideoView.start();
        mHandler.sendEmptyMessageDelayed(TIME,0);
//        new Thread(this).start();
    }

    private void initData() {
        //得到播放地址
        Uri uri = getIntent().getData();//文件夹，图片浏览器，QQ空间
        List<MediaItem> mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        int position = getIntent().getIntExtra("position", 0);
        MyLog.i("playermyinitData","==="+position);
        if (mediaItems != null && mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(position);
            //boolean isNetUri = CommenUtils.isNetUri(mediaItem.getData());
            MyLog.i("MyVitamioPlayerTest",mediaItem.getData());
            mVideoView.setVideoPath(mediaItem.getData());
        } else if (uri != null) {
            //boolean isNetUri = CommenUtils.isNetUri(uri.toString());
            mVideoView.setVideoURI(uri);
        } else {
            Toast.makeText(this, "没有传递数据", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有的消息
        mHandler.removeCallbacksAndMessages(null);
        try {
            unregisterReceiver(batteryBroadcastReceiver);
        } catch (IllegalArgumentException ex) {

        }
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                Message msg = new Message();
                msg.obj = (level*100)/scale+"";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    public void registerBoradcastReceiver() {
        //注册电量广播监听
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);

    }

    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (get_current_ui_flags)
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            MyLog.i(TAG, "Turning immersive mode mode off. ");
        } else {
            MyLog.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }
}