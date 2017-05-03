package com.susu.hh.myapptools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.mytestui.BgActivity;

/**
 * Created by 我在测试分支
 */
public class SettingActivity extends SweipeBackActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitlText("设置");
        initView();
    }

    private void initView() {
        RelativeLayout blackNum = (RelativeLayout) findViewById(R.id.black_number);
        blackNum.setOnClickListener(this);
        RelativeLayout gexing = (RelativeLayout) findViewById(R.id.gexing);
        gexing.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.black_number:
                startActivity(new Intent(this, BgActivity.class));
                break;
            case R.id.gexing:
                Intent intent = new Intent(this,GoBackMia.class);
                startActivity(intent);
                break;
        }
    }
}
