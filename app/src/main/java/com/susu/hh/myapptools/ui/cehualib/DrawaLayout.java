package com.susu.hh.myapptools.ui.cehualib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.susu.hh.myapptools.R;

/**
 * Created by Administrator on 2016/5/13.
 * <p/>
 * <p/>
 * onTouchEvent ---->mViewDragHelper.processTouchEvent(event);--->mViewDragHelper.dragTo
 * <p/>
 * private void dragTo(int left, int top, int dx, int dy) {
 * int clampedX = left;
 * int clampedY = top;
 * final int oldLeft = mCapturedView.getLeft();
 * final int oldTop = mCapturedView.getTop();
 * if (dx != 0) {
 * <p/>
 * //移动的的位置由使用者的来决定，接口回调的方式
 * clampedX = mCallback.clampViewPositionHorizontal(mCapturedView, left, dx);
 * <p/>
 * //真正移动控件的方法
 * mCapturedView.offsetLeftAndRight(clampedX - oldLeft);
 * }
 * if (dy != 0) {
 * clampedY = mCallback.clampViewPositionVertical(mCapturedView, top, dy);
 * mCapturedView.offsetTopAndBottom(clampedY - oldTop);
 * }
 * <p/>
 * if (dx != 0 || dy != 0) {
 * final int clampedDx = clampedX - oldLeft;
 * final int clampedDy = clampedY - oldTop;
 * 当拖拽的子控件的位置改变的时候调用
 * mCallback.onViewPositionChanged(mCapturedView, clampedX, clampedY,
 * clampedDx, clampedDy);
 * }
 * }
 * <p/>
 * <p/>
 * <p/>
 * 设计：
 * 1.菜单界面不可以拖拽，主面板拖拽
 * 2.限制拖拽范围0~mMaxDragRange
 * 3.拖拽菜单的时候去拖拽主面板的移动：将菜单的拖拽dx作用在主面板
 * 4.当松手的时候打开或关闭菜单：松手的速度和松手的位置
 * <p/>
 * 5.拖拽状态的回调
 * <p/>
 * 6.拖拽的伴随动画：
 * 主面板： 缩放     1 ----->0.8
 * <p/>
 * 菜单：   缩放    0.5---->1
 * 透明度： 0---->1
 * 位置移动：-mWith / 2 ---> 0
 * <p/>
 * 背景：   颜色 ：黑色----->透明
 */
public class DrawaLayout extends FrameLayout {


    private static final String TAG = "DrawaLayout";
    private ViewDragHelper mViewDragHelper;
    private View mMenuView;
    private View mMainPanle;
    private int mWidth;
    private int mHeight;
    private int mMaxDragRange;

    //拖拽的状态
    public enum DragStatus {

        Close, Open, Draging;
    }

    //拖拽的接口回调
    public interface DragStatusListener {

        public void onClose();

        public void onOpen();

        public void onDraging(float percent);

        public boolean swipeLayoutIsOpned();
    }

    private DragStatusListener mDragStatusListener;

    //设置监听的方法
    public void setDragStatusListener(DragStatusListener dragStatusListener) {

        this.mDragStatusListener = dragStatusListener;
    }

    //当前 Close, Open, Draging ？？？由mainPanle距离左边的位置：
    // mainPanneleft : 0 ==>Close
    // mainPanneleft : mMaxDragRange ==>Open

    //保存当前的拖拽状态
    private DragStatus mDragStatus = DragStatus.Close;

    //外部需要获取当前的拖拽状态
    public DragStatus getCurrentDragStatus() {

        return mDragStatus;
    }

    public DragStatus upDragStatus() {

        int left = mMainPanle.getLeft();
        if (left == 0) {

            return DragStatus.Close;
        } else if (left == mMaxDragRange) {

            return DragStatus.Open;
        } else {

            return DragStatus.Draging;
        }

    }

    //拖拽位置改变的时候onViewPositionChanged---->dispatchDragStatus
    private void dispatchDragStatus(float percent) {

        //之前的拖拽状态
        DragStatus preDragStatus = mDragStatus;
        mDragStatus = upDragStatus();

        if (mDragStatusListener == null) return;

        mDragStatusListener.onDraging(percent);

        //如果之前的状态和本次状态一样:就不回调
        if (preDragStatus == mDragStatus) return;

        switch (mDragStatus) {

            case Open:
                mDragStatusListener.onOpen();
                break;
            case Close:
                mDragStatusListener.onClose();
                break;
            case Draging:

                break;
            default:
                break;
        }

    }


    public DrawaLayout(Context context) {
        this(context, null);
    }

    public DrawaLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundResource(R.drawable.bg);


        //ViewDragHelper create(ViewGroup forParent, Callback cb)
        //@param forParent Parent view to monitor 就是要把自己的子控件交给ViewDragHelper去处理
        mViewDragHelper = ViewDragHelper.create(this, mViewDragHelperCallback);
    }

    private ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {

        //在ViewDragHelper对拖拽事件就行拦截的时候：会根据这个方法返回的值来决定是否拦截拖拽事件
        //mViewDragHelper.shouldInterceptTouchEvent(ev);
        @Override
        public int getViewHorizontalDragRange(View child) {

            return mMaxDragRange;
        }


        //当拖拽的时候，调用View child就是拖拽触摸的子控件，返回值决定是否可以拖拽

        /*
                当child是菜单界面的时候返回flase
                当child是主面板的时候可以拖拽 返回true

         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

//            if (child == mMenuView) {
//
//                return false;
//            } else {
//
//                return true;
//            }

            return true;

        }

        //可以在这个方法里面限制拖拽的范围
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            if (child == mMainPanle) {

                left = fixDragRange(left);
            }

            return left;
        }

        //拖拽的菜单的时候去改变主面板的位置
        // dx拖拽移动的值
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            if (changedView == mMenuView) {

                //菜单应该位置限制：不可以移动
                mMenuView.layout(0, 0, mWidth, mHeight);

                Log.i(TAG, "dx:" + dx);

                //移动了主面板
                //mMainPanle.offsetLeftAndRight(dx);
                int oldMainPanleLeft = mMainPanle.getLeft();

                int newMainPanelLeft = oldMainPanleLeft + dx;
                //限制拖拽范围
                newMainPanelLeft = fixDragRange(newMainPanelLeft);

                mMainPanle.layout(newMainPanelLeft, 0, newMainPanelLeft + mWidth, mHeight);
            }

            //伴随动画的执行是与主面板距离左边的距离关系
            int leftL = mMainPanle.getLeft();
            // left : 0  ~ mMaxDragRange ====>  0 ~ 1
            float percent = leftL * 1.0f / mMaxDragRange;

            //位置改变的时候去更新拖拽的状态
            dispatchDragStatus(percent);
            //拖拽的时候执行伴随动画
            dispatchAnimation(percent);
        }

        /*
            当拖拽松手的时候调用：
            float xvel 水平方向的速度：xvel > 速度向右

         */
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            int mainPanleLeft = mMainPanle.getLeft();
            //当速度为0的时候打开右松手的位置来决定
            if (xvel == 0 && mainPanleLeft > mMaxDragRange * 0.5f) {

                open();
                //当速度大于0，向右的拖拽意图
            } else if (xvel > 0) {

                open();
            } else {

                close();
            }
        }
    };

    /*

        拖拽的伴随动画：
 *      主面板： 缩放     1 ----->0.8
 *
 *      菜单：   缩放    0.5---->1
 *              位置移动：-mWith / 2 ---> 0
 *              透明度： 0---->1
 *
 *
 *      背景：   颜色 ：黑色----->透明
     */
    private void dispatchAnimation(float percent) {

//        //伴随动画的执行是与主面板距离左边的距离关系
//        int left = mMainPanle.getLeft();
//        // left : 0  ~ mMaxDragRange ====>  0 ~ 1
//
//        float percent = left * 1.0f / mMaxDragRange;
        //主面板： 缩放     1 ----->0.8
        Float mainPanleScaleValue = CommenUtils.evaluateFloat(percent, 1, 0.8);
        ViewCompat.setScaleX(mMainPanle, mainPanleScaleValue);
        ViewCompat.setScaleY(mMainPanle, mainPanleScaleValue);
        Float mainPanleScaleValuetwo = CommenUtils.evaluateFloat(percent, 0, -8);
        //ObjectAnimator animator = ObjectAnimator.ofFloat(mMainPanle,"rotationY",0,mainPanleScaleValuetwo);
        mMainPanle.setRotationY(mainPanleScaleValuetwo);
        //animator.setDuration(100);
        //animator.start();
        // 菜单：   缩放    0.5---->1
        Float menuScaleValue = CommenUtils.evaluateFloat(percent, 0.5, 1);
        ViewCompat.setScaleX(mMenuView, menuScaleValue);
        ViewCompat.setScaleY(mMenuView, menuScaleValue);

        //菜单： 位置移动：-mWith / 2 ---> 0
        Float menuTraslationValue = CommenUtils.evaluateFloat(percent, -mWidth * 0.5f, 0);
        ViewCompat.setTranslationX(mMenuView, menuTraslationValue);

        //菜单：透明度： 0---->1
        Float menuAlphaValue = CommenUtils.evaluateFloat(percent, 0, 1);
        ViewCompat.setAlpha(mMenuView, menuAlphaValue);

        //背景：   颜色 ：黑色----->透明
        //  public void setColorFilter(@ColorInt int color, @NonNull PorterDuff.Mode mode)
        //backColorChangeValue  : Color.BLACK ---->Color.TRANSPARENT
        Integer backColorChangeValue = (Integer) CommenUtils.evaluateColor(percent, Color.BLACK, Color.RED);
        //合成颜色模式：PorterDuff.Mode:SRC_OVER 颜色覆盖
        getBackground().setColorFilter(backColorChangeValue, PorterDuff.Mode.SRC_OVER);

    }


    //View :invalidate ---->  onDraw() ---->computeScroll()
    @Override
    public void computeScroll() {

        if (mViewDragHelper.continueSettling(true)) {

            ViewCompat.postInvalidateOnAnimation(this);//-----调用：computeScroll()
        }
    }

    public void close() {

        //mMainPanle.layout(0, 0, mWidth, mHeight);
        if (mViewDragHelper.smoothSlideViewTo(mMainPanle, 0, 0)) {

            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void open() {

        /*

          View :  public void computeScroll() {} 当View重新绘制的时候调用这个方法，通常控件跑动画的时候需要用这个方法
         */

        //mMainPanle.layout(mMaxDragRange, 0, mMaxDragRange + mWidth, mHeight);

        //Animate the view <code>child</code> to the given (left, top) position.
        //mViewDragHelper平滑的去改变控件的位置
        //public boolean smoothSlideViewTo(View child, int finalLeft, int finalTop)
        //true if animation should continue through {@link #continueSettling(boolean)} calls
        //如果这个方法返回true代表动画没有走完，需要调用mViewDragHelper--->continueSettling方法继续
        //public boolean continueSettling(boolean deferCallbacks)
        //@return true if settle is still in progress
        //如果返回true动画还没有走完，需要继续调用这个方法continueSettling
        if (mViewDragHelper.smoothSlideViewTo(mMainPanle, mMaxDragRange, 0)) {

            //while (mViewDragHelper.continueSettling(false)) ;
            // mViewDragHelper.continueSettling(false)
            //invalidate(); <======> ViewCompat.postInvalidateOnAnimation(this);
            ViewCompat.postInvalidateOnAnimation(this); ///--->触发public void computeScroll()
        }
    }

    /*{

         限制拖拽的范围： 0 ~ mMaxDragRange
     */
    private int fixDragRange(int left) {

        if (left < 0) {

            return 0;
        }

        if (left > mMaxDragRange) {

            return mMaxDragRange;
        }


        return left;
    }


    /*

        需要判断当前的事件是否是一恶搞拖拽事件：如果是拖拽事件就拦截，不是不拦截
        这个拖拽事件的判断由mViewDragHelper可以判断
        mViewDragHelper.shouldInterceptTouchEvent(ev);

        当有侧拉删除打开的时候不拦截事件就是当前事件是滑动事件：接口回调
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        /*
            如果当有SwipeLayout是打开的就不去拦截事件
         */
        if (mDragStatusListener != null && mDragStatusListener.swipeLayoutIsOpned()) {

            return false;
        }

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }


    //如果当前的事件是一个拖拽事件就拦截了，这个方法就被调用：把拖拽事件交给mViewDragHelper去处理
    //mViewDragHelper.processTouchEvent(event);
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            mViewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
        当xml 加载完毕之后调用这个方法：
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();

        if (childCount != 2) {

            throw new IllegalArgumentException("child view count must be 2");
        }

        mMenuView = getChildAt(0);
        mMainPanle = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mMaxDragRange = (int) (mWidth * 0.3f);
    }
}
