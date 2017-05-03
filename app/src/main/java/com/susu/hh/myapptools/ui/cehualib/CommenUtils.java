package com.susu.hh.myapptools.ui.cehualib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/3.
 * <p/>
 * 工具类：
 */
public class CommenUtils {

    private static final String TAG = "CommenUtils";






    /*
        制定字符串传拼音字符串的规则：
          空格：忽略
          字母： 转大写
          汉字：hanziToPinyin---》拼音
          特殊符号或数字：传---> #

          传 &&&&&&  ）））000cdcd智 887***&&播  ((&&B客
     */
    public static String formatPinyinString(String pinyinStr) {

        if (TextUtils.isEmpty(pinyinStr)) {

            return "";
        }

        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = pinyinStr.toCharArray();

        for (char ch : chars) {

            Log.i(TAG, "ch:" + ch);

            //判断:
            //如果是空格就忽略
            if (Character.isWhitespace(ch)) {
                continue;
                //是字母:转大写
            } else if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]")) {

                String pinyin ="" ;//hanziToPinyin(ch);
                Log.i(TAG, "pinyin:" + pinyin);
                stringBuffer.append(pinyin);

                //Character.isLetter(ch) 汉字也会当作字母
            } else if (Character.isLetter(ch)) {

                Log.i(TAG, "Character.isLetter:" + ch);
                stringBuffer.append(Character.toUpperCase(ch));
                //汉字：hanziToPinyin---》拼音 汉字的正则：[\u4E00-\u9FA5]
            } else {

                stringBuffer.append("#");
            }
        }


        return stringBuffer.toString();
    }


    public static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static Toast sToast;

    public static float dip2px(Context context, float value) {


        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static Handler getHandler() {

        return sHandler;
    }

    public static void showSafeToast(final Context context, final String text) {

        sHandler.post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void showSingleToast(Context context, String text) {

        if (sToast == null) {

            sToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }

        sToast.setText(text);
        sToast.show();
    }


    public static Float evaluateFloat(float fraction, Number startValue, Number endValue) {

        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }


    public static Object evaluateColor(float fraction, Object startValue, Object endValue) {

        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

    public static String getCurrentTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }


}
