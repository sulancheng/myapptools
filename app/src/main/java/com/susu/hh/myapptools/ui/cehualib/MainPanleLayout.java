package com.susu.hh.myapptools.ui.cehualib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/5/14.
 * <p/>
 * 主面板：
 * <p/>
 * 需求：  1.当菜单打开的时候：
 * 2.点击主面板的时候，把菜单界面关闭
 * <p/>
 * 分析： 1.打开了菜单；2.点击了主面板；3.单击事件
 * 主面板把单击事件拦截下来处理
 * <p/>
 * 要知道：菜单是否打开，解决方式：接口回调
 */
public class MainPanleLayout extends LinearLayout {


    private GestureDetector mGestureDetector;

    public MainPanleLayout(Context context) {
        this(context, null);
    }

    public MainPanleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainPanleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            //单击的时候：关闭菜单
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                if (mCallback != null) {

                    mCallback.closeLeftMenu();
                }
                return super.onSingleTapUp(e);
            }
        });
    }

    /*
        要知道当前菜单是否打开
        关闭菜单
     */
    public interface Callback {

        public boolean leftMenuIsOpen();

        public void closeLeftMenu();
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {

        this.mCallback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //当菜单是打开的时候  主菜单知道什么时候是打开
        if (mCallback != null && mCallback.leftMenuIsOpen()) {

            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }


    /*
        拦截事件：单击的时候：关闭菜单

     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);
        return true;
    }
}
