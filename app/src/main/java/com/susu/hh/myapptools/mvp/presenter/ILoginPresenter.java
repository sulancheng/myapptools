package com.susu.hh.myapptools.mvp.presenter;

/**
 * 作者：sucheng on 2018/8/17 14:31
 * 在这里定义接口
 */
public interface ILoginPresenter {
    void clear();

    void doLogin(String name, String passwd);

    void setProgressBarVisiblity(int visiblity);

    void getLoginData();

}
