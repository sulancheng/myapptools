package com.susu.hh.myapptools.mvp.view;

/**
 * 作者：sucheng on 2018/8/17 14:48
 */
public interface ILoginView {
     void onClearText();
     void onLoginResult(Boolean result, int code);
     void onSetProgressBarVisibility(int visibility);
     void setData(String data);
     void findbyid();
}
