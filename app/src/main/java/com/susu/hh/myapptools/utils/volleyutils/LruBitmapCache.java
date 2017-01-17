package com.susu.hh.myapptools.utils.volleyutils;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/9/30.
 */
public class LruBitmapCache implements ImageLoader.ImageCache {
    private android.util.LruCache<String, Bitmap> mCache;
    //新建一个LruCache,这是个一级缓存，是缓存到内存上的
    public LruBitmapCache() {
        //指定大小为15Mb
        int maxSize = 15 * 1024 * 1024;
        mCache = new android.util.LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };
    }
    //ImageLaoder.ImageCache借口的回调方法，从内存缓存中获取图片
    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }
    //将图片缓存到内存中
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}
