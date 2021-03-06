package com.susu.hh.myapptools.network;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.susu.hh.myapptools.utils.Base64Utils;
import com.susu.hh.myapptools.utils.MyLog;
import com.susu.hh.myapptools.utils.MyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by su
 * on 2017/3/24.
 */
public class OkUtils {

    public static boolean RELEASE = true;
    private static final String ADDRESS_2 = "http://hcb.leifang.xin"; //release service
    private static final String ADDRESS_1 = "http://192.168.0.106:8080"; //local service
    private static final String SERVICE = "/app";
    private static Gson gson = new Gson();

    public static String getServerChannel() {
        String serverUrl = "";
        if (RELEASE) {
            serverUrl = ADDRESS_2 + SERVICE;
        } else {
            serverUrl = ADDRESS_1 + SERVICE;
        }
        return serverUrl;
    }

    public static String photopa() {
        String serverUrl = "";
        if (RELEASE) {
            serverUrl = ADDRESS_2;
        } else {
            serverUrl = ADDRESS_1;
        }
        return serverUrl;
    }


    public static void getTestbiao(Context context, Object reparam, MyResponse myresponse) {
        requsetJsonparm(getServerChannel() + "/getExamineList", context, reparam, myresponse);
    }

    public static void upDataStudy(Context context, List<File> files, HashMap<String, String> params, MyResponse myresponse) {
        requsetWenjian(getServerChannel() + "/update", context, files, params, myresponse, 1);
    }
    //厨师列表
    public static void getCookerList(Context context, Object reparam, MyResponse myresponse) {
        requsetJsonparm(getServerChannel() + "/getCookerList", context, reparam, myresponse);
    }


    //    //获取论坛回复
//    public static synchronized void getLunThfconent(Context context, ZxXqLt reparam, MyResponse myresponse) {
//        requsetJsonparm(getServerChannel() + "/getForumReplyByID",context,reparam,myresponse);
//    }
    public static void logintwo(Context context, MyResponse myresponse, File file) {
        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("username", "sulc");
        paramss.put("password", "123456");
        paramss.put("q", "s");
        ArrayList<File> mfiles = new ArrayList<>();
        mfiles.add(file);
        //requsetWenjian("http://192.168.0.101:8085/php/upload_file.php", context, mfiles, myresponse);
    }

    public static void requsetJsonparm(String url, final Context mContext, HashMap<String, String> params, final MyResponse myresponse, final int type) {
        MyLog.i("post_json数据请求", " url=" + url + " params = " + params.toString());
        OkGo.<String>post(url)
                .tag(mContext)
                //.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .params(params)//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        checkresponse(1, response.body(), myresponse);
                        MyLog.i("post_json数据请求返回的初始数据", " response =" + response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        MyToast.makeText(mContext, "网络或服务器异常!", Toast.LENGTH_SHORT);
                        MyLog.i("返回的数据", response.getException().toString());
                        checkresponse(2, response.body(), myresponse);
                    }
                });
    }

    public static void requsetWenjian(String url, final Context mContext, List<File> files, HashMap<String, String> params, final MyResponse myresponse, final int type) {

        PostRequest<String> myfileRequset = OkGo.<String>post(url)//"http://192.168.0.105:8081/student/form"
                .tag(mContext);
        //.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
        // .params("myfile", myfile);//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
        if (files != null && files.size() > 0) {
            for (int a = 0; a < files.size(); a++) {
                myfileRequset.params("imgs" + a, files.get(a));
            }
        }
       // myfileRequset.params("imgs1" , files.get(0));
//        myfileRequset.addFileParams("imgs",files);
        myfileRequset.params(params);
        myfileRequset.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                checkresponse(1, response.body(), myresponse);
                MyLog.i("post_json数据请求返回的初始数据", " response =" + response.body());
            }

            @Override
            public void onError(Response<String> response) {
                MyToast.makeText(mContext, "网络或服务器异常!", Toast.LENGTH_SHORT);
                MyLog.i("返回的数据", response.getException().toString());
                checkresponse(2, response.body(), myresponse);
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                MyLog.i("返回的进度", progress.fraction + "");
                if (myresponse != null) {
                    myresponse.getProgress(progress);
                }
            }
        });
    }

    public static void requsetJsonparm(final String url, final Context mContext, Object obj, final MyResponse myresponse) {

        String json = gson.toJson(obj);
        String encodejson = Base64Utils.encodeBase64(json);
        MyLog.i("post_json数据请求", " url=" + url + " json = " + json );
        OkGo.<String>post(url)
                .tag(mContext)
                //.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                // .params(params)//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                .upJson(encodejson)//setCertificates
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        checkresponse(1, response.body(), myresponse);
                        MyLog.i("post_json数据请求返回的初始数据", " response =" + response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        MyToast.makeText(mContext, "网络或服务器异常!", Toast.LENGTH_SHORT);
                        MyLog.i("网络或服务器异常", " response =" + url);
                        checkresponse(2, response.body(), myresponse);
                    }
                });
    }

    public abstract static class MyResponse {
        public abstract void expResponse(String myresponse);

        public abstract void error(String erro);

        public void getProgress(Progress progress) {

        }
    }

    private static void checkresponse(int type, String responseBody, MyResponse myresponse) {
        if (type == 1) {//成功
            if (myresponse != null) {
                myresponse.expResponse(Base64Utils.decodeBase64(responseBody));
            }

        } else {
            if (myresponse != null) {
                myresponse.error(Base64Utils.decodeBase64(responseBody));
            }
        }

    }
}
