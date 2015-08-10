package com.stv.launcher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerFragment extends BaseFragment {

    protected String tag;

    protected View mContainer;

    protected abstract View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

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

}
