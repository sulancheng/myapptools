package com.susu.hh.myapptools.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * Created by Administrator on 2016/11/2.
 */
public class FcfViewGroupAdapter extends FancyCoverFlowAdapter {
    private int[] images;

    public FcfViewGroupAdapter(int[] images) {

        this.images = images;
    }

    public FcfViewGroupAdapter() {

    }


    // =============================================================================
    // Private members
    // =============================================================================


    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Integer getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        CustomViewGroup customViewGroup = null;

        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 600));
        }

        customViewGroup.getImageView().setImageResource(this.getItem(i));
        //customViewGroup.getTextView().setText(String.format("Item %d", i));

        return customViewGroup;
    }

    class CustomViewGroup extends LinearLayout {

        // =============================================================================
        // Child views
        // =============================================================================

        //private TextView textView;

        private ImageView imageView;

        private Button button;

        // =============================================================================
        // Constructor
        // =============================================================================

        CustomViewGroup(Context context) {
            super(context);

            this.setOrientation(VERTICAL);

            //this.textView = new TextView(context);
            this.imageView = new ImageView(context);
            this.button = new Button(context);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //this.textView.setLayoutParams(layoutParams);
            this.imageView.setLayoutParams(layoutParams);
            //this.button.setLayoutParams(layoutParams);

            //this.textView.setGravity(Gravity.CENTER);

            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.imageView.setAdjustViewBounds(true);

            this.button.setText("立即购买");
            // this.button.setBackgroundResource(R.mipmap.ic_launcher);
            this.button.setTextColor(Color.WHITE);
            //this.button.setScaleY(0.8f);
            this.button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //*Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://davidschreiber.github.com/FancyCoverFlow"));
                    //view.getContext().startActivity(i);*//*
                    //view.getContext().startActivity(new Intent(view.getContext(), ProductListActivity.class));
                }
            });

            //this.addView(this.textView);
            this.addView(this.imageView);
            //this.addView(this.button);
        }
        Button getButton(){
            return button;
        }
        ImageView getImageView() {
            return imageView;
        }
    }
}
