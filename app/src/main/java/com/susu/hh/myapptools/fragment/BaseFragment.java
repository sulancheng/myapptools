package com.susu.hh.myapptools.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.susu.hh.myapptools.R;

/**
 * Created by Administrator on 2016/10/25.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private View view;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        if(null == view){
            view = inflater.inflate(R.layout.fragment_base, container, false);
            FrameLayout fl_container = (FrameLayout) view.findViewById(R.id.trplace);
            fl_container.addView(createView(inflater,container,savedInstanceState));
            viewCreated(view,savedInstanceState);
        }
        return view;
    }

   /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }*/
    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void viewCreated(View view, Bundle savedInstanceState);
}
