package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class HelloViewpagerActivity extends Activity {
    private int[] imgs = { R.drawable.a, R.drawable.b, R.drawable.c};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_viewpager);
        // 和ListView很相似
        ViewPager viper = (ViewPager) findViewById(R.id.viper);
        viper.setAdapter(new MyAdapter());
        //ViewPager存在预加载,会造成instantiateItem执行多次
        //设置预加载次数
//		viper.setOffscreenPageLimit(2);
    }
    private List<ImageView> ivs;
    private class MyAdapter extends PagerAdapter {
        public MyAdapter() {
            ivs=new ArrayList<>();
        }
        // 公有多少页
        @Override
        public int getCount() {
            return imgs.length;
        }

        // instantiateItem的方法返回的是Object,系统要判断参数2返回的是否是一个view
        // ViewPager的每个条目都有一个对应的ItemInfo,里面有position和对应的Object,Object就是instantiateItem返回的,
        //系统会通过isViewFromObject来判断object是否是一个视图,如果是,则会返回条目信息,用于显示当前数据
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 初始化条目
        // 参数1:ViewPager
        // 参数2:对应条目的position
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(ivs.isEmpty()){
                ImageView iv=new ImageView(HelloViewpagerActivity.this);
                ivs.add(iv);
            }
            MyLog.i("test", "集合大小:"+ivs.size());
            ImageView iv = ivs.remove(0);
            iv.setImageResource(imgs[position]);
            container.addView(iv);
            return iv;
        }

        // 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv=(ImageView) object;
            container.removeView(iv);
            ivs.add(iv);
        }
    }
}
