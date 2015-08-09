package com.stv.launcher.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stv.launcher.app.ItemInfo;

public abstract class PagerFragment extends BaseFragment {

    protected abstract View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void bindData(ArrayList<ItemInfo> items);

    protected abstract void release();

    protected String tag;

    protected View mContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContainer == null) {
            mContainer = onInflaterContent(inflater, container, savedInstanceState);
        }
        return mContainer;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if (mContainer != null) {
            ViewGroup parent = (ViewGroup) mContainer.getParent();
            if (parent != null) {
                parent.removeView(mContainer);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

}
