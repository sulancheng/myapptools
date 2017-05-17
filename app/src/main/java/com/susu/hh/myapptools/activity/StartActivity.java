package com.susu.hh.myapptools.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.BitmapUtil;
import com.susu.hh.myapptools.utils.LogcatFileManager;

import java.io.File;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        requestMultiplePermissions();
    }

    private void initfile() {
        String dpath = BitmapUtil.getDpath();
        File file = new File(dpath);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    private void requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean b1 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            boolean b2 = checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            boolean b3 = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean b4 = checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;

//            PermissionUtils.requestPermission(this,1000,Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_PHONE_STATE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.ACCESS_FINE_LOCATION,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_CONTACTS,true);


            if (!b1 || !b2 || !b3 || !b4){
                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 2000);
            } else {
                starttoactovoty();
                initfile();
                LogcatFileManager.getInstance().start(BitmapUtil.getDpath("mylog"));
            }
        } else {
            starttoactovoty();
            initfile();
            LogcatFileManager.getInstance().start(BitmapUtil.getDpath("mylog"));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean flag = false;
        if (requestCode == 2000) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                    break;
                }
            }
            if(flag){

            }else {
                //starttoactovoty();
            }
            starttoactovoty();
            initfile();
            LogcatFileManager.getInstance().start(BitmapUtil.getDpath("mylog"));
        }
    }
    void starttoactovoty(){
        startActivity(new Intent(StartActivity.this,MainActivity.class));
    }
}
