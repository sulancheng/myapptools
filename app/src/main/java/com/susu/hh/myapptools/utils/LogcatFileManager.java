package com.susu.hh.myapptools.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by mac on 16/1/15.
 */

public class LogcatFileManager implements Thread.UncaughtExceptionHandler {
    private static LogcatFileManager INSTANCE = null;
    private static String PATH_LOGCAT;
    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private LogDumper mLogDumper = null;
    private int mPId;
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String TAG = LogcatFileManager.class.toString();

    public static LogcatFileManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new LogcatFileManager();
        }
        return INSTANCE;
    }


    private LogcatFileManager()
    {
        mPId = android.os.Process.myPid();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public void startLogcatManager(Context context)
    {
        String folderPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MMF-Logcat";
        }
        else
        {
            folderPath = context.getFilesDir().getAbsolutePath() + File.separator + "MMF-Logcat";
        }
        LogcatFileManager.getInstance().start(folderPath);
    }


    public void stopLogcatManager()
    {
        LogcatFileManager.getInstance().stop();
    }


    private void setFolderPath(String folderPath)
    {
        File folder = new File(folderPath);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
//        if (!folder.isDirectory())
//        {
//            throw new IllegalArgumentException("The logcat folder path is not a directory: " + folderPath);
//        }


        PATH_LOGCAT = folderPath.endsWith("/") ? folderPath : folderPath + "/";
        MyLog.d(PATH_LOGCAT);
    }


    public void start(String saveDirectoy)
    {

        try {
            setFolderPath(saveDirectoy);
            if (mLogDumper == null)
            {
                mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
            }
            mLogDumper.start();
        }
        catch (Exception e){

        }
    }


    public void stop()
    {
        if (mLogDumper != null)
        {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
            Log.d("TEST", "defalut");
        }
        else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("", "error : ", e);
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            // mDefaultHandler.uncaughtException(thread, ex);
            System.exit(1);
        }
    }


    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 收集设备参数信息
//        collectDeviceInfo(cont);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }


    /**
     * 保存错误信息到文件
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = getTraceInfo(ex);
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = simpleDateFormat1.format(new Date());
            String fileName = "crash-myapptools-" + time + "-" + timestamp + ".log";

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = BitmapUtil.getDpath("mylogcrash/");
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }


    /**
     * 整理异常信息
     * @param e
     * @return
     */
    public static StringBuffer getTraceInfo(Throwable e) {
        StringBuffer sb = new StringBuffer();

        Throwable ex = e.getCause() == null ? e : e.getCause();
        StackTraceElement[] stacks = ex.getStackTrace();
        for (int i = 0; i < stacks.length; i++) {
            if (i == 0) {
                setError(ex.toString());
            }
            sb.append("class: ").append(stacks[i].getClassName())
                    .append("; method: ").append(stacks[i].getMethodName())
                    .append("; line: ").append(stacks[i].getLineNumber())
                    .append(";  Exception: ").append(ex.toString() + "\n");
        }
        Log.d(TAG, sb.toString());
        return sb;
    }


    /**
     * 设置错误的提示语
     * @param e
     */
    public static void setError(String e) {

    }



    private class LogDumper extends Thread
    {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;


        public LogDumper(String pid, String dir)
        {
            mPID = pid;
            try
            {
                out = new FileOutputStream(new File(dir, "logcat-myapptools-" + simpleDateFormat1.format(new Date()) + ".log"), true);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


            /**
             * * * log level：*:v , *:d , *:w , *:e , *:f , *:s * * Show the
             * current mPID process level of E and W log. * *
             */
            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            cmds = "logcat  *:v | grep \"( Tohow )\"";
        }


        public void stopLogs()
        {
            mRunning = false;
        }


        @Override
        public void run()
        {
            try
            {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null)
                {
                    if (!mRunning)
                    {
                        break;
                    }
                    if (line.length() == 0)
                    {
                        continue;
                    }
                    if (out != null && line.contains(mPID))
                    {
                        out.write((simpleDateFormat2.format(new Date()) + "  " + line + "\n").getBytes());
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (logcatProc != null)
                {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null)
                {
                    try
                    {
                        mReader.close();
                        mReader = null;
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (out != null)
                {
                    try
                    {
                        out.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
}