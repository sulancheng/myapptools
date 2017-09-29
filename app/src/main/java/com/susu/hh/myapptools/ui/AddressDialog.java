package com.susu.hh.myapptools.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.CommentViewHolder;

public class AddressDialog extends Dialog implements OnItemClickListener {
	private Context mcontext;
	private GridView lvStyle;
	private int[] pics = {R.drawable.guide_bt_001,R.drawable.guide_bt_002,R.drawable.guide_bt_003,R.drawable.guide_bt_004};
	private String[] items = new String[] { "半透明", "活力橙", "二维码", "金属灰"};

	//private int[] bgs = new int[] { R.drawable.address_normal, R.drawable.address_orangle, R.drawable.address_blue, R.drawable.address_gray, R.drawable.address_green };

	public AddressDialog(Context context) {
		super(context, R.style.AddressDialog);
		mcontext = context;
	}
/*	//获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
		DisplayMetrics  dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenWidth = dm.widthPixels;

		int screenHeight = dm.heightPixels;
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_address_style);
		LayoutParams params = getWindow().getAttributes();
		//params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; // 向下对齐
		params.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL; // 向下对齐
		lvStyle = (GridView) findViewById(R.id.lvStyle);
		lvStyle.setAdapter(new StyleAdapter());
		lvStyle.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position){
			case 2:
				break;
		}
		dismiss();
	}

	private void erweima() {

	}

	class StyleAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return items.length == 0? 0 :items.length;
		}

		@Override
		public Object getItem(int position) {

			return items[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CommentViewHolder commentViewHolder = CommentViewHolder.getCommentViewHolder(mcontext, convertView, R.layout.gridview_list);
			ImageView iv_pho = commentViewHolder.getImageView(R.id.iv_pho);
			TextView tv_text = commentViewHolder.getTextView(R.id.tv_text);
			iv_pho.setImageResource(pics[position]);
			tv_text.setText(items[position]);
			return commentViewHolder.convertView;
		}
	}

	/*@Override
	public void show() {
		super.show();
		*//**
		 * 设置宽度全屏，要设置在show的后面    试验可以全屏
		 *//*
		LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.gravity=Gravity.BOTTOM;
		layoutParams.width= LayoutParams.MATCH_PARENT;
		layoutParams.height= LayoutParams.MATCH_PARENT;

		getWindow().getDecorView().setPadding(0, 0, 0, 0);

		getWindow().setAttributes(layoutParams);

	}*/
}
