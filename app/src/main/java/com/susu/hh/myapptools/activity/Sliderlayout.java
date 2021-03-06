package com.susu.hh.myapptools.activity;

import android.os.Bundle;
import android.view.View;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.ui.SlidingDrawer;
import com.susu.hh.myapptools.utils.MyLog;

/**
 * Created by Administrator on 2016/11/15.
 */
public class Sliderlayout extends SweipeBackActivity implements SlidingDrawer.OnSlideListener{

    private SlidingDrawer slidingdrawer;
    private View expanded_view;
    private View target,target2;
    private View bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidertest);
        target = findViewById(R.id.sample_view);
        target2 = findViewById(R.id.sample_view2);
        bt = findViewById(R.id.sliding_drawer_collapsed_view);
        slidingdrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
        expanded_view = findViewById(R.id.expanded_view);
        //slidingdrawer.setDragView(findViewById(Rz.id.slidable_view));
        slidingdrawer.addSlideListener(this);
        target.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                slidingdrawer.setState(slidingdrawer.getState() == SlidingDrawer.EXPANDED ? SlidingDrawer.COLLAPSED : SlidingDrawer.EXPANDED);
            }
        });
        slidingdrawer.setDragView(expanded_view);//view是谁  谁就可以拖动
        //设置了sliding_drawer_collapsed_view这个id会看不到字。
    }

    @Override
    public void onSlide(SlidingDrawer slidingDrawer, float currentSlide) {
        MyLog.i("SlidingDraweronSlide",currentSlide+"");
        expanded_view.setAlpha(currentSlide);
        if(currentSlide == 1){
            slidingdrawer.setDragView(target2);
        }else {
            slidingdrawer.setDragView(expanded_view);
        }
        //slidingDrawer.invalidate();
    }

}
