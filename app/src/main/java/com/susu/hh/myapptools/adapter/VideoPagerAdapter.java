package com.susu.hh.myapptools.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.bean.MediaItem;
import com.susu.hh.myapptools.utils.CommenUtils;

import java.util.ArrayList;

/**
 */
public class VideoPagerAdapter extends BaseAdapter {

    private final boolean isVideo;
    private Context context;
    private final ArrayList<MediaItem> mediaItems;

    public VideoPagerAdapter(Context context, ArrayList<MediaItem> mediaItems, boolean isVideo) {
        this.context = context;
        this.mediaItems = mediaItems;
        this.isVideo = isVideo;
    }

    @Override
    public int getCount() {
        return mediaItems.size() == 0 ? 0 : mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mediaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video_pager, null);
            viewHoder = new ViewHoder();
            viewHoder.iv_icon = (com.susu.hh.myapptools.ui.roundedimageview.RoundedImageView) convertView.findViewById(R.id.iv_icon);
            viewHoder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHoder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHoder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);

            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        //根据position得到列表中对应位置的数据
        MediaItem mediaItem = mediaItems.get(position);
        viewHoder.tv_name.setText(mediaItem.getName());
        viewHoder.tv_size.setText(Formatter.formatFileSize(context, mediaItem.getSize()));
        viewHoder.tv_time.setText(CommenUtils.stringForTime((int) mediaItem.getDuration()));
        //得到视频截图。当地址是网络的地址  则会报错
        Bitmap videoThumbnail = getVideoThumbnail(mediaItem.getData(), 60, 60, MediaStore.Images.Thumbnails.MICRO_KIND);
        if(videoThumbnail!= null){
            viewHoder.iv_icon.setImageBitmap(videoThumbnail);
        }
        if (!isVideo) {
            //音频
            viewHoder.iv_icon.setImageResource(R.drawable.music_default_bg);
        }

        return convertView;
    }


    static class ViewHoder {
        com.susu.hh.myapptools.ui.roundedimageview.RoundedImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }

    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}

