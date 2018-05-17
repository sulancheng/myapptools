package com.susu.hh.myapptools.utils.ObserverDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.susu.hh.myapptools.R;

public class ObserTestActivity extends AppCompatActivity implements Watcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obser_test);
        doSomeThing();

    }
    private void doSomeThing() {
        ObseaverBaseBean instener = ObseaverBaseBean.getInstener();
        instener.add(this);
        instener.steal("偷到了一台三桑note7",null);//开始搞事了
    }

    @Override
    public void discover(String s) {
        //做想做的事情。
    }
}
