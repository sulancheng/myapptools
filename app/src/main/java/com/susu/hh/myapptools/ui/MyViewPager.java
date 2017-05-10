package com.susu.hh.myapptools.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MyViewPager extends LinearLayout implements OnPageChangeListener, OnClickListener{
	private ViewPager vpPager;
	private List<ImageView> list;
	private int headwith;
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		//充气
		View view = View.inflate(context, R.layout.view_pager,this);
		//this.addView(view);
		init();
	}
	private void init() {
		initDatas();
		initTab();
		initView();
		vpPager.setAdapter(new MyViewPagerAdapter());
		vpPager.setOnPageChangeListener(this);
		
	}
/*	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		vpPager.addOnPageChangeListener(this);
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		vpPager.removeOnPageChangeListener(this);
	}*/
	
	private void initTab() {
		tab = findViewById(R.id.tab);
		LinearLayout  head = (LinearLayout)findViewById(R.id.head);
		DisplayMetrics outMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		//初始化横线的长度
		wm.getDefaultDisplay().getMetrics(outMetrics);
		winWidth = outMetrics.widthPixels;
		MyLog.i("winwid",winWidth+"");
		//获得参数，并设置
		LayoutParams params = (LayoutParams) tab.getLayoutParams();
		int width = head.getWidth();
		headwith = head.getLayoutParams().width;
		MyLog.i("compare head with", headwith +" =="+ width);
		//heads = winWidth;
		//params.width = winWidth/3;
		params.width = winWidth/3-12;//减12测试小的间距.
		tab.setLayoutParams(params);
		
	}
	private void initView() {
		vpPager = (ViewPager) findViewById(R.id.vp);
		friend = (TextView) findViewById(R.id.tv_friend);
		group = (TextView) findViewById(R.id.tv_group);
		setting = (TextView) findViewById(R.id.tv_setting);
		group.setOnClickListener(this);
		friend.setOnClickListener(this);
		setting.setOnClickListener(this);
	}
	private int[] imags = {R.drawable.a,R.drawable.b,R.drawable.c};
	private int winWidth;
	private View tab;
	private TextView friend;
	private TextView group;
	private TextView setting;
	private void initDatas() {
		list = new ArrayList<>();
		for (int i = 0; i < imags.length; i++) {
			ImageView iv = new ImageView(getContext());
			iv.setBackgroundResource(imags[i]);
			list.add(iv);
		}
	}
	class MyViewPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView iv = (ImageView) object;
			container.removeView(iv);
		}
		
	}
	//状态改变
	@Override
	public void onPageScrollStateChanged(int arg0) {
		Log.i("test", "我改变了1");
	}
	//页面滚动
	// 参数2:ViewPager移动的屏幕距离的百分比(从0-1)
	// 参数3:ViewPager移动的实际屏幕的像素值
	@Override//当背景图片完全被切换了，position才会改变。
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		//动态滚动图片。positionOffset换图表示移动了100%。
		LayoutParams params = (LayoutParams) tab.getLayoutParams();
		params.leftMargin = (int) (winWidth/3*positionOffset+position*winWidth/3)+6;//加了6测试小的间距
		//params.leftMargin = positionOffsetPixels/3+position*winWidth/3;
		tab.setLayoutParams(params);
		Log.i("test", "我改变了2");
		MyLog.i("onPageScrolled"," positionOffset = "+positionOffset +"  positionOffsetPixels ="+positionOffsetPixels);
	}
	//页面被选择
	@Override
	public void onPageSelected(int arg0) {
		Log.i("test", "我改变了3");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_friend:
			vpPager.setCurrentItem(0);
			
			break;
		case R.id.tv_group:
			vpPager.setCurrentItem(1);
			break;
		case R.id.tv_setting:
			vpPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}
//注释viewpager的回收使用
//private List<ImageView> ivs;
//	private class MyAdapter extends PagerAdapter {
//		public MyAdapter() {
//			ivs=new ArrayList<ImageView>();
//		}
//		// 公有多少页
//		@Override
//		public int getCount() {
//			return imgs.length;
//		}
//
//		// instantiateItem的方法返回的是Object,系统要判断参数2返回的是否是一个view
//		// ViewPager的每个条目都有一个对应的ItemInfo,里面有position和对应的Object,Object就是instantiateItem返回的,
//		//系统会通过isViewFromObject来判断object是否是一个视图,如果是,则会返回条目信息,用于显示当前数据
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		// 初始化条目
//		// 参数1:ViewPager
//		// 参数2:对应条目的position
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			if(ivs.isEmpty()){
//				ImageView iv=new ImageView(MainActivity.this);
//				ivs.add(iv);
//			}
//			Log.i("test", "集合大小:"+ivs.size());
//			ImageView iv = ivs.remove(0);
//			iv.setImageResource(imgs[position]);
//			container.addView(iv);
//			return iv;
//		}
//
//		// 销毁条目
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			ImageView iv=(ImageView) object;
//			container.removeView(iv);
//			ivs.add(iv);
//		}
//	}
}
