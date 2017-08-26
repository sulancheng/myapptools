package news.heima.itcast.autoroll;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import news.heima.itcast.mylibrary.CommenUtils;

/**
 * Created by Administrator on 2016/5/4.
 * <p/>
 * public static View inflate(Context context, int resource, ViewGroup root)
 * <p/>
 * 把resource指定的资源加载成View 返回， ViewGroup root指定加载的View挂载到的父容器
 */
public class
AutoRollLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String TAG = "AutoRollLayout";
    private ViewPager mViewPager;
    private TextView mTitleTextView;
    private LinearLayout mDotsView;
    private int dot_size;
    private GestureDetector mGestureDetector;

    public AutoRollLayout(Context context) {
        this(context, null);
    }

    public AutoRollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        public void onItemClick(RollItem rollItem);
    }

    //不管怎么使用都会调用这个方法
    public AutoRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //加载xml布局
        View.inflate(context, R.layout.roll_layout, this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //手势识别
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            //点击事件回调
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                Log.i("Test", "imageView : onClick");

                if (mOnItemClickListener != null) {

                    int currentItem = mViewPager.getCurrentItem();
                    RollItem rollItem = mRollItems.get(currentItem);
                    mOnItemClickListener.onItemClick(rollItem);
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                //CommenUtils.showSingleToast(getContext(), "GestureDetector:onScroll");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        //给ViewPager监听触摸事件
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //把用户的触摸事件给手势识别
                mGestureDetector.onTouchEvent(event);


                int action = event.getAction();

                //手指按下的时候暂停轮播
                if (action == MotionEvent.ACTION_DOWN) {

                    mTapInTouch = true;
                    //当手指抬起的时候继续轮播
                } else if (action == MotionEvent.ACTION_UP) {

                    mTapInTouch = false;
                }

                //返回了true ViewPager就不能滑动了，滑动是由ViewPager的onTouchEvent
                return false;
            }
        });

        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mDotsView = (LinearLayout) findViewById(R.id.ll_dots_view);

        dot_size = (int) CommenUtils.dip2px(context, 10);

    }

    private List<RollItem> mRollItems;

    //向外部提供设置数据的方法
    public AutoRollLayout setData(List<RollItem> rollItems) {

        if (rollItems == null) return this;
        this.mRollItems = rollItems;

        //设置适配
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnPageChangeListener(this);
        //加滑动的点
        addDots();

        mPosition = 0;
        //默认选中第一个
        onPageSelected(0);

        return this;

    }

    //手指按下
    private boolean mTapInTouch;

    //任务：完成自动轮播的任务
    private Runnable mAutoTask = new Runnable() {

        @Override
        public void run() {

            //轮播
            if (!mTapInTouch) {

                autoNext();
            }
            CommenUtils.getHandler().postDelayed(this, 2000);
        }
    };

    private boolean mAutoRight;

    private void autoNext() {

        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == 0) {

            mAutoRight = true;
        }

        if (currentItem == mPagerAdapter.getCount() - 1) {

            mAutoRight = false;
        }
        // 6  %  5 = 1
        currentItem = mAutoRight ? (currentItem + 1) : (currentItem - 1) % mPagerAdapter.getCount();
        //Log.i(TAG, mPagerAdapter.getPageTitle(currentItem).toString());
        mViewPager.setCurrentItem(currentItem);
    }


    public void startAuto() {

        mTapInTouch = false;
        stopAuto();
        CommenUtils.getHandler().post(mAutoTask);
    }

    public void stopAuto() {

        CommenUtils.getHandler().removeCallbacks(mAutoTask);
    }

    private OnClickListener mDotOnClickListener = new OnClickListener() {

        //找到点击的点在父容器的位置
        @Override
        public void onClick(View v) {

            int index = mDotsView.indexOfChild(v);
            mViewPager.setCurrentItem(index);
        }
    };

    private void addDots() {

        mDotsView.removeAllViews();
        int count = mPagerAdapter.getCount();
        for (int i = 0; i < count; i++) {

            View view = new View(getContext());
            //点击事件，点击点的时候切换
            view.setOnClickListener(mDotOnClickListener);
            TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                    0, 0, 0);
            view.setBackgroundResource(R.drawable.selector_dot_bc);
            //getLayoutParams会获取不到  null
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
//            params.width = dot_size;
//            params.height = dot_size;
           // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dot_size, dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dot_size, dot_size);//不能getlayparams回报空指针。
            params.rightMargin = dot_size;
           // view.setLayoutParams(params);
            mDotsView.addView(view,params);
        }

    }

    private OnClickListener mImageViewOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            Log.i("Test", "imageView : onClick");
            int currentItem = mViewPager.getCurrentItem();
            String title = mRollItems.get(currentItem).title;
            Log.i("Test", "title:" + title);
        }
    };


    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        private List<ImageView> mCache = new ArrayList<>(1);

        @Override
        public CharSequence getPageTitle(int position) {

            if (mRollItems != null && position < mRollItems.size()) {

                return mRollItems.get(position).title;
            }
            return "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if (mCache.isEmpty()) {

                ImageView imageView = new ImageView(container.getContext());
                //imageView的点击事件
                //imageView的点击事件消费了事件会阻碍ViewPager onTouch事件
                //imageView.setOnClickListener(mImageViewOnClickListener);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mCache.add(imageView);
            }

            //从缓存拿destroyItem方法放入的
            ImageView imageView = mCache.remove(0);

            //下载图片显示到界面
            String url = mRollItems.get(position).url;
            Picasso.with(container.getContext()).load(url).placeholder(R.drawable.ic_launcher).fit().error(R.drawable.error_icon).into(imageView);

            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            //缓存ImageView
            mCache.add((ImageView) object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {

            return mRollItems == null ? 0 : mRollItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }
    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private int mPosition;

    //ViewPager滑动切换的时候被调用
    @Override
    public void onPageSelected(int position) {

        //添加标题切换
        String title = mRollItems.get(position).title;
        mTitleTextView.setText(title);

        //点的切换
        mDotsView.getChildAt(mPosition).setEnabled(true);
        mDotsView.getChildAt(position).setEnabled(false);
        mPosition = position;

//        int childCount = mDotsView.getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//
//            View view = mDotsView.getChildAt(i);
//
//            if (position == i) {
//
//                view.setEnabled(false);
//            } else {
//
//                view.setEnabled(true);
//            }
//        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }
}
