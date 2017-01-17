package com.susu.hh.myapptools.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susu.hh.myapptools.R;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Toggle extends RelativeLayout {
    private TextView tvTitle;
    private ImageView ivToggle;
    public Toggle(Context context) {
        this(context,null);
    }

    public Toggle(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Toggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.view_toggle, null);

        //添加到自已里面来
        this.addView(view);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        //取用户设置的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Toggle);
        String title = typedArray.getString(R.styleable.Toggle_lefttext);
        String type = typedArray.getString(R.styleable.Toggle_type);
        boolean isToggle = typedArray.getBoolean(R.styleable.Toggle_isToggle, true);
        typedArray.recycle();

        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivToggle = (ImageView) findViewById(R.id.iv_toggle);

        if(isToggle == false){
            ivToggle.setVisibility(View.GONE);
        }

        tvTitle.setText(title);

        if("top".equals(type)){
            //this.setBackgroundResource(R.drawable.setting_top_selector);
        }else if("mid".equals(type)){
           // this.setBackgroundResource(R.drawable.setting_mid_selector);
        }else if("bottom".equals(type)){
          //  this.setBackgroundResource(R.drawable.setting_bottom_selector);
        }

    }
    private boolean isToggleOn;


    public void toggle(){
		/*if(isToggleOn){
			setToggleOn(false);
		}else{
			setToggleOn(true);
		}*/
        setToggleOn(!isToggleOn);
    }


    /**
     * 设置开关状态
     */
    public void setToggleOn(boolean isToggleOn){
        if(isToggleOn){
            ivToggle.setImageResource(R.drawable.alarm_on);
        }else{
            ivToggle.setImageResource(R.drawable.alarm_off);
        }
        this.isToggleOn = isToggleOn;
    }

    public boolean isToggleOn(){
        return isToggleOn;
    }

}
