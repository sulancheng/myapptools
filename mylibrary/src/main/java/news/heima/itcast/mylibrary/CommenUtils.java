package news.heima.itcast.mylibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/3.
 * <p/>
 * 工具类：
 */
public class CommenUtils {

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


}
