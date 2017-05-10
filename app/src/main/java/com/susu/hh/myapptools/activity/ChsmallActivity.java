package com.susu.hh.myapptools.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.ui.cehualib.DrawaLayout;
import com.susu.hh.myapptools.ui.cehualib.MainPanleLayout;

public class ChsmallActivity extends Activity {
    private DrawaLayout mDrawaLayout;
    private ImageView iv_headicon;
    private MainPanleLayout mMainPanleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chsmall);
        initView();
        setLister();
    }

    private void initView() {
        mDrawaLayout = (DrawaLayout) findViewById(R.id.drawalayout_id);
        iv_headicon = (ImageView) findViewById(R.id.iv_headicon);
        mMainPanleLayout = (MainPanleLayout) findViewById(R.id.ll_main_panle);
    }
    private void setLister() {
        mDrawaLayout.setDragStatusListener(new DrawaLayout.DragStatusListener() {
            @Override
            public void onClose() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_headicon, "translationX", 5);
                //ObjectAnimator.of
                objectAnimator.setDuration(500);
                //来回循环的差值其
                objectAnimator.setInterpolator(new CycleInterpolator(5));
                objectAnimator.start();
            }

            @Override
            public void onOpen() {

            }

            @Override
            public void onDraging(float percent) {
                iv_headicon.setAlpha(1-percent);
            }

            @Override
            public boolean swipeLayoutIsOpned() {
                return false;
            }
        });

        //菜单打开的时候：单击住面板关闭菜单的监听
        mMainPanleLayout.setCallback(new MainPanleLayout.Callback() {

            //菜单是否打开
            @Override
            public boolean leftMenuIsOpen() {

                return mDrawaLayout.getCurrentDragStatus() == DrawaLayout.DragStatus.Open;
            }

            @Override
            public void closeLeftMenu() {

                mDrawaLayout.close();
            }
        });
    }

}
