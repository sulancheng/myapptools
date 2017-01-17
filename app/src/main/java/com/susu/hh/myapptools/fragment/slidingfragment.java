package com.susu.hh.myapptools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susu.hh.myapptools.R;
import com.susu.hh.myapptools.ui.SlidingDrawer;

/**
 * Created by Administrator on 2016/11/16.
 */
public class slidingfragment extends Fragment implements SlidingDrawer.OnSlideListener {
    private SlidingDrawerContainer slidingDrawerContainer;
    private View expanded_view;
    private View collapsedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.slidertest2,container, false);
        collapsedView = inflate.findViewById(R.id.sliding_drawer_collapsed_view);
        expanded_view = inflate.findViewById(R.id.expanded_view);
        if( slidingDrawerContainer != null){
            slidingDrawerContainer.setDragView(collapsedView);
        };
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    /* @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public void viewCreated(View view, Bundle savedInstanceState) {
        collapsedView = view.findViewById(R.id.sliding_drawer_collapsed_view);
        expanded_view = view.findViewById(R.id.expanded_view);
        if( slidingDrawerContainer != null){
            slidingDrawerContainer.setDragView(collapsedView);
        };
    }*/

    @Override
    public void onSlide(SlidingDrawer slidingDrawer, float currentSlide) {
    //currentSlide==0 或==1判断三种形态
        expanded_view.setAlpha(currentSlide);
        if(currentSlide == 0) {
            collapsedView.setVisibility(View.VISIBLE);
            expanded_view.setVisibility(View.INVISIBLE);

            slidingDrawer.setDragView(collapsedView);
        }else if (currentSlide == 1) {
            collapsedView.setVisibility(View.INVISIBLE);
            expanded_view.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SlidingDrawerContainer) {
            slidingDrawerContainer = (SlidingDrawerContainer) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName() +" must implement " +SlidingDrawerContainer.class.getSimpleName());
        }
    }

    public interface SlidingDrawerContainer {
        void setDragView(View view);
    }
    public void setOnlidingDrawerContainer(SlidingDrawerContainer slidingDrawerContainer){
        this.slidingDrawerContainer = slidingDrawerContainer;
    }
}
