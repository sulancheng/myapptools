package com.susu.hh.myapptools.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.susu.hh.myapptools.R;


/**
 * Created by Administrator
 * 这个是一个从上而下的dialog   获取屏幕宽  设置位置底部，，还有宽高
 * on 2017/8/30.
 */

public class CommentDialog extends Dialog implements
        View.OnClickListener {

    private InputMethodManager inputManager;
    private View view;

    public interface OnSendListener {
        void sendComment(String content);
    }

    private Context mContext;
    private EditText mEdittext;
    private TextView mTvCancel;
    private TextView mTvSend;
    private OnSendListener onSendListener;

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }

    public CommentDialog(Context context) {
        super(context, R.style.AddressDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(mContext, R.layout.comment_edittext_dialoglayout, null);
        initView(view);
        setContentView(view);
        setLayout();
        setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                mEdittext.setFocusableInTouchMode(true);
                mEdittext.requestFocus();
                inputManager = (InputMethodManager) mEdittext
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEdittext,
                        InputMethodManager.SHOW_IMPLICIT);
            }

        });
    }

    private void initView(View v) {
        mEdittext = (EditText) v.findViewById(R.id.et_comment);
        mTvCancel = (TextView) v.findViewById(R.id.tv_cancel);
        mTvSend = (TextView) v.findViewById(R.id.tv_send);
        mTvCancel.setOnClickListener(this);
        mTvSend.setOnClickListener(this);

        mEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = d.getHeight()/5+20;
        getWindow().setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                inputManager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                dismiss();
                // 如果软键盘已经显示，则隐藏，反之则显示
                //inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.tv_send:
                String content = mEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                if (onSendListener != null) {
                    onSendListener.sendComment(content);
                }
                inputManager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                dismiss();
                break;
            default:
                break;
        }
    }
}
