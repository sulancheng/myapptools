package com.susu.hh.myapptools.utils.ObserverDemo;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sucheng on 2018/4/10 09:01
 * 被观察者
 */

public class ObseaverBaseBean implements Mistakable, View.OnClickListener {
    // 技术不行，可能被好多人看到
    private List<Watcher> mWatchers = new ArrayList<>();
    private static ObseaverBaseBean obseaverBaseBean;

    private ObseaverBaseBean() {

    }

    public static ObseaverBaseBean getInstener() {
        if (obseaverBaseBean == null) {
            obseaverBaseBean = new ObseaverBaseBean();
        }
        return obseaverBaseBean;
    }

    @Override
    public void add(Watcher watcher) {
        if (watcher != null)
            mWatchers.add(watcher);
    }

    @Override
    public void dele(Watcher watcher) {
        if (watcher != null)
            mWatchers.remove(watcher);
    }

    @Override
    public void steal(String str, Watcher mywatcher) { //当观察动作触发了之后
        if (mywatcher == null)
            for (Watcher watcher : mWatchers) {
                watcher.discover(str);
            }
        else
            mywatcher.discover(str);
    }

    @Override
    public void onClick(View v) {
        steal("inhoa", null);
    }
}
