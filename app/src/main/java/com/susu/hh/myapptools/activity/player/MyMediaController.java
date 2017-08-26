package com.susu.hh.myapptools.activity.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.susu.hh.myapptools.R;

import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 公共方法
 public void onFinishInflate()
 从XML加载完所有子视图后调用。初始化控制视图（调用initControllerView方法，设置事件、绑定控件和设置默认值）。

 public void setAnchorView(View view)
 设置MediaController绑定到一个视图上。例如可以是一个VideoView对象，或者是你的activity的主视图。
 参数
 view	可见时绑定的视图

 public void setMediaPlayer(MediaPlayerControl player)
 设置媒体播放器。并更新播放/暂停按钮状态。

 public void setInstantSeeking(boolean seekWhenDragging)
 设置用户拖拽SeekBar时画面是否跟着变化。（VPlayer默认完成操作后再更新画面）

 public void show()
 显示MediaController。默认显示3秒后自动隐藏。

 public void show(int timeout)
 显示MediaController。在timeout毫秒后自动隐藏。
 参数
 timeout	超时时间，单位毫秒。为0时控制条的hide()将被调用。

 public void setFileName(String name)
 设置视频文件名称。

 public void setInfoView(OutlineTextView v)
 设置保存MediaController的操作信息。例如进度改变时更新v。

 public void setAnimationStyle(int animationStyle)
 更改MediaController的动画风格。
 如果MediaController正在显示，调用此方法将在下一次显示时生效。
 参数
 animationStyle	在MediaController显示或隐藏时使用的动画风格。设置-1为默认风格，0没有动画，或设置一个明确的动画资源。

 public boolean isShowing()
 获取MediaController是否已经显示。

 public void hide()
 隐藏MediaController。

 public void setOnShownListener(OnShownListener l)
 注册一个回调函数，在MediaController显示后被调用。

 public void setOnHiddenListener(OnHiddenListener l)
 注册一个回调函数，在MediaController隐藏后被调用。

 public boolean onTouchEvent(MotionEvent event)
 调用show()并返回true。

 public boolean onTrackballEvent(MotionEvent ev)
 调用show()并返回false。

 public void setEnabled(boolean enabled)
 设置MediaController的可用状态。包括进度条和播放/暂停按钮。

 受保护方法
 protected View makeControllerView()
 创建控制播放的布局视图。子类可重写此方法创建自定义视图。
 */
public class MyMediaController extends MediaController implements View.OnClickListener {

    private static final int HIDEFRAM = 0;
    private static final int SHOW_PROGRESS = 2;

    private GestureDetector mGestureDetector;
    private ImageButton img_back;//返回键
    private ImageView img_Battery;//电池电量显示
    private ImageButton textViewTime;//时间提示
    private TextView textViewBattery,mediacontroller_file_name;//文字显示电池
    private VideoView videoView;
    private Activity activity;
    private Context context;
    private int controllerWidth = 0;//设置mediaController高度为了使横屏时top显示在屏幕顶端

    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg;
    private TextView mOperationTv;
    private AudioManager mAudioManager;

    private SeekBar seekBarProgress;
    private boolean progress_turn;
    private int progress;

    private boolean mDragging;
    private MediaPlayerControl player;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;

    //返回监听
    private OnClickListener backListener = new OnClickListener() {
        public void onClick(View v) {
            if(activity != null){
                activity.finish();
            }
        }
    };


    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case HIDEFRAM:
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    mOperationTv.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private final int controllerheigth;


    //videoview 用于对视频进行控制的等，activity为了退出
    public MyMediaController(Context context, VideoView videoView , Activity activity) {
        super(context);
        this.context = context;
        this.videoView = videoView;
        this.activity = activity;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);


        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        controllerWidth = outMetrics.widthPixels;
        controllerheigth = outMetrics.heightPixels;
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
        setInstantSeeking(true);
    }

    @Override
    protected View makeControllerView() {
        View v = View.inflate(context, R.layout.mymediacontroller, this);
       // View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("mymediacontroller", "layout", getContext().getPackageName()), this);
        v.setMinimumHeight(controllerWidth);
        //TOP
        img_back = (ImageButton) v.findViewById(R.id.mediacontroller_top_back);
        img_Battery = (ImageView) v.findViewById(R.id.mediacontroller_imgBattery);
        img_back.setOnClickListener(backListener);
        textViewBattery = (TextView)v.findViewById(R.id.mediacontroller_Battery);
        textViewTime = (ImageButton)v.findViewById(R.id.mediacontroller_time);
        textViewTime.setOnClickListener(this);
        mediacontroller_file_name = (TextView)v.findViewById(R.id.mediacontroller_file_name);
        mediacontroller_file_name.setOnClickListener(this);

        seekBarProgress = (SeekBar)v.findViewById(R.id.mediacontroller_seekbar);

        //mid
        mVolumeBrightnessLayout = (RelativeLayout)v.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView)v.findViewById(R.id.operation_bg);
        mOperationTv = (TextView) v.findViewById(R.id.operation_tv);
        mOperationTv.setVisibility(View.GONE);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        return v;

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                if (progress_turn) {
                    onFinishSeekBar();
                    progress_turn = false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        // 隐藏
        myHandler.removeMessages(HIDEFRAM);
        myHandler.sendEmptyMessageDelayed(HIDEFRAM, 1);
    }

    @Override
    public void onClick(View v) {
        if(v==textViewTime){
            String path = "";
            new MyClickTask().execute(path);
        }

    }

    //关于横竖吧切换
    private boolean fullscreen = false;
    public class MyClickTask extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            //String path = params[0];
            String title;
            MyVitamioPlayerTest myVitamioPlayerTest = (MyVitamioPlayerTest) MyMediaController.this.context;
            if (!fullscreen) {//设置RelativeLayout的全屏模式
//                * ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE：
//
//                * 希望Activity在横向屏幕上显示，但是可以根据方向传感器指示的方向来进行改变。
//                * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE：
//
//                * 希望Activity在横向屏上显示，也就是说横向的宽度要大于纵向的高度，并且忽略方向传感器的影响。
                //   System.out.println("-----linearLayout_player_nba----false------->>：1" );
                myVitamioPlayerTest.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//                myVitamioPlayerTest.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                myVitamioPlayerTest.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                title = "quan";
                //   LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)linearLayout_player_nba.getLayoutParams();
                fullscreen = true;//改变全屏/窗口的标记
            } else {//设置RelativeLayout的窗口模式
//                * ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT：
//
//                * 希望Activity在纵向屏幕上显示，但是可以根据方向传感器指示的方向来进行改变。
                myVitamioPlayerTest.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//                myVitamioPlayerTest.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                title = "xiao";
                fullscreen = false;//改变全屏/窗口的标记
            }

            Log.i("titledoInBackground",title);
            return title;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("resultdoInBackground",result);
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.INVISIBLE);//消除状态栏
            ViewGroup.LayoutParams lp = videoView.getLayoutParams();

            if(result.equals("quan")) {
                textViewTime.setBackgroundResource(R.drawable.xiaopin);
                lp.height = controllerWidth;
                lp.width = controllerheigth+200;
            }
            else {
                textViewTime.setBackgroundResource(R.drawable.quanpin);
                lp.height = (int) (controllerheigth * (1 - 0.618));
                lp.width = controllerWidth;
            }
            videoView.setLayoutParams(lp);
            decorView.setSystemUiVisibility(View.INVISIBLE);//显示状态栏
        }
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当收拾结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            progress = getProgress();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float beginX = e1.getX();
            float endX = e2.getX();
            float beginY = e1.getY();
            float endY = e2.getY();

            Display disp = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);
            int windowWidth = size.x;
            int windowHeight = size.y;
            if (Math.abs(endX - beginX) < Math.abs(beginY - endY)) {//上下滑动
                if (beginX > windowWidth * 3.0 / 4.0) {// 右边滑动 屏幕3/5
                    onVolumeSlide((beginY - endY) / windowHeight);
                } else if (beginX < windowWidth * 1.0 / 4.0) {// 左边滑动 屏幕2/5
                    onBrightnessSlide((beginY - endY) / windowHeight);
                }
            }else {
                onSeekTo((endX - beginX) / 20);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        //双击暂停或开始
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 滑动改变播放进度
     *
     * @param percent
     */
    @SuppressLint("SetTextI18n")
    private void onSeekTo(float percent) {
        //计算并显示 前进后退
        if (!progress_turn) {
           // onStartSeekBar();
            progress_turn = true;
        }
        int change = (int) (percent);
        if (change > 0) {
            mOperationBg.setImageResource(R.drawable.right);
        } else {
            mOperationBg.setImageResource(R.drawable.left);
        }
        mOperationTv.setVisibility(View.VISIBLE);

        mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        if (progress + change > 0) {
            if ((progress + change < 1000))
                mOperationTv.setText(setSeekBarChange(progress + change) + "/" + StringUtils.generateTime(videoView.getDuration()));
            else
                mOperationTv.setText(setSeekBarChange(1000) + "/" + StringUtils.generateTime(videoView.getDuration()));
        } else {
            mOperationTv.setText(setSeekBarChange(0) + "/" + StringUtils.generateTime(videoView.getDuration()));

        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
//            mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        if (index >= 10) {
            mOperationBg.setImageResource(R.drawable.volmn_100);
        } else if (index >= 5 && index < 10) {
            mOperationBg.setImageResource(R.drawable.volmn_60);
        } else if (index > 0 && index < 5) {
            mOperationBg.setImageResource(R.drawable.volmn_30);
        } else {
            mOperationBg.setImageResource(R.drawable.volmn_no);
        }
        //DecimalFormat    df   = new DecimalFormat("######0.00");
        mOperationTv.setText((int) (((double) index / mMaxVolume)*100)+"%");
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            //mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            mOperationTv.setVisibility(VISIBLE);

        }



        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);

        mOperationTv.setText((int) (lpa.screenBrightness * 100) + "%");
        if (lpa.screenBrightness * 100 >= 90) {
            mOperationBg.setImageResource(R.drawable.light_100);
        } else if (lpa.screenBrightness * 100 >= 80 && lpa.screenBrightness * 100 < 90) {
            mOperationBg.setImageResource(R.drawable.light_90);
        } else if (lpa.screenBrightness * 100 >= 70 && lpa.screenBrightness * 100 < 80) {
            mOperationBg.setImageResource(R.drawable.light_80);
        } else if (lpa.screenBrightness * 100 >= 60 && lpa.screenBrightness * 100 < 70) {
            mOperationBg.setImageResource(R.drawable.light_70);
        } else if (lpa.screenBrightness * 100 >= 50 && lpa.screenBrightness * 100 < 60) {
            mOperationBg.setImageResource(R.drawable.light_60);
        } else if (lpa.screenBrightness * 100 >= 40 && lpa.screenBrightness * 100 < 50) {
            mOperationBg.setImageResource(R.drawable.light_50);
        } else if (lpa.screenBrightness * 100 >= 30 && lpa.screenBrightness * 100 < 40) {
            mOperationBg.setImageResource(R.drawable.light_40);
        } else if (lpa.screenBrightness * 100 >= 20 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_30);
        } else if (lpa.screenBrightness * 100 >= 10 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.drawable.light_20);
        }


    }


    public void setTime(String time){
//        if (textViewTime != null)
//            textViewTime.setText(time);
    }
    //显示电量，
    public void setBattery(String stringBattery){
        if(textViewTime != null && img_Battery != null){
            textViewBattery.setText(stringBattery + "%");
            int battery = Integer.valueOf(stringBattery);
            if(battery < 15)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_15));
            if(battery < 30 && battery >= 15)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_15));
            if(battery < 45 && battery >= 30)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_30));
            if(battery < 60 && battery >= 45)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_45));
            if(battery < 75 && battery >= 60)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_60));
            if(battery < 90 && battery >= 75)img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_75));
            if(battery > 90 )img_Battery.setImageDrawable(getResources().getDrawable(R.drawable.battery_90));
        }
    }
    //隐藏/显示
    private void toggleMediaControlsVisiblity(){
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }
    //播放与暂停
    private void playOrPause(){
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
    }
}
