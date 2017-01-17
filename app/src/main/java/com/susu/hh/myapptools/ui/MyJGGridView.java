package com.susu.hh.myapptools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MyJGGridView extends GridView {
    public MyJGGridView(Context context) {
        this(context,null);
    }

    public MyJGGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyJGGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
