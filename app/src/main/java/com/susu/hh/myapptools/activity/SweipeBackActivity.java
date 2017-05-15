package com.susu.hh.myapptools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.ui.SwipeBackLayout;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SweipeBackActivity extends BaseActivity {
    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback_base, null);*/
        View inflate = View.inflate(this, R.layout.swipeback_base, null);
        SwipeBackLayout layout = (SwipeBackLayout) inflate.findViewById(R.id.swipeBackLayout);
        layout.attachToActivity(this);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
       // overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }




    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       //overridePendingTransition(0, R.anim.base_slide_right_out);
    }

}
