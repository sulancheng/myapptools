package com.susu.hh.myapptools.utils.volleyutils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

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
