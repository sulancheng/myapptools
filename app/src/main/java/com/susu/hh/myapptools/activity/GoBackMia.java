package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.susu.hh.myapptools.R;

/**
 * Created by Administrator on 2016/11/9.
 */
public class GoBackMia extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ceshiti);
        //单选框
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb3) //rb3设定为正确答案
                {
                    Toast.makeText(GoBackMia.this, "选对了", Toast.LENGTH_SHORT).show();
                }
                RadioButton rb = (RadioButton) findViewById(checkedId);
                Toast.makeText(GoBackMia.this, rb.getText(), Toast.LENGTH_LONG).show();
            }
        });
        //复选框
        CheckBox cb_st = (CheckBox)findViewById(R.id.cb_st);
        cb_st.setOnCheckedChangeListener(new CBOnCheckedChangListenter() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cb_st.setOnCheckedChangeListener(new CBOnCheckedChangListenter());

        CheckBox cb_jc = (CheckBox)findViewById(R.id.cb_jc);
        cb_jc.setOnCheckedChangeListener(new CBOnCheckedChangListenter());

        CheckBox cb_xt = (CheckBox)findViewById(R.id.cb_xt);
        cb_xt.setOnCheckedChangeListener(new CBOnCheckedChangListenter());

        CheckBox cb_xhx = (CheckBox)findViewById(R.id.cb_xhx);
        cb_xhx.setOnCheckedChangeListener(new CBOnCheckedChangListenter());


    }
    private class CBOnCheckedChangListenter implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CheckBox cb = (CheckBox)buttonView;

            if (isChecked) {
                Toast.makeText(GoBackMia.this, "选中了" + cb.getText(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(GoBackMia.this, "取消了" + cb.getText(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
