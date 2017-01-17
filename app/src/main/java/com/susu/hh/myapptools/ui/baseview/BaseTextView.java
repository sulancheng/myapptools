package com.susu.hh.myapptools.ui.baseview;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.susu.hh.myapptools.utils.CommenUtils;

/**
 * Created by Administrator on 2016/11/3.
 */
public class BaseTextView extends TextView {

    public BaseTextView(Context context) {
        this(context,null);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (CommenUtils.isEn(context)) {
            AssetManager assets = context.getAssets();
            Typeface typeface = Typeface.createFromAsset(assets, "fonts/Arial.ttf");
            setTypeface(typeface);
            //setIncludeFontPadding(false);//会使得有些字体显示不全
        }
    }
}
