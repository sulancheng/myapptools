package com.susu.hh.myapptools.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.SettingActivity;
import com.susu.hh.myapptools.adapter.FcfViewGroupAdapter;
import com.susu.hh.myapptools.adapter.MyJGGridAdapter;
import com.susu.hh.myapptools.ui.MyJGGridView;
import com.susu.hh.myapptools.utils.CommenUtils;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;
import java.util.Arrays;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import news.heima.itcast.autoroll.AutoRollLayout;
import news.heima.itcast.autoroll.RollItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends BaseFragment implements AdapterView.OnItemClickListener {
    private MyJGGridAdapter myGridAdapter;
    public String[] img_text = {"转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
            "当面付", "亲密付", "机票"};
    String[] titledatas = {"朝辞白帝彩云间", "千里江陵一日还", "两岸猿声啼不住", "轻舟已过万重山"};
    public int[] imgs = {R.drawable.app_transfer, R.drawable.app_fund,
            R.drawable.app_phonecharge, R.drawable.app_creditcard,
            R.drawable.app_movie, R.drawable.app_lottery,
            R.drawable.app_facepay, R.drawable.app_close,
            R.drawable.app_plane};
    private AutoRollLayout mAutoRollLayout;
    private MyJGGridView myJGGridView;
    private FancyCoverFlow fcf;
    private FcfViewGroupAdapter fcfad;
    private ScrollView scroll;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_, container, false);
    }

    @Override
    public void viewCreated(View view, Bundle savedInstanceState) {
        myJGGridView = (MyJGGridView) view.findViewById(R.id.jggridview);
        mAutoRollLayout = (AutoRollLayout) view.findViewById(R.id.autoroll);
        scroll = (ScrollView) view.findViewById(R.id.scroll);
        fcf = (FancyCoverFlow) view.findViewById(R.id.fancyCoverFlow);
        initDatas();
        isWindow(mAutoRollLayout);

    }

    private void initDatas() {
        int[] images = {R.drawable.aa, R.drawable.bb, R.drawable.cc, R.drawable.dd, R.drawable.ff};
        if (null == fcfad) {
            fcfad = new FcfViewGroupAdapter(images);
            fcf.setAdapter(fcfad);
            fcf.setSelection(1);
        }
        ArrayList<RollItem> rollItems = new ArrayList<>();
        rollItems.add(new RollItem(titledatas[0], "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=17c8c83f2e2eb938ec3872f4e552a90d/730e0cf3d7ca7bcb49690cf1bf096b63f724a8da.jpg"));
        rollItems.add(new RollItem(titledatas[1], "https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=c2bb7cec5bafa40f3c93c6db9b542f77/bd3eb13533fa828b1755563dfc1f4134960a5afe.jpg"));
        rollItems.add(new RollItem(titledatas[2], "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=17c8c83f2e2eb938ec3872f4e552a90d/730e0cf3d7ca7bcb49690cf1bf096b63f724a8da.jpg"));
        rollItems.add(new RollItem(titledatas[3], "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=17c8c83f2e2eb938ec3872f4e552a90d/730e0cf3d7ca7bcb49690cf1bf096b63f724a8da.jpg"));
        mAutoRollLayout.setData(rollItems);
        if (myGridAdapter == null) {
            myGridAdapter = new MyJGGridAdapter(mContext, img_text, imgs);
            myJGGridView.setAdapter(myGridAdapter);
        } else {
            myGridAdapter.upDateDatas(mContext, img_text, imgs);
            myGridAdapter.notifyDataSetChanged();
        }
        myJGGridView.setOnItemClickListener(this);
        mAutoRollLayout.startAuto();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
        CommenUtils.showSingleToast(mContext, "点击了第" + position + "个");
    }


    @Override
    public void onStart() {
        super.onStart();
        //initDatas();
        if (mAutoRollLayout != null) {
            mAutoRollLayout.startAuto();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAutoRollLayout != null) {
            mAutoRollLayout.stopAuto();
        }
    }

    public boolean isWindow(View view) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        //outMetrics = getResources().getDisplayMetrics();
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;
        Rect rect = new Rect(0, 0, width2, height2);

        int[] location = new int[2];
        view.getLocationInWindow(location);
        MyLog.i("location", Arrays.toString(location));

        Rect viewRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
         /*if(rect.contains(viewRect)){

        }*/
        if (view.getLocalVisibleRect(rect)) {
            MyLog.i("location", "可见");
        }
        return view.getLocalVisibleRect(rect);
        /* 获取屏幕密度（方法2）
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        Log.e(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
        Log.e(TAG + " DisplayMetrics", "density=" + density + "; densityDPI=" + densityDPI);
        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        Log.e(TAG + " DisplayMetrics(111)", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight
        */
    }
}
