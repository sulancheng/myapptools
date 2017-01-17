package com.susu.hh.myapptools.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.utils.CommentViewHolder;

/**
 * Created by Administrator on 2016/10/26.
 */
public class PhotoSelAdapter extends BaseAdapter {
    private String []datas;
    private Context mContext;

    public PhotoSelAdapter(String []datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return datas.length == 0 ? 0 : datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder commentViewHolder = CommentViewHolder.getCommentViewHolder(mContext, convertView, R.layout.sle_dialog_item);
        TypedArray icons = mContext.getResources().obtainTypedArray(R.array.icons_photoselect);
        Drawable drawable = icons.getDrawable(position);
        //.getResources().getStringArray(R.array.array_setphoto  若是字符串数组
        TextView textView = commentViewHolder.getTextView(R.id.tv_content);
        ImageView imageView = commentViewHolder.getImageView(R.id.iv_icon);
        imageView.setImageDrawable(drawable);
        icons.recycle();
        textView.setText(datas[position]);
        return commentViewHolder.convertView;
    }
}
