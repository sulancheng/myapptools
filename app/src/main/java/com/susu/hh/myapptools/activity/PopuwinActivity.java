package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.susu.hh.myapptools.R;

public class PopuwinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popuwin);
    }
    private void popushow(View ll_bum) {
        View view = View.inflate(PopuwinActivity.this, R.layout.layout_popupwindow, null);
        ListView lsvMore = (ListView) view.findViewById(R.id.lv_bum);

        //lsvMore.setOnItemClickListener(this);
        int[] location = new int[2];
        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        ll_bum.getLocationOnScreen(location);
        //lsvMore.setAdapter(new BumAdapter(datas, ContectActivity.this));
        PopupWindow popupWindow = new PopupWindow(view, ll_bum.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        //测量view 注意这里，如果没有测量  ，下面的popupHeight高度为-2  ,因为LinearLayout.LayoutParams.WRAP_CONTENT这句自适应造成的
//        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
//        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度

        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);

        //popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);  //设置动画
//        重载的方法为
//
//        public void showAtLocation(View parent, int gravity, int x, int y)
//
//        第一个参数就是父布局类，一般都是方法体中的参数view。
//        第二个参数gravity指的是popupWindow在父布局中出现的大致位置。常见的有 Gravity.NO_GRAVITY，Gravity.LEFT,Gravity.RIGHT,Gravity.TOP,
//                Gravity.BOTTOM。具体意思一看就明了，就不解释类。
//        第三个参数int x指的是以第二个参数gravity指点的位置为原点，popupWindow相对于原点X轴上的位置。x为正popupWindow向右移动，x为负popupWindow向
//        左移动。
//        第四个参数int y同X差不多，指的是y轴上的位置。y为正popupWindow向上，y为负popupWindow向下。
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        popupWindow.showAsDropDown(ll_bum);
//        TranslateAnimation showAnimation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, -1.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f);
//        showAnimation.setDuration(200);
//        lin_filter.setAnimation(showAnimation);
//        lin_filter.setVisibility(View.VISIBLE);
//        TranslateAnimation hiddenAnimation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, -1.0f);
//        hiddenAnimation.setDuration(200);
//        lin_filter.setAnimation(hiddenAnimation);
//        lin_filter.setVisibility(View.GONE);
    }
}
