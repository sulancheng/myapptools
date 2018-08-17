package com.susu.hh.myapptools.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.mvp.presenter.LoginPresenterCompl;

public class ILoginActivity extends Activity implements ILoginView {

    private TextView tv_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilogin);
        findbyid();
        LoginPresenterCompl loginPresenterCompl = new LoginPresenterCompl(this,this);
        loginPresenterCompl.getLoginData();
    }

    @Override
    public void findbyid() {
        tv_res = findViewById(R.id.tv_res);
    }

    @Override
    public void onClearText() {

    }

    @Override
    public void onLoginResult(Boolean result, int code) {

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }

    @Override
    public void setData(String data) {
        tv_res.setText(data);
    }
}
