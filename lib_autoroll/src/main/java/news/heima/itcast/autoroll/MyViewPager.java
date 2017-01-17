package news.heima.itcast.autoroll;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/5/4.
 */
public class MyViewPager extends ViewPager {


    private static final String TAG = "Test";

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        boolean result = super.onTouchEvent(ev);
        Log.i(TAG, "MyViewPager onTouchEvent: " + result);
        return result;
    }
}
