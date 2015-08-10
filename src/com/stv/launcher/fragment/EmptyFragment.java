package com.stv.launcher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.R;

public class EmptyFragment extends PagerFragment {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, null);
    }

    @Override
    protected boolean onFocusRequested(int requestDirection) {
        return false;
    }

    @Override
    protected void onFragmentShowChanged(boolean gainShow) {

    }

}
