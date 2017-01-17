package com.susu.hh.myapptools.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MynoScrollViewPager extends ViewPager {
    public MynoScrollViewPager(Context context) {
        super(context);
    }

    public MynoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
  /*  @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);//父类不要拦截
        return super.dispatchTouchEvent(ev);
    }*/
    /*对于底层的View来说，有一种方法可以阻止父层的View截获touch事件，
    就是调用getParent().requestDisallowInterceptTouchEvent(true);方法。
    一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action。*/
}
