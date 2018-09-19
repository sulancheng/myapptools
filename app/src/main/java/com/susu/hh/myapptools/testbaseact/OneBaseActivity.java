package com.susu.hh.myapptools.testbaseact;

import android.os.Bundle;
import android.widget.TextView;

import com.susu.hh.myapptools.R;

import butterknife.BindView;


public class OneBaseActivity extends BaseTestActivity {
    @BindView(R.id.tv_one)
    TextView tv_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_one_base);

//        //初始化控件
//        initView();
//        //设置数据
//        initData();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_one_base;
    }

    @Override
    public void initView() {
        tv_one.setText("这是baseactivituy的第一个页面");
    }

    @Override
    public void initData() {

    }
}
