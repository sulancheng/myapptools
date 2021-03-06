package com.susu.hh.myapptools.activity.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.utils.MyLog;
import com.susu.hh.myapptools.utils.MyToast;
import com.susu.hh.myapptools.utils.SreenUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
/*
* 在这里写有关于视频列表的问题，在阿里云播放端有例子，原理就是利用texttureview的实习替换
*
* 实际中有很牛的第三方框架管理 https://github.com/danylovolokh/VideoPlayerManager 实现列表切换自动播放
* 下面是使用的例子：
*   https://www.cnblogs.com/You0/p/5716266.html
*
* */

public class MyVitamioPlayerTest extends Activity {

    public static final String TAG = "MyVitamioPlayerTest";
    private static final int STARTNOF = 1001;
    private static final int SETPROGRESS = 1002;

    private VideoView mVideoView;
    //    private MediaController mMediaController;
    private MyMediaController myMediaController;

    String path1 = Environment.getExternalStorageDirectory() + "/Download/eva.mkv";

    private static final int TIME = 0;
    private static final int BATTERY = 1;
    private boolean showDanmaku;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            switch (msg.what) {
                case TIME:
                    String str = sdf.format(new Date());
                    myMediaController.setTime(str.toString());
                    mHandler.sendEmptyMessageDelayed(TIME, 1000);
                    break;
                case BATTERY:
                    myMediaController.setBattery(msg.obj.toString());
                    break;
                case STARTNOF:
                    mVideoView.start();
                    break;
                case SETPROGRESS:
                    myMediaController.setSeekBarChange(progress);
                    mVideoView.start();
                    MyLog.i("进度是progress读取 = " + progress);
                    break;

            }
        }
    };
    private int progress;
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private LinearLayout ll_list;
    private FrameLayout fl_par;

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
        if (myMediaController != null) {
            mHandler.sendEmptyMessageDelayed(SETPROGRESS, 300);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
        if (myMediaController != null) {
            progress = myMediaController.getProgress();
            mVideoView.pause();
            MyLog.i("进度是progress = " + progress);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());//qianwanbiewangjile
//        //定义全屏参数
//        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        //获得当前窗体对象
//        Window window = this.getWindow();
//        //设置当前窗体为全屏显示
//        window.setFlags(flag, flag);

        //toggleHideyBar();
        setContentView(R.layout.activity_vedio_test);

        mVideoView = (VideoView) findViewById(R.id.surface_view);
        fl_par = (FrameLayout) findViewById(R.id.fl_par);
        //-------------------------------------------------------------
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, SreenUtil.dip2px(this, 240));;
//      params2.height =  (int) (ScreenUtils.getScreenWidth(this) * 9.0f / 16);
        fl_par.setLayoutParams(params2);
        //---------------------------------------------------------------
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        initData();
        initdanmu();
        MediaController  mMediaController = new MediaController(this);
        myMediaController = new MyMediaController(this, mVideoView, this);

        myMediaController.show(5000);
//        mMediaController.show(5000);
        mVideoView.setMediaController(myMediaController);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        mVideoView.requestFocus();





        //画面是否拉伸
//       mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 16/9 );
        registerBoradcastReceiver();
        MyLog.i("myMediaController", myMediaController.getProgress() + "zgssdadjja");
        mHandler.sendEmptyMessageDelayed(TIME, 0);
        setlistener();
        //开始播放
//        mHandler.sendEmptyMessageDelayed(STARTNOF, 2500);

    }

    private void initdanmu() {
        danmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
    }
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    /**
     * 向弹幕View中添加一条弹幕
     * @param content
     *          弹幕的具体内容
     * @param  withBorder
     *          弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.RED;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }




    private void setlistener() {
        //准备好的监听
        mVideoView.setOnPreparedListener(new MyOnPreparedListener());

        //播放出错了的监听
        mVideoView.setOnErrorListener(new MyOnErrorListener());

        //播放完成了的监听
        mVideoView.setOnCompletionListener(new MyOnCompletionListener());

        mVideoView.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                MyLog.i("播放器："+"拖动进度条动作完成！");
            }
        });
    }

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        //当底层解码准备好的时候
        @Override
        public void onPrepared(MediaPlayer mp) {
//            if (mVideoView != null) {
//                mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
////                mVideoView.invalidate();
//            }
            mVideoView.start();//开始播放
            myMediaController.hide();//默认是隐藏控制面板

        }
    }

    private class MyOnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            MyLog.i("播放器："+"播放失败！");
            MyToast.makeText(MyVitamioPlayerTest.this,"对不起，播放失败!",0);
            finish();
//            Toast.makeText(SystemVideoPlayer.this, "播放出错了哦", Toast.LENGTH_SHORT).show();
//            showErrorDialog();
            //1.播放的视频格式不支持--跳转到万能播放器继续播放
            //2.播放网络视频的时候，网络中断---1.如果网络确实断了，可以提示用于网络断了；2.网络断断续续的，重新播放
            //3.播放的时候本地文件中间有空白---下载做完成
            return true;
        }
    }

    private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
//            playNextVideo();
//            Toast.makeText(SystemVideoPlayer.this, "播放完成了="+uri, Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        //得到播放地址
        Uri uri = getIntent().getData();//文件夹，图片浏览器，QQ空间
        List<MediaItem> mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        int position = getIntent().getIntExtra("position", 0);
        MyLog.i("playermyinitData", "===" + position);
        if (mediaItems != null && mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(position);
            //boolean isNetUri = CommenUtils.isNetUri(mediaItem.getData());
            MyLog.i("MyVitamioPlayerTest", mediaItem.getData());
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
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
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
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                mHandler.obtainMessage(BATTERY, (level * 100) / scale + "").sendToTarget();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        int layoutDirection = newConfig.orientation;
        Log.i("onConfigurationChanged", layoutDirection + "   getRequestedOrientation=" + getRequestedOrientation());
        if (layoutDirection == Configuration.ORIENTATION_LANDSCAPE) {//横着是2
            //横屏

            ll_list.setVisibility(View.GONE);
            myMediaController.title = "quan";
            myMediaController.fullscreen = true;
            myMediaController.textViewTime.setBackgroundResource(R.drawable.xiaopin);
            //隐藏状态栏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);//消除状态栏

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width,height);;
//      params2.height =  (int) (ScreenUtils.getScreenWidth(this) * 9.0f / 16);
//            params2.height =height;
//            params2.width = width;
//            params2.height = FrameLayout.LayoutParams.MATCH_PARENT;
//            params2.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            params2.height = SreenUtil.getScreenHeight(this);
//            params2.width = SreenUtil.getScreenWidth(this);
            fl_par.setLayoutParams(params2);
            if (mVideoView != null) {
                mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            }

        } else if (layoutDirection == Configuration.ORIENTATION_PORTRAIT){
            ll_list.setVisibility(View.VISIBLE);
            //否则就是1  也就是竖屏
            myMediaController.title = "xiao";
            myMediaController.fullscreen = false;
            myMediaController.textViewTime.setBackgroundResource(R.drawable.quanpin);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width,SreenUtil.dip2px(this, 240));
//            params2.height =  (int) (ScreenUtils.getScreenWidth(this) * 9.0f / 16);
//            params2.height = SreenUtil.dip2px(this, 240);
            fl_par.setLayoutParams(params2);
            //显示状态栏
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);//显示状态栏
            if (mVideoView != null) {
                mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            }
        }

//        if (layoutDirection != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
//                }
//            }, 6000);
//        }
        //Configuration.ORIENTATION_LANDSCAPE;
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            //当前为横屏， 在此处添加额外的处理代码
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);//随着用户的使用变化
//            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);//消除状态栏
//
//
//
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            int width = dm.widthPixels;
//            int height = dm.heightPixels;
//            //vWidth = width;
//            ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
//            lp.width = width;
//            lp.height = height;
//
//            mVideoView.setLayoutParams(lp);
//            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);//显示状态栏
//
//        } else {
//            //当前为竖屏， 在此处添加额外的处理代码
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);//随着用户的使用变化
//
//
//
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            int width = dm.widthPixels;
//            int height = dm.heightPixels;
//            //vWidth = width;
//            ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
//            lp.width = width;
//            lp.height = (int) (height * (1 - 0.618));
//
//            mVideoView.setLayoutParams(lp);
//            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);//显示状态栏
//        }
//
//        // 检测屏幕的方向：纵向或横向
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//
//        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//        }
//
//
//        //检测实体键盘的状态：推出或者合上
//        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//            //实体键盘处于推出状态，在此处添加额外的处理代码
//        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//            //实体键盘处于合上状态，在此处添加额外的处理代码
//        }


    }
}
