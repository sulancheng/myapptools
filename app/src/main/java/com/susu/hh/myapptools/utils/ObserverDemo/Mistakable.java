package com.susu.hh.myapptools.utils.ObserverDemo;

/**
 * 作者：sucheng on 2018/4/10 09:00
 * 定义被观察者接口，需要被观察的行为
 */

public interface Mistakable {
    void add(Watcher watcher);//引起一些长眼睛的人注意,注册

    void dele(Watcher watcher);

    void steal(String s,Watcher mywatcher);//搞事  触发
}
