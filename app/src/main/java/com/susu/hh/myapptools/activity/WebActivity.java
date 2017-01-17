package com.susu.hh.myapptools.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.MyLog;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class WebActivity  extends SweipeBackActivity{
    private FrameLayout video_fullView;// 全屏时视频加载view
    private ProgressBar pb;
    private WebView mWebView;
    private GestureDetector mOnGestureListener;
    private WebSettings webSettings;
    private View xCustomView;
    private myWebChromeClient xwebchromeclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();

        initView();
        initSetlisten();
    }

    private void init() {
        video_fullView = (FrameLayout) findViewById(R.id.video_fullView);
        setTitlText("浏览器");
        setLeft(R.drawable.icon_back);
        setRight(R.drawable.icon_favorite);
        setonclickLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }else {
                    finish();
                }
            }
        });
        setonclickright(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigAndSmal();
            }
        });
    }

    public static final String FONT_SIZE_CHOOSES[] = {"最小", "更小", "普通", "更大", "最大"};
    private void bigAndSmal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(FONT_SIZE_CHOOSES, 0, mOnClickListener);
        builder.setPositiveButton("确定", mOnClickListener);
        builder.setNegativeButton("取消", mOnClickListener);
        builder.show();
    }
    private int mChooseItem;
    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //看源码 点击确定，返回值是-1；  点击选择条目的时候走的是default.
                    WebSettings.TextSize[] values = WebSettings.TextSize.values();
                    webSettings.setTextSize(values[mChooseItem]);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:

                    break;
                default:
                    //点击对话框条目选择时候which就是被点击条目的索引，找个变量储备
                    mChooseItem = which;
                    break;
            }
        }
    };

    private void initSetlisten() {
        //设置监听
        mWebView.setWebViewClient(new WebViewClient() {
            //开始加载时候调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //页面重定向。默认点击webview上面的连接会使用其他的浏览器打开
                //设置下面的，会在自己页面中 打开
                mWebView.loadUrl(url);
                //return super.shouldOverrideUrlLoading(view, url);
                return false;
            }
        });
        mOnGestureListener = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent e2, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判断竖直方向移动的大小
                if(Math.abs(e1.getRawY() - e2.getRawY())>100){
                    Toast.makeText(getApplicationContext(), "动作不合法", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(Math.abs(velocityX)<150){
                    Toast.makeText(getApplicationContext(), "移动的太慢", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if((e1.getRawX() - e2.getRawX()) >200){// 表示 向右滑动表示下一页
                    //显示下一页
                    //next(null);
                    Toast.makeText(getApplicationContext(), "下一页", Toast.LENGTH_SHORT).show();
                    mWebView.goForward();
                    return true;
                }

                if((e2.getRawX() - e1.getRawX()) >200){  //向左滑动 表示 上一页
                    //显示上一页
                    // pre(null);
                    Toast.makeText(getApplicationContext(), "上一页", Toast.LENGTH_SHORT).show();
                    mWebView.goBack();
                    return true;//消费掉当前事件  不让当前事件继续向下传递
                }
                return false;
            }
        });
        //点击双击事件。
        mOnGestureListener.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        String https = intent.getStringExtra("https");

        pb = (ProgressBar) findViewById(R.id.pb);
        mWebView = (WebView) findViewById(R.id.wv);

        webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setBuiltInZoomControls(true);//设置出现缩放工具
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        //设置支持js，默认不支持
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);// 保存表单数据
        //webSettings.setLoadWithOverviewMode(true);
        //ws.setGeolocationEnabled(true);// 启用地理定位
        //ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setSupportMultipleWindows(true);// 新加
        xwebchromeclient = new myWebChromeClient();
        mWebView.setWebChromeClient(xwebchromeclient);
        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        mWebView.requestFocusFromTouch();
        //Log.i("myhttp",https);
        if(null == https){
            https ="http://www.baidu.com" ;
        }else {

            https ="http://"+ https ;
        }
        mWebView.setClickable(true);
        mWebView.setLongClickable(true);
        //根据url加载数据 并呈现
        mWebView.loadUrl(https);
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    /*   *//**
     * 把触摸事件交给手势识别对象
     */
  /*  @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {    //注意这里不能用ONTOUCHEVENT方法，不然无效的
        mOnGestureListener.onTouchEvent(ev);
        mWebView.onTouchEvent(ev);//这几行代码也要执行，将webview载入MotionEvent对象一下，况且用载入把，不知道用什么表述合适
        return super.dispatchTouchEvent(ev);
    }*/
    //手势识别
       /*mOnGestureListener = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判断竖直方向移动的大小
                if(Math.abs(e1.getRawY() - e2.getRawY())>100){
                    //Toast.makeText(getApplicationContext(), 动作不合法, 0).show();
                    return true;
                }
                if(Math.abs(velocityX)<150){
                    //Toast.makeText(getApplicationContext(), 移动的太慢, 0).show();
                    return true;
                }

                if((e1.getRawX() - e2.getRawX()) >200){// 表示 向右滑动表示下一页
                    //显示下一页
                    //next(null);
                    return true;
                }

                if((e2.getRawX() - e1.getRawX()) >200){  //向左滑动 表示 上一页
                    //显示上一页
                    // pre(null);
                    mWebView.goBack();
                    return true;//消费掉当前事件  不让当前事件继续向下传递
                }
                return false;
            }
        });*/

    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    public class myWebChromeClient  extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        //播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mWebView.setVisibility(View.INVISIBLE);
            //如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            xCustomView = view;
            video_fullView.addView(xCustomView);
            xCustomViewCallback = callback;
            video_fullView.setVisibility(View.VISIBLE);
        }
        @Override
        //视频播放退出全屏会被调用的
        public void onHideCustomView() {
            if (xCustomView == null)//不是全屏播放状态
                return;
            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            // Remove the custom view from its container.
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            video_fullView.setVisibility(View.VISIBLE);
        }

// //视频加载添加默认图标
// @Override
// public Bitmap getDefaultVideoPoster() {
// //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
// if (xdefaltvideo == null) {
// xdefaltvideo = BitmapFactory.decodeResource(getResources(), R.drawable.seach_icon);
// }
// return xdefaltvideo;
// }

        //视频加载时进程loading
// @Override
// public View getVideoLoadingProgressView() {
// //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
// if (xprogressvideo == null) {
// LayoutInflater inflater = LayoutInflater.from(PlayVideoWebViewActivity.this);
// xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
// }
// return xprogressvideo;
// }

        //网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTitle(title);
            //获取加载的内容
            MyLog.i("newprogress",title+"--");
            setContText(title);
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //加载的进度
            MyLog.i("newprogress",newProgress+"");
        }
    }
    /**
     * 判断是否是全屏
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }
    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        video_fullView.removeAllViews();
        mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.destroy();
        mWebView = null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else if(mWebView.canGoBack()){
                mWebView.goBack();
            }else {
                finish();
            }
        }
        return false;
    }
}
