package com.susu.hh.myapptools.testbaseact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_test);
        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();
    /**
     *  使用频率高 一般用于Activity初始化界面
     *  例如 onCreate()里。初始化布局就用到setContentView(R.layout.xxxx) 就是初始化页面布局
     */
    @Override
    public void setContentView(int layoutResId){
        super.setContentView(layoutResId);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }

    /**
     * 这个一般用于加载自定义控件，或者系统空间的布局
     */
    @Override
    public void setContentView(View view){
        super.setContentView(view);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }

    /**
     *
     */
    @Override
    public void setContentView(View view,ViewGroup.LayoutParams params){
        super.setContentView(view,params);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }
}
