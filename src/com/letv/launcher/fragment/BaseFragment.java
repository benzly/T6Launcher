package com.letv.launcher.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.ItemInfo;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    /** create fragment view */
    protected abstract View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void onFragmentShowChanged(boolean gainShow);

    protected abstract void bindData(ArrayList<ItemInfo> items);

    protected abstract void asyncGetData();

    protected abstract void release();

    protected String tag;

    protected View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = onInflaterContent(inflater, container, savedInstanceState);
        }
        return mContentView;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

}
