package com.susu.hh.myapptools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public interface SizeChangedListener{
        void onSizeChangedListnner();
    }
    SizeChangedListener mSizeChangedListener;
    //让外界知道我的变化
    public void setSizeChangedListener(SizeChangedListener sizeChangedListener){
        this.mSizeChangedListener = sizeChangedListener;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mSizeChangedListener!=null){
            mSizeChangedListener.onSizeChangedListnner();
        }
    }
}
