package com.susu.hh.myapptools.mvp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.susu.hh.myapptools.bean.Login;
import com.susu.hh.myapptools.mvp.view.ILoginView;
import com.susu.hh.myapptools.network.OkUtils;
import com.susu.hh.myapptools.utils.MyLog;


/**
 * 作者：sucheng on 2018/8/17 14:32
 * 将业务逻辑放到present的实现类中,目的是将activity和fragment中的业务和viewui分离。使用此类操控全局。
 * 可以将多个activity 同样的业务逻辑放到这里进行统一的管理
 */
public class LoginPresenterCompl implements ILoginPresenter {
    private ILoginView iLoginView;
    private Handler mhander;
    private Context mContext;
    private String TAG = "LoginPresenterCompl";

    public LoginPresenterCompl(ILoginView iLoginView, Context mContext) {
        this.iLoginView = iLoginView;
        this.mContext = mContext;
        mhander = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void clear() {

    }

    @Override
    public void doLogin(String name, String passwd) {

    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {

    }

    @Override
    public void getLoginData() {
        OkUtils.getCookerList(mContext, new Login(), new OkUtils.MyResponse() {
            @Override
            public void expResponse(String myresponse) {
                iLoginView.setData(myresponse);
            }

            @Override
            public void error(String erro) {
                MyLog.e(TAG, "错误:" + erro);
            }
        });
    }
}
