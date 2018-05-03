package com.susu.hh.myapptools.fragment;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.player.MyVitamioPlayerTest;
import com.susu.hh.myapptools.adapter.VideoPagerAdapter;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends BaseFragment {
    private ListView listview;
    private TextView tv_nomedia;
    private ProgressBar pb_loading;
    private VideoPagerAdapter videoPagerAdapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    if(videoPagerAdapter == null){
                        //有数据
                        //设置适配器
                        videoPagerAdapter = new VideoPagerAdapter(mContext,mediaItems,true);
                        listview.setAdapter(videoPagerAdapter);
                        pb_loading.setVisibility(View.VISIBLE);
                        listview.setEmptyView(pb_loading);
                    }
                    if(mediaItems != null && mediaItems.size() >0){
                        //把文本隐藏
                        tv_nomedia.setVisibility(View.GONE);
                    }else{
                        //没有数据
                        //文本显示
                        tv_nomedia.setVisibility(View.VISIBLE);
                        pb_loading.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    };
    /**
     * 装数据集合
     */
    private ArrayList<MediaItem> mediaItems;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateb = inflater.inflate(R.layout.fragment_fragment_b, container, false);
        return inflateb;
    }
    @Override
    public void viewCreated(View view, Bundle savedInstanceState) {
        initDate();
        listview = (ListView) view.findViewById(R.id.listview);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        //设置ListView的Item的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,MyVitamioPlayerTest.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist",mediaItems);
                intent.putExtras(bundle);
                intent.putExtra("position",position);
                mContext.startActivity(intent);
                //对面接  序列化为了传递对象 需实现响应的序列化
                /*Book mBook = (Book)getIntent().getParcelableExtra("mBook");
                  * Person mPerson = (Person)getIntent().getSerializableExtra("mPerson");    */
            }
        });
    }

    private void initDate() {
        MyLog.i("FragmentB","本地视频的数据被初始化了。。。");
        //加载本地视频数据
        getDataFromLocal();
        getDataFromSpringboot();
    }

    private void getDataFromSpringboot() {

    }

    /**
     * 从本地的sdcard得到数据
     * //1.遍历sdcard,后缀名
     * //2.从内容提供者里面获取视频
     * //3.如果是6.0的系统，动态获取读取sdcard的权限
     */
    public void getDataFromLocal() {
        new Thread(){
            @Override
            public void run() {
                super.run();

//                isGrantExternalRW((Activity) context);
//                SystemClock.sleep(2000);
                mediaItems = new ArrayList<>();
                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard的名称
                        MediaStore.Video.Media.DURATION,//视频总时长
                        MediaStore.Video.Media.SIZE,//视频的文件大小
                        MediaStore.Video.Media.DATA,//视频的绝对地址
                        MediaStore.Video.Media.ARTIST,//歌曲的演唱者

                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if(cursor != null){
                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();


                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//视频的文件大小
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);//视频的播放地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);
                        MyLog.i("FragmentB","mediaItem。。。"+mediaItem.toString());
                        mediaItems.add(mediaItem);//写在上面
                    }
                    cursor.close();
                }
                //Handler发消息
                mHandler.sendEmptyMessage(10);
            }
        }.start();

    }
}
