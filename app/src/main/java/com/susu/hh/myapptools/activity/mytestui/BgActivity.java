package com.susu.hh.myapptools.activity.mytestui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.susu.hh.myapptools.R;

import java.util.ArrayList;
import java.util.List;

public class BgActivity extends Activity implements View.OnClickListener {

    private View divider;
    private List<String> datas;
    private ImageView iv_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);
        init();
    }

    private void init() {
        View ll_all = findViewById(R.id.ll_all);
        ll_all.setOnClickListener(this);
        divider = findViewById(R.id.divider);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        initDatas();
    }

    private void initDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add("条目" + i);
        }
    }

    // 代码适配:
    // 当代码的api过时后,高版本api和低版本api不是同一个,我们要对不同版本进行不同的处理
    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                iv_arrow.setImageResource(R.drawable.up);
                View contentView = View.inflate(this, R.layout.bg, null);
                ListView lv_level1 = (ListView) contentView.findViewById(R.id.lv_level1);
                final ListView lv_level2 = (ListView) contentView.findViewById(R.id.lv_level2);
                lv_level1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lv_level2.setAdapter(new ArrayAdapter<String>(BgActivity.this, android.R.layout.simple_list_item_1, datas));
                    }
                });
                Drawable drawable;
                // 判断当前版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_launcher, null);
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_launcher);
                }
                lv_level1.setDivider(drawable);
                // 去除分割线,实际:设置分割线高度为0
                // lv_level1.setDividerHeight(0);
                lv_level1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas));
                PopupWindow pw = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                pw.setBackgroundDrawable(new ColorDrawable());
                pw.setFocusable(true);
                // 参数:锚点
                // 以参数为基准点向下弹出PopupWindow
                pw.showAsDropDown(divider);

                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        iv_arrow.setImageResource(R.drawable.down);
                    }
                });
                break;

            default:
                break;
        }
    }
}
