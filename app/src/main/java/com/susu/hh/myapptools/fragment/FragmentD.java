package com.susu.hh.myapptools.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.activity.MainActivity;
import com.susu.hh.myapptools.activity.player.MyVitamioPlayerTest;
import com.susu.hh.myapptools.adapter.NetVideoPagerAdapter;
import com.susu.hh.myapptools.adapter.SearchAdapter;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.bean.SearchBean;
import com.susu.hh.myapptools.utils.MyLog;
import com.susu.hh.myapptools.utils.volleyutils.VolleyEngine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * susu
 * A simple {@link Fragment} subclass.
 */
public class FragmentD extends BaseFragment implements AdapterView.OnItemClickListener {
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private SearchBox search;
    private NetVideoPagerAdapter videoPagerAdapter;
    private SearchAdapter mSearchAdapter;
    private ProgressBar pb_loading;
    private TextView tv_nomedia;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyLog.i("mediaItemfragmentD", "msg.what = " + msg.what);
            switch (msg.what) {
                case 100:
                    if (videoPagerAdapter == null) {
                        //有数据
                        //设置适配器
                        videoPagerAdapter = new NetVideoPagerAdapter(mContext, mediaItems);
                        listview.setEmptyView(pb_loading);
                        listview.setAdapter(null);
                        listview.setAdapter(videoPagerAdapter);
                        videoPagerAdapter.notifyDataSetChanged();
                        MyLog.i("mediaItemfragmentD", "videoPagerAdapter = " + msg.what);
                    }
                    break;
                case 101:
                    showData();
                    break;
            }

        }
    };
    private List<SearchBean.ItemData> items;

    private void showData() {
        if (mSearchAdapter == null) {
            if (items != null && items.size() > 0) {
                mSearchAdapter = new SearchAdapter(mContext, items);
                listview.setEmptyView(pb_loading);
                listview.setAdapter(null);
                listview.setAdapter(mSearchAdapter);
                mSearchAdapter.notifyDataSetChanged();
            } else {
                pb_loading.setVisibility(View.GONE);
            }
        }
    }

    private ListView listview;
    private SearchBean searchBean;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyLog.i("FragmentD", "FragmentD调用了");
        View inflated = inflater.inflate(R.layout.fragment_fragment_d, container, false);
        return inflated;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) this.mContext;
        mainActivity.headMain(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity mainActivity = (MainActivity) this.mContext;
        mainActivity.headMain(View.VISIBLE);
    }

    @Override
    public void viewCreated(View view, Bundle savedInstanceState) {
//        MainActivity mainActivity = (MainActivity) this.mContext;
//        mainActivity.headMain(View.GONE);
        search = (SearchBox) view.findViewById(R.id.searchbox);
        listview = (ListView) view.findViewById(R.id.listview);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);

        if (items != null && items.size() > 0) {
            showData();
        } else {
            seachfoNetwork("");
        }
        for (int x = 0; x < 10; x++) {
            SearchResult option = new SearchResult("本人好帅 " + Integer.toString(x), getResources().getDrawable(R.drawable.ic_history));
            search.addSearchable(option);
        }

        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                search.toggleSearch();
                Toast.makeText(FragmentD.this.mContext, "Menu click", Toast.LENGTH_SHORT).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
            }

            @Override
            public void onSearchClosed() {
            }

            @Override
            public void onSearchTermChanged(String term) {
                Toast.makeText(FragmentD.this.mContext, "onSearchTermChang" +
                        "ed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearch(String searchTerm) {
                //点击查找之后
                Toast.makeText(FragmentD.this.mContext, searchTerm + " Searched", Toast.LENGTH_SHORT).show();
                seachfoNetwork(searchTerm);
                pb_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResultClick(SearchResult result) {
                Toast.makeText(FragmentD.this.mContext, "onResultClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchCleared() {
                Toast.makeText(FragmentD.this.mContext, "onSearchCleared", Toast.LENGTH_SHORT).show();
            }

        });
        search.enableVoiceRecognition(this);
        search.setEnabled(false);
        search.toggleSearchclose();
        search.setOverflowMenu(R.menu.overflow_menu);
        search.setOverflowMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.test_menu_item:
                        Toast.makeText(FragmentD.this.mContext, "Clicked!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });


        listview.setOnItemClickListener(this);
    }

    private String tname;

    private void seachfoNetwork(String name) {
        String url = null;
        this.tname = name;

        try {
            String text = URLEncoder.encode(name, "UTF-8");
            if ("".equals(name)) {
                url = VolleyEngine.NET_URL;
            } else {
                url = VolleyEngine.SEARCH_URL + text;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MyLog.i("onResponse", url);
        VolleyEngine.getInstance().jsonObjRequest(url, null, new Response.Listener<JSONObject>() {
            private Gson gson;

            @Override
            public void onResponse(JSONObject jsonObject) {
                MyLog.i("onResponse", jsonObject.toString());
                if ("".equals(tname)) {
                    //json太长了  去部分的来比较简单
                    //MediaRespond mediaRespond = gson.fromJson(jsonObject.toString(), MediaRespond.class);
                    try {
                        JSONArray jsonArray = jsonObject.optJSONArray("trailers");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //获取第i个数组元素
                                JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                                if (jsonObjectItem != null) {
                                    //下面方法时保留所有的属性，bean没有  只有部分
                                    // MediaItem mediaItem = gson.fromJson(jsonObject.toString(), MediaItem.class);
                                    MediaItem mediaItem = new MediaItem();

                                    String movieName = jsonObjectItem.optString("movieName");//name
                                    mediaItem.setName(movieName);

                                    String videoTitle = jsonObjectItem.optString("videoTitle");//desc
                                    mediaItem.setDesc(videoTitle);
                                    //缩略图用
                                    String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                                    mediaItem.setImageUrl(imageUrl);

                                    String hightUrl = jsonObjectItem.optString("hightUrl");//data
                                    mediaItem.setData(hightUrl);
                                    MyLog.i("mediaItemfragmentD", mediaItem.toString());
                                    //把数据添加到集合
                                    mediaItems.add(mediaItem);
                                }
                            }
                            mHandler.sendEmptyMessage(100);
                        }
                    } catch (Exception e) {
                        Toast.makeText(FragmentD.this.mContext, "网路错误!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    gson = new Gson();
                    searchBean = gson.fromJson(jsonObject.toString(), SearchBean.class);
                    items = searchBean.getItems();
                    mHandler.sendEmptyMessage(101);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FragmentD.this.mContext, "网路错误!", Toast.LENGTH_SHORT).show();
                pb_loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //3.传递列表数据-对象-序列化
        Intent intent = new Intent(mContext, MyVitamioPlayerTest.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videolist", mediaItems);
        intent.putExtras(bundle);
        intent.putExtra("position", position);
        mContext.startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == getActivity().RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            MyLog.i("fragmentd activity", matches.toString());
            search.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
