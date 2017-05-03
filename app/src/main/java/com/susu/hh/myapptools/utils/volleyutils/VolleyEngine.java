package com.susu.hh.myapptools.utils.volleyutils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30.
 */
public class VolleyEngine implements Response.Listener<JSONObject> {
    public static boolean RELEASE = true;
    /**
     * 网络视频的联网地址
     */
    public static final String NET_URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    /**
     * 搜索的路径
     */
    public static  final  String SEARCH_URL = "http://hot.news.cntv.cn/index.php?controller=list&action=searchList&sort=date&n=20&wd=";

    public static final int TIME_OUT = 5000;

    private static final String ADDRESS_1 = "http://inside.purifit.fitband.tech";//debug service
    private static final String ADDRESS_2 = "http://api.vifit.fitband.tech"; //release service
    private static final String SERVICE = "/Service";

    private static String getServerChannel() {
        String serverUrl = null;
        if (RELEASE) {
            serverUrl = ADDRESS_2 + SERVICE;
        } else {
            serverUrl = ADDRESS_1 + SERVICE;
        }
        return serverUrl;
    }

    private static VolleyEngine instance;

    private VolleyEngine() {
        listteners = new ArrayList<>();
    }

    public static VolleyEngine getInstance() {
        if (instance == null) {
            instance = new VolleyEngine();
        }
        return instance;
    }

    public void stringRequest(String url) {
        String tag_str_obj = "StringRequest";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("TAG", error.getMessage(), error);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将这个request加入到requestQueue中，就可以执行了
        AppController.getInstance().addToRequestQueue(stringRequest, null);
    }

    //post
    public void stringRequestpo(String url) {
        // 2 创建一个post请求
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //tv_volley_result.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // tv_volley_result.setText("请求失败" + volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("value1", "param1");

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, null);
    }
//login
   /* public static void login(Login param) {
        //jsonObjRequest(getServerChannel() + "/userinfoDatas/loginAll",param);
    }*/

    public void jsonObjRequest(String url, Map<String, String> map) {
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("user", "zhangqi");
        if (map != null) {
            map2 = map;
        }
        JSONObject params = new JSONObject(map2);
        jsonObjRequest(url, params, 001);
    }

    public void jsonObjRequest(String url, Object param) {
       /* //伪代码
        String json = "[{\"username\":\"test\"},{\"username\":\"test2\"}]";
        Gson gson = new Gson();//gson将对象等转成Json语句  通过obj转成json对象。上传。*/
        JSONObject jsonObject = null;
        Gson gson = new Gson();
        String json = gson.toJson(param);
        try {
            jsonObject = new JSONObject(json);//可以将json转化为obj
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            jsonObjRequest(url, jsonObject, 001);
        }
    }

    public void jsonObjRequest(String url, JSONObject params, int tag) {//param为null，就是get
        String tag_json_obj = "JsonObjRequest";
        //第二个参数我们传了user=zhangqi。则请求方法就为post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, params, this, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, null);
    }
    //自己传入接口
    public void jsonObjRequest(String url, JSONObject params, Response.Listener<JSONObject>  respone,Response.ErrorListener  err) {//param为null，就是get,
        String tag_json_obj = "JsonObjRequest";
        //第二个参数我们传了user=zhangqi。则请求方法就为post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, params, respone, err);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, null);
    }
    //对外的接口
    private OnMyNetworkInterface onMyNetworkInterface;
    public interface OnMyNetworkInterface{
        void onSucessd(JSONObject jsonObject);
        void onFail(VolleyError volleyError);
    }
    private void setOnMyNetworkInterface(OnMyNetworkInterface onMyNetworkInterface){
        this.onMyNetworkInterface = onMyNetworkInterface;
    }
    public void JsonObjRequest2(String url) {
        String tag_json_obj = "JsonObjRequest";
        //伪代码
        String json = "[{\"username\":\"test\"},{\"username\":\"test2\"}]";
        Gson gson = new Gson();//gson将对象等转成Json语句  通过obj转成json对象。上传。
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", "zhangqi");
        JSONObject params = new JSONObject(map);
        /*try {
            JSONObject jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //第二个构造。
        //第二个参数我们传了user=zhangqi。则请求方法就为post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //可在调用此方法的时候传入自己的接口 将jsonpbject做了处理之后再次 通过自己的接口返回。
                if(onMyNetworkInterface !=null){
                    onMyNetworkInterface.onSucessd(jsonObject);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(onMyNetworkInterface !=null){
                    //可将结果进行处理传入自己需要的值。
                    onMyNetworkInterface.onFail(volleyError);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");
                return params;
            }
        };
        //addToRequestQueue(jsonObjectRequest, tag_json_obj);
    }

    private List<Response.Listener<JSONObject>> listteners;

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (listteners != null) {
            for (Response.Listener<JSONObject> locationListener : listteners) {
                locationListener.onResponse(jsonObject);
            }
        }
    }

    public void addListener(Response.Listener<JSONObject> locationListener) {
        listteners.add(locationListener);
    }

    public void deleteListener(Response.Listener<JSONObject> locationListener) {
        listteners.remove(locationListener);
    }
}
