package com.susu.hh.myapptools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.susu.hh.myapptools.R;

/**
 * Created by Administrator on 2016/10/28.
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.black_number:
                break;
            case R.id.gexing:
                Intent intent = new Intent(this,GoBackMia.class);
                startActivity(intent);
                break;
        }
    }
}
