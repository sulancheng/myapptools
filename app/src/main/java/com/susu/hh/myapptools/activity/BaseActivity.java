package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.susu.hh.myapptools.R;

public class BaseActivity  extends Activity{

    private ImageView left_img;
    private ImageView reght_img;
    private TextView textTit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void setLeft(int id){
        left_img = (ImageView) findViewById(R.id.left_click);
        left_img.setImageResource(id);
    }
    public void setRight(int id){
        reght_img = (ImageView) findViewById(R.id.right_click);
        reght_img.setImageResource(id);
    }
    public void setTitlText(String text){
        if(textTit==null){
            textTit = (TextView) findViewById(R.id.textTit);
        }
        textTit.setText(text);
    }
    public void setonclickLeft(View.OnClickListener onClickListener){
        left_img.setOnClickListener(onClickListener);
    }
    public void setonclickright(View.OnClickListener onClickListener){
        reght_img.setOnClickListener(onClickListener);
    }
    public void setContText(String text){
        TextView textCont = (TextView) findViewById(R.id.textCont);
        textCont.setVisibility(View.VISIBLE);
        textCont.setText(text);
    }

}
