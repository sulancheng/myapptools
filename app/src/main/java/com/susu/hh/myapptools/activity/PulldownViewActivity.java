package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.ui.list.PullScrollView;
import com.susu.hh.myapptools.utils.MyLog;

import java.io.File;

/**
 * Created by Administrator on 2016/10/31.
 */
public class PulldownViewActivity extends Activity implements PullScrollView.OnTurnListener{
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private TableLayout mMainLayout;
    private ImageView mImageView;
    private File file = new File(Environment.getExternalStorageDirectory(),"my.jpg");;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pull_down);


        initView();
        showTable();

    }

    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);
        mImageView = (ImageView) findViewById(R.id.user_avatar);
        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;
            //Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            MyLog.i("fileexists","fileexists");
            //mImageView.setImageBitmap(bm);
            Picasso.with(this).load(file).into(mImageView);
        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("wahah " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PulldownViewActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });
            mMainLayout.addView(tableRow);
        }
    }

    @Override
    public void onTurn() {

    }

}
