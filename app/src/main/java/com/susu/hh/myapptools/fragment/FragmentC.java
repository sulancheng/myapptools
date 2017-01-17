package com.susu.hh.myapptools.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.PulldownViewActivity;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentC extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vpPager;
    private List<ImageView> list;
    private int heads;
    private int[] imags = {R.drawable.a,R.drawable.b};
    private int winWidth;
    private View tab;
    private TextView friend;
    private TextView group;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatec = inflater.inflate(R.layout.fragment_fragment_c, container, false);
        return inflatec;
    }
    @Override
    public void viewCreated(View view, Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {
        initDatas();
        initTab(view);
        initView(view);
        vpPager.setAdapter(new MyViewPagerAdapter());
        vpPager.setOnPageChangeListener(this);
  /*      @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {

            getParent().requestDisallowInterceptTouchEvent(true);
            return super.dispatchTouchEvent(ev);
        }*/
        vpPager.setPageMargin(8);
    }
    private void initDatas() {
        list = new ArrayList<>();
        for (int i = 0; i < imags.length; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(imags[i]);
            list.add(iv);
        }
    }
    private void initTab(View view) {
        tab = view.findViewById(R.id.tab);
        //LinearLayout head = (LinearLayout)view.findViewById(R.id.head);

        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        //初始化横线的长度
        wm.getDefaultDisplay().getMetrics(outMetrics);
        winWidth = outMetrics.widthPixels;

        //获得参数，并设置
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tab.getLayoutParams();
        //heads = head.getLayoutParams().width;
        params.width = winWidth/2-30;
        //params.width = heads/3-30;//减12测试小的间距.
        tab.setLayoutParams(params);

    }
    private void initView(View view) {
        vpPager = (ViewPager)view.findViewById(R.id.vp);
        friend = (TextView)view.findViewById(R.id.tv_friend);
        group = (TextView) view.findViewById(R.id.tv_group);
        group.setOnClickListener(this);
        friend.setOnClickListener(this);
    }
    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //在这里设置监听
            container.addView(list.get(position));
            list.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.i("onclick","我点击到了"+position);
                   /* Intent intent ;
                    if(position==0){
                        intent = new Intent(mContext,PulldownViewActivity.class);
                        startActivity(intent);
                    }*/
                }
            });
            return list.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv = (ImageView) object;
            container.removeView(iv);
        }

    }
    //状态改变
    @Override
    public void onPageScrollStateChanged(int arg0) {
        Log.i("test", "onPageScrollStateChanged===1");
    }
    //页面滚动
    // 参数2:ViewPager移动的屏幕距离的百分比(从0-1)
    // 参数3:ViewPager移动的实际屏幕的像素值
    @Override//当背景图片完全被切换了，position才会改变。
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //动态滚动图片。positionOffset换图表示移动了100%。
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tab.getLayoutParams();
        params.leftMargin = (int) (winWidth/2*positionOffset+position*winWidth/2)+15;//加了6测试小的间距
        //params.leftMargin = positionOffsetPixels/3+position*winWidth/3;
        tab.setLayoutParams(params);
        setColor(position);
        Log.i("test", "真的变化了onPageScrolled===2");
    }
   private void setColor(int position) {
        if(position == 0){
            friend.setTextColor(Color.parseColor("#66ff0000"));
            group.setTextColor(Color.parseColor("#000000"));
        }else{
            friend.setTextColor(Color.parseColor("#000000"));
            group.setTextColor(Color.parseColor("#66ff0000"));
        }
    }

    //页面被选择
    @Override
    public void onPageSelected(int position) {
        Log.i("test", "真的变化了onPageSelected===3");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_friend:
                vpPager.setCurrentItem(0);

                break;
            case R.id.tv_group:
                vpPager.setCurrentItem(1);
                break;
            case R.id.vp:
                MyLog.i("vppager","我被点击了");
                int currentItem = vpPager.getCurrentItem();
                Intent intent ;
                if(currentItem==0){
                    intent = new Intent(mContext,PulldownViewActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
