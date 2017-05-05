package com.susu.hh.myapptools.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.MyLog;

import java.io.File;

public class SelectLocalwjActivity extends Activity {
    private static final int SELECT_FILE_REQ = 1;
    private static final String EXTRA_URI = "uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_localwj);
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/zip");

        // intent.setType(“image/*”);
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // file browser has been found on the device
            startActivityForResult(intent, SELECT_FILE_REQ);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case SELECT_FILE_REQ:
// and read new one
                final Uri uri = data.getData();
            /*
			 * The URI returned from application may be in 'file' or 'content' schema. 'File' schema allows us to create a File object and read details from if
			 * directly. Data from 'Content' schema must be read by Content Provider. To do that we are using a Loader.
			 */
                if (uri.getScheme().equals("file")) {
                    // the direct path to the file has been returned
                    final String path = uri.getPath();
                    final File file = new File(path);
                    //updateFileInfo(file.getName(), file.length(), mFileType);
                    MyLog.i("选择系统文件返回的信息：" + file.getName() + " === " + file.length() + "====" + file.getPath());
                } else if (uri.getScheme().equals("content")) {
                    // an Uri has been returned
                    // if application returned Uri for streaming, let's us it. Does it works?
                    // FIXME both Uris works with Google Drive app. Why both? What's the difference? How about other apps like DropBox?
                    final Bundle extras = data.getExtras();
                    if (extras != null && extras.containsKey(Intent.EXTRA_STREAM)) {
                        Uri uuuri = extras.getParcelable(Intent.EXTRA_STREAM);
                    }

                    // file name and size must be obtained from Content Provider
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable(EXTRA_URI, uri);
                    //getLoaderManager().restartLoader(SELECT_FILE_REQ, bundle, this);
                }
                break;
        }
    }
}
