package com.susu.hh.myapptools.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.SPUtils;


/**
 * Created by Administrator on 2016/5/20.
 */
public class SplashActivity extends Activity {
    private RelativeLayout mRelativeLayout;
    private TextView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);
        //ViewUtils.inject(this);
        startAnim3();
        //startAnim2();
    }

    private void startAnim2() {
        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        //mRelativeLayout.startAnimation(animation);
    }

    private void startAnim() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(3000);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = null;
                //if(SPUtils.getBoolean(SplashActivity.this, Constant.EXPER)){
                    intent = new Intent(SplashActivity.this, MainActivity.class);
               // }else {
                //    intent = new Intent(SplashActivity.this, GuideActivity.class);
               // }
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRelativeLayout.startAnimation(animationSet);
    }
    private void startAnim3(){
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        RotateAnimation rotateAnimation = new RotateAnimation(0,1800,Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //绕Y轴旋转。
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRelativeLayout, "rotationY", 0,270);
        animator.setDuration(2000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                1.0f,Animation.RELATIVE_TO_PARENT,0.0f,Animation.RELATIVE_TO_PARENT,1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f);
        //ta.setInterpolator(new OvershootInterpolator());
        ta.setInterpolator(new DecelerateInterpolator());
        //ta.setDuration(3000);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(ta);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(3000);
        //mRelativeLayout.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //mImageView.startAnimation(startAnim5());
                Intent intent = null;
                if(SPUtils.getBoolean(SplashActivity.this, "11")){
                    //intent = new Intent(SplashActivity.this, MainActivity.class);
                }else {
                    //intent = new Intent(SplashActivity.this, GuideActivity.class);
                }
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
//    private Animation startAnim4(){
//        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_translate_anim);
//       return animation;
//       /* ObjectAnimator oa = ObjectAnimator.ofFloat(mImageViewIcon,"translationX",6);
//        oa.setDuration(600);
//        //来回循环的差值其
//        oa.setInterpolator(new CycleInterpolator(6));
//        // oa.setRepeatMode(ObjectAnimator.REVERSE);
//        oa.start();*/
//
//    }
    private AnimationSet startAnim5(){
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation ta = new TranslateAnimation(Animation.REVERSE,
                1.0f,Animation.REVERSE,0.0f,Animation.RELATIVE_TO_PARENT,1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f);
        ta.setInterpolator(new OvershootInterpolator());
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(ta);
        set.addAnimation(scaleAnimation);
        set.setDuration(3000);
        return set;
    }
}
