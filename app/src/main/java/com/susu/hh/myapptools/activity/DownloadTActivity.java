package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.MyLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTActivity extends Activity {
    private static final int DOWNLOAD_COMPLETE = 001;
    private static final int MAXPROGRESS = 002;
    private static final int PROGRESS = 003;
    NumberProgressBar m_progressDlg;
    private String m_appNameStr = "test.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_t);
        init();
    }

    private void init() {
        m_progressDlg = (NumberProgressBar) findViewById(R.id.mprogress);
        //m_progressDlg =  new ProgressDialog(this);
        //m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        //m_progressDlg.setIndeterminate(false);
        doNewVersionUpdate();
    }

    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdate() {

        String str = "是否更新？";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                m_progressDlg.setTitle("正在下载");
//                                m_progressDlg.setMessage("请稍候...");
//                                m_progressDlg.show();
                                downFile(m_appNameStr);  //开始下载
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    private void downFile(final String m_appNameStr) {
        //下载完后存到SD中。
        final File file = new File(
                Environment.getExternalStorageDirectory(),
                m_appNameStr);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //1.获取服务器地址（URL）
                    //String path = "http://192.168.15.28:8080/weixin/2.jpg";
                    String path = "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-30-17265582_1877445642507654_3057988544061505536_n.jpg";
                    URL url = new URL(path);
                    //2.建立连接
                    HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
                    //3设置请求方式和请求时间
                    openConnection.setRequestMethod("GET");
                    openConnection.setConnectTimeout(5000);
                    //4判断响应
                    if (openConnection.getResponseCode() == 200) {
                        //5.响应正确，下载图片（通过流）
                        InputStream inputStream = openConnection.getInputStream();
                        int contentLength = openConnection.getContentLength();
                        Log.e("DownloadTActivity", "contentLength = " + contentLength);
                        mhandle.obtainMessage(MAXPROGRESS, contentLength,0).sendToTarget();
                        //将流变成图片
                        //Bitmap bm = BitmapFactory.decodeStream(inputStream);

                        OutputStream outputStream = new FileOutputStream(file);
                        int len;
                        int count = 0;
                        byte[] arr = new byte[1024];
                        while ((len = inputStream.read(arr)) != -1) {
                            outputStream.write(arr, 0, len);
                            count += len;
                            int aa = (count * 100) / contentLength;
                            MyLog.i("下载进度", "aa =" + aa);
                            if (contentLength > 0) {
                                mhandle.obtainMessage(PROGRESS, count,0).sendToTarget();
                                Thread.sleep(500);
                            }
                        }
                        if (outputStream != null) {

                            outputStream.close();
                        }
                        if (inputStream != null)
                            inputStream.close();
                        mhandle.obtainMessage(DOWNLOAD_COMPLETE).sendToTarget();
                        //mhandler.sendEmptyMessage(SUCCESS_DOWNLOAD);
                        Log.e("DownloadTActivity", "下载成功！");

                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("DownloadTActivity", "下载失败！");
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // install();
                    break;
                case MAXPROGRESS:
                    m_progressDlg.setMax(msg.arg1);
                    break;
                case PROGRESS:
                    m_progressDlg.setProgress(msg.arg1);
                    break;
            }

        }
    };

    private void install() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }
}

