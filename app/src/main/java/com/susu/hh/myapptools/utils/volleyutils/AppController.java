package com.susu.hh.myapptools.utils.volleyutils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/9/30.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    //
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initokgo();
    }
    private void initokgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(15, TimeUnit.SECONDS);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newBuilder().connectTimeout(5, TimeUnit.SECONDS)
        //.readTimeout(20, TimeUnit.SECONDS)
//                .build();
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())               //必须调用初始化
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
        //.addCommonHeaders(headers)                      //全局公共头
        //.addCommonParams(params);                       //全局公共参数

    }
    public static synchronized AppController getInstance() {

        return mInstance;
    }
    //获得requestQueue实例，如果没有的话则创建一个新的
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //在这里调用Volley.newRequestQueue()
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    //获得imageLoader实例，因为我们会加载图片，这个是Volley提供的图片加载器
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            //新建imageLoader需要传入两个参数，第一个为当前的requestQueue
            //第二个为设置缓存的大小，LruBitmapCache对缓存做了初始化，后面会介绍
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    //将Request添加到requestQueue中的方法，可以给request设置一个tag作为标记
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // 如果没有传入tag，那么就将TAG传入作为默认的标记
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    //将TAG作为request的默认标记
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    //取消队列中的标记为tag的request
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
