package com.letv.launcher.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.ItemInfo;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    protected abstract View onInflaterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void onFragmentShowChange(boolean isShow);

    protected abstract void asyncGetData();

    protected abstract void bindData(ArrayList<ItemInfo> items);

    protected String tag;

    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = onInflaterView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
    }

}
