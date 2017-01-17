package com.susu.hh.myapptools.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sweet_xue.two_dimension_code.MipcaActivityCapture;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.squareup.picasso.Picasso;
import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.adapter.PhotoSelAdapter;
import com.susu.hh.myapptools.fragment.BaseFragment;
import com.susu.hh.myapptools.fragment.FragmentA;
import com.susu.hh.myapptools.fragment.FragmentB;
import com.susu.hh.myapptools.fragment.FragmentC;
import com.susu.hh.myapptools.fragment.FragmentD;
import com.susu.hh.myapptools.ui.AddressDialog;
import com.susu.hh.myapptools.ui.CircleImageView;
import com.susu.hh.myapptools.ui.RoundProgressBar;
import com.susu.hh.myapptools.utils.BitmapUtil;
import com.susu.hh.myapptools.utils.CommenUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private static final int MY_PROGRESS = 0;
    private static final int ARIM_PROGRESS = 88;
    private List<BaseFragment> datas;
    private SlidingMenu menu;
    private FragmentManager fragmentManager;
    private List<String> dataTit;
    private TextView title;
    private Handler mHandle = new Handler() {
        private int progress;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MY_PROGRESS:
                    progress = person_progress.getProgress();
                    if (progress < ARIM_PROGRESS) {
                        person_progress.setProgress(progress + 2);
                        mHandle.sendEmptyMessageDelayed(MY_PROGRESS, 8);
                    } else {
                        person_progress.setProgress(ARIM_PROGRESS);
                    }
                    break;
            }
        }
    };
    private RoundProgressBar person_progress;
    private CircleImageView myphoto;
    private File file = new File(Environment.getExternalStorageDirectory(), "my.jpg");
    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化SlideMenu
        initRightAndLeftMenu();
        // 初始化ViewPager
        initViewPager();

    }

    private void initViewPager() {
        final String[] tit = {"AA", "本地视频", "", "美女", "浏览器"};
        datas = new ArrayList<>();
        datas.clear();
        BaseFragment fragmentA = new FragmentA();
        datas.add(fragmentA);
        datas.add(new FragmentC());
        datas.add(null);
        datas.add(new FragmentB());
        datas.add(new FragmentD());
        fragmentManager = getSupportFragmentManager();
        //没有用viewpager
        // ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_main);
        //mViewPager.setAdapter(mFragmentPagerAdapter);
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.main_radio);
        title = (TextView) findViewById(R.id.textTit);
        title.setText(tit[0]);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View child = group.findViewById(checkedId);
                int i = group.indexOfChild(child);
                if (i == 2) {
                    return;
                }
                if (i <= 3) {
                    title.setText(tit[i]);
                }else {
                    headMain(View.GONE);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.vp_main, datas.get(i));
                transaction.commit();
                //mViewPager.setCurrentItem(i);
            }
        });
        mRadioGroup.check(R.id.home);
        ImageView left_img = (ImageView) findViewById(R.id.left_click);
        ImageView reght_img = (ImageView) findViewById(R.id.right_click);
        LinearLayout ll_center_more = (LinearLayout) findViewById(R.id.ll_center_more);
        ll_center_more.setOnClickListener(this);
        left_img.setOnClickListener(this);
        reght_img.setOnClickListener(this);
    }

    //主页面不用vierpager
 /*   private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    };*/
    private void initRightAndLeftMenu() {
        String[] arr = {"聊天", "发现", "通讯录", "朋友圈", "订阅号"};
        setBehindContentView(R.layout.left_menu_frame);
        ListView mLeftList = (ListView) findViewById(R.id.iv_left_menu);
        LinearLayout ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
        LinearLayout name_person = (LinearLayout) findViewById(R.id.name_person);
        TextView miaosu = (TextView) findViewById(R.id.miaosu);
        SpannableString ss1 = new SpannableString("这是一个很阳光的人，他会自由的追梦！！！！");
        ss1.setSpan(new AbsoluteSizeSpan(25), 5, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // set size  不包含end
        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 5, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // set size  不包含end
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 5, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // set size  不包含end
        miaosu.setText(ss1);
        ll_setting.setOnClickListener(this);
        name_person.setOnClickListener(this);
        person_progress = (RoundProgressBar) findViewById(R.id.person_progress);
        mLeftList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arr));
        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setSlidingEnabled(true);//能否滑动
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);//阴影的宽度
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setBehindWidth()
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // menu.setBehindScrollScale(1.0f);
        menu.setSecondaryShadowDrawable(R.drawable.shadow);
        //设置右边（二级）侧滑菜单
        //menu.setSecondaryMenu(R.layout.right_menu_frame);
        menu.setSecondaryMenu(R.layout.right_menu_frame);
        Button bt_ewm = (Button) findViewById(R.id.bt_ewm);
        Button sltest = (Button) findViewById(R.id.bt_sltest);
        bt_ewm.setOnClickListener(this);
        sltest.setOnClickListener(this);
        mLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemClick", "点了");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //参数2设置style,第二个构造函数。
                /*AlertDialog dialog = builder.create();
                dialog.setContentView();*/
                builder.setTitle("hehe");
                builder.setCancelable(false);// 设置点击屏幕Dialog不消失
                builder.setMessage("本人太帅了").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNeutralButton("额", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                initLeftMeun();
            }
        });
        menu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                tiaozhuan(tiaoz);
            }
        });
    }

    private void tiaozhuan(int o) {
        Intent intent;
        switch (o) {
            case 0:
                break;
            case 4:
                tiaoz = 0;
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case 5:
                tiaoz = 0;
                intent = new Intent(MainActivity.this, Sliderlayout.class);
                startActivity(intent);
                break;
        }
    }

    private void initLeftMeun() {
        person_progress.setProgress(0);
        mHandle.sendEmptyMessage(MY_PROGRESS);
        selTouPhoto();
    }

    public void headMain(int zt) {
        RelativeLayout head_main = (RelativeLayout) findViewById(R.id.head_main);
        head_main.setVisibility(zt);
    }

    private void selTouPhoto() {
        myphoto = (CircleImageView) findViewById(R.id.myphoto);
        myphoto.setOnClickListener(this);
        Picasso.with(this).load(file).into(myphoto);
    }

    private int tiaoz;

    //左右点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.left_click:
                //点击关闭菜单
                menu.toggle();
                break;
            case R.id.right_click:
               /* if (!getSlidingMenu().isSecondaryMenuShowing()) {
                    getSlidingMenu().showSecondaryMenu();
                }
                关闭的话用getSlidingMenu().showContent()方法。*/
                if (!menu.isSecondaryMenuShowing()) {
                    menu.showSecondaryMenu();//打开
                } else {
                    menu.showContent();//关闭
                }
                break;
            case R.id.ll_center_more:
                AddressDialog addressDialog = new AddressDialog(this);
                addressDialog.show();
                // twodia();
                break;
            case R.id.bt_ewm:
                intent = new Intent(this, MipcaActivityCapture.class);
                startActivityForResult(intent, 555);
                break;
            case R.id.myphoto:
                showPhotoDialog();
                break;
            case R.id.ll_setting:
                //menu.showContent();//关闭
                menu.toggle();
                tiaoz = 4;
                break;
            case R.id.name_person:
                //menu.showContent();//关闭
                intent = new Intent(MainActivity.this, PulldownViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_sltest:
                tiaoz = 5;
                menu.showContent();
                break;
        }
    }

    private void twodia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择一个城市");
        //    指定下拉列表的显示数据
        final String[] cities = {"广州", "上海", "北京", "香港", "澳门"};

        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommenUtils.showSingleToast(MainActivity.this, "选择的城市为：" + cities[which]);
            }
        });
        builder.show();
    }

    private void showPhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View contentViews = View.inflate(this, R.layout.photodialog, null);
        dialog.setView(contentViews);
        //dialog.setContentView(contentViews);
        /*ad.getWindow().setContentView(layout);
        ad.getWindow().setBackgroundDrawableResource(R.drawable.bind_dialog_bg);
        ad.getWindow().setLayout(DensityUtil.dip2px(LoginActivity.this,250),DensityUtil.dip2px(LoginActivity.this,150));*/
   /*     WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        dialog.getWindow().setAttributes(p);*/

        TextView tv_content = (TextView) contentViews.findViewById(R.id.tv_content);
        ListView list = (ListView) contentViews.findViewById(R.id.list);
        tv_content.setText("选");
        String[] datas = {"拍照", "选择"};
        list.setAdapter(new PhotoSelAdapter(datas, this));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setHeadPhoto(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static final int REQUESTCODE_NONE = 6;
    public static final int REQUESTCODE_PHOTOGRAPH = 3;
    public static final int REQUESTCODE_PHOTOZOOM = 4;
    public static final int REQUESTCODE_PHOTOSAVE = 5;
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private void setHeadPhoto(int index) {
        Intent i = null;
        switch (index) {
            case 0:
                i = BitmapUtil.buildImageCaptureIntent(Uri.parse(BitmapUtil.IMAGE_FILE_LOCATION));
                startActivityForResult(i, REQUESTCODE_PHOTOGRAPH);
                break;
            case 1:
                i = new Intent(Intent.ACTION_PICK, null);
                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(i, REQUESTCODE_PHOTOZOOM);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras;
        if (resultCode == 666) {
            switch (requestCode) {
                case 555:
                    String result = data.getExtras().getString("result");
                    /*Intent intent = new Intent(mContext,MoWebActivity.class);
                    intent.putExtra("url_", result);
                    startActivity(intent);*/
                    //将结果携带给搜索。
                    Log.i("result", result);
                    break;
            }
        }
        //Build.VERSION_CODES.M
        if (Build.VERSION.SDK_INT < 23) {
            if (resultCode == REQUESTCODE_NONE)
                return;
        }
        if (requestCode == REQUESTCODE_PHOTOGRAPH) {//paizao
            try {
                Intent intent = BitmapUtil.buildImageCropIntent(Uri.parse(BitmapUtil.IMAGE_FILE_LOCATION), Uri.parse(BitmapUtil.IMAGE_FILE_LOCATION), 100, 100, true);
                startActivityForResult(intent, REQUESTCODE_PHOTOSAVE);
            } catch (Exception e) {
            }
        }
        if (data == null)
            return;

        if (requestCode == REQUESTCODE_PHOTOZOOM) {//xiangce
            Intent intent = BitmapUtil.startPhotoZoomOld(data.getData());
            startActivityForResult(intent, REQUESTCODE_PHOTOSAVE);
        }

        //保存并显示当前选择并剪切之后的图片
        if (requestCode == REQUESTCODE_PHOTOSAVE) {
            extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO:保存用户头像
                //  if (user != null)
                //    try {
                //       user.setPortrait(new String(Base64.encodeBase64(baos.toByteArray()), "UTF-8"));
                //   } catch (UnsupportedEncodingException e) {
                //       e.printStackTrace();
                //  }
                //if (photo != null) {
                //   needToSave = true;
                myphoto.setImageBitmap(photo);
                //}
            }
        }
    }

    //设置点击两次退出程序
    private boolean isExist = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ToQuitTheApp();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void ToQuitTheApp() {
        if (isExist) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        } else {
            isExist = true;
            CommenUtils.showSingleToast(this, "再按一次退出程序app");
            CommenUtils.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExist = false;
                }
            }, 3000);
        }
    }
}
