package com.susu.hh.myapptools.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.CommentViewHolder;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MyJGGridAdapter extends BaseAdapter {
    private Context mContext;
    private String[] img_text;
    private int[] imgs;

    /*public String[] img_text = { "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
            "当面付", "亲密付", "机票", };
    public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
            R.drawable.app_phonecharge, R.drawable.app_creditcard,
            R.drawable.app_movie, R.drawable.app_lottery,
            R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane };*/

    public MyJGGridAdapter(Context mContext, String[] img_text, int[] imgs) {
        super();
        this.mContext = mContext;
        this.img_text = img_text;
        this.imgs = imgs;
    }

    //设置下来刷新数据的方法
    public void upDateDatas(Context mContext, String[] img_text, int[] imgs) {
        this.mContext = mContext;
        this.img_text = img_text;
        this.imgs = imgs;
        //刷新
        notifyDataSetChanged();
    }

    //加载更多数据
    public void loadMoreDatas(String[] img_text, int[] imgs) {
        if (img_text == null && imgs == null) {
            this.img_text = img_text;
            this.imgs = imgs;
        } else {
            //this.mNewsPagerItemInfos.addAll(mNewsPagerItemInfos);//伪代码应用于集合 这里只是只是总结。
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length == 0 ? 0 : img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return img_text[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //自定义holder
        CommentViewHolder commentViewHolder = CommentViewHolder.getCommentViewHolder(mContext, convertView, R.layout.jg_grid_item);
        /*if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }*/
        TextView tv = commentViewHolder.getTextView(R.id.tv_item);
        //TextView tv = MyGridViewHolder.get(convertView, R.id.tv_item);
        //ImageView iv = MyGridViewHolder.get(convertView, R.id.iv_item);
        ImageView iv = commentViewHolder.getImageView(R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return commentViewHolder.convertView;//记住这里不要返回错误。
    }
}
