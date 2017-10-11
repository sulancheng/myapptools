package com.susu.hh.myapptools.utils;

import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator
 * on 2017/10/11.微信聊天界面点击语音时，喇叭的动画效果，下面上代码，该类是控制背景变化的工具类，不涉及语音录制和播放。

 图片资源：
 */

public class VoicePlayingBgUtil {
    private Handler handler;

    private ImageView imageView;

    private ImageView lastImageView;

    private Timer timer = new Timer();
    private TimerTask timerTask;

    private int i;

    private int modelType = 1;//类型

    private int[] leftVoiceBg;// = new int[] { R.drawable.ic_battery_0, R.drawable.gray2, R.drawable.gray3 };
    private int[] rightVoiceBg; //= new int[] { R.drawable.green1, R.drawable.green2, R.drawable.green3 };
    private int[] collectVoiceBg; //= new int[] { R.drawable.collect_voice_1, R.drawable.collect_voice_2, R.drawable.collect_voice_3 };

    public VoicePlayingBgUtil(Handler handler) {
        super();
        this.handler = handler;
    }

    public void voicePlay() {
        if (imageView == null) {
            return;
        }
        i = 0;
        timerTask = new TimerTask() {

            @Override
            public void run() {
                if (imageView != null) {
                    if (modelType == 1) {
                        changeBg(leftVoiceBg[i % 3], false);
                    }else if(modelType==2){
                        changeBg(rightVoiceBg[i % 3], false);
                    }else if(modelType==3){
                        changeBg(collectVoiceBg[i % 3], false);
                    }
                }
                else {
                    return;
                }
                i++;
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    public void stopPlay() {
        lastImageView = imageView;
        if (lastImageView != null) {
            switch (modelType) {
                case 1:
                    //changeBg(R.drawable.gray3, true);
                    break;
                case 2:
                    //changeBg(R.drawable.green3, true);
                    break;
                case 3:
                    //changeBg(R.drawable.collect_voice_3, true);
                default:
                    //changeBg(R.drawable.gray3, true);
                    break;
            }
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    private void changeBg(final int id, final boolean isStop) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isStop) {
                    lastImageView.setImageResource(id);
                }
                else {
                    imageView.setImageResource(id);

                }
            }
        });
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

}
